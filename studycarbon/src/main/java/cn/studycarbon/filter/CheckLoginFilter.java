package cn.studycarbon.filter;

import cn.studycarbon.controller.StudyCarbonInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
 * @brief:
 * fileName:过滤名字
 * urlPatterns:过滤器的范围
*/
@WebFilter(filterName = "checkLoginFilter", urlPatterns = "/*")
public class CheckLoginFilter implements Filter {

    public static Logger logger = LoggerFactory.getLogger(StudyCarbonInfoController.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        String requestURI = httpServletRequest.getRequestURI();
        logger.info("intercept requestURI:"+requestURI);

        filterChain.doFilter(httpServletRequest, httpServletResponse);

        return;
    }
}
