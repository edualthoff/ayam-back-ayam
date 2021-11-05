package br.flower.boot.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class ThreadConfig {
	private static final Logger log = LoggerFactory.getLogger(ThreadConfig.class);

	/**
	 * Configuração da Thread do Scheduler - Spring.. O padrão do Poll é 1
	 * Alterado para 2 
	 * @return
	 */
    
    @Bean("threadMaling")
    public ThreadPoolTaskExecutor threadMaling() {
    	log.debug("Thread threadMaling..");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("threadMaling");
        executor.initialize();

        return executor;
    }
}
