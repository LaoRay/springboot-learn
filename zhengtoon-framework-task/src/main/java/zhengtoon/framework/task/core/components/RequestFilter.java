package zhengtoon.framework.task.core.components;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * RequestFilter
 *
 * @author qindaorong
 * @create 2017-12-12 2:22 PM
 **/
@Order(9)
@WebFilter(filterName="RequestFilter",urlPatterns={"/add","/update","/delete","/find","/queryTasks"})
public class RequestFilter implements Filter {
    
    private static final Logger log = LoggerFactory.getLogger(RequestFilter.class);

    private final static String SECURITY_MSG = "the security code is incorrect";

    private final static String HEAD_KEY= "taskName";
    private final static String HEAD_VALUE= "taskCode";

    private final static String ACCESS_HEAD_KEY = "zhengtoon";
    private final static String ACCESS_HEAD_VALUE= "zhengtoon.framework";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("RequestFilter init......");
    }
    
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        Map<String,String> headerMap = this.getHeaderMap(request);
        if(!checkVerifyCode(headerMap)){
            response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED,SECURITY_MSG);
            return ;
        }
    
        filterChain.doFilter(servletRequest, servletResponse);
    }
    
    @Override
    public void destroy() {
        log.info("RequestFilter destroy.....");
    }
    
    
    /**
     * 推送校验
     * @param headerMap
     * @return
     */
    private boolean checkVerifyCode(Map<String,String> headerMap){
        String taskName = headerMap.get("taskName");
        String taskCode = headerMap.get("taskCode");

        if(StringUtils.endsWithIgnoreCase(ACCESS_HEAD_KEY,taskName) && StringUtils.endsWithIgnoreCase(ACCESS_HEAD_VALUE,taskCode)){
            return true;
        }else{
            return false;
        }
    }
    
    
    /**
     * 获得用户验证信息
     * @param request
     * @return
     */
    private Map<String,String> getHeaderMap(HttpServletRequest request){
        Map<String,String> headerMap = new HashMap<>(2);
        
        String headerKey = request.getHeader(HEAD_KEY);
        String headerValue = request.getHeader(HEAD_VALUE);
        
        headerMap.put(HEAD_KEY,headerKey);
        headerMap.put(HEAD_VALUE,headerValue);
        return headerMap;
    }
}
