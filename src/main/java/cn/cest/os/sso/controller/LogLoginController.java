package cn.cest.os.sso.controller;

import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.service.LogLoginService;
import cn.cest.os.sso.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
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
    @GetMapping("/logLoginByDateAndUser")
    public List<LogLogin> getByUsernameAndDate(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate) {
        //左闭 右闭
        return logLoginService.getByUsernameAndDate(username, startDate, endDate);
    }

    @GetMapping("/loginLogs")
    public Result<PageResult> getAllLogin(@RequestParam(value = "pageNum", required = true) Integer pageNum,
                                          @RequestParam(value = "pageSize", required = true) Integer pageSum){
        PageResult res = logLoginService.getLogs(pageNum, pageSum);
        if(res == null)
            return Result.fail("获取日志失败");
        return Result.ok(res);
    }

    @DeleteMapping("/deleteLogs")
    public Result deleteLogs(@RequestBody List<Integer> ids){
        Boolean flag = logLoginService.deleteLogs(ids);
        if(!flag)
            return Result.ok("删除成功");
        return Result.fail("删除失败");
    }

}
