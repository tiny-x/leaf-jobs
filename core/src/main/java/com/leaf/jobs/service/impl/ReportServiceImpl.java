package com.leaf.jobs.service.impl;

import com.leaf.jobs.dao.mapper.TaskInvokeRecordMapper;
import com.leaf.jobs.dao.mapper.TaskMapper;
import com.leaf.jobs.dao.model.TaskInvokeRecord;
import com.leaf.jobs.model.Response;
import com.leaf.jobs.model.StatsVo;
import com.leaf.jobs.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private TaskInvokeRecordMapper taskInvokeRecordMapper;

    /**
     *  @Cacheable 不支持失效时间
     *
     * @return
     */
    @Cacheable(value = "statsVo")
    @Override
    public Response<StatsVo> stats() {
        Integer errorCount = taskInvokeRecordMapper.selectErrorCount();
        Integer count = taskInvokeRecordMapper.selectCount(null);
        Integer taskCount = taskMapper.selectCount(null);

        StatsVo statsVo = StatsVo.builder()
                .invokeCount(count)
                .errorCount(errorCount)
                .taskCount(taskCount)
                .successPercent(BigDecimal.valueOf((count - errorCount) * 100 / count).setScale(2, RoundingMode.HALF_UP))
                .build();
        return Response.ofSuccess(statsVo);
    }

    @Override
    public Response<StatsVo> charts() {
        List<TaskInvokeRecord> dayOfInvokeCount = taskInvokeRecordMapper.dayOfInvokeCount();

        List<Integer> counts = new ArrayList<>();
        List<Integer> errorCounts = new ArrayList<>();
        List<Integer> successCounts = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (TaskInvokeRecord taskInvokeRecord : dayOfInvokeCount) {
            counts.add(taskInvokeRecord.getCount());
            successCounts.add(taskInvokeRecord.getSuccessCount());
            errorCounts.add(taskInvokeRecord.getErrorCount());
            dateList.add(simpleDateFormat.format(taskInvokeRecord.getInvokeDate()));
        }

        StatsVo statsVo = StatsVo.builder()
                .counts(counts)
                .successCounts(successCounts)
                .dateList(dateList)
                .errorCounts(errorCounts)
                .build();

        return Response.ofSuccess(statsVo);
    }
}
