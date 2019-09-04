CREATE FUNCTION data_exchange2() RETURNS void AS $$
BEGIN
SELECT a.guid,a.sfqy,a.lrsj,y.xm,x.mc,x.ljdz,z..xm xgrsm,a.tb,
  (CASE WHEN a.sfqy = '1' THEN '开始' ELSE '停止' END) sfqyz)
    FROM t_fs a LEFT JOIN t_yb y ON a.lrr = y.yhid
LEFT JOIN
  t_fg z
ON z.yhid = a.xgr
LEFT JOIN  z_tgd x
ON a.url = x.guid
WHERE a.zt_d ='1';
END;
$$ LANGUAGE plpgsql;
