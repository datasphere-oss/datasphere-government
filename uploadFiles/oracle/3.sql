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