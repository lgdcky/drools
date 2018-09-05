package com.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dean Lu
 * Date: 8/20/18
 * Time: 11:30 AM
 */
@Component
@ConfigurationProperties(prefix = "droolsconfig")
public class CustomKieBaseConfiguration {

    private Map<String,String> kieBaseconfiguration = new HashMap<>();

    public Map<String, String> getKieBaseconfiguration() {
        return kieBaseconfiguration;
    }

    public void setKieBaseconfiguration(Map<String, String> kieBaseconfiguration) {
        this.kieBaseconfiguration = kieBaseconfiguration;
    }
}
