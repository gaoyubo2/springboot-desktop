package cn.cest.os.sso.aspect;

import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.service.LogLoginService;
import cn.cest.os.sso.utils.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Aspect
@Component
public class LoginLoggingAspect {

    @Autowired
    private LogLoginService logLoginService;

    @Around("execution(* cn.cest.os.sso.controller.SsoController.*(..))")
    public Object logLoginInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取请求路径
        String requestPath = getRequestPath();
        System.out.println("请求路径："+requestPath);
        // 判断是否为登录请求
        if ("/login".equals(requestPath)) {
            // 获取请求参数
            Object[] args = joinPoint.getArgs();
            String username = (String) args[0];
//            String password = (String) args[1];
//            String url = (String) args[2];
//            String code = (String) args[3];
            HttpServletRequest request = (HttpServletRequest) args[args.length - 1];
            String ipAddress = request.getRemoteAddr();

            // 执行目标方法
            Result<Map<String,Object>> resObj = null;
            Object result = null;
            boolean success = false;
            LogLogin logLogin = new LogLogin();
            logLogin.setUsername(username);
            logLogin.setIp(ipAddress);
            logLogin.setLoginTime(LocalDateTime.now());
            try {
                //执行方法
                result = joinPoint.proceed();
                //获取结果
                resObj = (Result) result;
                //登陆成功
                if(resObj.getCode() == Result.SUCCESS) success = true;
                return result;
            } finally {
                // 记录登录结果信息
                if (success) {
                    logLogin.setSuccess(1);
                } else {
                    String msg = resObj.getMsg();
                    logLogin.setFailReason(msg);
                    logLogin.setSuccess(0);
                }
                logLoginService.save(logLogin);
                System.out.println("登录记录："+logLogin);

            }
        }

        // 对于非登录请求，直接执行目标方法
        return joinPoint.proceed();
    }

    private String getRequestPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRequestURI();
    }
}