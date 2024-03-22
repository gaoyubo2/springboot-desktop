package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.desktop.AppModel;

import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.pojo.vo.AppWithChildVO;
import cn.cest.os.sso.pojo.vo.RoleTreeVO;
import cn.cest.os.sso.pojo.vo.RoleVO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
@CrossOrigin
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
    @DeleteMapping("deleterole")
    public Result<Boolean> deleteRole(@RequestBody List<Integer> roleIds){
//        for(Integer roleid:roleIds){
//            roleService.
//        }
        boolean flag =  roleService.deleteUserAndRole(roleIds);
        if(flag){
            return Result.ok(true,"删除角色成功");
        }
        return Result.fail("删除角色失败");
        //gyb
        //cyl
    }
    @GetMapping("rolesWithApps")
    public Result<PageResult> getRolesWithApps(@RequestParam(value = "pageNum", required = true) Integer pageNum,
                                               @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                               @RequestParam(value="roleName", required = false) String roleName){

        Page<Role> page = new Page<>(pageNum, pageSize);

        //封装分页返回结果 total
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        if(roleName != null){
            roleQueryWrapper.like("name", roleName);
        }

        Long count = roleService.getBaseMapper().selectCount(roleQueryWrapper);
        roleService.getBaseMapper().selectPage(page, roleQueryWrapper);
        List<Role> roles = page.getRecords();     //得到分页查询的roles，还需要加入app


        //List<Role> roles = roleService.list(null);
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
//        if(roleWithApps != null && roleWithApps.size() != 0){
//            return Result.ok(new PageResult(count, roleWithApps));
//            //return Result.ok(roleWithApps,"获取角色列表成功");
//        }
        return Result.ok(new PageResult(count, roleWithApps));
    }

    //接口为展示多级角色，需要role增加字段 parentid进行实现
//    @GetMapping("/showroles")
//    public Result<List<RoleTreeVO>> showRoles(){
//        List<RoleTreeVO> result =  roleService.findChildrenRoles(0);
//        if(result == null){
//            return Result.fail("获取角色列表失败");
//        }
//        return Result.ok(result);
//    }

    @GetMapping("rolebyname")
    public Result<PageResult> getRolesByName(@RequestParam(value = "roleName", required = true) String roleName,
                                             @RequestParam(value = "pageNum", required = true) Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize){
        PageResult pageResult = roleService.selectByName(roleName, pageNum, pageSize);
        if(pageResult == null)
            return Result.fail("查询角色列表失败");
        return Result.ok(pageResult);
    }

    @GetMapping("getEnableRoles")
    public Result<List<Role>> getEnableRoles(){
        List<Role> roles = roleService.getEnableRoles();
        if(roles == null)
            return Result.fail("获取角色列表失败");
        return Result.ok(roles);
    }


    @PutMapping("enableRole")
    public Result enableRole(@RequestParam Integer roleId,
                             @RequestParam Integer isDelete){
        boolean flag = roleService.enableRole(roleId, isDelete);
        if(!flag)
            return Result.fail("修改角色状态失败");
        return Result.ok(true, "修改角色状态成功");

    }

//    @PutMapping("changeRole")
//    public Result changeRole(@RequestParam ){
//
//    }


}

