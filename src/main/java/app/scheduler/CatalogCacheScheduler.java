package app.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CatalogCacheScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "items", allEntries = true)
    public void clearCatalogCache() {

        log.info("Scheduled catalog cache eviction triggered");
    }
}