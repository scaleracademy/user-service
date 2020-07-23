package dev.rakesh.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
	final private static Integer eventCorePoolSize = 3;
	final private static Integer eventMaxPoolSize = 10;


	@Bean(name = "asyncExecutorForPublishingEvent")
	public Executor asyncExecutorForPublishingEvent() {
		return getExecutor();
	}

	private Executor getExecutor() {
		final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		executor.setCorePoolSize(eventCorePoolSize);
		executor.setMaxPoolSize(eventMaxPoolSize);
		executor.initialize();

		return executor;
	}
}
