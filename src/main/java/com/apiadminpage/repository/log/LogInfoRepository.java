package com.apiadminpage.repository.log;

import com.apiadminpage.entity.log.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogInfoRepository extends JpaRepository<LogInfo, Integer> {
}
