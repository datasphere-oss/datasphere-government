package producre;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TGSqlParser;
import org.junit.Test;

/**
 * Created by jeq 29.
 */
public class Check {

    @Test
    public void testCheckSyntax(){
        String sql = "create table emp1(id1 int,name1 varchar2(200),\n"
                + " money1 double(10,2),\n"
                + " PRIMARY KEY ( id1 ));";
        TGSqlParser mysqlSqlParser = new TGSqlParser(EDbVendor.dbvmysql);
        mysqlSqlParser.sqltext = sql;
        int rs = mysqlSqlParser.checkSyntax();
        if(rs == 0){
            System.out.println("语法正确！");
        }else{
            System.out.println("语法错误："+mysqlSqlParser.getErrormessage());
        }
    }


    @Test
    public void testProducre(){
        String sql = "CREATE OR REPLACE\n" +
                "procedure     p_add\n" +
                "as\n" +
                "begin\n" +
                "INSERT INTO \"ZYT\".\"STUDENTS2\" (\"IDS\",\"USERNAMES\",\"USERAGES\") SELECT \"ID\",USERNAME,USERAGE from \"ZYT\".\"STUDENTS\";\n" +
                "end;";
        TGSqlParser mysqlSqlParser = new TGSqlParser(EDbVendor.dbvmysql);
        mysqlSqlParser.sqltext = sql;
        int rs = mysqlSqlParser.checkSyntax();
        if(rs == 0){
            System.out.println("语法正确！");
        }else{
            System.out.println("语法错误："+mysqlSqlParser.getErrormessage());
        }
    }

}
