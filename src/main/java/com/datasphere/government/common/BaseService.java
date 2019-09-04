package com.datasphere.government.common.corebase;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.datasphere.government.common.database.MyBatisSqlSessionFactoryService;
import com.datasphere.government.common.utils.PropertyUtil;

@Singleton
public class BaseService {
   @Inject
   protected MyBatisSqlSessionFactoryService sqlSessionFactoryService;

   //数据血缘 - 数据源配置
   protected String dbTypePG;
   protected String jdbcUrlPG;
   protected String usernamePG;
   protected String passwordPG;
   protected String schemaNamePG;
   protected String dbTypeMysql;
   protected String jdbcUrlMysql;
   protected String usernameMysql;
   protected String passwordMysql;
   protected String schemaNameMysql;
   protected String dbTypeOracle;
   protected String jdbcUrlOracle;
   protected String usernameOracle;
   protected String passwordOracle;
   protected String schemaNameOracle;
   protected String dbTypeHive;
   protected String jdbcUrlHive;
   protected String usernameHive;
   protected String passwordHive;
   protected String schemaNameHive;

   {
      this.dbTypePG = PropertyUtil.getProperty("datasource.pg.type");
      this.jdbcUrlPG = PropertyUtil.getProperty("datasource.pg.url");
      this.usernamePG = PropertyUtil.getProperty("datasource.pg.username");
   	  this.passwordPG = PropertyUtil.getProperty("datasource.pg.password");
   	  this.schemaNamePG = PropertyUtil.getProperty("datasource.pg.schema");
      this.dbTypeMysql = PropertyUtil.getProperty("datasource.mysql.type");
      this.jdbcUrlMysql = PropertyUtil.getProperty("datasource.mysql.url");
      this.usernameMysql = PropertyUtil.getProperty("datasource.mysql.username");
      this.passwordMysql = PropertyUtil.getProperty("datasource.mysql.password");
      this.schemaNameMysql = PropertyUtil.getProperty("datasource.mysql.schema");
      this.dbTypeOracle = PropertyUtil.getProperty("datasource.oracle.type");
      this.jdbcUrlOracle = PropertyUtil.getProperty("datasource.oracle.url");
      this.usernameOracle = PropertyUtil.getProperty("datasource.oracle.username");
      this.passwordOracle = PropertyUtil.getProperty("datasource.oracle.password");
      this.schemaNameOracle = PropertyUtil.getProperty("datasource.oracle.schema");
      this.dbTypeHive = PropertyUtil.getProperty("datasource.hive.type");
      this.jdbcUrlHive = PropertyUtil.getProperty("datasource.hive.url");
      this.usernameHive = PropertyUtil.getProperty("datasource.hive.username");
      this.passwordHive = PropertyUtil.getProperty("datasource.hive.password");
      this.schemaNameHive = PropertyUtil.getProperty("datasource.hive.schema");
      System.setProperty("jetty.config.httpPort", PropertyUtil.getProperty("jetty.config.httpPort"));
   }


}
