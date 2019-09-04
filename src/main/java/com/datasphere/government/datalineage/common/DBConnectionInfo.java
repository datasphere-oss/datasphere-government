package com.datasphere.government.datalineage.common;

/**
 * 关系型数据库
 */
public class DBConnectionInfo {
  private String dbType; //数据库类型
  private String hostIp; // 主机IP地址
  private String hostPort; // 主机Port
  private String userName; // 用户名
  private String userPassword; // 密码
  private String databaseName; // 数据库名称 或 Oracle的SID

  public String getDbType() {
    return dbType;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  public String getHostIp() {
    return hostIp;
  }

  public void setHostIp(String hostIp) {
    this.hostIp = hostIp;
  }

  public String getHostPort() {
    return hostPort;
  }

  public void setHostPort(String hostPort) {
    this.hostPort = hostPort;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }
}
