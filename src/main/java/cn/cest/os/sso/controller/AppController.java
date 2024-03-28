package cn.cest.os.sso.controller;


import cn.cest.os.sso.service.DesktopService;
import cn.cest.os.sso.utils.Result;
import cn.cest.os.sso.pojo.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
@RestController
public class AppController {
    @Autowired
    private DesktopService desktopService;

    @GetMapping("/apps")
    public Result<List<AppVO>> getAppVO(){
        return desktopService.getAppList();
    }

//    @GetMapping("/app")
//    public Result<List<App>> getAppById(@RequestParam("appid") Integer appid){
//        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("appid", appid);
//        List<App> apps = appMapper.selectList(queryWrapper);
//        if(apps == null)
//            return Result.fail("查询应用失败");
//        return Result.ok(apps);
//
//    }

}

