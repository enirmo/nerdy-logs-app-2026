package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableFeignClients
@EnableScheduling
@EnableCaching
@SpringBootApplication
public class NerdyLogsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(NerdyLogsAppApplication.class, args);
    }


    // Only uncomment this if you would like to test with more items already added to database; this is set up in TestData, under app.config
    //--------------------------------------------------

    /*
    @Bean
    CommandLineRunner loadTestData(ItemService itemService) {
        return args -> {
            if (itemService.getAllItems().isEmpty()) {
                TestData.load(itemService);
            }
        };
    }
    */

}
