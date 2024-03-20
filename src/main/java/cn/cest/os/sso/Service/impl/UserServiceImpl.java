package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.pojo.desktop.MemberModel;
import cn.cest.os.sso.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleAppService roleAppService;
    @Autowired
    private DesktopService desktopService;
    @Override
    public void extracted(List<UserInfoVO> userInfoVOList, User user) {
        System.out.println(user);
        Integer roleId = user.getRoleId();
        Role role = roleService.getById(roleId);
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUid(user.getTbid());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setCreateTime(user.getCreateTime());
        userInfoVO.setRoleId(roleId);
        userInfoVO.setRoleName(role.getName());
        userInfoVOList.add(userInfoVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addUserAndMember(User user) {
        try {
            // 添加User
            addUser(user);

            // 添加Member
            return addMemberForUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果出现异常，事务会回滚
            return false;
        }
    }

    private void addUser(User user) {
        // 添加User
        baseMapper.insert(user);
    }

    private String extractAppList(User user) {
        List<RoleApp> roleApps = roleAppService.list(new QueryWrapper<RoleApp>().eq("role_id", user.getRoleId()));
        StringBuilder stringBuilder = new StringBuilder();
        // 获取用户的权限列表
        for (RoleApp roleApp: roleApps){
            stringBuilder.append(roleApp.getAppId());
            stringBuilder.append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private Boolean addMemberForUser(User user) {
        String appList = extractAppList(user);

        MemberModel memberModel = new MemberModel();
        memberModel.setDesk1(appList);
        memberModel.setUsername(user.getUsername());
        memberModel.setAppxy("x");
        memberModel.setDesk(1);
        memberModel.setDockpos("right");
        memberModel.setAppsize(48);
        memberModel.setAppverticalspacing(50);
        memberModel.setApphorizontalspacing(50);
        memberModel.setWallpaperId(1);
        memberModel.setWallpaperstate(1);
        memberModel.setWallpapertype("lashen");
        memberModel.setSkin("default");
        // 添加Member
        return desktopService.addMemberModel(memberModel);
    }

    @Override
    public Boolean changeUserAndMember(User user) {
        String roleApps = extractAppList(user);
        //修改Member的Desk1
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(user.getUsername());
        memberModel.setDesk1(roleApps);

        //清空dock和desk
        memberModel.setDock(null);
        memberModel.setDesk2(null);
        memberModel.setDesk3(null);
        memberModel.setDesk4(null);
        memberModel.setDesk5(null);

        //修改
        return desktopService.updateMemberByUserName(memberModel);
    }
}
