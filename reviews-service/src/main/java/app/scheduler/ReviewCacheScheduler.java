package app.scheduler;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ReviewCacheScheduler {
    
    @Scheduled(
            fixedDelay = 2,
            timeUnit = TimeUnit.HOURS
    )
    @CacheEvict(
            value = "reviewAverages",
            allEntries = true
    )
    public void clearReviewAverageCache() {
    }
}