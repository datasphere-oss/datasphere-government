-- Create table
create table PUB_TRADINGDAY
(
  id             NUMERIC(18) not null,
  tradingday     DATE,
  exchangecode   NUMERIC(10) not null,
  nexttradingday DATE,
  lasttradingday DATE,
  ntradingdaytm  NUMERIC(2),
  ntradingdaytw  NUMERIC(1),
  ndaytw         NUMERIC(1),
  istradingday   CHAR(1),
  isweekend      CHAR(1),
  ismonthend     CHAR(1),
  isquarterend   CHAR(1),
  isyearend      CHAR(1),
  entrytime      DATE not null,
  updatetime     DATE not null,
  groundtime     DATE not null,
  updateid       NUMERIC(18) not null,
  resourceid     VARCHAR(20) not null,
  recordid       VARCHAR(50),
  normalday      DATE not null,
  lwtradingday   DATE,
  lmtradingday   DATE,
  lytradingday   DATE,
  b5tradingday   DATE,
  b10tradingday  DATE,
  b1mradingday   DATE,
  b3mtradingday  DATE,
  b6mtradingday  DATE
);
-- Add comments to the table 
comment on table PUB_TRADINGDAY
  is '交易日期表';
-- Add comments to the columns 
comment on column PUB_TRADINGDAY.id
  is 'ID';
comment on column PUB_TRADINGDAY.tradingday
  is '最近一个交易日';
comment on column PUB_TRADINGDAY.exchangecode
  is '证券市场代码';
comment on column PUB_TRADINGDAY.nexttradingday
  is '下一交易日';
comment on column PUB_TRADINGDAY.lasttradingday
  is '上一交易日';
comment on column PUB_TRADINGDAY.ntradingdaytm
  is '本月第N个交易日';
comment on column PUB_TRADINGDAY.ntradingdaytw
  is '本周第N个交易日';
comment on column PUB_TRADINGDAY.ndaytw
  is '本周第N日';
comment on column PUB_TRADINGDAY.istradingday
  is '是否交易日';
comment on column PUB_TRADINGDAY.isweekend
  is '是否本周最后一个交易日';
comment on column PUB_TRADINGDAY.ismonthend
  is '是否本月最后一个交易日';
comment on column PUB_TRADINGDAY.isquarterend
  is '是否本季最后一个交易日';
comment on column PUB_TRADINGDAY.isyearend
  is '是否本年最后一个交易日';
comment on column PUB_TRADINGDAY.entrytime
  is '记录进表时间';
comment on column PUB_TRADINGDAY.updatetime
  is '记录修改时间';
comment on column PUB_TRADINGDAY.groundtime
  is '记录落地时间';
comment on column PUB_TRADINGDAY.updateid
  is '变动标识';
comment on column PUB_TRADINGDAY.resourceid
  is '来源标识';
comment on column PUB_TRADINGDAY.recordid
  is '来源记录';
comment on column PUB_TRADINGDAY.normalday
  is '日期';
comment on column PUB_TRADINGDAY.lwtradingday
  is '上周最后一个交易日';
comment on column PUB_TRADINGDAY.lmtradingday
  is '上月最后一个交易日';
comment on column PUB_TRADINGDAY.lytradingday
  is '上年最后一个交易日';
comment on column PUB_TRADINGDAY.b5tradingday
  is '前五个交易日';
comment on column PUB_TRADINGDAY.b10tradingday
  is '前十个交易日';
comment on column PUB_TRADINGDAY.b1mradingday
  is '前一个月交易日';
comment on column PUB_TRADINGDAY.b3mtradingday
  is '前两个月交易日';
comment on column PUB_TRADINGDAY.b6mtradingday
  is '前三个月交易日';
-- Create/Recreate indexes 
create index IUM30287393 on PUB_TRADINGDAY (UPDATEID);
create index IX_PUB_TRADINGDAY on PUB_TRADINGDAY (TRADINGDAY, EXCHANGECODE);
-- Create/Recreate primary, unique and foreign key constraints 
alter table PUB_TRADINGDAY
  add constraint PK_PUB_TRADINGDAY primary key (ID);
