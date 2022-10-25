package com.apiadminpage.thread;

import com.apiadminpage.exception.ResponseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class ScheduleProcessRun {
    private static final Logger logger = Logger.getLogger(ScheduleProcessRun.class);

    @Autowired
    private ScheduleProcessControlMethod scheduleProcessControlMethod;

    @Value("${host-master}")
    private String HOST_MASTER;

    //    @Scheduled(cron = "0 1 0 ? * *") // every 12.00 PM
    @Scheduled(cron = "30 * * ? * *") //every 0.30 sec
    public void run() {
        if (System.getProperty("hostName").equals(HOST_MASTER)) {
            logger.info("Running ScheduleProcessRun ...");
            try {
                scheduleProcessControlMethod.taskQuantityProduct();
            } catch (ResponseException e) {
                throw e;
            }
            logger.info("Done ScheduleProcessRun ...");
        }
    }
}
