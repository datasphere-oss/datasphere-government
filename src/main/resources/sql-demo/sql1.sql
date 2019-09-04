INSERT INTO ids.ids_app_game_rank_product_100_5_m SELECT
  b.standard_app_id,
  month_id month_id,
  b.standard_app_name,
  c.cate_id,
  c.NAME,
  develop_company_id,
  0 publish_compary_id,
  uv,
  0 uv_ratio,
  pv_perman_perday,
  duration_perman_perday,
  rn,
  1
FROM
  (
    SELECT
      *
    FROM
      (
        SELECT
          month_id,
          standard_app_id,
          standard_app_name,
          uv,
          pv_perman_perday,
          duration_perman_perday duration_perman_perday
        FROM
          ids.ids_app_game_rank_product_5_m
        WHERE
          month_id = '''|| v_proc_date ||'''
      ) a
    WHERE
      rn <= 100
  ) b
JOIN (
  SELECT
    a.stand_app_id standard_app_id,
    a.cate_id,
    b.NAME,
    c.develop_company_id
  FROM
    (
      SELECT
        stand_app_id,
        max(cate_id) cate_id,
        sub_type
      FROM
        dim.dim_stand_category
      WHERE
        STATUS = 1
      AND mark = 1
      AND sub_type = 3
      GROUP BY
        1,
        3
    ) c
  JOIN dim.dim_app_category b ON a.cate_id = b.id
  JOIN dim.dim_stand_app c ON a.stand_app_id = c.app_id
) d ON d.standard_app_id = a.standard_app_id