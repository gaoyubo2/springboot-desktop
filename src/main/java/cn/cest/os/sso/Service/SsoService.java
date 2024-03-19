package cn.cest.os.sso.Service;

import java.util.List;

public interface SsoService {

    /**
     * 根据用户名密码获取UID
     * @param username 用户名
     * @param password 密码
     * @return 用户id
     */
    Integer getUidBuUserNameAndPwd(String username,String password);

    /**
     * 根据用户id获取权限列表
     * @param uid 用户id
     * @return 权限列表
     */
    List<Integer> getPermissionListByUid(Integer uid);
}