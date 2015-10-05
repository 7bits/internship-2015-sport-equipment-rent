package it.sevenbits.config;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CsrfInterceptor extends HandlerInterceptorAdapter {
    @Override
    public void postHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final ModelAndView mav
    ) throws Exception {
        if (mav != null) {
            mav.addObject("_csrf", request.getAttribute("_csrf"));
        }
    }
}
