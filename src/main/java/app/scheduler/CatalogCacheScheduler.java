package app.scheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CatalogCacheScheduler {

    @Scheduled(cron = "0 0 0 * * *")
    @CacheEvict(value = "items", allEntries = true)
    public void clearCatalogCache() {
    }
}