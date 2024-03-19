package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("role")
    public Result<Integer> addRole(@RequestBody Role role){
        boolean flag = roleService.save(role);
        if(flag){
            return Result.ok(role.getTbid(),"添加角色成功");
        }
        return Result.fail("添加角色失败");
    }
    @GetMapping("roles")
    public Result<List<Role>> getRoles(){
        List<Role> roles = roleService.list(null);
        if(roles != null){
            return Result.ok(roles,"获取角色列表成功");
        }
        return Result.fail("获取角色列表失败");
    }
    @DeleteMapping("role")
    public Result<Boolean> deleteRole(@RequestBody Role role){
        boolean flag = roleService.removeById(role);
        if(flag){
            return Result.ok(true,"删除角色成功");
        }
        return Result.fail("删除角色失败");
        //gyb
        //cyl
    }


}

