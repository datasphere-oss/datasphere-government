CREATE OR REPLACE FUNCTION dw_mine.real_clt_account_avg_rate_daily(v_period_id character)
RETURNS numeric AS
$BODY$

  --real_clt_account_avg_rate_daily by wuhui

  declare ln_linenum int4;
  v_program_name varchar(50);
  v_table_name   varchar(80);
  v_last_30_trd_date integer;
	v_sql varchar(10000);
  v_return integer;


BEGIN
  v_return := 0;
  v_program_name          := 'dw_mine.real_clt_account_avg_rate_daily';

  ln_linenum     := 10;
select last_30_trd_date into v_last_30_trd_date
from (
select period_id,lag(period_id,30) over(order by period_id) last_30_trd_date
from dw_dim.period_dim t
where t.period_level='D'
and t.period_id=t.latest_trade_begin_date
and t.period_id between cast(to_char(to_date(v_period_id,'YYYYMMDD')-60,'YYYYMMDD') as integer) and v_period_id
) t
where period_id=v_period_id;

delete from  dw_mine.real_clt_account_avg_rate_pre where period_id=v_last_30_trd_date;

v_sql=$a$
insert into dw_mine.real_clt_account_avg_rate_pre
select a.dc_business_date as period_id,a.client_id,case when a.business_flag in(4001,4701,4703) then 1 else 2 end as bs_flag,
	sum((b.max_asset_price1-a.business_price)/a.business_price) buy_1_avg_rate,
	sum((b.max_asset_price5-a.business_price)/a.business_price) buy_5_avg_rate,
	sum((b.max_asset_price10-a.business_price)/a.business_price) buy_10_avg_rate,
	sum((b.max_asset_price30-a.business_price)/a.business_price) buy_30_avg_rate,
	sum(-(b.min_asset_price1-a.business_price)/a.business_price) sell_1_avg_rate,
	sum(-(b.min_asset_price5-a.business_price)/a.business_price) sell_5_avg_rate,
	sum(-(b.min_asset_price10-a.business_price)/a.business_price) sell_10_avg_rate,
	sum(-(b.min_asset_price30-a.business_price)/a.business_price) sell_30_avg_rate,
	count(1) trd_times
from his_ht_hs08_hshis.his_deliver a,
(
select b.period_id,c.exchange_type,c.stock_code,
	max(case when c.dc_business_date<=b.period_id_aft1 then c.asset_price else 0 end) max_asset_price1,
	max(case when c.dc_business_date<=b.period_id_aft5 then c.asset_price else 0 end) max_asset_price5,
	max(case when c.dc_business_date<=b.period_id_aft10 then c.asset_price else 0 end) max_asset_price10,
	max(case when c.dc_business_date<=b.period_id_aft30 then c.asset_price else 0 end) max_asset_price30,
	min(case when c.dc_business_date<=b.period_id_aft1 then c.asset_price else 999999 end) min_asset_price1,
	min(case when c.dc_business_date<=b.period_id_aft5 then c.asset_price else 999999 end) min_asset_price5,
	min(case when c.dc_business_date<=b.period_id_aft10 then c.asset_price else 999999 end) min_asset_price10,
	min(case when c.dc_business_date<=b.period_id_aft30 then c.asset_price else 999999 end) min_asset_price30
from (
			select a.period_id,
						lead(period_id,1) over(order by period_id) period_id_aft1,
						lead(period_id,5) over(order by period_id) period_id_aft5,
						lead(period_id,10) over(order by period_id) period_id_aft10,
						lead(period_id,30) over(order by period_id) period_id_aft30
			from dw_dim.period_dim a
			where a.period_level='D'
			and a.period_id=a.latest_trade_begin_date
			and a.period_id between $a$||v_last_30_trd_date||$a$ and cast(to_char(to_date($a$||v_last_30_trd_date||$a$,'YYYYMMDD')+70,'YYYYMMDD') as integer)
			) b,
	his_ht_hs08_hsuser.price c,src_ht_hs08_hsuser.stkcode d
where b.period_id=$a$||v_last_30_trd_date||$a$
and c.dc_business_date between b.period_id and b.period_id_aft30
and c.exchange_type in('1','2')
and c.money_type='0'
and c.exchange_type=d.exchange_type and c.stock_code=d.stock_code and d.stock_type in('0','c')
group by b.period_id,c.exchange_type,c.stock_code
) b
where a.dc_business_date=$a$||v_last_30_trd_date||$a$
and a.exchange_type in('1','2')
and (a.business_flag in(4001,4002) or a.business_flag between 4701 and 4704)
and a.stock_type in('0','c')
and b.period_id=a.dc_business_date
and a.exchange_type=b.exchange_type
and a.stock_code=b.stock_code
group by a.dc_business_date,a.client_id,case when a.business_flag in(4001,4701,4703) then 1 else 2 end
$a$;
execute v_sql;
 return v_return;

exception
  when others then
    perform dw_pub.program_error(v_program_name || '[' || ln_linenum || ']',
                                 '归' || v_table_name || '历史错误. ' || SQLERRM,
                                 5,
                                 '',
                                 '',
                                 'greenplum db');
    return 1;
END
$BODY$
LANGUAGE plpgsql VOLATILE;