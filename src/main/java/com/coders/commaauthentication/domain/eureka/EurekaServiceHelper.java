package com.coders.commaauthentication.domain.eureka;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class EurekaServiceHelper {

    private final EurekaClient eurekaClient;

    public List<String> getServiceInstanceIPs(String serviceName) {
        Application application = eurekaClient.getApplication(serviceName);
        if (application == null) return List.of();

        return application.getInstances().stream()
                .map(InstanceInfo::getIPAddr)
                .collect(Collectors.toList());
    }
}
