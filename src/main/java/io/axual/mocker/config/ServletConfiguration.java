package io.axual.mocker.config;

//import com.ecwid.consul.v1.ConsulClient;
//import com.ecwid.consul.v1.agent.model.NewService;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
//import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ServletConfiguration {

    private final BaseConfig baseConfig;
//    private final ConsulClient client;
//    private final ConsulDiscoveryProperties discoveryProperties;
    private final List<String> services = new ArrayList<>();
    private final List<String> kvs = new ArrayList<>();

    @Autowired
    public ServletConfiguration(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

//    @Autowired
//    public ServletConfiguration(BaseConfig baseConfig, ConsulClient client, ConsulDiscoveryProperties discoveryProperties) {
//        this.baseConfig = baseConfig;
//        this.client = client;
//        this.discoveryProperties = discoveryProperties;
//    }
//

    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        baseConfig.getConfigurations().stream().skip(1).forEach(configuration -> {
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setPort(configuration.getPort());
            tomcat.addAdditionalTomcatConnectors(connector);
        });
        baseConfig.getConfigurations().forEach(configuration -> {
            String clusterName = "V" + (configuration.getPort() - baseConfig.getConfigurations().get(0).getPort() + 1);
            String clusterNameLower = clusterName.toLowerCase();
//            NewService service = new NewService();
//            service.setId(discoveryProperties.getInstanceId()  + "-" + port);
//            service.setName(discoveryProperties.getServiceName());
//            service.setAddress(discoveryProperties.getIpAddress());
//            service.setTags(Arrays.asList("cluster-api"));
//            service.setPort(port);
////            client.agentServiceRegister(service);
//            services.add(service.getId());

//            addKV("config/application/cluster/" + clusterName + "/name", clusterNameLower);
//            addKV("config/application/cluster/" + clusterName + "/bootstrap-servers-public", "bootstrap.public." + clusterNameLower + ".axual.io:29293");
//            addKV("config/application/cluster/" + clusterName + "/bootstrap-servers-internal", "bootstrap.internal." + clusterNameLower + ".axual.io:29293");
//            addKV("config/application/cluster/" + clusterName + "/schema-registry-url", "schemaregistry." + clusterNameLower + ".axual.io:18001");
//            addKV("config/application/cluster/" + clusterName + "/rest-proxy-url", "proxy." + clusterNameLower + ".axual.io:8080");
//            addKV("config/application/cluster/" + clusterName + "/url", "http://" + discoveryProperties.getServiceName() + ".service.axual:" + port);
        });
        return tomcat;
    }

    @PreDestroy
    public void destroy(){
        log.info("Deregistering custom services");
//        services.forEach(client::agentServiceDeregister);
//        kvs.forEach(client::deleteKVValue);

    }

    private void addKV(String key, String value) {
//        client.setKVValue(key, value);
        kvs.add(key);
    }

}
