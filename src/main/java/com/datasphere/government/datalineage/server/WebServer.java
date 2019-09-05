package com.datasphere.government.datalineage.server;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.GzipHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class WebServer extends Server {
    SelectChannelConnector connector;// 通道连接器
	private int httpPort = 8081;

	public WebServer(int httpPort) {
		this.connector = new SelectChannelConnector();
		this.connector.setMaxIdleTime(30000);
		this.connector.setPort(httpPort);
		this.setConnectors(new Connector[] { this.connector });
        final ContextHandlerCollection contexts = new ContextHandlerCollection();
        this.setHandler((Handler)contexts);
    }

    public WebServer run(WebServer server) {
        try {
            server.addResourceHandler("/", "./", true, new String[] { "index.html" });
            server.start();
            server.join();
        } catch (Exception e) {
            boolean flag = true;
            while (flag) {
                ++httpPort;
                try {
                    server = new WebServer(httpPort);
                    server.addResourceHandler("/", "./", true, new String[] { "index.html" });
                    server.start();
                    server.join();
                    flag = false;
                } catch (Exception e1) {
                    flag = true;
                }
            }
        }
        System.err.println("Jetty Server is started at port:"+httpPort);
        return server;
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
    
    public void addServletContextHandler(final String path, final HttpServlet servlet) {
        final ServletContextHandler ctx = new ServletContextHandler(1);
        ctx.setContextPath("/");
        ctx.addServlet(new ServletHolder((Servlet)servlet), path);
        this.addContextForHandler(path, (ContextHandler)ctx);
    }

    class SecurityAccess {
        public void disopen() {

        }
    }
}
