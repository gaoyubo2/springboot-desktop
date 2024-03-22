package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class RoleAppController {
    @Autowired
    private RoleAppService roleAppService;

    @PostMapping("role-app")
    public Result<Boolean> addRoleApp(@RequestBody RoleAppIdDTO roleAppIdDTO){
        Boolean flag = roleAppService.addRoleApp(roleAppIdDTO);
        if(flag){
            return Result.ok(true,"添加角色权限成功");
        }
        return Result.fail(false,"添加角色权限失败");
    }

}

