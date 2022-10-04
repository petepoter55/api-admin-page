package com.apiadminpage.service.log;

import com.apiadminpage.entity.log.LogInfo;
import com.apiadminpage.model.request.log.LogRequest;
import com.apiadminpage.repository.log.LogInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class LogService {
    private final LogInfoRepository logInfoRepository;

    @Autowired
    public LogService(LogInfoRepository logInfoRepository) {
        this.logInfoRepository = logInfoRepository;
    }

    public void insertLog(LogRequest logRequest) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUsername(logRequest.getCreateBy())
                .setType(logRequest.getType())
                .setCreateDateTime(logRequest.getCreateDateTime());

        logInfoRepository.save(logInfo);
    }
}
