package com.devh;

import com.devh.scheduler.lotto.core.LottoScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LottoSchedulerApplication {

    public static void main(String[] args) {
        /* Start as non-web applications */
//        SpringApplication app = new SpringApplication(LottoSchedulerApplication.class);
//        app.setWebApplicationType(WebApplicationType.NONE);
//        app.run(args);
        SpringApplication.run(LottoSchedulerApplication.class, args);
    }

}
