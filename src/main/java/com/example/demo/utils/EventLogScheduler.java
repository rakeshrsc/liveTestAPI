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

    private Long totalCount =0l;
    private List<Integer> idList = new ArrayList<Integer>();

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public void addToIdList(Integer id) {
        this.idList.add(id);
    }

    private void clearValues() {
        setTotalCount(0l);
        this.idList.clear();
    }

    @Scheduled(cron = "1 * * * * *")
    public void logRequest() {
        log.info("Total number of requests: " + getTotalCount());
        clearValues();
    }

}
