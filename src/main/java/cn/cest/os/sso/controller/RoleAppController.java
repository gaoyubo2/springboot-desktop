package cn.cest.os.sso.controller;


import cn.cest.os.sso.service.DesktopService;
import cn.cest.os.sso.service.RoleAppService;
import cn.cest.os.sso.service.RoleService;
import cn.cest.os.sso.utils.Result;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsNoChildVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

public class RoleAppController {
    @Autowired
    private RoleAppService roleAppService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DesktopService desktopService;

    @PostMapping("role-app")
    public Result<Boolean> addRoleApp(@RequestBody RoleAppIdDTO roleAppIdDTO,Boolean sameName){
        Boolean flag = roleAppService.addRoleApp(roleAppIdDTO,false);
        if(flag){
            return Result.ok(true,"添加角色权限成功");
        }
        return Result.fail(false,"添加角色权限失败");
    }
    @PostMapping("modifyRoleApp")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> modifyRoleApp(@RequestBody RoleWithAppsNoChildVO roleWithAppsNoChildVO){
        //判断权限是否进行了修改
        boolean hasModify = roleAppService.hasModifyRoleApp(roleWithAppsNoChildVO.getRoleId(), roleWithAppsNoChildVO.getAppList());

        //修改角色名
        String originName = roleService.getById(roleWithAppsNoChildVO.getRoleId()).getName();
        System.out.println("原名字："+originName);
        System.out.println("新名字："+roleWithAppsNoChildVO.getName());
        boolean sameName = originName.equals(roleWithAppsNoChildVO.getName());
        if(!sameName){
            System.out.println("name不同");
            Role role = new Role();
            role.setTbid(roleWithAppsNoChildVO.getRoleId());
            role.setName(roleWithAppsNoChildVO.getName());
            roleService.updateById(role);
        }
        boolean flag = false;
        //删除旧roleApp
        flag = roleAppService.removeAllWithRoleId(roleWithAppsNoChildVO.getRoleId());
        //添加新appList
        flag = roleAppService.addAppList(roleWithAppsNoChildVO.getRoleId(), roleWithAppsNoChildVO.getAppList());
        //修改权限
        if(hasModify){
            //删除memberApp,添加新memberApp,修改member表
            flag = desktopService.modifyRoleApp(roleWithAppsNoChildVO);
        }
        return flag? Result.ok(true,"修改角色成功"):Result.fail(true,"修改角色失败");
    }

}

