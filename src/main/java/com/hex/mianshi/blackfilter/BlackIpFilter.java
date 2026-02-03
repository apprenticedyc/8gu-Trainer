/**
 * IP黑名单过滤器
 *
 * 功能说明：
 * - 拦截所有HTTP请求（urlPatterns = "/*"）
 * - 使用布隆过滤器快速判断IP是否在黑名单中
 * - 黑名单IP直接拒绝访问，返回JSON错误信息
 *
 */
package com.hex.mianshi.blackfilter;

import com.hex.mianshi.utils.NetUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "blackIpFilter")
public class BlackIpFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 1. 获取客户端真实IP地址
        String ipAddress = NetUtils.getIpAddress((HttpServletRequest) servletRequest);

        // 2. 检查IP是否在黑名单中
        if (BlackIpUtils.isBlackIp(ipAddress)) {
            // 3. 如果是黑名单IP，拒绝访问并返回错误信息
            servletResponse.setContentType("text/json;charset=UTF-8");
            servletResponse.getWriter().write("{\"errorCode\":\"-1\",\"errorMsg\":\"黑名单IP，禁止访问\"}");
            return; // 终止过滤器链，不继续执行后续过滤器
        }

        // 4. 如果是白名单IP，正常放行
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
