with tbl_replaced
as (
    
        select 
            launch_datetime
            as launch_datetime,
            
            uniqueid, 
            version,
            cfrom,
            tfrom,
            mobilecode,
            imei,
            resolution,
            platform,
            mobileversion,
            model,
            nettype ,
            ip,
            carriername,
            mac,
            ostype           
        from src_mobile.ht_user_log as srctable
        where hdfs_par = '__TODAY__'
            and length(uniqueid) = 36
            and dates = '__TODAY__'
            
 
)
INSERT INTO ana_clientact.zl_dsj_mid2_app_launch_record(
launch_datetime,
    uniqueid, 
    version,
    cfrom,
    tfrom,
    mobilecode,
    imei,
    resolution,
    platform,
    mobileversion,
    model,
    nettype ,
    ip,
    carriername,
    mac,
    ostype
)
SELECT 
    launch_datetime,
    uniqueid, 
    version,
    cfrom,
    tfrom,
    mobilecode,
    imei,
    resolution,
    platform,
    mobileversion,
    model,
    nettype ,
    ip,
    carriername,
    mac,
    ostype
FROM tbl_replaced;

INSERT OVERWRITE ana_clientact.zl_dsj_mid2_app_launch_record partition (hdfs_par='__TODAY__')
SELECT 
    launch_datetime,
    uniqueid, 
    version,
    cfrom,
    tfrom,
    mobilecode,
    imei,
    resolution,
    platform,
    mobileversion,
    model,
    nettype ,
    ip,
    carriername,
    mac,
    ostype
FROM tbl_replaced;
