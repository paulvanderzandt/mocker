package io.axual.mocker.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.axual.mocker.model.ClusterConfiguration;
import io.axual.mocker.model.ClusterState;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("app")
public class BaseConfig {

    private Map<Integer, ClusterState> active = new HashMap<>();

    private List<ClusterConfiguration> configurations;

    @PostConstruct
    public void init() {
        configurations.forEach(configuration -> active.put
                (configuration.getPort(), ClusterState.builder()
                        .state(ClusterState.State.ACTIVE)
                        .strategy(configuration.getStrategy())
                        .build()));
    }
}
