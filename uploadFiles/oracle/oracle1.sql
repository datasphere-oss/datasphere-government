create PROCEDURE PROC_UPDATE_COMPOSITERATING AS
  CUR_MONTH NUMBER;
  CURSOR CUR IS
    SELECT -1 FROM DUAL UNION SELECT -2 FROM DUAL UNION SELECT -3 FROM DUAL UNION SELECT -6 FROM DUAL;
BEGIN
  EXECUTE IMMEDIATE 'DELETE FROM RPT_COMPOSITERATING';
  OPEN CUR;
  LOOP
    FETCH CUR
      INTO CUR_MONTH;
    EXIT WHEN CUR%NOTFOUND;

    INSERT INTO RPT_COMPOSITERATING
      (ID,
       SECUCODE,
       TRADINGCODE,
       SECUABBR,
       STATISTICDATE,
       RATINGAGENCYNUM,
       BUYAGENCYNUM,
       INCREASEAGENCYNUM,
       NEUTRALAGENCYNUM,
       REDUCEAGENCYNUM,
       SELLAGENCYNUM,
       HIGHESTPRICE,
       LOWESTPRICE,
       AVGPRICE,
       MONTH,
       ENTRYTIME,
       UPDATETIME,
       GROUNDTIME,
       UPDATEID,
       RESOURCEID,
       RECORDID,
       PUBDATE)
      SELECT SEQ_ID.NEXTVAL,
             A.SECUCODE,
             A.TRADINGCODE,
             A.SECUABBR,
             TRUNC(SYSDATE) STATISTICDATE,
             A.RATINGAGENCYNUM,
             NVL(A.BUYAGENCYNUM, 0) AS BUYAGENCYNUM,
             NVL(A.INCREASEAGENCYNUM, 0) AS NCREASEAGENCYNUM,
             NVL(A.NEUTRALAGENCYNUM, 0) AS NEUTRALAGENCYNUM,
             NVL(A.REDUCEAGENCYNUM, 0) AS REDUCEAGENCYNUM,
             NVL(A.SELLAGENCYNUM, 0) AS SELLAGENCYNUM,
             NVL(B.HIGHESTPRICE, 0) AS HIGHESTPRICE,
             NVL(B.LOWESTPRICE, 0) AS LOWESTPRICE,
             NVL(B.AVGPRICE, 0) AS AVGPRICE,
             CUR_MONTH*-1 AS MONTH,
             SYSDATE,
             SYSDATE,
             SYSDATE,
             SEQ_ID.NEXTVAL,
             SEQ_ID.NEXTVAL,
             SEQ_ID.NEXTVAL,
             PUBDATE
        FROM (SELECT SECUCODE,
                     TRADINGCODE,
                     SECUABBR,
                     SUM(C) RATINGAGENCYNUM,
                     SUM(DECODE(INVRATINGCODE, 1, C)) BUYAGENCYNUM,
                     SUM(DECODE(INVRATINGCODE, 2, C)) INCREASEAGENCYNUM,
                     SUM(DECODE(INVRATINGCODE, 3, C)) NEUTRALAGENCYNUM,
                     SUM(DECODE(INVRATINGCODE, 4, C)) REDUCEAGENCYNUM,
                     SUM(DECODE(INVRATINGCODE, 5, C)) SELLAGENCYNUM
                FROM (SELECT a.SECUCODE,
                             a.TRADINGCODE,
                             b.SECUABBR,
                             INVRATINGCODE,
                             COUNT(*) C
                        FROM TEXT_FORECASTRATING a
                        JOIN pub_securitiesmain b ON a.secucode = b.secucode
                       WHERE TRUNC(PUBDATE) >= ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)
                         AND a.SECUCODE != 0
                         AND INVRATINGCODE IN (1, 2, 3, 4, 5)
                       GROUP BY a.SECUCODE,
                                a.TRADINGCODE,
                                b.SECUABBR,
                                INVRATINGCODE)
               GROUP BY SECUCODE, TRADINGCODE, SECUABBR) A,
             (SELECT SECUCODE,MAX(T.PUBDATE) AS PUBDATE,
                     MAX(T.INDEXVAL) HIGHESTPRICE,
                     MIN(T.INDEXVAL) LOWESTPRICE,
                     TRUNC(AVG(T.INDEXVAL), 2) AVGPRICE
                FROM TEXT_PERFORMANCEFORECAST T
               WHERE T.PUBDATE > ADD_MONTHS(TRUNC(SYSDATE), CUR_MONTH)
                 AND T.INDEXCODE = 1190
               GROUP BY SECUCODE) B
       WHERE A.SECUCODE = B.SECUCODE(+);

  END LOOP;
  CLOSE CUR;
  COMMIT;
END PROC_UPDATE_COMPOSITERATING;