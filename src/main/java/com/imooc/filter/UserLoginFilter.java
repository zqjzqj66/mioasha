package com.imooc.filter;

import com.imooc.exception.ErrListEnum;
import com.imooc.exception.MyException;
import com.imooc.properties.JwtProperties;
import com.imooc.util.JwtTokenUtil;
import com.imooc.util.RedisUtil;
import com.imooc.util.threadlocal.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author 周启江
 * @ClassName: UserLoginFilter
 * @date 2019/7/13 15:41
 */
@Slf4j
public class UserLoginFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/" + jwtProperties.getAuthPath())) {
            filterChain.doFilter(request, response);
            return;
        }


        //这里应做一个忽略路径的判断  但是我这里是前后端结合的 检查的比较的多 所以这里直接使用请求参数来替代
        String ignore = request.getParameter(jwtProperties.getIgnore());
        if(ignore !=null){
            filterChain.doFilter(request, response);
            return;
        }
        String token = request.getParameter(jwtProperties.getHeader());
        //判断token是否为null
        if (token != null) {
            try {
                jwtTokenUtil.parseToken(token);
                String userIDFromToken = jwtTokenUtil.getUserIDFromToken(token);
                //这里不使用jwt来验证是否过期 因为他不能token的自动的刷新 这里过期的时间有redis判断
                //验证token是否过期,包含了验证jwt是否正确
                    /*boolean flag = jwtTokenUtil.isTokenExpired(token);
                    if (flag) {
                        throw new MyException(ErrListEnum.USER_LOGING_EXPIRED);
                        return;
                    }*/
                //这里使用redis来刷新token时间
                String s = RedisUtil.get(userIDFromToken);
                if (s == null) {
                    throw new MyException(ErrListEnum.USER_LOGING_EXPIRED);
                } else {
                    RedisUtil.set(userIDFromToken, s);
                    //保存用户信息在上下文中流转 来代替session
                    UserThreadLocal.set(userIDFromToken);
                    filterChain.doFilter(request, response);
                    //在响应执行后 把threadlocal里面的保存的数据删除 避免内存泄露
                    UserThreadLocal.remove();
                    return;
                }
            } catch (Exception e) {
                throw new MyException(ErrListEnum.USER_TOKEN_ILLEGAL);
            }
        } else {
            throw new MyException(ErrListEnum.USER_LOGING_EXPIRED);
        }
    }
}
