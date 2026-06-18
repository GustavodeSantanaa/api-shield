package com.apishield.core.shieldcore.dto;

public class EndpointStatsResponse {

    private String endpoint;

    private Long requestCount;

    public EndpointStatsResponse() {
    }

    public EndpointStatsResponse(String endpoint, Long requestCount) {
        this.endpoint = endpoint;
        this.requestCount = requestCount;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(Long requestCount) {
        this.requestCount = requestCount;
    }
}
