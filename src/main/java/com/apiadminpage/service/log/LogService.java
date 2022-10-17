package com.apiadminpage.service.log;

import com.apiadminpage.entity.log.LogInfo;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.log.LogRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.model.response.log.LogResponse;
import com.apiadminpage.repository.log.LogInfoRepository;
import com.apiadminpage.utils.UtilityTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
public class LogService {
    private final LogInfoRepository logInfoRepository;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public LogService(LogInfoRepository logInfoRepository) {
        this.logInfoRepository = logInfoRepository;
    }

    public void insertLog(LogRequest logRequest) {
        LogInfo logInfo = new LogInfo();
//        logInfo.setUsername(logRequest.getCreateBy())
//                .setType(logRequest.getType())
//                .setCreateDateTime(logRequest.getCreateDateTime());

        logInfoRepository.save(logInfo);
    }

    public Response inquiryLog(String username) {
        List<LogResponse> logResponseList = new ArrayList<>();
        try {
            List<LogInfo> logInfo = logInfoRepository.findUserNative(username);
            if (logInfo.size() < 0) {
                throw new ResponseException(Constant.STATUS_CODE_ERROR, Constant.ERROR_INQUIRY_LOG_DATA_NOT_FOUND);
            }

            for (LogInfo v : logInfo){
                LogResponse logResponse = new LogResponse();
                logResponse.setUsername(v.getUsername());
                logResponse.setDescription(verifyTypeLog(v.getType()));
                logResponse.setCreateDateTime(utilityTools.generateDateTimeToThai(v.getCreateDateTime()));

                logResponseList.add(logResponse);
            }
        } catch (ResponseException e) {
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        } catch (ParseException e) {
            return Response.fail(String.valueOf(e.hashCode()), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_LOG, logResponseList);
    }

    private String verifyTypeLog(String type) {
        String desc = null;

        switch (type) {
            case "R01":
                desc = "ลงทะเบียนสำเร็จ";
                break;
            case "R02":
                desc = "ได้มีการเปลี่ยนแปลงข้อมูล";
                break;
            case "R03":
                desc = "ถูกลบข้อมูลข้อมูล";
                break;
        }

        return desc;
    }
}
