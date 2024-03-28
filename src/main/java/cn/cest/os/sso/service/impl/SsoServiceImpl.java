package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.constant.Constant;
import cn.cest.os.sso.service.SsoService;
import cn.cest.os.sso.mapper.manage.RoleAppMapper;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.utils.DateTimeUtil;
import cn.cest.os.sso.utils.Result;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public Map<String, Object> getUserInfoRedis(Integer uid) {
        User user = userMapper.selectById(uid);
        Integer roleId = user.getRoleId();
        List<RoleApp> roleApps = roleAppMapper.selectList(new QueryWrapper<RoleApp>().eq("role_id", roleId));
        List<Integer> appList = new ArrayList<>(roleApps.size());
        for(RoleApp roleApp: roleApps){
            appList.add(roleApp.getAppId());
        }
        Map<String,Object> res = new HashMap<>();
        res.put("uid",uid);
        res.put("username",user.getUsername());
        res.put("appList",appList);
        return res;
    }

    @Override
    public boolean checkCode(HttpServletRequest request,String code) {
        HttpSession code_session = request.getSession();
        //取出验证码
        String sessionCode = (String) code_session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //取出时间
        Date firstTime = DateTimeUtil.convertLocalDateTimeToDate((LocalDateTime) code_session.getAttribute(Constants.KAPTCHA_SESSION_DATE));
        //验证码长度不相等 或者 超时
        if(sessionCode.length() != code.length() || System.currentTimeMillis() - firstTime.getTime() > 60 * 1000L)
            return false;
        //System.out.println("验证时：session id："+session2.getId());
        //System.out.println(sessionCode);
        boolean codeFlag = code.equalsIgnoreCase(sessionCode);
        code_session.removeAttribute("logincode");
        return codeFlag;
    }

    @Override
    public Integer checkLogin(String username, String password) {
        //获取uid
        Integer uid = this.getUidBuUserNameAndPwd(username,password);
        if(uid == -1) {
            return Constant.UserOrPwdError;
        }
        //被禁用账户无法登录
        User user = userMapper.selectById(uid);
        if(user.getIsDelete() == 1){
            return Constant.UserNoAccess;
        }
        //验证通过
        return uid;
    }

    @Override
    public Map<String, Object> getLoginResult(Integer uid, String url, String token) {
        Map<String,Object> map = new HashMap<>();
        map.put("uid",uid);
        map.put("redirect",url);
        map.put("token",token);
        return map;
    }
}
