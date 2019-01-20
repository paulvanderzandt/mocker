package io.axual.mocker.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Builder
public class ClusterState {

    private State state;
    private String strategy;

    public enum State {
        ACTIVE,
        INACTIVE,
        UNRESPONSIVE
    }
}
