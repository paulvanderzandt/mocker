package io.axual.mocker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import io.axual.mocker.config.BaseConfig;
import io.axual.mocker.model.ClusterResponse;
import io.axual.mocker.model.ClusterState;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/admin", produces = APPLICATION_JSON_VALUE)
public class MainController {

    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    private BaseConfig baseConfig;

    @Autowired
    public MainController(BaseConfig baseConfig) {
        this.baseConfig = baseConfig;
    }

    @GetMapping("/active")
    public ClusterResponse getState(HttpServletRequest request) {
        int port = request.getServerPort();
        if (baseConfig.getActive().get(port).getState() == ClusterState.State.UNRESPONSIVE) {
            throw new IllegalStateException("Cluster unresponsive");
        }
        boolean active = baseConfig.getActive().get(port).getState() == ClusterState.State.ACTIVE;
        return ClusterResponse.builder().active(active)
                .strategy(baseConfig.getActive().get(port).getStrategy()).build();
    }

    @PostMapping("activate")
    @ResponseStatus(HttpStatus.OK)
    public void activate(HttpServletRequest request) {
        int port = request.getServerPort();
        baseConfig.getActive().get(port).setState(ClusterState.State.ACTIVE);
    }

    @PostMapping("deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivate(HttpServletRequest request) {
        int port = request.getServerPort();
        baseConfig.getActive().get(port).setState(ClusterState.State.INACTIVE);
    }

    @PostMapping("crash")
    @ResponseStatus(HttpStatus.OK)
    public void unresponsive(HttpServletRequest request) {
        int port = request.getServerPort();
        baseConfig.getActive().get(port).setState(ClusterState.State.UNRESPONSIVE);
    }
}
