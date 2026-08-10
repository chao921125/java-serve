package com.cc.xxljob;

import com.xxl.job.core.context.XxlJobHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * XXL-Job 分片工具类
 * 用于大数据量批处理场景的分片执行
 *
 * @author cc
 */
@Slf4j
public final class ShardingUtil {

    private ShardingUtil() {}

    /**
     * 获取当前分片号（从 0 开始）
     */
    public static int getShardIndex() {
        return XxlJobHelper.getShardIndex();
    }

    /**
     * 获取总分片数
     */
    public static int getShardTotal() {
        return XxlJobHelper.getShardTotal();
    }

    /**
     * 判断当前分片是否应该处理该数据项
     * 按 ID 哈希均匀分配
     *
     * @param id 数据项 ID
     * @return true-当前分片负责处理
     */
    public static boolean isMyShard(long id) {
        int shardTotal = getShardTotal();
        if (shardTotal <= 1) return true;
        return (int) (id % shardTotal) == getShardIndex();
    }

    /**
     * 按分片号过滤 ID 列表
     *
     * @param ids 全部 ID 列表
     * @return 当前分片负责处理的 ID 列表
     */
    public static List<Long> filterByShard(List<Long> ids) {
        int shardTotal = getShardTotal();
        if (shardTotal <= 1) return ids;

        int shardIndex = getShardIndex();
        List<Long> result = new ArrayList<>();
        for (Long id : ids) {
            if ((int) (id % shardTotal) == shardIndex) {
                result.add(id);
            }
        }
        return result;
    }

    /**
     * 获取分片信息字符串（用于日志）
     */
    public static String getShardInfo() {
        return String.format("shard[%d/%d]", getShardIndex() + 1, getShardTotal());
    }

    /**
     * 记录分片日志（带分片信息前缀）
     */
    public static void log(String format, Object... args) {
        String msg = String.format(format, args);
        log.info("{} {}", getShardInfo(), msg);
        XxlJobHelper.log(getShardInfo() + " " + msg);
    }

    /**
     * 任务执行成功回调
     */
    public static void handleSuccess(String message) {
        XxlJobHelper.handleSuccess(message);
    }

    /**
     * 任务执行失败回调
     */
    public static void handleFail(String message) {
        XxlJobHelper.handleFail(message);
    }

    /**
     * 任务超时回调
     */
    public static void handleTimeout(String message) {
        XxlJobHelper.handleTimeout(message);
        XxlJobHelper.log("[TIMEOUT] " + message);
    }
}
