package com.datasphere.government.datalineage.gsp.Connection;

import java.sql.*;

import com.datasphere.government.datalineage.common.DBConstant;
import com.datasphere.government.datalineage.gsp.entity.ConnectEntity;


public class GreenplumUtils {
    public static Connection getConnect(String url,String userName ,String password){
        try {
            Class.forName(DBConstant.POSTGRESQL_CLASSNAME);
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    public static String getTableNames(String TableName,ConnectEntity connectEntity ) {
        String columnsInDb = "";
        Connection conn  = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String url = connectEntity.getUrl(); 
            String userName = connectEntity.getUserName();
            String password = connectEntity.getPassword();
            conn = getConnect(url, userName, password);
            String sql = "select * from "+TableName+" limit 0";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData() ;
            int colCount = metaData.getColumnCount();
            for (int i = 1; i <= colCount; i++) {
                columnsInDb = columnsInDb+","+metaData.getColumnName(i);
            }
            columnsInDb = columnsInDb.substring(1,columnsInDb.length());
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
            Connection conn  = getConnect(url, userName, password);
            String sqlssss = "select * from pg_proc where proname='"+functionName+"';";
            PreparedStatement pre = conn.prepareStatement(sqlssss);
            ResultSet rs = pre.executeQuery();
            while(rs.next()){
//                String proname = rs.getString("proname");
//                String proargnames = rs.getString("proargnames");
                 storeSql = rs.getString("prosrc");
            }
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
