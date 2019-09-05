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

package com.datasphere.government.common.dbms;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

@Service
public class DataSourceService {
  private static final Logger log = LoggerFactory.getLogger(DataSourceService.class);
  private HikariDataSource dataSource;

  /**
   * 初始化注入数据库连接池
   */
  @PostConstruct
  public void initialize() {
    log.info("Initialize DataSource ......");
    String propertyFileName = System.getProperty("user.dir")+File.separator+"dlresources"+File.separator+"hikari.properties";
    HikariConfig config = new HikariConfig(propertyFileName);
    dataSource = new HikariDataSource(config);
  }

  /**
   * 释放数据库连接池
   */
  @PreDestroy
  public void releaseResource() {
    log.info("Close DataSource!");
    if(dataSource != null) {
      dataSource.close();
    }
  }

  /**
   * 得到数据库连接
   * @return
   */
  public HikariDataSource getDataSource() {
    return dataSource;
  }

  class SecurityAccess {
    public void disopen() {

    }
  }
}
