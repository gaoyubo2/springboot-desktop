package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import cn.cest.os.sso.pojo.vo.RoleWithAppsNoChildVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@CrossOrigin
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
//        String originName = roleService.getById(roleWithAppsNoChildVO.getRoleId()).getName();
//        //修改角色名
//        Boolean sameName = originName.equals(roleWithAppsNoChildVO.getName());
//        if(!sameName){
//            Role role = new Role();
//            role.setTbid(roleWithAppsNoChildVO.getRoleId());
//            role.setName(roleWithAppsNoChildVO.getName());
//            roleService.updateById(role);
//        }
        Boolean flag = false;
        if(hasModify){
            //修改RoleApp：删除旧的权限，添加新的
            flag = roleAppService.removeAllWithRoleId(roleWithAppsNoChildVO.getRoleId());
            if (!flag) Result.fail(true,"修改角色失败-1");
            //修改memberApp
            flag = desktopService.modifyRoleApp(roleWithAppsNoChildVO);
            if (!flag) Result.fail(true,"修改角色失败-2");
            RoleAppIdDTO roleAppIdDTO  = new RoleAppIdDTO();
            roleAppIdDTO.setAppIdList(roleWithAppsNoChildVO.getAppList());
            roleAppIdDTO.setName(roleWithAppsNoChildVO.getName());
            //添加新角色
            flag = roleAppService.addRoleApp(roleAppIdDTO,false);
            if (!flag) Result.fail(true,"修改角色失败-3");
        }
        return flag? Result.ok(true,"修改角色成功"):Result.fail(true,"修改角色失败-2");
    }

}

