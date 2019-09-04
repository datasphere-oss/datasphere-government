package com.datasphere.government.dl.test;

public class DemoData {
    public static String test0 = "";
//1043 20 {ids,userid}
//CREATE FUNCTION message_deletes(ids varchar,userid varchar) RETURNS int AS $$
//    DECLARE
//    r RECORD;
//    del bool;
//    num int4 := 0;
//    sql "varchar";
//    BEGIN
//    sql := 'select id,receiveuserid,senduserid,senddelete,receivedelete from message where id in (' || ids || ')';
//    FOR r IN EXECUTE sql LOOP
//    del := false;
//    IF r.receiveuserid=userid and r.senduserid=userid THEN
//    del := true;
//    ELSEIF r.receiveuserid=userid THEN
//    IF r.senddelete=false THEN
//    update message set receivedelete=true where id = r.id;
//    ELSE
//    del := true;
//    END IF;
//    ELSEIF r.senduserid=userid THEN
//    IF r.receivedelete=false THEN
//    update message set senddelete=true where id = r.id;
//    ELSE
//    del := true;
//    END IF;
//    END IF;
//    IF del THEN
//    delete from message where id = r.id;
//    num := num + 1;
//    END IF;
//    END LOOP;
//  return num;
//    END;
//$$ LANGUAGE plpgsql;

//CREATE OR REPLACE FUNCTION test ()
//RETURNS integer AS $$
//BEGIN
//insert into a(id,name) select id,name from b;
//RETURN 0;
//END;
//$$ LANGUAGE plpgsql;
//
//
//    BEGIN
//    insert into a(id,name) select id,name from b;
//    RETURN 0;
//    END;


    public static String test1 = "CREATE OR REPLACE FUNCTION bj_yl_001 ()\n" +
            "RETURNS integer AS $$\n" +
            "BEGIN\n" +
            "insert into jxm(id,name,age,createtime) \n" +
            "select id,name,age,createtime from jxm_test;\n" +
            "RETURN 0;\n" +
            "END;\n" +
            "$$ LANGUAGE plpgsql;";

    public static String test2 = "BEGIN\n" +
            "insert into jxm(id,name,age,createtime) \n" +
            "select id,name,age,createtime from jxm_test;\n" +
            "RETURN 0;\n" +
            "END;\n";

//    create table DATALLIANCE.YL_TECHNOLYGE_DEPARTMENT(
//        id NUMBER,
//        name VARCHAR(32),
//        sex VARCHAR(32),
//        age NUMBER,
//        address VARCHAR(32),
//        iphone VARCHAR(32),
//        education VARCHAR(32),
//        works VARCHAR(32),
//        introduction VARCHAR2(32)
//    );
}

//LineageParseResult{
//        userId=null
//        , tenantId=null
//        , workspaceId=null
//        , schemaName='null'
//        , optType='null'
//        , command='null'
//        , megrezLineageDto=com.datalliance.governor.gsp.dlineage.entity.MegrezLineageDto@7b531193
//        , inputList=[com.datalliance.governor.gsp.dlineage.entity.Entity@20d241ea]
//        , fieldList=[null.JXM_TEST.ID, null.JXM.ID, null.JXM_TEST.NAME,
//        null.JXM.NAME, null.JXM_TEST.AGE, null.JXM.AGE,
//        null.JXM_TEST.CREATETIME, null.JXM.CREATETIME]
//        , outputList=[com.datalliance.governor.gsp.dlineage.entity.Entity@52874961]
//        }
//
//LineageParseResult{
//        userId=null
//        , tenantId=null
//        , workspaceId=null
//        , schemaName='null'
//        , optType='null'
//        , command='null'
//        , megrezLineageDto=com.datalliance.governor.gsp.dlineage.entity.MegrezLineageDto@15e756e8
//        , inputList=[com.datalliance.governor.gsp.dlineage.entity.Entity@5108d9c]
//        , fieldList=[null.B.ID, null.A.ID, null.B.NAME, null.A.NAME]
//        , outputList=[com.datalliance.governor.gsp.dlineage.entity.Entity@1524bd66]
//        }
