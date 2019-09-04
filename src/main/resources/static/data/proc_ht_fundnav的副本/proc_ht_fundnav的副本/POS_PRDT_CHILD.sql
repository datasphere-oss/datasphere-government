-- Create table
create table POS_PRDT_CHILD
(
  id               NUMERIC(18) not null,
  manager_id       NUMERIC(18) not null,
  parent_prdt_code VARCHAR(20) not null,
  prdt_code        VARCHAR(20) not null,
  prdt_name        VARCHAR(100) not null,
  bus_type         NUMERIC(1),
  create_date      DATE,
  memo             VARCHAR(200)
);
-- Add comments to the table 
comment on table POS_PRDT_CHILD
  is '子产品信息表   非母子产品也必须存在此表中';
-- Add comments to the columns 
comment on column POS_PRDT_CHILD.manager_id
  is '所属管理人';
comment on column POS_PRDT_CHILD.parent_prdt_code
  is '母产品代码';
comment on column POS_PRDT_CHILD.prdt_code
  is '产品代码';
comment on column POS_PRDT_CHILD.prdt_name
  is '产品名称';
comment on column POS_PRDT_CHILD.bus_type
  is '1-外包 2-托管 3-外包+托管';
comment on column POS_PRDT_CHILD.create_date
  is '成立日期';
comment on column POS_PRDT_CHILD.memo
  is '描述';
-- Create/Recreate primary, unique and foreign key constraints 
alter table POS_PRDT_CHILD
  add constraint PK_POS_PRDT_CHILD primary key (ID);
