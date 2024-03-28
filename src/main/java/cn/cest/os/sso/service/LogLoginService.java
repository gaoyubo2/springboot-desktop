package cn.cest.os.sso.service;

import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.pojo.result.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.sql.Date;
import java.util.List;

public interface LogLoginService extends IService<LogLogin> {

    PageResult getLogs(Integer pageNum, Integer pageSum);

    Boolean deleteLogs(List<Integer> ids);
    List<LogLogin> getByUsernameAndDate(String username, Date startDate, Date endDate);
}