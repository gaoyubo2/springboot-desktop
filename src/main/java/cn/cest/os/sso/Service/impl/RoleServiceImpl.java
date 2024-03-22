package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.mapper.manage.RoleMapper;
import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.pojo.vo.RoleTreeVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean deleteUserAndRole(List<Integer> roleIds) {
        for(Integer roleid:roleIds){
            Role role = roleMapper.selectById(roleid);
            if(role == null){
                return false;
            }
            role.setIsDelete(1);        //逻辑删除角色
            roleMapper.updateById(role);

            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("role_id", roleid);
            List<User> users = userMapper.selectList(userQueryWrapper);
            for(User user:users){
                user.setIsDelete(1);    //逻辑删除所属角色下 的用户
                userMapper.updateById(user);
            }
        }

        return true;
    }

    @Override
    public List<RoleTreeVO> showRoles() {
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("parentid", 0);
        List<Role> roles = roleMapper.selectList(roleQueryWrapper);     //一级role

        List<RoleTreeVO> roleTreeVOS = new ArrayList<RoleTreeVO>(roles.size());

        for(int i=0; i<roles.size(); i++){
            RoleTreeVO index = new RoleTreeVO();
            index.setRoleTreeId(roles.get(i).getTbid());   //一级id
            index.setRoleTreeName(roles.get(i).getName()); //一级name

            //开始插入二级列表
            Integer parent = index.getRoleTreeId();
            QueryWrapper<Role> roleQueryWrapper1 = new QueryWrapper<>();
            roleQueryWrapper1.eq("parentid", parent);
            List<Role> roles2 = roleMapper.selectList(roleQueryWrapper1);
            //index.setChildren(roles2);
            roleTreeVOS.add(index);
        }
        return roleTreeVOS;

    }

    @Override
    public List<RoleTreeVO> findChildrenRoles(Integer parentId) {
        List<RoleTreeVO> childrenRoles = new ArrayList<>();

        // 查询当前父级角色ID对应的子级角色列表
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentid", parentId);
        List<Role> directChildren = this.list(queryWrapper);

        // 如果存在直接子级角色，递归查询其下一级子级角色列表
        if (directChildren != null && !directChildren.isEmpty()) {
            for (Role child : directChildren) {
                // 递归调用，获取下一级子级角色列表
                List<RoleTreeVO> grandChildren = findChildrenRoles(child.getTbid());
                // 创建 RoleTreeVO 对象并设置属性
                RoleTreeVO roleTreeVO = new RoleTreeVO()
                        .setRoleTreeId(child.getTbid())
                        .setRoleTreeName(child.getName())
                        .setChild(grandChildren);
                // 将当前角色添加到列表中
                childrenRoles.add(roleTreeVO);
            }
        }

        return childrenRoles;
    }

    @Override
    public PageResult selectByName(String roleName, Integer pageNum, Integer pageSize) {

        Page<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("rolename", roleName);


        return null;
    }
}
