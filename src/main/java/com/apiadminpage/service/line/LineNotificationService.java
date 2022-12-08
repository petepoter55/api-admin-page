package com.apiadminpage.service.line;

import com.apiadminpage.environment.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class LineNotificationService {
    private final URI lineUrl = URI.create(Constant.URL_NOTIFICATION);
    private final String contentType = Constant.CONTENT_TYPE_REQUEST_LINE;

    @Value("${line-token}")
    private String LINE_TOKEN;

    public ResponseEntity<String> httpPost(String data) {
        String bearerAuth = "Bearer " + LINE_TOKEN;
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", contentType);
        headers.add("Content-Length", "" + data.getBytes().length);
        headers.add("Authorization", bearerAuth);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("message", data);

        RequestEntity request = new RequestEntity(body, headers, HttpMethod.POST, lineUrl);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(request, String.class);
    }
}
