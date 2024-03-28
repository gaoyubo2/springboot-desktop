package cn.cest.os.sso.service.impl;

import cn.cest.os.sso.mapper.manage.LogLoginMapper;
import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.pojo.result.PageResult;
import cn.cest.os.sso.service.LogLoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.LogManager;

import java.sql.Date;
import java.util.List;

@Service

public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements LogLoginService {


    @Override
    public PageResult getLogs(Integer pageNum, Integer pageSum) {
        Page<LogLogin> page = new Page<>(pageNum, pageSum);
        Page<LogLogin> logLoginPage = this.baseMapper.selectPage(page, null);
        List<LogLogin> records = logLoginPage.getRecords();
        int total = Math.toIntExact(this.baseMapper.selectCount(null));
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
    @Override
    public PageResult getByUsernameAndDate(String username, Date startDate, Date endDate, Integer pageNum, Integer pageSize) {
        Page<LogLogin> page = new Page<>(pageNum, pageSize);

        QueryWrapper<LogLogin> queryWrapper = new QueryWrapper<>();
        if (username != null && !username.isEmpty()) {
            queryWrapper.eq("username", username);
        }
        if (startDate != null) {
            queryWrapper.ge("login_time", startDate);
        }
        if (endDate != null) {
            // 如果 endDate 不为空，将其增加一天作为查询条件，包括当天的数据
            queryWrapper.le("login_time", new Date(endDate.getTime() + 24 * 60 * 60 * 1000));
        }
        Page<LogLogin> res = baseMapper.selectPage(page, queryWrapper);
        List<LogLogin> records = res.getRecords();
        return new PageResult(res.getTotal(),records);
    }
}

