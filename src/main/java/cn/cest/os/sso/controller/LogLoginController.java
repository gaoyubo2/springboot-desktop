package cn.cest.os.sso.controller;

import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.service.LogLoginService;
import cn.cest.os.sso.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LogLoginController {

    @Autowired
    private LogLoginService logLoginService;

    @GetMapping("logLoginList")
    public Result<List<LogLogin>> getAllLogLogin(){
        List<LogLogin> list = logLoginService.list();
        return Result.ok(list,"获取登录日志成功");
    }

}
