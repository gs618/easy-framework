package com.github.gs618.easy.starter.transmission;

import feign.Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author gaosong
 */
@Configuration
@EnableConfigurationProperties(value = {
        TransmissionProperties.class
})
public class TransmissionAutoConfigure {

    @Autowired
    private TransmissionProperties transmissionProperties;

    @ConditionalOnClass(TransmissionProperties.class)
    @Bean
    public KeyValues keyValues() {
        return new KeyValues(transmissionProperties);
    }

    @ConditionalOnClass(Feign.class)
    @Bean
    public FeignKeyValueTransmitter feignKeyValueTransmitter() {
        return new FeignKeyValueTransmitter(keyValues());
    }

    @ConditionalOnClass(RestTemplate.class)
    @Bean
    public RestTemplateKeyValueTransmitter restTemplateKeyValueTransmitter(@Autowired(required = false) List<RestTemplate> restTemplates) {
        return new RestTemplateKeyValueTransmitter(restTemplates, keyValues());
    }
}
