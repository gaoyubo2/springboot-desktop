package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.mapper.manage.RoleAppMapper;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.pojo.desktop.AppModel;
import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import cn.cest.os.sso.pojo.dto.RoleAppIdDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Boolean addRoleApp(RoleAppIdDTO roleAppIdDTO) {
        try {
            List<Integer> appIdList = roleAppIdDTO.getAppIdList();
            //添加角色
            Role role = new Role();
            role.setName(roleAppIdDTO.getName());
            roleService.save(role);
            Integer roleId = role.getTbid();
            //为角色添加权限
            for(Integer appId: appIdList){
                //根据id查询，查询desktop的AppModel
                AppModel appModel = desktopService.getAppModelById(appId);
                //添加权限：添加appid和roleId
                baseMapper.insert(new RoleApp(null,appId,roleId));
                //为用户添加角色权限：插入Desktop的memberApp
                MemberAppModel memberAppModel = new MemberAppModel();
                BeanUtils.copyProperties(appModel,memberAppModel);
                memberAppModel.setRealid(appId);
                //memberId字段当作角色
                memberAppModel.setMemberId(roleId);
                desktopService.addMemberAppModel(memberAppModel);
            }
            return true;
        } catch (BeansException e) {
            e.printStackTrace();
            return false;
        }
    }

}
