package com.registration.client.config;

import org.springframework.stereotype.Component;

import com.registration.exception.NotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;


@Component
public class FeignErrorDecoder implements ErrorDecoder{

    @Override
    public Exception decode(String methodKey, Response response) {
        
        if(response.status()==404){
            return new NotFoundException("Resource not found");
        }

        return new RuntimeException("Feign client error: "+response.status());
    }

}
