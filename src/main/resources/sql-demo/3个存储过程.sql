
 declare
  v_program_name varchar(50);
  v_return                  integer;
  v_count_cft                   integer;
  v_count_crm                integer;
BEGIN
  v_return                  := 0;
  v_program_name            := 'report.tifstockpool_check';
  v_count_cft                   :=0;
  v_count_crm                   :=0;
  
  delete from report.tifstockpool_check_detail where period_id=v_period_id;
  insert into  report.tifstockpool_check_detail
(
period_id,
system_name,
name,
stockcode,
marketno,
remark,
optype ,
name_opp,
stockcode_opp,
marketno_opp,
remark_opp,
optype_opp
)
select v_period_id as period_id,'cft',a.name,a.stockcode,a.marketno,a.remark,a.optype ,
b.name,b.stockcode,b.marketno,b.remark,b.optype
from  src_ht_zlfin_mobile.tifstockpool a
full join src_ht_risk.tifstockpool b
on a.stockcode=b.stockcode
where a.name<>b.name
or a.stockcode<>b.stockcode
or a.marketno<>b.marketno
or a.remark<>b.remark
or a.optype<>b.optype;

insert into  report.tifstockpool_check_detail
(
period_id,
system_name,
name,
stockcode,
marketno,
remark,
optype ,
name_opp,
stockcode_opp,
marketno_opp,
remark_opp,
optype_opp
)
select v_period_id as period_id,'crm',a.name,a.stockcode,a.marketno,a.remark,a.optype ,
b.name,b.stockcode,b.marketno,b.remark,b.optype
from  src_ht_sb81.swap_tifstockpool a
full join src_ht_zlfin_mobile.tifstockpool b
on a.stockcode=b.stockcode
where a.name<>b.name
or a.stockcode<>b.stockcode
or a.marketno<>b.marketno
or a.remark<>b.remark
or a.optype<>b.optype;

select count(*) into v_count_cft
 from report.tifstockpool_check_detail 
 where period_id=v_period_id and system_name='cft';

select count(*) into v_count_crm
 from report.tifstockpool_check_detail 
 where period_id=v_period_id and system_name='cft';
 
 delete  from report.tifstockpool_check_result where period_id=v_period_id;
 if v_count_cft=0 then 
 insert into report.tifstockpool_check_result
 select v_period_id,'�Ƹ�ͨ','����һ��';
 else 
  insert into report.tifstockpool_check_result
 select v_period_id,'�Ƹ�ͨ','���ݲ�һ��';
end if;

if v_count_cft=0 then 
 insert into report.tifstockpool_check_result
 select v_period_id,'crm','����һ��';
 else 
  insert into report.tifstockpool_check_result
 select v_period_id,'crm','���ݲ�һ��';
end if;


 
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

  

 declare
  v_program_name varchar(50);
  v_return                  integer;
BEGIN
  v_return                  := 0;
  v_program_name            := 'report.neeq_risk_result';

delete from report.neeq_risk_result where period_id=v_period_id;
delete from report.neeq_risk_result_flag where period_id=v_period_id;
insert into report.neeq_risk_result
(
period_id,
risk_test_date,
client_id,
end_date,
last_Date,
asset
)
select DISTINCT v_period_id,cast(upd_dt as INTEGER),sor_pty_id,cast(to_char(to_date(upd_dt,'YYYYMMDD')+20,'YYYYMMDD') as integer) as last_Date,
cast(upd_dt as INTEGER)-10000 as begin_Date,d.avg_aset
 from his_ht_sb81.ods_ocrm_sbj_habit a,src_ht_sb81.serv_pty_cust_conv b,
src_ht_hs08_hsasset.sstaccount c ,his_ht_sb81.serv_cust_xsb_avgaset d
where upd_dt BETWEEN dw_pub.get_last_n_tradedate(v_period_id,-2) and v_period_id
and a.pty_id=d.cust_id  and a.attrib_name='811010'
and sstacct_status='2' and organ_flag='0'
and a.upd_dt=d.data_dt
and d.avg_aset<1000000
and cast(upd_dt as INTEGER)-10000>sub_risk_date
and cast(upd_dt as INTEGER)-10000>right_open_date
and a.pty_id=b.pty_id and b.sor_pty_id=c.client_id
order by 2
;

delete from  report.neeq_risk_result a
using ( select client_id,max(init_Date) as init_Date
 from src_ht_hs08_hsasset.ht_clientsignjour where agreement_kind='GTHT0086'
group by client_id
) b
where a.client_id=b.client_id and a.period_id=v_period_id
and a.last_date<=b.init_Date;

delete from report.neeq_risk_result_bo where period_id=v_period_id;
INSERT into report.neeq_risk_result_bo
(
period_id,
up_no,
branch_no,
up_name,
branch_name,
client_id,
client_name,
neeq_open_Date,
latest_date,
check_asset,
holer_status,
remark)
select v_period_id, b.branch_up,b.branch_no,b.up_name,b.branch_name, c.client_id,b.client_name,c.right_open_date,a.risk_test_date,a.asset,
case 
when sstacct_status=0 then '�Ǽ�'  
when sstacct_status=1 then '�Ǽ��걨'
when sstacct_status=2 then '�Ǽǳɹ�'
when sstacct_status=3 then '�Ǽǻر�ʧ��'
when sstacct_status=4 then 'ע���걨'
when sstacct_status=5 then 'ע������'
when sstacct_status=6 then '�����˻��Ǽ�' else '' end,remark
 from src_ht_hs08_hsasset.sstaccount c ,(select distinct t.*,row_number()over(partition by t.client_id order by risk_test_date desc) as rak_1 from report.neeq_risk_result t) a,
wuyb.cb_client_info_branch b
where  c.organ_flag='0' and rak_1=1
and a.client_id=b.client_id
and a.client_id=c.client_id;






 
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

  
