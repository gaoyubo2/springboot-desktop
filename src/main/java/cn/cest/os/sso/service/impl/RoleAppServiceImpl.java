package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.service.DesktopService;
import cn.cest.os.sso.service.RoleService;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.mapper.manage.RoleAppMapper;
import cn.cest.os.sso.service.RoleAppService;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
@Service
public class RoleAppServiceImpl extends ServiceImpl<RoleAppMapper, RoleApp> implements RoleAppService {

    @Autowired
    private DesktopService desktopService;

    @Autowired
    private RoleService roleService;

    /**
     * 添加角色应用权限
     *
     * @param roleAppIdDTO 角色信息、权限ID列表
     * @return 添加标志
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRoleApp(RoleAppIdDTO roleAppIdDTO,Boolean sameName) {
        //判断是否存在同名
        long count = roleService.count(new QueryWrapper<Role>().eq("name", roleAppIdDTO.getName()));
        if(count != 0) return false;
        try {
            List<Integer> appIdList = roleAppIdDTO.getAppIdList();
            //添加角色
            Role role = new Role();
            role.setName(roleAppIdDTO.getName());
            roleService.save(role);
            Integer roleId = role.getTbid();
            //为角色添加权限
            for(Integer appId: appIdList){
                //添加权限：添加appid和roleId
                baseMapper.insert(new RoleApp(null,appId,roleId));

                //为用户添加角色权限：插入Desktop的memberApp
                AppModel appModel = desktopService.getAppModelById(appId);
                MemberAppModel memberAppModel = new MemberAppModel();
                BeanUtils.copyProperties(appModel,memberAppModel);
                memberAppModel.setRealid(appId);
                memberAppModel.setMemberId(roleId);
                memberAppModel.setFolderId(0);
                memberAppModel.setType("window");
                desktopService.addMemberAppModel(memberAppModel);

            }
            return true;
        } catch (BeansException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 比较两个角色的权限信息是否相同
    @Override
    public boolean hasModifyRoleApp(Integer roleId, List<Integer> updatedPermissions) {
        // 获取原始角色的权限
        List<Integer> originalPermissions = getRoleAppByRoleId(roleId);
        System.out.println("原始权限："+originalPermissions);
        System.out.println("修改权限："+updatedPermissions);
        // 判断权限列表长度是否相同，若不相同则说明权限已经改变
        if (originalPermissions.size() != updatedPermissions.size()) {
            return true;
        }
        // 判断两个权限列表中的元素是否相同
        for (Integer permission : originalPermissions) {
            // 若更新后的权限列表中不包含原始权限列表中的某一权限，则说明权限已经改变
            if (!updatedPermissions.contains(permission)) {
                return true;
            }
        }
        // 所有权限都相同，则说明权限未改变
        return false;
    }

    @Override
    public List<Integer> getRoleAppByRoleId(Integer roleId) {
        List<RoleApp> roleApps = this.list(new QueryWrapper<RoleApp>().eq("role_id", roleId));
        List<Integer> appList = new ArrayList<>(roleApps.size());
        for(RoleApp roleApp : roleApps){
            appList.add(roleApp.getAppId());
        }
        return appList;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAllWithRoleId(Integer roleId) {
        System.out.println("roleId:"+roleId);
        boolean flag2 = this.remove(new QueryWrapper<RoleApp>().eq("role_id",roleId));
        System.out.println("删除roleApp表roleId=:"+roleId);
        return flag2;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAppList(Integer roleId,List<Integer> appList) {
        for(Integer appId: appList){
            RoleApp roleApp = new RoleApp();
            roleApp.setRoleId(roleId).setAppId(appId);
            baseMapper.insert(roleApp);
        }
        return true;
    }

}
