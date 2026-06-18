package com.apishield.core.shieldcore.dto;

public class LogStatsResponse {

    private Long totalRequests;

    private Long successfulRequests;

    private Long blockedRequests;

    public LogStatsResponse() {
    }

    public LogStatsResponse(
            Long totalRequests,
            Long successfulRequests,
            Long blockedRequests
    ) {
        this.totalRequests = totalRequests;
        this.successfulRequests = successfulRequests;
        this.blockedRequests = blockedRequests;
    }

    public Long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public Long getSuccessfulRequests() {
        return successfulRequests;
    }

    public void setSuccessfulRequests(Long successfulRequests) {
        this.successfulRequests = successfulRequests;
    }

    public Long getBlockedRequests() {
        return blockedRequests;
    }

    public void setBlockedRequests(Long blockedRequests) {
        this.blockedRequests = blockedRequests;
    }

}
