/*
 * Copyright 2019, Huahuidata, Inc.
 * DataSphere is licensed under the Mulan PSL v1.
 * You can use this software according to the terms and conditions of the Mulan PSL v1.
 * You may obtain a copy of Mulan PSL v1 at:
 * http://license.coscl.org.cn/MulanPSL
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR
 * PURPOSE.
 * See the Mulan PSL v1 for more details.
 */

package com.datasphere.government.datalineage.common;

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
