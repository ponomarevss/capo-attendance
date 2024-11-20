package ru.sspo.service;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseDtoService {
    protected static final String ATTENDANCE_REST = "ATTENDANCE-REST";
    private final DiscoveryClient discoveryClient;

    protected BaseDtoService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    protected RestClient restClient() {
        List<ServiceInstance> instances = discoveryClient.getInstances(ATTENDANCE_REST);
        if (instances.isEmpty()) {
            throw new IllegalStateException("No instances available for service: " + ATTENDANCE_REST);
        }
        int instanceIndex = ThreadLocalRandom.current().nextInt(instances.size());
        ServiceInstance instance = instances.get(instanceIndex);
        String uri = "http://" + instance.getHost() + ":" + instance.getPort();
        return RestClient.create(uri);
    }
}
