package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
