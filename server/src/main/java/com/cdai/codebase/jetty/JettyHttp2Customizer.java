package com.cdai.codebase.jetty;

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyServerCustomizer;
import org.springframework.stereotype.Component;

/**
 */
@Component
public class JettyHttp2Customizer implements EmbeddedServletContainerCustomizer {

    private final ServerProperties prop;

    @Autowired
    public JettyHttp2Customizer(ServerProperties prop) {
        this.prop = prop;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        ((JettyEmbeddedServletContainerFactory) container).addServerCustomizers((JettyServerCustomizer) server -> {
            if (prop.getSsl() != null && prop.getSsl().isEnabled()) {
                ServerConnector conn = (ServerConnector) server.getConnectors()[0];
                SslContextFactory sslCtxFactory = conn.getConnectionFactory(SslConnectionFactory.class).getSslContextFactory();
                HttpConfiguration conf = conn.getConnectionFactory(HttpConnectionFactory.class).getHttpConfiguration();
                sslCtxFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);
                sslCtxFactory.setUseCipherSuitesOrder(true);

                SslConnectionFactory sslConnFactory = new SslConnectionFactory(sslCtxFactory, "alpn");
                ALPNServerConnectionFactory alpnConnFactory = new ALPNServerConnectionFactory("h2", "h2-17", "h2-16", "h2-15", "h2-14");
                HTTP2ServerConnectionFactory http2ConnFactory = new HTTP2ServerConnectionFactory(conf);
                ConnectionFactory[] connFactory = new ConnectionFactory[]{ sslConnFactory, alpnConnFactory, http2ConnFactory };

                ServerConnector newConn = new ServerConnector(server, connFactory);
                newConn.setPort(conn.getPort());
                server.setConnectors(new Connector[]{newConn});
            }
        });
    }
}
