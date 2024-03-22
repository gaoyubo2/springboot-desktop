package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.DesktopService;
import cn.cest.os.sso.Service.RoleAppService;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.mapper.manage.RoleMapper;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.RoleApp;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.pojo.desktop.MemberModel;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserMapper userMapper;
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
        userInfoVO.setIsDelete(user.getIsDelete());
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean changeUserAndMember(User user,Boolean roleChange) {
        boolean flag = this.updateById(user);
        if(!roleChange) return flag;
        //获取所有信息
        user = this.getById(user.getTbid());

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

    @Override
    public PageResult search(Integer pageNum, Integer pageSize, String rolename, String username) {
        Page<User> page = new Page<>(pageNum, pageSize);

        //有username 就构造username的分页条件
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            userQueryWrapper.like("username", username);
        }

        //有rolename 就构造rolename的分页条件
        if (rolename != null && !rolename.isEmpty()) {
            // 构建查询角色名对应的roleId
            QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
            roleQueryWrapper.eq("name", rolename);
            Role role = roleMapper.selectOne(roleQueryWrapper);
            if (role != null) {
                userQueryWrapper.eq("role_id", role.getTbid());
            } else {
                // 如果查询不到对应的 roleId，则直接返回空结果
                return null;
            }
        }
        IPage<User> userPage = userMapper.selectPage(page, userQueryWrapper);
        List<UserInfoVO> userInfoVOList = new ArrayList<>();
        List<User> records = userPage.getRecords();
        for (User user : records) {
            extracted(userInfoVOList, user);
        }
        //返回符合条件的所有total
        Long count = userMapper.selectCount(userQueryWrapper);
        return new PageResult(count, userInfoVOList);
    }

    @Override
    public Boolean deleteusers(List<Integer> uids) {
        for(Integer id:uids){
            User user = userMapper.selectById(id);
            user.setIsDelete(1);
            int i = userMapper.updateById(user);
            if(i == 0)
                return false;
        }
        return true;
    }

    @Override
    public Boolean enableUser(Integer userId, Integer isDelete) {
        User user = userMapper.selectById(userId);
        Integer roleId = user.getRoleId();
        Role role = roleMapper.selectById(roleId);

        //用户所属角色未启用
        if(role.getIsDelete() == 1)
            return false;
        user.setIsDelete(isDelete);
        int i = userMapper.updateById(user);
        if(i != 0)
            return true;

        return false;
    }

    @Override
    public Boolean ifRoleChange(Integer roleId, Integer tbid) {
        //获取之前roleId
        Integer beforeRoleId = this.getById(tbid).getRoleId();
        return !Objects.equals(beforeRoleId, roleId);
    }

    @Override
    public Boolean ifRoleChange(Integer roleId, Integer tbid) {
        //获取之前roleId
        Integer beforeRoleId = this.getById(tbid).getRoleId();
        return !Objects.equals(beforeRoleId, roleId);
    }
}
