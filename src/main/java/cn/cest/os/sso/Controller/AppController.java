package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.mapper.manage.AppMapper;
import cn.cest.os.sso.pojo.App;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.vo.AppVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
@CrossOrigin
public class AppController {
    @Autowired
    private DesktopService desktopService;

    @GetMapping("/apps")
    public Result<List<AppVO>> getAppVO(){
        List<AppModel> apps = desktopService.getApps();
        List<AppVO> apps2 = new ArrayList<AppVO>(apps.size());

        for(AppModel appModel:apps){
            AppVO appVO = new AppVO();
            BeanUtils.copyProperties(appModel, appVO);
            apps2.add(appVO);
        }


        if(apps2 == null || apps2.size() == 0 ){
            return Result.fail("获取应用列表失败");
        }
        return Result.ok(apps2);
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

