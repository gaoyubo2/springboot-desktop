package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.mapper.manage.LogLoginMapper;
import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.service.LogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {

}

