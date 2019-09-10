package demo;


public class OracleSQLS {
  
    public  static  String ORACLE_SQL1 = "create PROCEDURE PROC_UPDATE_COMPOSITERATING AS\n" +
            "  CUR_MONTH NUMBER;\n" +
            "  CURSOR CUR IS\n" +
            "    SELECT -1 FROM DUAL UNION SELECT -2 FROM DUAL UNION SELECT -3 FROM DUAL UNION SELECT -6 FROM DUAL;\n" +
            "BEGIN\n" +
            "  EXECUTE IMMEDIATE 'DELETE FROM RPT_COMPOSITERATING';\n" +
            "  OPEN CUR;\n" +
            "  LOOP\n" +
            "    FETCH CUR\n" +
            "      INTO CUR_MONTH;\n" +
            "    EXIT WHEN CUR%NOTFOUND;\n" +
            "\n" +
            "    INSERT INTO RPT_COMPOSITERATING\n" +
            "      (ID,\n" +
            "       SECUCODE,\n" +
            "       TRADINGCODE,\n" +
            "       SECUABBR,\n" +
            "       STATISTICDATE,\n" +
            "       RATINGAGENCYNUM,\n" +
            "       BUYAGENCYNUM,\n" +
            "       INCREASEAGENCYNUM,\n" +
            "       NEUTRALAGENCYNUM,\n" +
            "       REDUCEAGENCYNUM,\n" +
            "       SELLAGENCYNUM,\n" +
            "       HIGHESTPRICE,\n" +
            "       LOWESTPRICE,\n" +
            "       AVGPRICE,\n" +
            "       MONTH,\n" +
            "       ENTRYTIME,\n" +
            "       UPDATETIME,\n" +
            "       GROUNDTIME,\n" +
            "       UPDATEID,\n" +
            "       RESOURCEID,\n" +
            "       RECORDID,\n" +
            "       PUBDATE)\n" +
            "      SELECT SEQ_ID.NEXTVAL,\n" +
            "             A.SECUCODE,\n" +
            "             A.TRADINGCODE,\n" +
            "             A.SECUABBR,\n" +
            "             TRUNC(SYSDATE) STATISTICDATE,\n" +
            "             A.RATINGAGENCYNUM,\n" +
            "             NVL(A.BUYAGENCYNUM, 0) AS BUYAGENCYNUM,\n" +
            "             NVL(A.INCREASEAGENCYNUM, 0) AS NCREASEAGENCYNUM,\n" +
            "             NVL(A.NEUTRALAGENCYNUM, 0) AS NEUTRALAGENCYNUM,\n" +
            "             NVL(A.REDUCEAGENCYNUM, 0) AS REDUCEAGENCYNUM,\n" +
            "             NVL(A.SELLAGENCYNUM, 0) AS SELLAGENCYNUM,\n" +
            "             NVL(B.HIGHESTPRICE, 0) AS HIGHESTPRICE,\n" +
            "             NVL(B.LOWESTPRICE, 0) AS LOWESTPRICE,\n" +
            "             NVL(B.AVGPRICE, 0) AS AVGPRICE,\n" +
            "             CUR_MONTH*-1 AS MONTH,\n" +
            "             SYSDATE,\n" +
            "             SYSDATE,\n" +
            "             SYSDATE,\n" +
            "             SEQ_ID.NEXTVAL,\n" +
            "             SEQ_ID.NEXTVAL,\n" +
            "             SEQ_ID.NEXTVAL,\n" +
            "             PUBDATE\n" +
            "        FROM (SELECT SECUCODE,\n" +
            "                     TRADINGCODE,\n" +
            "                     SECUABBR,\n" +
            "                     SUM(C) RATINGAGENCYNUM,\n" +
            "                     SUM(DECODE(INVRATINGCODE, 1, C)) BUYAGENCYNUM,\n" +
            "                     SUM(DECODE(INVRATINGCODE, 2, C)) INCREASEAGENCYNUM,\n" +
            "                     SUM(DECODE(INVRATINGCODE, 3, C)) NEUTRALAGENCYNUM,\n" +
            "                     SUM(DECODE(INVRATINGCODE, 4, C)) REDUCEAGENCYNUM,\n" +
            "                     SUM(DECODE(INVRATINGCODE, 5, C)) SELLAGENCYNUM\n" +
            "                FROM (SELECT a.SECUCODE,\n" +
            "                             a.TRADINGCODE,\n" +
            "                             b.SECUABBR,\n" +
            "                             INVRATINGCODE,\n" +
            "                             COUNT(*) C\n" +
            "                        FROM TEXT_FORECASTRATING a\n" +
            "                        JOIN pub_securitiesmain b ON a.secucode = b.secucode\n" +
            "                       WHERE TRUNC(PUBDATE) >= ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)\n" +
            "                         AND a.SECUCODE != 0\n" +
            "                         AND INVRATINGCODE IN (1, 2, 3, 4, 5)\n" +
            "                       GROUP BY a.SECUCODE,\n" +
            "                                a.TRADINGCODE,\n" +
            "                                b.SECUABBR,\n" +
            "                                INVRATINGCODE)\n" +
            "               GROUP BY SECUCODE, TRADINGCODE, SECUABBR) A,\n" +
            "             (SELECT SECUCODE,MAX(T.PUBDATE) AS PUBDATE,\n" +
            "                     MAX(T.INDEXVAL) HIGHESTPRICE,\n" +
            "                     MIN(T.INDEXVAL) LOWESTPRICE,\n" +
            "                     TRUNC(AVG(T.INDEXVAL), 2) AVGPRICE\n" +
            "                FROM TEXT_PERFORMANCEFORECAST T\n" +
            "               WHERE T.PUBDATE > ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)\n" +
            "                 AND T.INDEXCODE = 1190\n" +
            "               GROUP BY SECUCODE) B\n" +
            "       WHERE A.SECUCODE = B.SECUCODE(+);\n" +
            "\n" +
            "  END LOOP;\n" +
            "  CLOSE CUR;\n" +
            "  COMMIT;\n" +
            "END PROC_UPDATE_COMPOSITERATING;\n";


    public  static String aaa = "create or replace procedure htsc_pos.proc_ht_fundnav is\n" +
            "  --v_code VARCHAR2(20);\n" +
            "  ----------------------------------\n" +
            "  v_cnt       int default 0;\n" +
            "  v_etime     timestamp;\n" +
            "  v_btime     timestamp;\n" +
            "  v_errortext varchar2(2000);\n" +
            "  v_errorcode varchar2(2000);\n" +
            "  v_pkgname   varchar2(2000);\n" +
            "  v_pkgsname  varchar2(2000);\n" +
            "\n" +
            "  -- cursor trdcodesor is\n" +
            "  --select c_port_code from v_ht_fundnav_all group by c_port_code;\n" +
            "\n" +
            "begin\n" +
            "RETURN;\n" +
            "  v_btime    := sysdate;\n" +
            "  v_pkgname  := 'proc_ht_fundnav';\n" +
            "  v_pkgsname := 'ÿ�ա��ܡ������鴦��';\n" +
            "  execute immediate 'truncate table v_ht_fundnav';\n" +
            "  /*open trdcodesor;\n" +
            "  loop\n" +
            "    fetch trdcodesor\n" +
            "      into v_code;\n" +
            "    exit when trdcodesor %notfound;*/\n" +
            "  ----ÿ������������\n" +
            "  insert into v_ht_fundnav\n" +
            "    select t1.*\n" +
            "      from v_ht_fundnav_all t1\n" +
            "      --join pos_prdt t2\n" +
            "        --on t1.c_port_code = t2.prdt_code\n" +
            "      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code\n" +
            "      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code\n" +
            "     where t2.nav_cycle = 1 and t2.nav_flag = 1\n" +
            "       and t1.d_biz in (select tradingday\n" +
            "                          from center_admin.pub_tradingday\n" +
            "                         where istradingday = 1\n" +
            "                           and exchangecode = 101);\n" +
            "  --ÿ�������������\n" +
            "  insert into v_ht_fundnav\n" +
            "    (N_ID,\n" +
            "     D_BIZ,\n" +
            "     C_PORT_CODE,\n" +
            "     C_PORT_NAME,\n" +
            "     N_ZR_NAVA,\n" +
            "     N_JR_NAVA,\n" +
            "     N_JR_LJDWJZ,\n" +
            "     N_JR_LJJZZZL,\n" +
            "     N_JR_SXSY_KFP,\n" +
            "     N_JR_SSZB,\n" +
            "     N_JR_MWFSY,\n" +
            "     N_JR_QJLJWFSY,\n" +
            "     N_JR_QRNHSYL,\n" +
            "     N_JR_QRFDNHSYL,\n" +
            "     N_JR_QRGDNHSYL,\n" +
            "     N_JR_ZQT_WCDBBL,\n" +
            "     N_JR_YGNHSYL,\n" +
            "     N_JR_ZQT_AJSYL,\n" +
            "     N_JR_BRSY,\n" +
            "     N_JR_FDSYL,\n" +
            "     N_JR_GDSYL,\n" +
            "     N_JR_FDSY,\n" +
            "     N_JR_GDSY,\n" +
            "     C_F_PORT_CODE)\n" +
            "    select t1.N_ID,\n" +
            "           t1.D_BIZ,\n" +
            "           t1.C_PORT_CODE,\n" +
            "           t1.C_PORT_NAME,\n" +
            "           t1.N_ZR_NAVA,\n" +
            "           t1.N_JR_NAVA,\n" +
            "           t1.N_JR_LJDWJZ,\n" +
            "           t1.N_JR_LJJZZZL,\n" +
            "           t1.N_JR_SXSY_KFP,\n" +
            "           t1.N_JR_SSZB,\n" +
            "           t1.N_JR_MWFSY,\n" +
            "           t1.N_JR_QJLJWFSY,\n" +
            "           t1.N_JR_QRNHSYL,\n" +
            "           t1.N_JR_QRFDNHSYL,\n" +
            "           t1.N_JR_QRGDNHSYL,\n" +
            "           t1.N_JR_ZQT_WCDBBL,\n" +
            "           t1.N_JR_YGNHSYL,\n" +
            "           t1.N_JR_ZQT_AJSYL,\n" +
            "           t1.N_JR_BRSY,\n" +
            "           t1.N_JR_FDSYL,\n" +
            "           t1.N_JR_GDSYL,\n" +
            "           t1.N_JR_FDSY,\n" +
            "           t1.N_JR_GDSY,\n" +
            "           t1.C_F_PORT_CODE\n" +
            "      from v_ht_fundnav_all t1\n" +
            "      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code\n" +
            "      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code\n" +
            "     where t2.nav_cycle = 2 and t2.nav_flag = 1\n" +
            "          -- and t1.c_port_code = v_code\n" +
            "       and t1.d_biz in\n" +
            "           (select end_tradingday\n" +
            "              from (select tradingday,\n" +
            "                           rk,\n" +
            "                           lead(rk, 1, rk) over(order by tradingday desc) week,\n" +
            "                           case\n" +
            "                             when rk < lead(rk, 1, rk)\n" +
            "                              over(order by tradingday desc) then\n" +
            "                              lead(tradingday, 1, tradingday)\n" +
            "                              over(order by tradingday desc)\n" +
            "                           end as end_tradingday\n" +
            "                      from (select tradingday, to_char(tradingday - 1, 'd') rk\n" +
            "                              from center_admin.pub_tradingday\n" +
            "                             where istradingday = 1\n" +
            "                               and exchangecode = 101))\n" +
            "             where end_tradingday is not null);\n" +
            "  --ÿ�������������\n" +
            "  insert into v_ht_fundnav\n" +
            "    (N_ID,\n" +
            "     D_BIZ,\n" +
            "     C_PORT_CODE,\n" +
            "     C_PORT_NAME,\n" +
            "     N_ZR_NAVA,\n" +
            "     N_JR_NAVA,\n" +
            "     N_JR_LJDWJZ,\n" +
            "     N_JR_LJJZZZL,\n" +
            "     N_JR_SXSY_KFP,\n" +
            "     N_JR_SSZB,\n" +
            "     N_JR_MWFSY,\n" +
            "     N_JR_QJLJWFSY,\n" +
            "     N_JR_QRNHSYL,\n" +
            "     N_JR_QRFDNHSYL,\n" +
            "     N_JR_QRGDNHSYL,\n" +
            "     N_JR_ZQT_WCDBBL,\n" +
            "     N_JR_YGNHSYL,\n" +
            "     N_JR_ZQT_AJSYL,\n" +
            "     N_JR_BRSY,\n" +
            "     N_JR_FDSYL,\n" +
            "     N_JR_GDSYL,\n" +
            "     N_JR_FDSY,\n" +
            "     N_JR_GDSY,\n" +
            "     C_F_PORT_CODE)\n" +
            "    select t1.N_ID,\n" +
            "           t1.D_BIZ,\n" +
            "           t1.C_PORT_CODE,\n" +
            "           t1.C_PORT_NAME,\n" +
            "           t1.N_ZR_NAVA,\n" +
            "           t1.N_JR_NAVA,\n" +
            "           t1.N_JR_LJDWJZ,\n" +
            "           t1.N_JR_LJJZZZL,\n" +
            "           t1.N_JR_SXSY_KFP,\n" +
            "           t1.N_JR_SSZB,\n" +
            "           t1.N_JR_MWFSY,\n" +
            "           t1.N_JR_QJLJWFSY,\n" +
            "           t1.N_JR_QRNHSYL,\n" +
            "           t1.N_JR_QRFDNHSYL,\n" +
            "           t1.N_JR_QRGDNHSYL,\n" +
            "           t1.N_JR_ZQT_WCDBBL,\n" +
            "           t1.N_JR_YGNHSYL,\n" +
            "           t1.N_JR_ZQT_AJSYL,\n" +
            "           t1.N_JR_BRSY,\n" +
            "           t1.N_JR_FDSYL,\n" +
            "           t1.N_JR_GDSYL,\n" +
            "           t1.N_JR_FDSY,\n" +
            "           t1.N_JR_GDSY,\n" +
            "           t1.C_F_PORT_CODE\n" +
            "      from v_ht_fundnav_all t1\n" +
            "      join pos_prdt_child t3 on t1.c_port_code = t3.prdt_code\n" +
            "      join pos_prdt t2 on t3.parent_prdt_code = t2.prdt_code\n" +
            "     where t2.nav_cycle = 3 and t2.nav_flag = 1\n" +
            "          --and t1.c_port_code = v_code\n" +
            "       and t1.d_biz in (select tradingday\n" +
            "                          from (select tradingday,\n" +
            "                                       to_char(tradingday, 'yyyymm'),\n" +
            "                                       to_char(tradingday, 'dd'),\n" +
            "                                       ROW_NUMBER() OVER(PARTITION BY to_char(tradingday, 'yyyymm') ORDER BY to_char(tradingday, 'dd') desc) as rk\n" +
            "                                  from center_admin.pub_tradingday\n" +
            "                                 where istradingday = 1\n" +
            "                                   and exchangecode = 101)\n" +
            "                         where rk = 1);\n" +
            "\n" +
            "  --ͳ�Ƽ�������\n" +
            "  v_cnt := v_cnt + sql%rowcount;\n" +
            "  --end loop;\n" +
            "  --close trdcodesor;\n" +
            "  commit;\n" +
            "\n" +
            "  --ȡ����ʱ��\n" +
            "  v_etime := sysdate;\n" +
            "  --�������м�¼\n" +
            "\n" +
            "  insert into run_result\n" +
            "  values\n" +
            "    (seq_pos.nextval,\n" +
            "     v_pkgname,\n" +
            "     v_pkgsname,\n" +
            "     trunc(sysdate),\n" +
            "     v_btime,\n" +
            "     v_etime,\n" +
            "     v_cnt,\n" +
            "     to_clob(v_errorcode || ' ' || v_errortext),\n" +
            "     '1');\n" +
            "  commit;\n" +
            "exception\n" +
            "  --�쳣����\n" +
            "  when others then\n" +
            "    v_etime     := sysdate;\n" +
            "    v_errorcode := sqlcode;\n" +
            "    v_errortext := substr(sqlerrm, 1, 2000) || v_errortext;\n" +
            "    rollback;\n" +
            "    insert into run_result\n" +
            "    values\n" +
            "      (seq_pos.nextval,\n" +
            "       v_pkgname,\n" +
            "       v_pkgsname,\n" +
            "       trunc(sysdate),\n" +
            "       v_btime,\n" +
            "       v_etime,\n" +
            "       v_cnt,\n" +
            "       to_clob(v_errorcode || ' ' || v_errortext),\n" +
            "       '0');\n" +
            "    commit;\n" +
            "end;";
}
