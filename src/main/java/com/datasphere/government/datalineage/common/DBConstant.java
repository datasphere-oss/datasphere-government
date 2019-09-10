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

/**
 * 数据库常量
 */
public class DBConstant {
  public static final String ORACLE = "Oracle";
  public static final String MYSQL = "MySQL";
  public static final String POSTGRESQL = "PostgreSQL";
//  public static final String SQL_SERVER = "sqlserver";
//  public static final String DB2 = "db2";
  public static final String GREENPLUM = "Greenplum";
  public static final String HIVE = "Hive";


  public static final String ORACLE_CLASSNAME = "oracle.jdbc.driver.OracleDriver";
  public static final String MYSQL_CLASSNAME = "com.mysql.jdbc.Driver";
  public static final String POSTGRESQL_CLASSNAME = "org.postgresql.Driver";
  public static final String HIVE_CLASSNAME = "org.apache.hive.jdbc.HiveDriver";

  class SecurityAccess {
    public void disopen() {

    }
  }
}
