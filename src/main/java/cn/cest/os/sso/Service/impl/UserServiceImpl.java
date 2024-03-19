package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.pojo.User;
import cn.cest.os.sso.mapper.UserMapper;
import cn.cest.os.sso.Service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
