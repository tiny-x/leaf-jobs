package com.leaf.jobs.dao.mapper;

import com.leaf.jobs.dao.model.Task;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface TaskMapper extends Mapper<Task> {

}