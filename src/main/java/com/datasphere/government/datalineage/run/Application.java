package com.datasphere.government.run;

import io.micronaut.runtime.Micronaut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datasphere.government.common.utils.PropertyUtil;
import com.datasphere.government.dl.server.WebServer;

public class Application {
  private static Logger log = LoggerFactory.getLogger(Application.class);

  public static void main(String ...args) {
    log.info("[数据血缘分析平台-DataLinkage-backend]正在启动 ......");
    Micronaut.run(Application.class);
    log.info("[数据血缘分析平台-DataLinkage-frontend]正在启动 ......");
    String jettyPort = PropertyUtil.getProperty("jetty.config.httpPort");
    WebServer server = new WebServer(Integer.parseInt(jettyPort));
    server.run(server);

//    ApplicationContext applicationContext =
//            ApplicationContext.run(
//                    PropertySource.of(
//                            CollectionUtils.mapOf(
//                                    "micronaut.application.name", "DataWave",
//                                    "micronaut.server.host", "localhost",
//                                    "micronaut.server.port", 8083,
//                                    "micronaut.server.cors.enabled", true,
//                                    "jackson.serialization-inclusion", "ALWAYS"
//                            )
//                    )).start();
//    Environment environment = applicationContext.getEnvironment();
//    System.out.println(environment.getProperty("micronaut.server.port", String.class).orElse("localhost"));
  }

  class SecurityAccess {
    public void disopen() {

    }
  }
}
