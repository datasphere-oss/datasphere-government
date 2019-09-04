CREATE OR REPLACE FUNCTION report.proc_southern_fund_staff_trade_detail(v_period_id character)
RETURNS numeric AS
$BODY$

  --call by ds job
  --Author: zak
  --Creation: 20141126
  --Discription:向南方基金提供其员工及亲属的成交明细
  --            业务接口人：经纪业务总部 姚亮
  --            处理优先级：低
  --            时间要求：每月前三个交易日跑上月数据
  --            数据用途：按接口要求生成南方基金员工及亲属的成交数据,后续由ds job生成excel
  --Modification: 20141126 初始化
  --              20141201 改成每月前三个交易日跑上月数据
  declare v_program_name varchar(50);
  ln_line          integer;
  v_return         integer;
  v_next_tradedate integer;
  v_last_month_begin_tradedate integer;
  v_last_month_end_tradedate   integer;
BEGIN
  v_return       := 0;
  v_program_name := 'report.proc_southern_fund_staff_trade_detail';

  ln_line := 1;
  if (dw_pub.get_month_begin_tradedate(v_period_id)<>v_period_id and
      dw_pub.get_month_begin_tradedate(v_period_id)<>dw_pub.get_last_n_tradedate(v_period_id, -1) AND
      dw_pub.get_month_begin_tradedate(v_period_id)<>dw_pub.get_last_n_tradedate(v_period_id, -2)) then
     return 0;
  end if;

  ln_line := 2;
  v_last_month_begin_tradedate := dw_pub.get_month_begin_tradedate(dw_pub.get_last_month_end_date(v_period_id));
  v_last_month_end_tradedate := dw_pub.get_month_end_tradedate(dw_pub.get_last_month_end_date(v_period_id));

  ln_line := 10;
  delete from report.southern_fund_staff_trade_detail
   where init_date between v_last_month_begin_tradedate and v_last_month_end_tradedate;

  ln_line := 20;
  insert into report.southern_fund_staff_trade_detail
    (stock_account,
     market_name,
     stock_code,
     stock_name,
     stock_type,
     entrust_bs,
     business_flag,
     business_price,
     business_amount,
     business_balance,
     init_date,
     client_id,
     created_busi_date,
     last_modified_busi_date,
     creation_date,
     created_by,
     last_modification_date,
     last_modified_by)
    select a.stock_account,
           b.source_name as market_name,
           a.stock_code,
           a.stock_name,
           e.source_name as stock_type,
           case
             when a.entrust_bs = '1' then
              '买入'
             when a.entrust_bs = '2' then
              '卖出'
             else
              '其他'
           end as entrust_bs,
           coalesce(case
             when j.special_digest_flag is not null and
                  j.special_digest_flag = 1 then
              j.source_digest_name
             else
              i.source_digest_name
           end,'') as business_flag,
           a.business_price,
           a.business_amount,
           a.business_balance,
           a.init_date,
           a.client_id,
           v_period_id created_busi_date,
           v_period_id last_modified_busi_date,
           now() creation_date,
           v_program_name created_by,
           now() last_modification_date,
           v_program_name last_modified_by
      from his_ht_hs08_hsasset.deliver       a
      join dw_dim.market_map                 b
           on trim(a.exchange_type) = trim(b.source_code)
              and b.source_system_id = 1
      join dw_dim.security_type_map          e
           on trim(a.stock_type) = trim(e.source_code)
          and e.source_system_id = 1
      left join dw_dim.trade_direction_map h on trim(a.entrust_bs) =
                                                trim(h.source_code)
                                            and h.source_system_id =
                                                1
      left join dw_dim.digest_map i on trim(a.business_flag) =
                                       trim(i.source_digest_code)
                                   and i.source_system_id =
                                       1
                                   and i.special_digest_flag <> 1
      left join dw_dim.digest_map j on trim(a.business_flag) =
                                       trim(j.source_digest_code)
                                   and h.trade_direction_id =
                                       j.trade_direction_id
                                   and j.source_system_id =
                                       1
                                   and j.special_digest_flag = 1
     where a.business_price <> 0
       and exists (select null from dw_mnu.southern_fund_staff_list   d,
           dw_dim.hs_client_dim_unreconsiled f
         where (trim(d.id_no) = trim(f.idno) or trim(d.id_no)=trim(f.adjust_idno))
       and trim(d.client_name) = trim(f.client_name)
       and f.source_system_id = 1
       and a.init_date between d.data_begin_date and d.data_end_date
       and d.valid_flag = 1
       and f.source_client_id = a.client_id)
       and a.dc_business_date between v_last_month_begin_tradedate and v_last_month_end_tradedate;

     return v_return;
EXCEPTION
  when others then
    perform dw_pub.program_error(v_program_name || '[' || ln_line || ']',
                                 '程序' || v_program_name || '运行错误. ' ||
                                 SQLERRM,
                                 5,
                                 '',
                                 '',
                                 'greenplum db');
    return 1;
end;
$BODY$
LANGUAGE plpgsql VOLATILE;