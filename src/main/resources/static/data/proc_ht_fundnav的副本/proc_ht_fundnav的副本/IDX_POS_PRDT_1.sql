-- Create table
create table POS_PRDT
(
  id                  NUMERIC(18) not null,
  manager_id          NUMERIC(18) not null,
  prdt_code           VARCHAR(20) not null,
  prdt_name           VARCHAR(100) not null,
  bus_type            NUMERIC(1),
  create_date         DATE,
  bulletin_pub_flag   NUMERIC(1),
  nav_cycle           NUMERIC(1),
  have_child_flag     NUMERIC(1),
  prdt_convert_code   VARCHAR(20),
  regular_report_flag NUMERIC(1),
  nav_flag            NUMERIC(1),
  duration_period     VARCHAR(50),
  trustee_name        VARCHAR(200),
  share_regist_org    VARCHAR(200)
);
-- Add comments to the table 
comment on table POS_PRDT
  is '产品基本信息表';
-- Add comments to the columns 
comment on column POS_PRDT.manager_id
  is '所属管理人';
comment on column POS_PRDT.prdt_code
  is '产品代码';
comment on column POS_PRDT.prdt_name
  is '产品名称';
comment on column POS_PRDT.bus_type
  is '1-外包 2-托管 3-外包+托管';
comment on column POS_PRDT.create_date
  is '成立日期';
comment on column POS_PRDT.bulletin_pub_flag
  is '是否可由管理人发公告 1：是 0：否 默认否。提供按钮修改。如果否，管理人可以新增公告，但不能展示给投资者';
comment on column POS_PRDT.nav_cycle
  is '产品净值周期1：日 2：周 3：月';
comment on column POS_PRDT.have_child_flag
  is '1：母子产品  0：非母子产品';
comment on column POS_PRDT.prdt_convert_code
  is '产品转换对应关系表';
comment on column POS_PRDT.regular_report_flag
  is '1：是 0：否';
comment on column POS_PRDT.nav_flag
  is '1：是 0：否';
comment on column POS_PRDT.duration_period
  is '存续期';
comment on column POS_PRDT.trustee_name
  is '托管人名称';
comment on column POS_PRDT.share_regist_org
  is '份额登记机构';
-- Create/Recreate indexes 
create index IDX_POS_PRDT_1 on POS_PRDT (MANAGER_ID, PRDT_CODE);
-- Create/Recreate primary, unique and foreign key constraints 
alter table POS_PRDT
  add constraint PK_POS_PRDT primary key (ID);
