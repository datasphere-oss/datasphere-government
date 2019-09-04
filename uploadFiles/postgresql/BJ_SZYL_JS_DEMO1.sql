CREATE FUNCTION data_exchange1(subtotal real) RETURNS real AS $$
BEGIN
  insert into
    YL_TECHNOLYGE_DEPARTMENT(id,name,sex,age,address,iphone,education,works,introduction)
      select
        id,name,sex,age,address,iphone,education,works,introduction
      from YL_DEPARTMENT_DEPARTMENT;
END;
$$ LANGUAGE plpgsql;