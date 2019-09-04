BEGIN
  INSERT INTO TABLE testA PARTITION(create_time='2015-07-11') select id, name, area from testB where id = 1;
END;
