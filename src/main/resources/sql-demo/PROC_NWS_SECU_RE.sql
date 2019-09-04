CREATE OR REPLACE PROCEDURE CENTER_ADMIN.PROC_NWS_SECU_RE(S_DATE DATE DEFAULT SYSDATE - 1,
                                             E_DATE DATE DEFAULT SYSDATE) AS
  V_TRDCODE  VARCHAR2(100);
  V_SECUABBR VARCHAR2(100);
  V_SECUCODE NUMBER(10);
  V_ID       NUMBER(18);
  --V_DATE     DATE;

  CURSOR CUR IS
   SELECT TRADINGCODE, SECUABBR, SECUCODE
      FROM STK_BASICINFO
     WHERE TRADINGCODE IN ('000518');
                           /*'000417',
                           '600327',
                           '600784',
                           '600575',
                           '000978',
                           '002549',
                           '002059',
                           '600714',
                           '600803',
                           '600846',
                           '600133',
                           '600095',
                           '600783',
                           '600385',
                           '000029',
                           '600288',
                           '600283',
                           '300318',
                           '600601',
                           '300358',
                           '300307',
                           '002707',
                           '300416',
                           '601519',
                           '002229',
                           '002366',
                           '002280',
                           '300188',
                           '600055',
                           '601002',
                           '300154',
                           '000559',
                           '300049',
                           '300053',
                           '603988',
                           '300468',
                           '603789',
                           '300210',
                           '300465');*/


BEGIN
  OPEN CUR;
  LOOP
    FETCH CUR
      INTO V_TRDCODE, V_SECUABBR, V_SECUCODE;
    EXIT WHEN CUR %NOTFOUND;
    BEGIN
    
    INSERT INTO TXT_NWS_SECU_RE
      SELECT 
      SEQ_ID.NEXTVAL,
      T1.ID,
      NULL,
      t1.pubdate,
      NULL,
      NULL,
      SYSDATE,
      SYSDATE,
      SYSDATE 
        FROM TEXT_BASICINFO T1
        JOIN CNT_TEXT T2
          ON T1.TEXTCATEGORY = T2.CONSTCODE
       WHERE T2.CNTTYPE = 30
         AND (TRUNC(T1.PUBDATE) BETWEEN S_DATE AND E_DATE)
         AND (T1.TEXTTITLE LIKE '%' || V_TRDCODE || '%'
          OR T1.TEXTTITLE LIKE '%' || V_SECUABBR || '%'
          OR dbms_lob.substr(t1.abstract,2000,1) LIKE '%' || V_TRDCODE || '%'
          OR dbms_lob.substr(t1.abstract,2000,1) LIKE '%' || V_SECUABBR || '%');
COMMIT;

    EXCEPTION
      WHEN OTHERS THEN
        NULL;
    END;
  END LOOP;
  CLOSE CUR;
EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE(V_ID || ',' || V_SECUCODE);
   -- RAISE_APPLICATION_ERROR(-20001, SQLERRM);
END PROC_NWS_SECU_RE;