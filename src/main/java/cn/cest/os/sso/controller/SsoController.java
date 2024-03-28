package cn.cest.os.sso.controller;

import cn.cest.os.sso.constant.Constant;
import cn.cest.os.sso.service.SsoService;
import cn.cest.os.sso.service.UserService;
import cn.cest.os.sso.utils.RedisTools;
import cn.cest.os.sso.utils.Result;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class SsoController {
    private final UserService userService;

    private final SsoService ssoService;

    private final RedisTemplate redisTemplate;

    private final RedisTools redisTools;

    public SsoController(SsoService ssoService, UserService userService, RedisTemplate redisTemplate, RedisTools redisTools) {
        this.ssoService = ssoService;
        this.userService = userService;
        this.redisTemplate = redisTemplate;
        this.redisTools = redisTools;
    }

    /**
     * 判断是否登录
     * @return 是否登录标志
     */
    @GetMapping("/ifLoginOld")
    public Result<Boolean> tokenIfValidOld(HttpServletRequest request){
        //判断是否登录（判断请求头中的token）
        boolean flag = StpUtil.isLogin();
        if(flag){
            return Result.ok(true,"已登录，token有效");
        }


        return Result.fail(false,"未登录，token无效");
    }
    /**
     * 判断是否登录
     * @return 是否登录标志
     */
    @GetMapping("/ifLogin")
    public Result<Boolean> tokenIfValid(HttpServletRequest request){
        //判断是否登录（判断请求头中的token）
        boolean flag = StpUtil.isLogin();
        if(!flag){
//            //再判断参数是否登录
//            String token = (String) request.getAttribute("token");
//            System.out.println("token:"+token);
//            SaSession saSession = StpUtil.(token);
//            TokenSign tokenSign = saSession.getTokenSignList().get(0);
//            if(tokenSign == null) Result.fail(false,"未登录，token无效");
            Result.fail(false,"未登录，token无效");
        }
        return Result.ok(true,"用户已登录");
    }


    @GetMapping("/session")
    public Result<SaSession> getToken(@RequestParam("uid") Integer uid){
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(uid);
        return Result.ok(sessionByLoginId,"获取session成功");
    }
    @GetMapping("/permissions")
    public Result<List<Integer>> getPermissionList(@RequestParam("uid") Integer uid){
        //获取权限列表
        List<Integer> permissionList = ssoService.getPermissionListByUid(uid);
        return Result.ok(permissionList,"获取权限列表成功");
    }
    @GetMapping("getInfoByToken")
    public Map<String,Object> getTokenSessionByToken(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token");
        SaSession sessionByToken = StpUtil.getTokenSessionByToken(token);
        return sessionByToken.getDataMap();
    }
    /**
     * 登录接口
     * @param username 用户名
     * @param password 密码
     * @return 用户uid
     */
    @PostMapping("/login")
    public Result<Map<String,Object>> login(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("requestUrl") String url,
                                            @RequestParam(value = "code", required = true) String code,
                                            HttpServletRequest request
    ){
        //验证码验证
        boolean flag = false;
        try {
            flag = ssoService.checkCode(request,code);
        } catch (Exception e) {
            return Result.fail(Constant.CaptchaNoGetStr);
        }
        if(!flag) return Result.fail(Constant.CaptchaErrorStr);
        //登录验证
        Integer uid = ssoService.checkLogin(username,password);
        if(uid.equals(Constant.UserNoAccess)) return Result.fail(Constant.UserNoAccessStr);
        if(uid.equals(Constant.UserOrPwdError)) return Result.fail(Constant.UserOrPwdErrorStr);
        //登录
        String token = ssoService.loginAndSetSession(uid, username);
        //存储到redis,7天过期
        Map<String,Object> mapUserInfo =  ssoService.getUserInfoRedis(uid);
        redisTools.hPutAll(token,mapUserInfo);
        redisTools.expire(token,7, TimeUnit.DAYS);
        //封装返回信息
        Map<String,Object> result = ssoService.getLoginResult(uid,url,token);
        return Result.ok(result,"登录成功");
    }
}
