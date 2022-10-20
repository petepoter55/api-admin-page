package com.apiadminpage.service.log;

import com.apiadminpage.entity.log.LogDescription;
import com.apiadminpage.entity.log.LogInfo;
import com.apiadminpage.environment.Constant;
import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.request.log.LogRequest;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.model.response.log.LogResponse;
import com.apiadminpage.repository.log.LogInfoRepository;
import com.apiadminpage.utils.UtilityTools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class LogService {
    private static final Logger logger = Logger.getLogger(LogService.class);

    private final LogInfoRepository logInfoRepository;
    private final EntityManager entityManager;

    UtilityTools utilityTools = new UtilityTools();

    @Autowired
    public LogService(LogInfoRepository logInfoRepository, EntityManager entityManager) {
        this.logInfoRepository = logInfoRepository;
        this.entityManager = entityManager;
    }

    public void insertLog(LogRequest logRequest) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUsername(logRequest.getCreateBy())
                .setType(logRequest.getType())
                .setCreateDateTime(logRequest.getCreateDateTime());

        logInfoRepository.save(logInfo);
    }

    public Response inquiryLog(String username) {
        List<LogResponse> logResponseList;
        List<Tuple> tuples;
        try {
            // create query
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createQuery(Tuple.class);

            // from table to used
            Root<LogInfo> rootLogInfo = criteriaQuery.from(LogInfo.class);
            Root<LogDescription> rootLogDescription = criteriaQuery.from(LogDescription.class);

            // and condition
            Predicate predicate = criteriaBuilder.and(criteriaBuilder.equal(rootLogInfo.get("username"), username));

            criteriaQuery.multiselect(
                    rootLogInfo.get("username"),
                    rootLogDescription.get("description"),
                    rootLogInfo.get("createDateTime")
            ).where(
                    criteriaBuilder.equal(rootLogInfo.get("type"), rootLogDescription.get("type")), // condition join table
                    criteriaBuilder.and(predicate)
            );

            tuples = entityManager.createQuery(criteriaQuery).getResultList();
            logResponseList = tuples.stream().map(tuple -> {
                LogResponse logResponse = new LogResponse();
                logResponse.setUsername(tuple.get(0, String.class));
                logResponse.setDescription(tuple.get(1, String.class));
                logResponse.setCreateDateTime(String.valueOf(tuple.get(2, Date.class)));
                return logResponse;
            }).collect(Collectors.toList());

        } catch (ResponseException e) {
            logger.error(String.format(Constant.THROW_EXCEPTION, e.getMessage()));
            return Response.fail(e.getExceptionCode(), e.getMessage(), null);
        }
        return Response.success(Constant.STATUS_CODE_SUCCESS, Constant.SUCCESS_INQUIRY_LOG, logResponseList);
    }

}
