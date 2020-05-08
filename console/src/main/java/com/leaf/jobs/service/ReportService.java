package com.leaf.jobs.service;

import com.leaf.jobs.model.Response;
import com.leaf.jobs.model.StatsVo;

public interface ReportService {

    /**
     * 数据统计
     *
     * @return
     */
    Response<StatsVo> stats();

    /**
     * echarts图
     *
     * @return
     */
    Response<StatsVo> charts();
}
