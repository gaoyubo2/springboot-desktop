package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.SsoService;
import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.mapper.manage.RoleAppMapper;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.User;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SsoServiceImpl implements SsoService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleAppMapper roleAppMapper;

    /**
     * 根据用户名密码获取UID
     *
     * @param username 用户名
     * @param password 密码
     * @return uid
     */
    @Override
    public Integer getUidBuUserNameAndPwd(String username, String password) {
        // 构造查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("password", password);

        // 查询数据库是否存在匹配的记录
        User user = userMapper.selectOne(queryWrapper);
        if(user != null) return user.getTbid();
        return -1;
    }

    /**
     * 根据用户id获取权限列表
     *
     * @param uid 用户id
     * @return 权限列表
     */
    @Override
    public List<Integer> getPermissionListByUid(Integer uid) {
        List<Integer> permissionList = new ArrayList<>();
        //获取角色
        User user = userMapper.selectById(uid);
        //获取角色权限
        QueryWrapper<RoleApp> queryWrapper = new QueryWrapper<RoleApp>().eq("role_id", user.getRoleId());
        List<RoleApp> roleApps = roleAppMapper.selectList(queryWrapper);
        for (RoleApp roleApp: roleApps){
            permissionList.add(roleApp.getAppId());
        }
        return permissionList;

    }

    @Override
    public String loginAndSetSession(Integer uid, String username) {
        StpUtil.login(uid);
        SaSession session = StpUtil.getSessionByLoginId(uid);
        //获取用户角色
        Integer roleId = userMapper.selectById(uid).getRoleId();
        session.set("username",username);
        session.set("uid",uid);
        session.set("roleId",roleId);
        return StpUtil.getTokenValue();
    }
}
