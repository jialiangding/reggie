package jialiang_ding.reggie.filter;

import com.alibaba.fastjson.JSON;
import jialiang_ding.reggie.common.R;
import jialiang_ding.reggie.util.BaseContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


/**
 * 检查用户是否完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter  implements Filter {




    public static  final AntPathMatcher antPathMatcher=new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        Long employee1 = (Long)request.getSession().getAttribute("employee");

        BaseContextUtil.setCurrentId(employee1);
        //判断本次请求 是否需要处理
        String[] urls=new String[]{
                "/employee/**",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/**"
        };
        if ( this.check(urls,request.getRequestURI())){
            filterChain.doFilter(request,response);
            return;
        }
        //判断访问的链接是否是需要先登录？ 如果是需要登录的  但是没有登录 拦截
        Object employee = ((HttpServletRequest) servletRequest).getSession().getAttribute("employee");
        if (employee!=null){
            //非空说明已经登录过了
            log.info("有登录过 直接放行");
            filterChain.doFilter(request,response);
            return;
        }



        Long userid =(Long)request.getSession().getAttribute("userid");
        BaseContextUtil.setCurrentId(userid);


        if (userid!=null){
            //非空说明已经登录过了
            log.info("有登录过 直接放行");
            filterChain.doFilter(request,response);
            return;
        }

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

//            输出流的方式来响应数据
        log.info("访问需要登录的链接，没有登录 拦截");
        log.info(request.getRequestURI());
        return;




    }



    public Boolean check(String[] urls,String requestUrl){
//      Arrays.asList(urls).forEach(url-> antPathMatcher.match(url,requestUrl));
        for ( String url:urls) {
                boolean match = antPathMatcher.match(url, requestUrl);
                if(match){
                    return  match;
                }
        }
        return  false;
        }

}
