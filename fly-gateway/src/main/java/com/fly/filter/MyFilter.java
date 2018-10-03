/*
package com.fly.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * @author 游雄
 * @describe
 * @create 11:03 2018/10/3 0003
 *//*

@Slf4j
public class MyFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        currentContext.setSendZuulResponse(true);
        if (authorization != null) {
            currentContext.set("Authorization",authorization);
        }
        return null;
    }
}
*/
