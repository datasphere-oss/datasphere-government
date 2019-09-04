package com.datasphere.government.datalineage.gsp.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datasphere.government.datalineage.gsp.ParseLineage;
import com.datasphere.government.datalineage.gsp.entity.ConnectEntity;

import java.sql.*;

/**
 * Created by jeq 27.
 */
public class OracleUtils {
    private static final Logger logger = LoggerFactory.getLogger(ParseLineage.class);

    public static Connection getConnect(String url,String userName ,String password){
        try{
//            Class.forName(DBConstant.ORACLE_CLASSNAME);
            Connection conn = DriverManager.getConnection(url, userName, password);
            logger.info("提示！oracle数据库连接成功");
            return  conn;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public static String getTableNames(String TableName,ConnectEntity connectEntity ) {
        String columnsInDb = "";
        try {
            String url = connectEntity.getUrl();
            String userName = connectEntity.getUserName();
            String password = connectEntity.getPassword();
            Connection conn  = getConnect(url, userName, password);
            String sql;
            if(connectEntity.getUrl().contains("oracle")) {/** jeq-单独处理oracle */
                TableName = TableName.toLowerCase();
                sql = "select * from "+TableName+" where rownum = 0";
                sql = sql.replace(TableName, "\""+TableName+"\"");
            } else {
                sql = "select * from " + TableName + " limit 0";
            }
            PreparedStatement ps = conn.prepareStatement(sql);
            System.err.println(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData() ;
            int colcount = rsmd.getColumnCount();
            for (int i = 1; i <= colcount; i++) {
                columnsInDb = columnsInDb+","+rsmd.getColumnName(i);
            }
            columnsInDb = columnsInDb.substring(1,columnsInDb.length());
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columnsInDb;
    }


    /**
     * 获取数据库中的存储过程
     */
    public static String getStoreFunctionInDb(String functionName,ConnectEntity connectEntity) {
        String storeSql = "";
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String url = connectEntity.getUrl();
            String userName = connectEntity.getUserName();
            String password = connectEntity.getPassword();
            String dbName = connectEntity.getDbName();
            conn = getConnect(url, userName, password);
            String sql = "select * from all_source where OWNER='"+dbName.toUpperCase()+"' and name='"+functionName.trim().toUpperCase()+"' ORDER BY line ASC";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                String text = rs.getString("TEXT");
                storeSql = storeSql +  text;
            }
            logger.info("提示！orcale的存储过程："+storeSql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return storeSql;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}