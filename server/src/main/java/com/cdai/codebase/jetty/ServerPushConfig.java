package com.cdai.codebase.jetty;

import org.eclipse.jetty.servlets.PushCacheFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 */
//@Configuration
public class ServerPushConfig {

    //@Bean
    //@Profile("pushcache")
    public PushCacheFilter pushCacheFilter() {
        return new PushCacheFilter();
    }

}