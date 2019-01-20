package io.axual.mocker.model;

import lombok.Data;

@Data
public class ClusterConfiguration {
    private int port;
    private String strategy;
}
