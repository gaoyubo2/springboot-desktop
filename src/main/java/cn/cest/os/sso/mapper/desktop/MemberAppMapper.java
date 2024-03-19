package cn.cest.os.sso.mapper.desktop;


import cn.cest.os.sso.pojo.desktop.MemberAppModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:MemberAppMapper类
 * <p>
 * 2015年7月20日 下午7:32:07
 */
@Mapper
public interface MemberAppMapper
{
    /**
     * Description: 新增
     *
     * @param memberAppModel
     */
    Integer insert(MemberAppModel memberAppModel);

    /**
     * Description: 删除
     *
     * @param memberAppModel
     */
    Integer delete(MemberAppModel memberAppModel);

    /**
     * Description: 通过tbid修改
     *
     * @param memberAppModel
     */
    Integer updateById(MemberAppModel memberAppModel);

    /**
     * Description: 通过条件查询
     *
     * @param memberAppModel
     */
    List<MemberAppModel> selectByCondition(MemberAppModel memberAppModel);

    /**
     * Description: 通过应用id组成的字符串和无权限应用名称字符串联合查询应用
     *
     * @param appIds noAuthByUsernames
     */
    List<MemberAppModel> selectByAppids(@Param("appIds") String appIds, @Param("appByUsername") String appByUsername);

    /**
     * Description: 通过无权限的应用名称删除对应用户应用
     */
    Integer deletBynoAuthByUsernames(@Param("memberId") Integer memberId, @Param("appByUsername") String appByUsername);
}