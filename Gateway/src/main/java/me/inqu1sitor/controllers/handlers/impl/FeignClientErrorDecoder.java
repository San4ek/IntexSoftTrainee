package me.inqu1sitor.controllers.handlers.impl;

import feign.Response;
import feign.codec.ErrorDecoder;
import me.inqu1sitor.exceptions.ClientException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * An implementation of {@link ErrorDecoder}.
 *
 * @author Alexander Sankevich
 */
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());
        if (responseStatus.is4xxClientError() || responseStatus.is5xxServerError()) {
            try {
                return new ClientException(responseStatus, response.body() != null ? IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8) : null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Exception(response.reason());
    }
}
