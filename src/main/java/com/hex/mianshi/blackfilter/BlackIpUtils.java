package com.hex.mianshi.blackfilter;
import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

@Slf4j
public class BlackIpUtils {

    /**
     * 布隆过滤器，用于高效判断IP是否在黑名单中
     * 使用基于BitMap位图实现的布隆过滤器，具有快速查询、内存效率高的特点
     */
    private static BitMapBloomFilter bloomFilter;
    // 判断 ip 是否在黑名单内

    public static boolean isBlackIp(String ip) {
        return bloomFilter.contains(ip);
    }

    // 重建 ip 黑名单
    public static void rebuildBlackIp(String configInfo) {
        if (StrUtil.isBlank(configInfo)) {
            configInfo = "{}";
        }
        // 解析 yaml 文件
        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(configInfo, Map.class);
        // 获取 ip 黑名单
        List<String> blackIpList = (List<String>) map.get("blackIpList");
        // 加锁防止并发
        synchronized (BlackIpUtils.class) {
            if (CollectionUtil.isNotEmpty(blackIpList)) {
                // 初始化位图单位是MB 对应1024*1024*8位
                BitMapBloomFilter bitMapBloomFilter = new BitMapBloomFilter(10);
                for (String ip : blackIpList) {
                    bitMapBloomFilter.add(ip);
                }
                bloomFilter = bitMapBloomFilter;
            } else {
                bloomFilter = new BitMapBloomFilter(100);
            }
        }
    }
}
