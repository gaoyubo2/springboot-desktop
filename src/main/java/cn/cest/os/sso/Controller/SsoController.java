package cn.cest.os.sso.Controller;

import cn.cest.os.sso.Service.SsoService;
import cn.cest.os.sso.Util.Result;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SsoController {
    @Autowired
    private RedisTemplate redisTemplate;

    private final SsoService ssoService;

    public SsoController(SsoService ssoService) {
        this.ssoService = ssoService;
    }

    /**
     * 判断是否登录
     * @return 是否登录标志
     */
    @GetMapping("/ifLogin")
    public Result<Boolean> tokenIfValid(){
        //判断是否登录（判断请求头中的token）
        boolean flag = StpUtil.isLogin();
        if(flag){
            return Result.ok(true,"已登录，token有效");
        }
        return Result.fail(false,"未登录，token无效");
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


        HttpSession session2 = request.getSession();
        String sessioncode = (String) session2.getAttribute("logincode");
        //System.out.println("验证时：session id："+session2.getId());
        //System.out.println(sessioncode);
        boolean codeFlag = code.equalsIgnoreCase(sessioncode);
        if(!codeFlag){
            return Result.fail("验证码错误");
        }
        else{
            System.out.println("验证码验证成功");
        }
        //以上增加验证码验证逻辑

        //获取uid
        Integer uid = ssoService.getUidBuUserNameAndPwd(username,password);
        if(uid == -1) {
            return Result.fail("用户名或密码错误");
        }
        //登录
        StpUtil.login(uid);
        //返回重定向
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("redirect",url);
        //获取session
        SaSession session = StpUtil.getSessionByLoginId(uid);
        map.put("session",session);
        return Result.ok(map,"登录成功");
    }
    @GetMapping("/session")
    public Result<SaSession> getToken(@RequestParam("uid") Integer uid){
        //获取uid
//        Integer uid = ssoService.getUidBuUserNameAndPwd(username,password);
        //获取session
        SaSession sessionByLoginId = StpUtil.getSessionByLoginId(uid);
        return Result.ok(sessionByLoginId,"获取session成功");
    }
    @GetMapping("/permissions")
    public Result<List<Integer>> getPermissionList(@RequestParam("uid") Integer uid){
        //获取权限列表
        List<Integer> permissionList = ssoService.getPermissionListByUid(uid);
        return Result.ok(permissionList,"获取权限列表成功");
    }


    /**
     * 退出登录
     * @return 成功标志
     */
    @PostMapping("/logout")
    public Result<Boolean> logout(){
        try {
            StpUtil.logout();
            return Result.ok(true,"退出成功");
        } catch (Exception e) {
            //todo 记录日志
            return Result.fail(false,"退出失败");
        }
    }


}
