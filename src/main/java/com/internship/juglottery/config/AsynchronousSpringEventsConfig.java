package com.internship.juglottery.config;

import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
        import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
        import org.springframework.context.event.ApplicationEventMulticaster;
        import org.springframework.context.event.SimpleApplicationEventMulticaster;

@Configuration
public class AsynchronousSpringEventsConfig {
    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(1);
        taskExecutor.setQueueCapacity(1000);
        taskExecutor.initialize();

        eventMulticaster.setTaskExecutor(taskExecutor);
        return eventMulticaster;
    }
}

