package com.apiadminpage.repository.log;

import com.apiadminpage.entity.log.LogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogInfoRepository extends JpaRepository<LogInfo, Integer> {
    List<LogInfo> findByUsername(String username);

    @Query("SELECT u FROM LogInfo u WHERE u.username = :username")
    List<LogInfo> findUserNative(@Param("username") String username);
}
