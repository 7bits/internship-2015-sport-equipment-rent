package it.sevenbits.config;

import it.sevenbits.web.utils.CustomHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by awemath on 9/21/15.
 */

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private CsrfInterceptor csrfInterceptor;


    @Autowired
    private CustomHandlerInterceptor handlerInterceptor;


    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(csrfInterceptor);
        registry.addInterceptor(handlerInterceptor);
    }
}
