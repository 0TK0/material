package com.zyq.test;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by tk on 2017/1/16.
 */
@Component
public class CronJobTest {

    private int i = 0;

    @Scheduled(cron = "${job.everysecond.cron}")
    public void everySecond() {
        System.out.println("第" + (++i) + "次调前时间：" + nowTime());
    }

    private String nowTime() {
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }
}
