package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.RoleService;
import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.Role;
import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private RoleService roleService;

//    @PostMapping("user")
//    public Result<Integer> addUser(@RequestBody User user){
//        boolean flag = userService.save(user);
//        Integer userId = userService.getById(user).getTbid();
//        return flag?Result.ok(userId,"添加成功"):Result.fail("添加失败");
//    }
    @DeleteMapping("user")
    public Result<Boolean> deleteUser(@RequestBody User user){
        boolean flag = userService.removeById(user);
        return flag?Result.ok(true,"删除成功"):Result.fail(false,"添加失败");
    }
    @PostMapping("user")
    public Result<Boolean> saveOrUpdateUser(@RequestBody User user){
        boolean flag = userService.saveOrUpdate(user);
        return flag?Result.ok(true,"操作成功"):Result.fail(false,"操作失败");
    }
    @GetMapping("user")
    public Result<User> getUser(@RequestParam Integer uid){
        User userById = userService.getById(uid);
        return userById!=null?Result.ok(userById,"查询成功"):Result.fail("查询失败");
    }
    @GetMapping("users")
    public Result<List<UserInfoVO>> getUsers(@RequestParam(value = "pageNum", required = true) Integer pageNum, @RequestParam(value = "pageSize", required = true) Integer pageSize){
        Page<User> page = new Page<>(pageNum, pageSize);
        userService.page(page, null);
        List<User> users = page.getRecords();
        if(users == null)
            return Result.fail("获取用户列表失败");

        //List<User> users = userService.list(null);
        System.out.println(users);
        List<UserInfoVO> userInfoVOList = new ArrayList<>(users.size());
        for(User user: users){
            userService.extracted(userInfoVOList, user);
        }
        return Result.ok(userInfoVOList,"获取用户列表成功");
    }
    @DeleteMapping("deleteUsers")
    public Result<Boolean> deleteUser(@RequestBody List<Integer> uids){
        for (Integer uid: uids){
            boolean flag = userService.removeById(uid);
            if(!flag) Result.fail(false,"删除失败");
        }

        return Result.ok(true,"删除成功");
    }


}

