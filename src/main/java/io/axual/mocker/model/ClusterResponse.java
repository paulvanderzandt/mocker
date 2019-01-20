package io.axual.mocker.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClusterResponse {

    private boolean active;
    private String strategy;
}
