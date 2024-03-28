package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.mapper.manage.LogLoginMapper;
import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.service.LogLoginService;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.LogManager;

@Service

public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {


    @Override
    public PageResult getLogs(Integer pageNum, Integer pageSum) {
        Page<LogLogin> page = new Page<>(pageNum, pageSum);
        Page<LogLogin> logLoginPage = this.baseMapper.selectPage(page, null);
        List<LogLogin> records = logLoginPage.getRecords();
        Integer total = Math.toIntExact(this.baseMapper.selectCount(null));
        return new PageResult(total, records);
    }

    @Override
    public Boolean deleteLogs(List<Integer> ids) {
        for (Integer id : ids) {
            int i = this.baseMapper.deleteById(id);
            if(i < 1)
                return false;
        }
        return true;
    }
}

