package it.sevenbits.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by awemath on 9/21/15.
 */
@Service
public class CustomHandlerInterceptor extends HandlerInterceptorAdapter {
    static final String ASSETS_SERVICE_NAME = "assets";



    @Autowired
    private AssetsResolver assetsResolver;

    @Override
    public void postHandle(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final Object handler,
            final ModelAndView mav
    ) throws Exception {
        if (mav != null) {
            mav.addObject(ASSETS_SERVICE_NAME, assetsResolver);
        }
    }
}
