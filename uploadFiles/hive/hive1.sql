DECLARE
start_time VARCHAR(20);
end_time VARCHAR(20);
return_code INT;
BEGIN
start_time = SYSDATE || '';
SELECT COUNT(1) FROM liuxiaowen.lxw1234;
return_code = SQLCODE;
end_time = SYSDATE || '';
INSERT INTO log (`start_time`,`end_time`,`return_code`)
VALUES(start_time,end_time,return_code);
EXCEPTION WHEN OTHERS THEN
return_code = SQLCODE;
end_time = SYSDATE || '';
INSERT INTO log (`start_time`,`end_time`,`return_code`)
VALUES(start_time,end_time,return_code);
DBMS_OUTPUT.PUT_LINE('SQL execute error,return code : ' || return_code);
END