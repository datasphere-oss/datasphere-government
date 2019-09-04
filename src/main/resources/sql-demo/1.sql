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