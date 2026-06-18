package com.apishield.core.shieldcore.repository;

import com.apishield.core.shieldcore.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

    List<RequestLog> findByResponseStatus(Integer status);

    Long countByResponseStatus(Integer status);

    @Query("""
       SELECT r.endpoint, COUNT(r)
       FROM RequestLog r
       GROUP BY r.endpoint
       """)
    List<Object[]> countRequestsByEndpoint();

}
