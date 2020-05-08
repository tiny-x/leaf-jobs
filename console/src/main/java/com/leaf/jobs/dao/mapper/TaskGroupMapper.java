package com.leaf.jobs.dao.mapper;

import com.leaf.jobs.dao.model.TaskGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TaskGroupMapper extends Mapper<TaskGroup> {

    int updateOnlineAddress(@Param("groupName") String groupName, @Param("onlineAddress") String onlineAddress);
}