package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/app")
public class AppController {
//    @Autowired
////    private DesktopService desktopService;
//
//    @GetMapping("/apps")
//    public Result<List<AppVO>> getAppVO(){
//        List<AppVO> appVOS = desktopService.appList();
//        if(appVOS == null && appVOS.size() == 0 ){
//            return Result.fail("获取角色列表失败");
//        }
//        return Result.ok(appVOS);
//    }

}

