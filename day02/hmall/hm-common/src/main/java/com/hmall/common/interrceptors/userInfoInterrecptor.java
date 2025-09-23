package com.hmall.common.interrceptors;

import cn.hutool.core.util.StrUtil;
import com.hmall.common.utils.UserContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 构建mvc拦截器,
 * 为所有的服务提供获取用户信息的功能
 * 不需要做人任何拦截,因为gateway已经做了拦截
 * 这里只获取用户细腻下
 */
@Component
public class userInfoInterrecptor implements HandlerInterceptor {
    /**
     * 在controller之前进行执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取用户登陆信息
        String userInfo = request.getHeader("user-info");
        // 2.是否获取了用户,如果有,放入threadlocal
        if (StrUtil.isNotBlank(userInfo)){
            UserContext.setUser(Long.valueOf(userInfo));
        }
        // 3.放行
        return true;
    }

    /**
     * 清理用户
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContext.removeUser();
    }

}
