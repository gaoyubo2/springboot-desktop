package cn.cest.os.sso.mapper.desktop;



import cn.cest.os.sso.pojo.desktop.AppModel;
import org.apache.ibatis.annotations.Mapper;


/**
 * Description:appMapper类
 * <p>
 * 2015年7月17日 下午1:16:22
 */
@Mapper
public interface DesktopAppMapper
{

    /**
     * 通过appID获取app信息
     * @param appId tbid
     * @return appInfo
     */
    AppModel selectAppByAPPId(Integer appId);
}
