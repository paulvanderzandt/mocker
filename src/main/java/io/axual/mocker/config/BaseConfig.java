package io.axual.mocker.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import io.axual.mocker.model.ClusterStatus;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("app")
public class BaseConfig {

    private Map<Integer, ClusterStatus> active = new HashMap<>();

    private List<Integer> ports;

    @PostConstruct
    public void init() {
        ports.forEach(port -> active.put(port, ClusterStatus.ACTIVE));
    }
}
