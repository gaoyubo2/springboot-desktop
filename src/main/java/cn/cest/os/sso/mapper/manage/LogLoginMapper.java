package cn.cest.os.sso.mapper.manage;

import cn.cest.os.sso.pojo.LogLogin;
import cn.cest.os.sso.pojo.RoleApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;

@Mapper
public interface LogLoginMapper extends BaseMapper<LogLogin> {
//    List<LogLogin> selectByUsernameAndDate(@Param("username") String username, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
