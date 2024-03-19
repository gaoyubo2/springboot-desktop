package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.mapper.desktop.MemberAppMapper;
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
import cn.cest.os.sso.mapper.desktop.DesktopAppMapper;

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

    private final MemberAppMapper memberAppMapper;
    private final DesktopAppMapper desktopAppMapper;

    public RoleAppServiceImpl(MemberAppMapper memberAppMapper, DesktopAppMapper desktopAppMapper) {
        this.memberAppMapper = memberAppMapper;
        this.desktopAppMapper = desktopAppMapper;
    }

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
            Integer roleId = roleAppIdDTO.getRoleId();
            for(Integer appId: appIdList){
                //根据id查询
                AppModel appModel = desktopAppMapper.selectAppByAPPId(appId);
                //添加appid和roleId
                baseMapper.insert(new RoleApp(null,appId,roleId));
//                //添加Manage角色权限
//                appModel.setMember_id(roleId);
                //插入Desktop的memberApp
                MemberAppModel memberAppModel = new MemberAppModel();
                BeanUtils.copyProperties(appModel,memberAppModel);
                memberAppModel.setRealid(appId);
                memberAppModel.setMemberId(roleId);
                memberAppMapper.insert(memberAppModel);
            }
            return true;
        } catch (BeansException e) {
            e.printStackTrace();
            return false;
        }
    }

}
