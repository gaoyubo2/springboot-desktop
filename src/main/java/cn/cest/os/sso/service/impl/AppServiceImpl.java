package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.service.AppService;
import cn.cest.os.sso.pojo.App;
import cn.cest.os.sso.mapper.manage.AppMapper;

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
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

}
