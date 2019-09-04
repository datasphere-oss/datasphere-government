declare
       uName varchar(40);
       Age int;
    begin
       uName:='1';
       Age:=234;
       PROC_UPDATE_COMPOSITERATING(uName,Age);
       DBMS_OUTPUT.PUT_LINE(uName||'   '||Age);
    END;
    exit;