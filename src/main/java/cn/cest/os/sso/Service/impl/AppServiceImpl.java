package cn.cest.os.sso.Service.impl;

import cn.cest.os.sso.Service.AppService;
import cn.cest.os.sso.pojo.App;
import cn.cest.os.sso.mapper.AppMapper;

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
