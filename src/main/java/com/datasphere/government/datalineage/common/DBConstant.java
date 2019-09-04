package com.datasphere.government.dl.common;

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
