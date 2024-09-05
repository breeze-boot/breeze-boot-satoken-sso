package com.breeze.boot.modules.bpm.config;

import lombok.extern.slf4j.Slf4j;
import org.flowable.app.spring.SpringAppEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProcessEngineConfig implements EngineConfigurationConfigurer<SpringAppEngineConfiguration> {

    private ChangeDbEngineConfigurator datasourceConfigurator;

    @Autowired
    public void setDatasourceConfigurator(ChangeDbEngineConfigurator datasourceConfigurator) {
        this.datasourceConfigurator = datasourceConfigurator;
    }

    @Override
    public void configure(SpringAppEngineConfiguration engineConfiguration) {
        engineConfiguration.addConfigurator(datasourceConfigurator);
    }

}