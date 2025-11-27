package com.apishield.core.shieldcore.repository;

import com.apishield.core.shieldcore.domain.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {

}
