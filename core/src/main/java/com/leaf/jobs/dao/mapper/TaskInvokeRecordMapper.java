package com.leaf.jobs.dao.mapper;

import com.leaf.jobs.dao.model.TaskInvokeRecord;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface TaskInvokeRecordMapper extends Mapper<TaskInvokeRecord> {

    /**
     * 查询执行记录
     *
     * @param taskInvokeRecord
     * @return
     */
    List<TaskInvokeRecord> selectTaskInvokeRecord(TaskInvokeRecord taskInvokeRecord);

    /**
     * 每天成功或者失败的次数
     *
     * @return
     */
    List<TaskInvokeRecord> dayOfInvokeCount();

    /**
     * 失败的次数
     * @return
     */
    Integer selectErrorCount();
}