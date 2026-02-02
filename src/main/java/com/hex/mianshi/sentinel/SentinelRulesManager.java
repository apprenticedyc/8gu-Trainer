package com.hex.mianshi.sentinel;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

/**
 * Sentinel 限流熔断规则管理器
 */
@Component
public class SentinelRulesManager {
    // 利用PostConstruct在Manager实例完成依赖注入后自动触发, 执行初始化
    @PostConstruct
    public void initRules() throws Exception {
        initFlowRules();
        initDegradeRules();
    }

    /**
     * 1. 初始化限流规则
     * 根据热点参数限流
     */
    public void initFlowRules() {
        ParamFlowRule rule = new ParamFlowRule("listQuestionVOByPage").setParamIdx(0) // 对第0个热点参数限流，即 IP 地址
                .setCount(60) // 每分钟最多 60 次
                .setDurationInSec(60); // 一次统计周期为 60 秒
        // 加载规则
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 初始化降级规则
     */
    public void initDegradeRules() {
        // 1. 熔断条件: 慢查询
        DegradeRule slowCallRule = new DegradeRule("listQuestionVOByPage").setGrade(CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType())
                .setCount(0.2) // 慢调用比例大于 20%
                .setTimeWindow(60) // 熔断持续时间 60 秒
                .setStatIntervalMs(30 * 1000) // 统计时长 30 秒
                .setMinRequestAmount(10) // 最小请求数
                .setSlowRatioThreshold(3); // 响应时间超过 3 秒
        // 2. 熔断条件: 错误率
        DegradeRule errorRateRule = new DegradeRule("listQuestionVOByPage").setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
                .setCount(0.1) // 异常率大于 10%
                .setTimeWindow(60) // 熔断持续时间 60 秒
                .setStatIntervalMs(30 * 1000) // 统计时长 30 秒
                .setMinRequestAmount(10); // 最小请求数
        // 加载规则
        DegradeRuleManager.loadRules(Arrays.asList(slowCallRule, errorRateRule));
    }
}