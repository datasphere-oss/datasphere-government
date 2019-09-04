package demo;
//package demos.dlineage;
//
//import gudusoft.gsqlparser.EDbVendor;
//import org.junit.Test;
//
////import com.datalliance.governor.gsp.dlineage.ParseLineage;
////import com.datalliance.governor.gsp.dlineage.entity.ConnectEntity;
//
///**
// * Created by jeq 27.
// */
//public class DataFlowAnalyzerTest {
//    /**
//     * 1测试点：无血缘测试  select 包含 resultset
//     */
//    @Test
//    public void test1() {
////        String sqls = "select id,name,age from my_test;";
////        ConnectEntity connectEntity = new ConnectEntity();
////        connectEntity.setUrl("jdbc:oracle:thin:@117.107.241.79:1528:helowin");
////        connectEntity.setUserName("datalliance");
////        connectEntity.setPassword("datalliance");
////        connectEntity.setDbName("DATALLIANCE");
////        DataLinkage(sqls,EDbVendor.dbvoracle,connectEntity);//dbvgreenplum
//        // jeq-只要在DlineageEntity中添加resultset属性即可正确解析
//    }
//
//    /**
//     * 2测试点：简单血缘测试
//     */
//    @Test
//    public void test2() {
//        String sqls = "insert into daas_test(id,name,age) select * from my_test;";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setUrl("jdbc:oracle:thin:@117.107.241.79:1528:helowin");
//        connectEntity.setUserName("datalliance");
//        connectEntity.setPassword("datalliance");
//        connectEntity.setDbName("DATALLIANCE");
////        DataLinkage(sqls,EDbVendor.dbvoracle,connectEntity);//dbvgreenplum
//    }
//
//    /**
//     * 3测试点：无血缘测试  drop <dlineage/>
//     */
//    @Test
//    public void test3() {
//        String sqls = "drop table b;";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setUrl("jdbc:oracle:thin:@117.107.241.79:1528:helowin");
//        connectEntity.setUserName("datalliance");
//        connectEntity.setPassword("datalliance");
//        connectEntity.setDbName("DATALLIANCE");
////        DataLinkage(sqls,EDbVendor.dbvoracle,connectEntity);//dbvgreenplum
//    }
//
//    /**
//     * 4测试点：简单血缘测试 先插入后查询
//     */
//    @Test
//    public void test4() {
//        String sqls = "insert into daas_test select id from b; select id,name,age from my_test;";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setUrl("jdbc:oracle:thin:@117.107.241.79:1528:helowin");
//        connectEntity.setUserName("datalliance");
//        connectEntity.setPassword("datalliance");
//        connectEntity.setDbName("DATALLIANCE");
////        DataLinkage(sqls,EDbVendor.dbvoracle,connectEntity);//dbvgreenplum
//    }
//
//    /**
//     * 测试点：简单血缘测试 先插入后查询，做提取多条SQL处理
//     */
//    @Test
//    public  void test5() {
//        String sqls = "insert into tablea select name,age from  b; select name,age from  tablea;";
//        String dbName = "lcc_gp";
////        DataLinkage(sqls,EDbVendor.dbvgreenplum,dbName);
//    }
//
//
//    /**
//     * 测试点：简单血缘测试 先插入后查询，做提取多条SQL处理 存储过程
//     */
//    @Test
//    public  void test6() {
//        String dbName = "lcc_gp";
////        DataLinkage(PRODUCER_SQL,EDbVendor.dbvoracle,dbName);
//    }
//
//
//    /**
//     * 测试点：只有insert  <dlineage/>
//     */
//    @Test
//    public  void test7() {
//        String sqls = "insert into tablea values(1,2);";
//        String dbName = "lcc_gp";
////        DataLinkage(sqls,EDbVendor.dbvgreenplum,dbName);
//    }
//
//
//    /**
//     * 测试点：简单血缘测试,不写列名
//     */
//    @Test
//    public  void test8() {
//        String sqls = "insert into table_a select * from table_b;";
//        String dbName = "lcc_gp";
////        DataLinkage(sqls,EDbVendor.dbvgreenplum,dbName);
//    }
//
//    /**
//     * 测试点：简单血缘测试,不写列名 greenplum
//     */
//    @Test
//    public  void test9() {
//        String sqls = "insert into public.user select * from public.user2;";
//        String dbName = "lcc_gp";
//        String url = "jdbc:postgresql://47.92.38.137:8006/lcc_gp";
//        String username = "lbq_test";
//        String password = "lbq_test@dtwave";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setDbName(dbName);
//        connectEntity.setUrl(url);
//        connectEntity.setUserName(username);
//        connectEntity.setPassword(password);
//
////        DataLinkage(sqls,EDbVendor.dbvgreenplum,connectEntity);
//    }
//
//    /**
//     * 10测试点： greenplum 存储过程
//     */
//    @Test
//    public void test10() {
//        String url = "jdbc:oracle:thin:@117.107.241.79:1528:helowin";
//        String username = "datalliance";
//        String password = "datalliance";
//        String dbName = "DATALLIANCE";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setDbName(dbName);
//        connectEntity.setUrl(url);
//        connectEntity.setUserName(username);
//        connectEntity.setPassword(password);
////        DataLinkage(GREENPLUM_PRODUCER2,EDbVendor.dbvpostgresql,connectEntity);
//    }
//
//    /**
//     * 11测试点：oracle 存储过程
//     */
//    @Test
//    public void test11() {
//        String url = "jdbc:oracle:thin:@117.107.241.79:1528:helowin";
//        String username = "datalliance";
//        String password = "datalliance";
//        String dbName = "DATALLIANCE";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setDbName(dbName);
//        connectEntity.setUrl(url);
//        connectEntity.setUserName(username);
//        connectEntity.setPassword(password);
////        DataLinkage(OracleSQLS.ORACLE_SQL1,EDbVendor.dbvoracle,connectEntity);
////        DataLinkage(OracleSQLS.aaa,EDbVendor.dbvoracle,connectEntity);
//    }
//
//    /**
//     * 测试点：完整的oracle测试
//     */
//    @Test
//    public void test12() {
//        String sql = "call PROC_UPDATE_COMPOSITERATING()";
//        String url = "jdbc:oracle:thin:@117.107.241.79:1528:helowin";
//        String username = "datalliance";
//        String password = "datalliance";
//        String dbName = "DATALLIANCE";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setDbName(dbName);
//        connectEntity.setUrl(url);
//        connectEntity.setUserName(username);
//        connectEntity.setPassword(password);
//
//        ParseLineage parseLineage = new ParseLineage();
////        parseLineage.getStoreFunctionInDb(sql,EDbVendor.dbvoracle,connectEntity);
//    }
//
//    /**
//     * 测试点：完整的oracle测试 不使用call调用
//     */
//    @Test
//    public void test13() {
//        String sql = " declare\n" +
//                "       uName varchar(40);\n" +
//                "       Age int;\n" +
//                "    begin\n" +
//                "       uName:='1';\n" +
//                "       Age:=234;\n" +
//                "       PROC_UPDATE_COMPOSITERATING(uName,Age);\n" +
//                "       DBMS_OUTPUT.PUT_LINE(uName||'   '||Age);\n" +
//                "    END;\n" +
//                "    exit;";
////        String url = "jdbc:oracle:thin:@47.97.11.138:1522:xe";
////        String username = "system";
////        String password = "oracle";
////        String dbName = "ZYT";
//        String url = "jdbc:oracle:thin:@117.107.241.79:1528:helowin";
//        String username = "datalliance";
//        String password = "datalliance";
//        String dbName = "DATALLIANCE";
//        ConnectEntity connectEntity = new ConnectEntity();
//        connectEntity.setDbName(dbName);
//        connectEntity.setUrl(url);
//        connectEntity.setUserName(username);
//        connectEntity.setPassword(password);
//
//        ParseLineage parseLineage = new ParseLineage();
////        parseLineage.getStoreFunctionInDb(sql,EDbVendor.dbvoracle,connectEntity);
//    }
//
//    public void DataLinkage(String sqls,EDbVendor dbType,ConnectEntity connectEntity) {
////        ParseLineage parseLineage = new ParseLineage();
////        parseLineage.parseSqlsLineage(sqls,dbType, connectEntity);
//    }
//}