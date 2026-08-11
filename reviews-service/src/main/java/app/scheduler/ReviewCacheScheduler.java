package app.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
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

        log.info("Scheduled review average cache eviction triggered");
    }
}