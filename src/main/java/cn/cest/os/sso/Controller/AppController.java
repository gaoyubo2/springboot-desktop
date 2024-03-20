package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.desktop.AppModel;
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
    public Result<List<AppModel>> getAppVO(){
        List<AppModel> apps = desktopService.getApps();
        if(apps == null || apps.size() == 0 ){
            return Result.fail("获取应用列表失败");
        }
        return Result.ok(apps,"获取应用列表成功");
    }

}

