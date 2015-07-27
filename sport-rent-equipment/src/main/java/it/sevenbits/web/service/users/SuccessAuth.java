package it.sevenbits.web.service.users;

import it.sevenbits.web.domain.GoodsForm;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by awemath on 7/23/15.
 */
public class SuccessAuth implements AuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();
        GoodsForm form = (GoodsForm) session.getAttribute("addNewGoods");
        if(form != null){
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            httpServletResponse.sendRedirect("home/confirm");
        }
    }
}
