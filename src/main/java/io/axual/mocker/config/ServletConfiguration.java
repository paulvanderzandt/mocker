package io.axual.mocker.config;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ServletConfiguration {

    private final BaseConfig baseConfig;
    private final ConsulClient client;
    private final ConsulDiscoveryProperties discoveryProperties;

    @Autowired
    public ServletConfiguration(BaseConfig baseConfig, ConsulClient client, ConsulDiscoveryProperties discoveryProperties) {
        this.baseConfig = baseConfig;
        this.client = client;
        this.discoveryProperties = discoveryProperties;
    }


    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        baseConfig.getPorts().stream().skip(1).forEach(port -> {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setPort(port);
            tomcat.addAdditionalTomcatConnectors(connector);
        });
        baseConfig.getPorts().forEach(port -> {
            NewService service = new NewService();
            service.setId(discoveryProperties.getInstanceId()  + "-" + port);
            service.setName(discoveryProperties.getServiceName());
            service.setAddress(discoveryProperties.getIpAddress());
            service.setTags(Arrays.asList("cluster-api"));
            service.setPort(port);
            client.agentServiceRegister(service);
        });
        return tomcat;
    }


}
