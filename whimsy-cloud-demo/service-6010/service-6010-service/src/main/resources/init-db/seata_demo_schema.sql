create database if not exists demo_cloud;

create table if not exists test_seata6010
(
    id    bigint primary key auto_increment comment 'id',
    money decimal(12, 2) null comment '金额'
)


