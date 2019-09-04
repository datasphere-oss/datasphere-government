-- Create table
create table RUN_RESULT
(
  id          NUMERIC not null,
  pkg_name    VARCHAR(100),
  remark      VARCHAR(100),
  excdate     DATE,
  begin_time  DATE,
  end_time    DATE,
  rowcn       NUMERIC,
  err_message TEXT,
  flag        CHAR(1),
  updateid    TIMESTAMP(6)
);
-- Add comments to the table 
comment on table RUN_RESULT
  is '程序执行情况日志表';
-- Add comments to the columns 
comment on column RUN_RESULT.id
  is 'id';
comment on column RUN_RESULT.pkg_name
  is '鍖呭悕';
comment on column RUN_RESULT.excdate
  is '鎵ц鏃堕棿';
comment on column RUN_RESULT.end_time
  is '缁撴潫鏃堕棿';
comment on column RUN_RESULT.err_message
  is '閿欒淇℃伅';
comment on column RUN_RESULT.flag
  is '鏍囪瘑';
comment on column RUN_RESULT.updateid
  is '变动标识';
-- Create/Recreate indexes 
create index IUM33593732 on RUN_RESULT (UPDATEID);
-- Create/Recreate primary, unique and foreign key constraints 
alter table RUN_RESULT
  add constraint PK_RUN_RESULT_ID primary key (ID);
