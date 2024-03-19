package cn.cest.os.sso.pojo.desktop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Description:app表
 *
 * @author bo.xu
 * 2015年7月17日 下午1:16:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private String name; //图标名称

    private String icon;//图标图片

    private String url;//图标链接

    private Integer width;//窗口宽度

    private Integer height;//窗口高度

    private Integer isresize;//是否能对窗口进行拉伸，1（是）0（否）

    private Integer isopenmax;//是否打开直接最大化，1（是）0（否）

    private Integer issetbar;//窗口是否有评分和介绍按钮，1（是）0（否）

    private Integer isflash;//是否为flash应用，1（是）0（否）

    private String remark;//备注

    private Integer usecount;//使用人数

    private Double starnum;//评分

    private String dt;//添加时间

    private Integer isrecommend;//是否推荐，1（是）0（否）

    private Integer verifytype;//审核状态，0（未提交审核）1（审核通过）2（审核中）3（审核不通过）

    private String verifyinfo;//审核提示信息

    private Integer app_category_id;//应用类目id

    private Integer member_id;//用户id

    private Integer current_page;//当前页


}
