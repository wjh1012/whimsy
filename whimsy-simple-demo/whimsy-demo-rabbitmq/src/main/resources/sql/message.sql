-- auto-generated definition
create table t_published_message
(
    id          bigint               not null comment 'id'
        primary key,
    source_key  varchar(50)          not null comment '消息生产者key',
    exchange    varchar(200)         not null comment '交换机',
    routing_key varchar(200)         not null comment '路由key',
    content     longtext             not null comment '消息内容',
    retries     int        default 0 not null comment '重试次数',
    expiry      timestamp            null comment '消息过期时间: null为不过期',
    type        tinyint              not null comment '消息发送的类型: 1::健康打卡',
    status      varchar(40)          not null comment '发送状态，成功则消息ack成功，其他状态都要重试',
    del_flag    tinyint(1) default 0 not null comment '逻辑删除',
    create_time datetime             null comment '创建时间',
    create_by   bigint               null comment '创建人id',
    update_time datetime             null comment '修改时间',
    update_by   bigint               null comment '修改人id',
    remark      varchar(500)         null comment '备注'
)
    comment '发布的消息';

create index t_published_message_source_key_index
    on t_published_message (source_key);

create index t_published_message_type_status_expiry_retries_index
    on t_published_message (type, status, expiry, retries);


-- auto-generated definition
create table t_receive_message
(
    id           bigint                 not null comment 'id'
        primary key,
    source_key   varchar(50)            not null comment '消息生产者key',
    business_key varchar(50) default '' null comment '业务key',
    content      longtext               not null comment '消息内容',
    retries      int         default 0  not null comment '重试次数',
    type         tinyint                not null comment '消息发送的类型: 1::发送待办 2::完成待办 3::删除待办 4::发送通知 5::删除通知',
    status       tinyint(1)             not null comment '处理状态: 0::失败 1::成功',
    del_flag     tinyint(1)  default 0  not null comment '逻辑删除',
    create_time  datetime               null comment '创建时间',
    create_by    bigint                 null comment '创建人id',
    update_time  datetime               null comment '修改时间',
    update_by    bigint                 null comment '修改人id',
    remark       varchar(500)           null comment '备注'
)
    comment '接收的消息';

create index t_receive_message_source_key_index
    on t_receive_message (source_key);

create index t_receive_message_type_status_retries_index
    on t_receive_message (type, status, retries);

