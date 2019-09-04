package com.datasphere.government.datalineage.gsp.Connection;

import java.sql.*;

import com.datasphere.government.datalineage.common.DBConstant;
import com.datasphere.government.datalineage.gsp.entity.ConnectEntity;

/**
 * Created by jeq 28.
 */
public class MysqlUtils {
    public static Connection getConnect(String url, String userName , String password){
        try{
            Class.forName(DBConstant.MYSQL_CLASSNAME);
            Connection conn = DriverManager.getConnection(url, userName, password);
            return  conn;
        } catch (Exception e){
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
            Connection conn = getConnect(url, userName, password);
            String sql = "select * from "+TableName+" limit 0";
            PreparedStatement ps = conn.prepareStatement(sql);
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
        try {
            String url = connectEntity.getUrl();
            String userName = connectEntity.getUserName();
            String password = connectEntity.getPassword();
            Connection conn = DriverManager.getConnection(url, userName, password);
            String sqlssss = "select * from all_source where OWNER='ZYT' and name='P_TEST' ORDER BY line ASC";
            PreparedStatement ps = conn.prepareStatement(sqlssss);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.println("===============》"+rs.getString("TEXT"));
                String text = rs.getString("TEXT");
                storeSql = storeSql +  text;
            }
            System.out.println("mysql的存储过程"+storeSql);
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storeSql;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
