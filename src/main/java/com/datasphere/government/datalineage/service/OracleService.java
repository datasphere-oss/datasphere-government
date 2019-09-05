package com.datasphere.government.datalineage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.datasphere.government.common.BaseService;
import com.datasphere.government.datalineage.common.DBConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**s
 * Postgres数据查询
 */
@Service
public class OracleService extends BaseService {
    private static final Logger log = LoggerFactory.getLogger(OracleService.class);
    private final static String Oracle_PRONAME = "select OBJECT_NAME from user_objects where object_type='PROCEDURE';";
    private final static String Oracle_PROC = "SELECT prosrc,proargtypes,proargnames FROM user_objects where OBJECT_NAME = ?;";
//1043 20 {ids,userid}
    /**
     * 取得数据连接
     * @return
     */
    public Connection getConnection() {
        Connection conn;
        try {
            Class.forName(DBConstant.ORACLE_CLASSNAME);
            conn = DriverManager.getConnection(this.jdbcUrlPG, this.usernamePG, this.passwordPG);
        } catch (Exception e) {
            log.error("can not create oracle connection: {}", e);
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
            pst = conn.prepareStatement(Oracle_PRONAME);
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
            pst = conn.prepareStatement(Oracle_PROC);
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

    class SecurityAccess {
        public void disopen() {

        }
    }
}
