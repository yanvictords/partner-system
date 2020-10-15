package br.com.partner.exceptions;

import br.com.partner.presenters.errors.Error;
import br.com.partner.utils.BeanUtilsConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;

import java.io.IOException;

import static org.springframework.http.HttpStatus.valueOf;

public class FeignClientException extends RuntimeException {

    private FeignException feignException;

    public FeignClientException(final FeignException feignException) {
        super(feignException);
        this.feignException = feignException;
    }

    public HttpStatus getHttpStatus() {
        return valueOf(feignException.status());
    }

    public Error getErrorPresenter() throws IOException {
        final var objectMapper = BeanUtilsConfig.getBean(ObjectMapper.class);
        return objectMapper.readValue(getResponseBodyError(), Error.class);
    }

    private String getResponseBodyError() {
        final var buffer = feignException.responseBody().get();
        final var bytes = buffer.array();
        return new String(bytes);
    }

}
