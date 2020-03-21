package com.leaf.jobs.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据统计
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsVo {

    private Integer taskCount;

    /**
     * 执行次数
     */
    private Integer invokeCount;

    /**
     * 失败次数
     */
    private Integer errorCount;

    /**
     * 成功执行率
     */
    private BigDecimal successPercent;

    //下面四个用于echarts

    private List<Integer> counts;

    /**
     * 成功次数列表
     */
    private List<Integer> successCounts;

    /**
     * 失败次数列表
     */
    private List<Integer> errorCounts;

    /**
     * 时间列表
     */
    private List<String> dateList;
}
