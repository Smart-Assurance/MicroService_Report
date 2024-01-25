package ma.fstt.microservicereport.controllers;


import ma.fstt.microservicereport.payload.request.TokenValidationRequest;
import ma.fstt.microservicereport.payload.response.TokenValidationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    private final RestTemplate restTemplate;

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean isValidExaminaterToken(String token) {
        String validationUrl = authServiceUrl + "/validate-token";
        TokenValidationRequest validationRequest = new TokenValidationRequest();
        validationRequest.setToken(token);
        ResponseEntity<TokenValidationResponse> responseEntity = restTemplate.postForEntity(validationUrl, validationRequest, TokenValidationResponse.class);
        return responseEntity.getBody().isResponse() == true && "examinater".equals(responseEntity.getBody().getMessage());
    }

    public boolean isValidEmployeeToken(String token) {
        String validationUrl = authServiceUrl + "/validate-token";
        TokenValidationRequest validationRequest = new TokenValidationRequest();
        validationRequest.setToken(token);
        ResponseEntity<TokenValidationResponse> responseEntity = restTemplate.postForEntity(validationUrl, validationRequest, TokenValidationResponse.class);
        return responseEntity.getBody().isResponse() == true && "employee".equals(responseEntity.getBody().getMessage());
    }

    public String getIdFromToken(String token) {
        String validationUrl = authServiceUrl + "/get-id";
        TokenValidationRequest validationRequest = new TokenValidationRequest();
        validationRequest.setToken(token);
        ResponseEntity<TokenValidationResponse> responseEntity = restTemplate.postForEntity(validationUrl, validationRequest, TokenValidationResponse.class);
        return responseEntity.getBody().getMessage();
    }

}