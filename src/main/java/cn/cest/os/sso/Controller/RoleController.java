package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.desktop.AppModel;

import cn.cest.os.sso.pojo.vo.AppWithChildVO;
import cn.cest.os.sso.pojo.vo.RoleVO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private DesktopService desktopService;
    @Autowired
    private RoleAppService roleAppService;

    @PostMapping("role")
    public Result<Integer> addRole(@RequestBody Role role){
        boolean flag = roleService.save(role);
        if(flag){
            return Result.ok(role.getTbid(),"添加角色成功");
        }
        return Result.fail("添加角色失败");
    }
    @GetMapping("roles")
    public Result<List<RoleVO>> getRoles(){
        List<Role> roles = roleService.list(null);
        List<RoleVO> roleVOS = new ArrayList<>();
        for (Role role: roles){
            RoleVO roleVO = new RoleVO();
            roleVO.setName(role.getName());
            roleVO.setTbid(role.getTbid());
            roleVOS.add(roleVO);
        }
        if(roleVOS != null){
            return Result.ok(roleVOS,"获取角色列表成功");
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
    @GetMapping("rolesWithApps")
    public Result<List<RoleWithAppsVO>> getRolesWithApps(){
        List<Role> roles = roleService.list(null);
        List<RoleWithAppsVO> roleWithApps = new ArrayList<>(roles.size());
        for (Role role: roles){
            List<RoleApp> appsIds = roleAppService.list(new QueryWrapper<RoleApp>().eq("role_id", role.getTbid()));
            List<AppWithChildVO> apps = new ArrayList<>();
            for(RoleApp roleApp: appsIds ){
                Integer appId = roleApp.getAppId();
                AppModel appModel = desktopService.getAppModelById(appId);
                AppWithChildVO appWithChildVO = new AppWithChildVO();
                appWithChildVO.setChild(null);
                BeanUtils.copyProperties(appModel,appWithChildVO);
                apps.add(appWithChildVO);
            }
            //构造vo
            RoleWithAppsVO roleWithAppsVO = new RoleWithAppsVO();
            BeanUtils.copyProperties(role,roleWithAppsVO);
            roleWithAppsVO.setApps(apps);
            //添加结果集
            roleWithApps.add(roleWithAppsVO);
        }
        if(roleWithApps != null && roleWithApps.size() != 0){
            return Result.ok(roleWithApps,"获取角色列表成功");
        }
        return Result.fail("获取角色列表失败");
    }


}

