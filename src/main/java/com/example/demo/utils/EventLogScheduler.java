package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Configuration
@EnableScheduling
public class EventLogScheduler {

    private Long totalCount =0L;
    private List<Integer> idList = new ArrayList<>();

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void addToIdList(Integer id) {
        this.idList.add(id);
    }

    /**
     * Clear values after 1 min
     */
    private void clearValues() {
        setTotalCount(0L);
        this.idList.clear();
    }

    /**
     * Scheduler which logs requests count every 1 min.
     */
    @Scheduled(cron = "1 * * * * *")
    public void logRequest() {
        log.info("Total number of requests: " + getTotalCount());
        clearValues();
    }

}
