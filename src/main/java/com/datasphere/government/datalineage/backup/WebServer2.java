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

package com.datasphere.government.datalineage.backup;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.GzipHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/*
 * 基于Server 的 WebServer
 */
public class WebServer2 extends Server {
    private static Logger logger;
    // 成员
    private static int startHttpPort;
    private static int endHttpPort;
    private int httpPort;
    private static String bindingInterface;

    static {
        WebServer2.logger = Logger.getLogger((Class)WebServer2.class);
        WebServer2.bindingInterface = "localhost";
        WebServer2.startHttpPort = 8080;
        WebServer2.endHttpPort = 65535;
    }

    public WebServer2(final String bindingInterface) {
        this.httpPort = -1;
        this.bindingInterface = bindingInterface;
        this.readPortsFromSysProps();
        this.httpPort = findFreePort(WebServer2.startHttpPort);
        if (WebServer2.logger.isInfoEnabled()) {
            WebServer2.logger.info((Object)("HTTP Server will start on : " + this.httpPort));
        }
        final ContextHandlerCollection contexts = new ContextHandlerCollection();
        this.setHandler((Handler)contexts);
    }

    private void readPortsFromSysProps() {
        final String httpPort = "8084";//System.getProperty("com.datalliance.config.httpPort");
        if (httpPort != null && !httpPort.isEmpty()) {
            try {
                WebServer2.startHttpPort = Integer.parseInt(httpPort);
            } catch (NumberFormatException ex) {}
        }
        if (WebServer2.logger.isDebugEnabled()) {
            WebServer2.logger.debug((Object)("Using :" + WebServer2.startHttpPort + " as HTTP port."));
        }
    }

    public static void main(String args[]) {
        final WebServer2 server = new WebServer2("localhost");
        try {
//            File file = new File("./");
//            String a = file.getAbsolutePath();
//            String path = a.substring(0, a.length()-1 )+"src/main/resources/static/";
            server.addResourceHandler("/", "./", true, new String[]{"index.html"});
            server.start();
        }
        catch (Exception e) {
            WebServer2.logger.error((Object)e);
        }
        System.err.println("Jetty Server is started at port:"+startHttpPort);
    }

    public void run() {
        /*Server server = runServer();*/
        WebServer2 server = new WebServer2("localhost");
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            boolean flag = true;
            while (flag) {
                ++startHttpPort;
                try {
                    server = runServer();
                    server.start();
                    server.join();
                    flag = false;
                } catch (Exception e1) {
                    flag = true;
                }
            }
        }

        System.err.println("Jetty Server is started at port:"+startHttpPort);
    }

    private WebServer2 runServer() {
        WebServer2 server = new WebServer2("localhost");
        try {
//            File file = new File("./");
//            String a = file.getAbsolutePath();
//            String path = a.substring(0, a.length()-1 )+"src/main/resources/static/";
            server.addResourceHandler("/datalinkage", "./", true, new String[]{"index.html"});
            server.start();
        }
        catch (Exception e) {
            WebServer2.logger.error((Object)e);
        }
        /*
        Server server = new Server(startHttpPort); // 服务器的监听端口  
        WebAppContext context = new WebAppContext();// 关联一个已经存在的上下文  
        // 设置描述符位置  
//        context.setDescriptor("/Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/src/main/webapp/WEB-INF/web.xml");
        File file = new File("./");
        String a = file.getAbsolutePath();
        System.out.println("*******************");
        System.out.println(a);
        String path = a.substring(0, a.length()-1 )+"src/main/resources/static/";
        System.out.println(path);
//      /Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/.
//      /Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/src/main/resources/static/
//      /Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/target/.
//      /Users/jiaenqiu/Downloads/0-YunLian/workspace-db/DataLinkage/target/src/main/resources/static/
        // 设置Web内容上下文路径  
        context.setResourceBase(path);
        context.setContextPath("/datalinkage");// 设置上下文路径  
        context.setParentLoaderPriority(true);
        server.setHandler(context);
        */
        return server;
    }

    public void addResourceHandler(final String contextPath, final String resourceBase, final boolean listDirectories, final String[] welcomeFiles) {
        final ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(listDirectories);
        resourceHandler.setWelcomeFiles(welcomeFiles);
        resourceHandler.setResourceBase(resourceBase);
        final GzipHandler gzipHandlerRES = new GzipHandler();
        gzipHandlerRES.setMimeTypes("text/html,text/plain,text/xml,text/css,application/javascript,text/javascript,text/x-javascript,application/x-javascript");
        gzipHandlerRES.setHandler((Handler)resourceHandler);
        this.addContextHandler(contextPath, (Handler)gzipHandlerRES);
    }

    public void addContextHandler(final String contextPath, final Handler handler) {
        final ContextHandler context = new ContextHandler();
        context.setContextPath(contextPath);
        context.setResourceBase("./" + contextPath);
        context.setHandler(handler);
        this.addContextForHandler(contextPath, context);
    }

    private void addContextForHandler(final String contextPath, final ContextHandler context) {
        final Handler[] handlers = this.getHandlers();
        final int len = (handlers == null) ? 0 : handlers.length;
        final Handler[] newHandlers = new Handler[len + 1];
        if (len > 0) {
            System.arraycopy(handlers, 0, newHandlers, 0, handlers.length);
        }
        newHandlers[len] = (Handler)context;
        final ContextHandlerCollection newContexts = new ContextHandlerCollection();
        newContexts.setHandlers(newHandlers);
        this.setHandler((Handler)newContexts);
    }


    public boolean available(final int start, final int port) {
        if (port < start || port > WebServer2.endHttpPort) {
            throw new IllegalArgumentException("Invalid start port: " + port);
        }
        if (this.httpPort != -1 && port == this.httpPort) {
            return false;
        }
        Socket ss = null;
        Enumeration<NetworkInterface> nets = null;
        try {
            nets = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException e) {
            WebServer2.logger.error((Object)"Could not get any network interfaces: ", (Throwable)e);
            throw new RuntimeException("No network interfaces available for web server connection");
        }
        for (final NetworkInterface netint : Collections.list(nets)) {
            final Enumeration<InetAddress> addrs = netint.getInetAddresses();
            for (final InetAddress addr : Collections.list(addrs)) {
                if (!"/127.0.0.1".equals(addr.toString())) {
                    if (!("/" + this.bindingInterface).equals(addr.toString())) {
                        continue;
                    }
                }
                try {
                    ss = new Socket(addr, port);
                    return false;
                }
                catch (IOException ex) {}
                finally {
                    if (ss != null) {
                        try {
                            ss.close();
                        }
                        catch (IOException ex2) {}
                    }
                }
            }
        }
        return true;
    }

    public int findFreePort(final int start) {
        for (int port = start; port < WebServer2.endHttpPort; ++port) {
            if (this.available(start, port)) {
                return port;
            }
        }
        return -1;
    }

    public void shutdown() throws Exception {
        this.stop();
    }
}
