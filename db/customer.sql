create table t_task
(
    TASK_ID bigint not null comment '服务ID'
        primary key,
    TASK_GROUP varchar(128) null comment '服务组',
    TASK_SERVICE_NAME varchar(256) null comment '服务名称',
    TASK_METHOD_NAME varchar(128) null comment '服务方法名称',
    TASK_NAME varchar(64) null comment '任务名称',
    PARAMS varchar(64) null,
    CRON varchar(64) null,
    TASK_STATUS varchar(32) null comment '运行状态',
    TIME_OUT int null comment '超时时间（毫秒）',
    ONLINE_ADDRESS varchar(32) null comment '在线服务地址',
    PRINCIPAL varchar(64) null,
    RISK_EMAIL varchar(256) null,
    REMARK varchar(256) null,
    CREATOR varchar(20) null,
    UPDATER varchar(20) null,
    CREATE_DATE datetime default CURRENT_TIMESTAMP null,
    UPDATE_DATE datetime default CURRENT_TIMESTAMP null,
    COMMENTS varchar(255) null
)
    comment '任务列表';


create table if not exists t_task_invoke_record
(
    RECORD_ID bigint null comment '记录id',
    TASK_ID mediumtext null comment '服务组',
    INVOKE_DATE datetime null,
    INVOKE_IP varchar(32) null,
    INVOKE_RESULT varchar(32) null,
    COMPLETE_DATE datetime null,
    RESPONSE varchar(256) null,
    STACK_TRACE varchar(512) null,
    CREATOR varchar(20) null,
    UPDATER varchar(20) null,
    CREATE_DATE datetime default CURRENT_TIMESTAMP null,
    UPDATE_DATE datetime default CURRENT_TIMESTAMP null,
    COMMENTS varchar(255) null
)
    comment '执行记录';






