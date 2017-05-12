package com.cdai.codebase.undertow;

import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config for Undertow container
 */
//@Configuration
public class UndertowConfig {

    //@Bean
    UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();
        //factory.addBuilderCustomizers(new ApplicationUndertowCustomizer());
        return factory;
    }

}
