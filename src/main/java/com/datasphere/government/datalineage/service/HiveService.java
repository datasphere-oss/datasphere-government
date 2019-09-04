package com.datasphere.government.dl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datasphere.government.common.BaseService;
import com.datasphere.government.dl.common.DBConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hive数据查询
 */
@Service
public class HiveService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(HiveService.class);
    private final static String Hive_PRONAME = "SELECT pg_proc.proname,pg_type.typname,pg_proc.pronargs FROM pg_proc JOIN pg_type " +
            "ON (pg_proc.prorettype = pg_type.oid) WHERE pg_type.typname != 'void' " +
            "AND pronamespace = (SELECT pg_namespace.oid FROM pg_namespace WHERE nspname = 'public');";
    private final static String Hive_PROC = "SELECT prosrc,proargtypes,proargnames FROM pg_catalog.pg_proc where proname = ?;";
//1043 20 {ids,userid}

    /**
     * 取得数据连接
     */
    public Connection getConnection() {
        Connection conn;
        try {
            Class.forName(DBConstant.HIVE_CLASSNAME);
            conn = DriverManager.getConnection(this.jdbcUrlPG, this.usernamePG, this.passwordPG);
        } catch (Exception e) {
            log.error("can not create hive connection: {}", e);
            conn = null;
        }
        return conn;
    }

    public List<String> listAllStoredProcedure() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();
        try {
            conn = getConnection();
            pst = conn.prepareStatement(Hive_PRONAME);
            rs = pst.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (Exception e) {
            log.error("error in postgres: {}", e);
            result = null;
        } finally {
            close(conn, pst, rs);
        }
        return result;
    }

    public Map<String, String> listStoredProcedureByName(String proname) {
        Map<String, String> map = new HashMap<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pst = conn.prepareStatement(Hive_PROC);
            pst.setString(1, proname);
            rs = pst.executeQuery();
            while (rs.next()) {
                map.put("prosrc", rs.getString(1));
                map.put("proargtypes", rs.getString(2));
                map.put("proargnames", rs.getString(3));//{ids,userid}
            }
        } catch (Exception e) {
            log.error("error in postgres: {}", e);
            map = null;
        } finally {
            close(conn, pst, rs);
        }
        return map;
    }

    private void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Connection conn;
        PreparedStatement psmt;
        ResultSet rs;
        try {
            Class.forName(DBConstant.POSTGRESQL_CLASSNAME);
            String jdbcUrl = "jdbc:postgresql://117.107.241.79:5432/catalog_db";
            String username = "postgres";
            String password = "DataCatalogCenter!123456";
            String schemaName = "public";
            conn = DriverManager.getConnection(jdbcUrl, username, password);
            conn.setSchema(schemaName);
            psmt = conn.prepareStatement("select * from a");
            rs = psmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int colCount = metaData.getColumnCount();
            for (int i = 0; i < colCount; ++i) {
                final int colIndex = i + 1;
                final int colType = metaData.getColumnType(colIndex);
                String colName = metaData.getColumnName(colIndex);
                String colTypeName = metaData.getColumnTypeName(colIndex);
                System.err.println(colIndex+"   "+colName+"     "+colTypeName+"     "+colType);
            }
        } catch (Exception e) {
            log.error("can not create oracle connection: {}", e);
            conn = null;
        }
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
