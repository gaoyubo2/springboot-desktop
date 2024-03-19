package cn.cest.os.sso.pojo.desktop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description:tb_member_app表
 *
 * @author bo.xu
 * 2015年7月20日 下午2:21:16
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAppModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private Integer realid;//真实id

    private String name;//图标名称

    private String icon;//图标图片

    private String url;//应用地址

    private String type;//应用类型

    private Integer width;//窗口宽度

    private Integer height;//窗口高度

    private Integer isresize;//是否能对窗口进行拉伸

    private Integer isopenmax;//是否打开直接最大化

    private Integer issetbar;//窗口是否有评分和介绍按钮

    private Integer isflash;//是否为flash应用

    private String ext;//扩展名

    private Integer size;//文件大小

    private String dt;//创建时间

    private String lastdt;//最后修改时间

    private Integer folderId;//文件夹id

    private Integer memberId;//用户id

    private Integer isshortcut;//是否快捷方式

    private Integer appid;

    private Integer realappid;

    private String error;

}