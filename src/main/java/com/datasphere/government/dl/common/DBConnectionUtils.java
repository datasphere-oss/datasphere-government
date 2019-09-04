package com.datasphere.government.dl.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 数据连接
 */
public class DBConnectionUtils {
    private static final Logger log = LoggerFactory.getLogger(DBConnectionUtils.class);

    /**
     * 取得数据连接
     */
    public static Connection getConnection(DBConnectionInfo connInfo) {
        Connection conn = null;
        String url;
        try {
            switch (connInfo.getDbType()) {
                case DBConstant.ORACLE:
                    Class.forName(DBConstant.ORACLE_CLASSNAME);
                    url = "jdbc:oracle:thin:@" + connInfo.getHostIp() + ":" + connInfo.getHostPort() + ":" +
                                    connInfo.getDatabaseName();
                    conn = DriverManager.getConnection(url,connInfo.getUserName(),connInfo.getUserPassword());
                    break;
                case DBConstant.MYSQL:
                    Class.forName(DBConstant.MYSQL_CLASSNAME);
                    url = "jdbc:mysql://" + connInfo.getHostIp() + ":" + connInfo.getHostPort() + "/" +
                                    connInfo.getDatabaseName()+"?useUnicode=true&characterEncoding=UTF-8";

                    conn = DriverManager.getConnection(url,connInfo.getUserName(),connInfo.getUserPassword());
                    break;
                case DBConstant.POSTGRESQL:
                    Class.forName(DBConstant.POSTGRESQL_CLASSNAME);
                    url = "jdbc:postgresql://" + connInfo.getHostIp() + ":" +
                                    connInfo.getHostPort() + "/" + connInfo.getDatabaseName();
                    conn = DriverManager.getConnection(url,connInfo.getUserName(),connInfo.getUserPassword());
                    break;
            }
        } catch (Exception e) {
            log.error("can not create connection: {}", e);
            conn = null;
        }
        return conn;
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
