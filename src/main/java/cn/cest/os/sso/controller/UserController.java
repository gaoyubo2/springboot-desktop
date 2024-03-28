package cn.cest.os.sso.controller;


import cn.cest.os.sso.service.DesktopService;
import cn.cest.os.sso.service.RoleService;
import cn.cest.os.sso.service.UserService;
import cn.cest.os.sso.utils.RedisTools;
import cn.cest.os.sso.utils.Result;
import cn.cest.os.sso.mapper.manage.UserMapper;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DesktopService desktopService;
    @Autowired
    private RedisTools redisTools;

    @DeleteMapping("deleteUsers")
    public Result<Boolean> deleteUser(@RequestBody List<Integer> uids){
        for(Integer userid:uids){
            boolean flag = userService.removeById(userid);
            if(!flag){
                return Result.fail("删除用户失败");
            }
        }
        return Result.ok(true, "删除用户成功");
//        boolean flag = userService.removeById(user);
//        return flag?Result.ok(true,"删除成功"):Result.fail(false,"添加失败");
    }
    @PostMapping("user")
    public Result<Boolean> saveUser(@RequestBody User user){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",user.getUsername());
        User user_old = userMapper.selectOne(userQueryWrapper);
        if(user_old != null)
            return Result.fail("用户名重复，请再次尝试");
        //以上增加判断新增用户名是否重复判断

        Boolean flag = userService.addUserAndMember(user);
        return flag?Result.ok(true,"添加成功"):Result.fail(false,"添加失败");
    }
    @PutMapping("user")
    @Transactional(rollbackFor = Exception.class)
    public Result<Boolean> putUser(@RequestBody User user){
        //删除redis
        String token = StpUtil.getTokenValueByLoginId(user.getTbid());
        if(token != null && !token.isEmpty()){
            //退出登录
            StpUtil.logout(user.getTbid());
            //删除token
            redisTools.delete(token);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().eq("username", user.getUsername()).ne("tbid", user.getTbid());
        User other = userMapper.selectOne(queryWrapper);
        if(other != null)
            return Result.fail("用户名无法修改");
        //以上增加判断修改用户名是否重复判断
        Boolean roleChange = userService.ifRoleChange(user.getRoleId(),user.getTbid());
        //修改用户名、修改member表
        String originName = userService.getById(user.getTbid()).getUsername();
        Boolean flag = desktopService.updateUsername(originName,user.getUsername());
        if (!flag) return Result.fail("修改用户名称失败");
        //修改无权限部分
        boolean flagNoApp = userService.updateById(user);
        boolean flagWithApp = true;
        if (roleChange) {
            System.out.println("用户权限改变了");
            flagWithApp  = userService.changeUserAndMember(user);
            System.out.println("flagWithApp:"+flagNoApp);
        }
        return (flagNoApp && flagWithApp)?Result.ok(true,"修改成功"):Result.fail(false,"修改失败");
    }
    @GetMapping("user")
    public Result<User> getUser(@RequestParam Integer uid){
        User userById = userService.getById(uid);
        return userById!=null?Result.ok(userById,"查询成功"):Result.fail("查询失败");
    }

    //封装分页结果查询
    @GetMapping("users")
    public Result<PageResult> getUsers(@RequestParam(value = "pageNum", required = true) Integer pageNum,
                                       @RequestParam(value = "pageSize", required = true) Integer pageSize,
                                       @RequestParam(value = "rolename", required = false) String rolename,
                                       @RequestParam(value = "username", required = false) String username){
        System.out.println(pageNum+"   "+pageSize);
        PageResult pageResult = userService.search(pageNum, pageSize, rolename, username);
        if(pageResult == null)
            return Result.fail("查询用户列表失败");
        return Result.ok(pageResult);
    }
    @DeleteMapping("deleteUsersLogic")
    public Result<Boolean> deleteUserLogic(@RequestBody List<Integer> uids){
        Boolean flag = userService.deleteusers(uids);
        if(!flag)
            return Result.fail("删除用户失败");
        return Result.ok(true,"删除用户成功");

        //throw new NoGetExecption(CodeEnum.RC500.getCode(), CodeEnum.RC500.getMsg());
    }

    @PutMapping("enableUser")
    public Result enableUser(@RequestParam Integer userId,
                             @RequestParam Integer isDelete){
        Boolean flag = userService.enableUser(userId, isDelete);
        if(!flag)
            return Result.fail("用户状态修改失败");
        return Result.ok(true, "用户状态修改成功");
    }


}

