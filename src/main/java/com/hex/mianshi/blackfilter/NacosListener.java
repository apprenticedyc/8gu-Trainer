package com.hex.mianshi.blackfilter;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Nacos配置监听器，监听IP黑名单配置变化并自动更新布隆过滤器
 *
 * 执行流程：
 * 1. 启动时：注册Nacos监听器，初始化黑名单
 * 2. 运行时：监听配置变化，异步重建布隆过滤器
 *
 * 使用方式：
 * - 在Nacos配置中设置blackIpList字段
 */
@Slf4j
@Component
public class NacosListener implements InitializingBean {

    @NacosInjected // 注入配置服务
    private ConfigService configService;

    @Value("${nacos.config.data-id}") // 读取spring配置
    private String dataId;

    @Value("${nacos.config.group}")
    private String group;

    // 启动时执行初始化方法,相当于 @PostConstruct 注解的另一种实现方式
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("nacos 监听器启动");
        // 获取nacos上的配置并注册监听器监听变更
        String config = configService.getConfigAndSignListener(dataId, group, 3000L, new Listener() {

            final ThreadFactory threadFactory = new ThreadFactory() {
                private final AtomicInteger poolNumber = new AtomicInteger(1);
                @Override
                public Thread newThread(@NotNull Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("refresh-ThreadPool" + poolNumber.getAndIncrement());
                    return thread;
                }
            };
            // 创建线程池，通过线程池异步处理黑名单变化的逻辑
            final ExecutorService executorService = Executors.newFixedThreadPool(1, threadFactory);

            // 实现Listener接口的两个注解
            @Override
            public Executor getExecutor() {
                return executorService;
            }

            // 监听后续黑名单变化
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("监听到配置信息变化：{}", configInfo);
                BlackIpUtils.rebuildBlackIp(configInfo);
            }
        });
        // 初始化黑名单
        BlackIpUtils.rebuildBlackIp(config);
    }
}
