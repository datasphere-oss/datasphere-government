declare
  v_program_name varchar(50);
  v_return                  integer;
BEGIN
  v_return                  := 0;
  v_program_name            := 'report.net_recomm_open_status';

truncate table   report.net_recomm_open_status;

insert into report.net_recomm_open_status(period_id,mobile_phone,client_name)
SELECT v_period_id,SJ,khxm FROM src_ht_hscif2.LCFXCKH_ZJB WHERE  sj
 NOT in  (select sj from src_ht_hscif2.lcfxckh where step in (5,6) and khfs='3')
 and sj in (select sj from src_ht_hscif2.lcfxckh where sqrq=v_period_id and khfs='3')
--and gxrq=v_period_id
;

--truncate table  report.net_recomm_open_status;

insert into report.net_recomm_open_status
(period_id,mobile_phone,client_id,client_name,client_status,is_new)
select v_period_id as period_id,a.sj as mobile_phone,a.khh as client_id,a.khxm as client_name,
0,case when to_char(identify_code_timestamp,'YYYYMMDD')<v_period_id then 0 else 1 end
 from src_ht_hscif2.lcfxckh  a
LEFT join src_ht_zlfin_mobile.t_account_info b
on a.sj=b.mobile_phone
where a.step in (5,6) and khfs='3'AND hfrq=v_period_id;

truncate table report.net_recomm_status_result;

insert into report.net_recomm_status_result
(period_id,mobile_phone,client_id,client_name,recom_mobile_phone,recom_client_id,recom_client_name,client_status)
select period_id,mobile_phone,client_id,client_name,recom_mobile_phone,recom_client_id,recom_client_name,client_status
 from report.net_recomm_open_status;

update report.net_recomm_status_result a
set tfrom=trim(b.tfrom)
from
src_ht_zlfin_mobile.t_account_info_kh b
where a.mobile_phone=b.mobile_phone;

update report.net_recomm_status_result a
set tfrom_page=substring(trim(b.attribute_value),1,200)
from  src_ht_zlfin_mobile.t_account_ext_info b
where b.mobile_phone=a.mobile_phone
and b.attribute_key='comeFrom';

update report.net_recomm_status_result a
set recom_mobile_phone=trim(b.attribute_value)
from  src_ht_zlfin_mobile.t_account_ext_info_kh b
where b.mobile_phone=a.mobile_phone
and b.attribute_key='recommender';

update report.net_recomm_open_status set recom_client_name='HTC1-7942646255'
where mobile_phone in
(select mobile_phone FROM src_ht_zlfin_mobile.t_account_info_kh a

where trim(tfrom) = 'HTC1-7942646255' or  trim(tfrom) like 'cft.bank.abc01%' )
and client_status='0';

update report.net_recomm_open_status a
set recom_mobile_phone=trim(b.attribute_value)
from  src_ht_zlfin_mobile.t_account_ext_info b
where b.mobile_phone=a.mobile_phone
and b.attribute_key='recommender';

update report.net_recomm_status_result a
set tfrom=b.source_code
from (
select distinct tfrom,c.source_code from report.net_recomm_status_result a,dw_dim.partner_user_source_link_map  b,
dw_dim.partner_user_source_link_map c
where tfrom not like 'HTC1%'
and substring(tfrom,1,case when strpos(tfrom,'#')-1>0 then strpos(tfrom,'#')-1 else length(tfrom) end  )=b.source_code and b.source_table_name='t_stat_account_infos' and c.source_table_name='crm'
and b.user_source_link_id=c.user_source_link_id )b
where a.tfrom=b.tfrom;

update report.net_recomm_status_result a set client_status=2
where  mobile_phone in
(select mobile_phone FROM src_ht_zlfin_mobile.t_account_info_kh a
where acc_kh_lsh is not null) and client_status=1;

truncate table report.t_fuel_cards_user;
insert into report.t_fuel_cards_user
(
id	,
mobile_no	,
create_time
)
select nextval('dw_fact.t_fuel_cards_user_req'),a.mobile_phone
,to_Date(b.hfrq, 'YYYYMMDD') || ' ' ||cast(to_timestamp(replace(b.hfsj,':',''), 'HH24MISS') as time)
FROM src_ht_zlfin_mobile.t_account_info_kh a ,src_ht_hscif2.lcfxckh b

--where trim(tfrom) = 'HTC1645243'
where trim(tfrom) = 'HTC1-8452522135'
and a.mobile_phone=b.sj and b.hfrq=v_period_id;


insert into wuyb.proc_log
(
period_id,
program_name,
exec_Date ,
exec_flag
)
select v_period_id,v_program_name,now(),0;

delete from report.net_recomm_status_result_his where period_id=v_period_id;
insert into report.net_recomm_status_result_his
select * from report.net_recomm_status_result ;

truncate table report.prog_flag;
insert into  report.prog_flag
(
proc_name,
period_id,
flag
)
select v_program_name,v_period_id,1;

return 0;
EXCEPTION
  when others then
    perform dw_pub.program_error(v_program_name,
                                 '����'||v_program_name||'���д���. ' ||
                                 SQLERRM,
                                 5,
                                 '',
                                 '',
                                 'greenplum db');
    return 1;
end;