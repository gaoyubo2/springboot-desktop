package cn.cest.os.sso.Controller;


import cn.cest.os.sso.Service.UserService;
import cn.cest.os.sso.Util.Result;
import cn.cest.os.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("user")
    public Result<Integer> addUser(@RequestBody User user){
        boolean flag = userService.save(user);
        Integer userId = userService.getById(user).getTbid();
        return flag?Result.ok(userId,"添加成功"):Result.fail("添加失败");
    }
    @DeleteMapping("user")
    public Result<Boolean> deleteUser(@RequestBody User user){
        boolean flag = userService.removeById(user);
        return flag?Result.ok(true,"删除成功"):Result.fail(false,"添加失败");
    }
    @PostMapping("user2")
    public Result<Boolean> changeUser(@RequestBody User user){
        boolean flag = userService.save(user);
        return flag?Result.ok(true,"更改成功"):Result.fail(false,"更改失败");
    }
    @GetMapping("user")
    public Result<User> getUser(@RequestBody User user){
        User userById = userService.getById(user);
        return userById!=null?Result.ok(userById,"查询成功"):Result.fail("查询失败");
    }

}

