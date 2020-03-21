package com.leaf.jobs.web.controller;

import com.leaf.jobs.model.Response;
import com.leaf.jobs.model.StatsVo;
import com.leaf.jobs.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报表统计
 */
@Slf4j
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * 数据统计
     *
     * @return
     */
    @RequestMapping("/report/stats")
    public Response<StatsVo> stats() {
        return reportService.stats();
    }

    /**
     * echarts图
     *
     * @return
     */
    @RequestMapping("/report/charts")
   public Response<StatsVo> charts() {
       return reportService.charts();
   }
}
