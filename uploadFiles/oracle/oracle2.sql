create or replace procedure htsc_pos.proc_ht_fundnav is
  --v_code VARCHAR2(20);
  ----------------------------------
  v_cnt       int default 0;
  v_etime     timestamp;
  v_btime     timestamp;
  v_errortext varchar2(2000);
  v_errorcode varchar2(2000);
  v_pkgname   varchar2(2000);
  v_pkgsname  varchar2(2000);

  -- cursor trdcodesor is
  --select c_port_code from v_ht_fundnav_all group by c_port_code;

begin
RETURN;
  v_btime    := sysdate;
  v_pkgname  := 'proc_ht_fundnav';
  v_pkgsname := 'ÿ�ա��ܡ������鴦��';
  execute immediate 'truncate table v_ht_fundnav';
  /*open trdcodesor;
  loop
    fetch trdcodesor
      into v_code;
    exit when trdcodesor %notfound;*/
  ----ÿ������������
  insert into v_ht_fundnav
    select t1.*
      from v_ht_fundnav_all t1
      --join pos_prdt t2
        --on t1.c_port_code = t2.prdt_code
      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code
      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code
     where t2.nav_cycle = 1 and t2.nav_flag = 1
       and t1.d_biz in (select tradingday
                          from center_admin.pub_tradingday
                         where istradingday = 1
                           and exchangecode = 101);
  --ÿ�������������
  insert into v_ht_fundnav
    (N_ID,
     D_BIZ,
     C_PORT_CODE,
     C_PORT_NAME,
     N_ZR_NAVA,
     N_JR_NAVA,
     N_JR_LJDWJZ,
     N_JR_LJJZZZL,
     N_JR_SXSY_KFP,
     N_JR_SSZB,
     N_JR_MWFSY,
     N_JR_QJLJWFSY,
     N_JR_QRNHSYL,
     N_JR_QRFDNHSYL,
     N_JR_QRGDNHSYL,
     N_JR_ZQT_WCDBBL,
     N_JR_YGNHSYL,
     N_JR_ZQT_AJSYL,
     N_JR_BRSY,
     N_JR_FDSYL,
     N_JR_GDSYL,
     N_JR_FDSY,
     N_JR_GDSY,
     C_F_PORT_CODE)
    select t1.N_ID,
           t1.D_BIZ,
           t1.C_PORT_CODE,
           t1.C_PORT_NAME,
           t1.N_ZR_NAVA,
           t1.N_JR_NAVA,
           t1.N_JR_LJDWJZ,
           t1.N_JR_LJJZZZL,
           t1.N_JR_SXSY_KFP,
           t1.N_JR_SSZB,
           t1.N_JR_MWFSY,
           t1.N_JR_QJLJWFSY,
           t1.N_JR_QRNHSYL,
           t1.N_JR_QRFDNHSYL,
           t1.N_JR_QRGDNHSYL,
           t1.N_JR_ZQT_WCDBBL,
           t1.N_JR_YGNHSYL,
           t1.N_JR_ZQT_AJSYL,
           t1.N_JR_BRSY,
           t1.N_JR_FDSYL,
           t1.N_JR_GDSYL,
           t1.N_JR_FDSY,
           t1.N_JR_GDSY,
           t1.C_F_PORT_CODE
      from v_ht_fundnav_all t1
      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code
      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code
     where t2.nav_cycle = 2 and t2.nav_flag = 1
          -- and t1.c_port_code = v_code
       and t1.d_biz in
           (select end_tradingday
              from (select tradingday,
                           rk,
                           lead(rk, 1, rk) over(order by tradingday desc) week,
                           case
                             when rk < lead(rk, 1, rk)
                              over(order by tradingday desc) then
                              lead(tradingday, 1, tradingday)
                              over(order by tradingday desc)
                           end as end_tradingday
                      from (select tradingday, to_char(tradingday - 1, 'd') rk
                              from center_admin.pub_tradingday
                             where istradingday = 1
                               and exchangecode = 101))
             where end_tradingday is not null);
  --ÿ�������������
  insert into v_ht_fundnav
    (N_ID,
     D_BIZ,
     C_PORT_CODE,
     C_PORT_NAME,
     N_ZR_NAVA,
     N_JR_NAVA,
     N_JR_LJDWJZ,
     N_JR_LJJZZZL,
     N_JR_SXSY_KFP,
     N_JR_SSZB,
     N_JR_MWFSY,
     N_JR_QJLJWFSY,
     N_JR_QRNHSYL,
     N_JR_QRFDNHSYL,
     N_JR_QRGDNHSYL,
     N_JR_ZQT_WCDBBL,
     N_JR_YGNHSYL,
     N_JR_ZQT_AJSYL,
     N_JR_BRSY,
     N_JR_FDSYL,
     N_JR_GDSYL,
     N_JR_FDSY,
     N_JR_GDSY,
     C_F_PORT_CODE)
    select t1.N_ID,
           t1.D_BIZ,
           t1.C_PORT_CODE,
           t1.C_PORT_NAME,
           t1.N_ZR_NAVA,
           t1.N_JR_NAVA,
           t1.N_JR_LJDWJZ,
           t1.N_JR_LJJZZZL,
           t1.N_JR_SXSY_KFP,
           t1.N_JR_SSZB,
           t1.N_JR_MWFSY,
           t1.N_JR_QJLJWFSY,
           t1.N_JR_QRNHSYL,
           t1.N_JR_QRFDNHSYL,
           t1.N_JR_QRGDNHSYL,
           t1.N_JR_ZQT_WCDBBL,
           t1.N_JR_YGNHSYL,
           t1.N_JR_ZQT_AJSYL,
           t1.N_JR_BRSY,
           t1.N_JR_FDSYL,
           t1.N_JR_GDSYL,
           t1.N_JR_FDSY,
           t1.N_JR_GDSY,
           t1.C_F_PORT_CODE
      from v_ht_fundnav_all t1
      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code
      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code
     where t2.nav_cycle = 3 and t2.nav_flag = 1
          --and t1.c_port_code = v_code
       and t1.d_biz in (select tradingday
                          from (select tradingday,
                                       to_char(tradingday, 'yyyymm'),
                                       to_char(tradingday, 'dd'),
                                       ROW_NUMBER() OVER(PARTITION BY to_char(tradingday, 'yyyymm') ORDER BY to_char(tradingday, 'dd') desc) as rk
                                  from center_admin.pub_tradingday
                                 where istradingday = 1
                                   and exchangecode = 101)
                         where rk = 1);

  --ͳ�Ƽ�������
  v_cnt := v_cnt + sql%rowcount;
  --end loop;
  --close trdcodesor;
  commit;

  --ȡ����ʱ��
  v_etime := sysdate;
  --�������м�¼

  insert into run_result
  values
    (seq_pos.nextval,
     v_pkgname,
     v_pkgsname,
     trunc(sysdate),
     v_btime,
     v_etime,
     v_cnt,
     to_clob(v_errorcode || ' ' || v_errortext),
     '1');
  commit;
exception
  --�쳣����
  when others then
    v_etime     := sysdate;
    v_errorcode := sqlcode;
    v_errortext := substr(sqlerrm, 1, 2000) || v_errortext;
    rollback;
    insert into run_result
    values
      (seq_pos.nextval,
       v_pkgname,
       v_pkgsname,
       trunc(sysdate),
       v_btime,
       v_etime,
       v_cnt,
       to_clob(v_errorcode || ' ' || v_errortext),
       '0');
    commit;
end;