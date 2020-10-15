package br.com.partner.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class RequestUtils {

    private static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static String getContextPath() {
        return getCurrentRequest().getContextPath() + getCurrentRequest().getServletPath();
    }

    public static String getHeader(final String headerName) {
        return getCurrentRequest().getHeader(headerName);
    }

}
