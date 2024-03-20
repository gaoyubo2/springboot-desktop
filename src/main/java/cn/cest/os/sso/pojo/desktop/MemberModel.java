package cn.cest.os.sso.pojo.desktop;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Date 2024/3/20 22:19
 * @Author 郜宇博
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberModel implements Serializable
{

    private static final long serialVersionUID = 1L;

    private Integer tbid;//主键

    private String username;//用户名

    private Integer type;//用户类型，0（普通用户）1（管理员）

    private String dock;//[应用码头]应用id，用","相连

    private String desk1;//[桌面1]应用id，用","相连

    private String desk2;//[桌面2]应用id，用","相连

    private String desk3;//[桌面3]应用id，用","相连

    private String desk4;//[桌面4]应用id，用","相连

    private String desk5;//[桌面5]应用id，用","相连

    private String appxy;//图标排列方式，x（横向排列）y（纵向排列）

    private Integer desk;//默认桌面

    private String dockpos;//应用码头位置，top（顶部）left（左侧）right（右侧）none（隐藏）

    private Integer appsize;//图标尺寸

    private Integer appverticalspacing;//图标垂直间距

    private Integer apphorizontalspacing;//图标水平间距

    private Integer wallpaperId;//壁纸id

    private String wallpaperwebsite;//壁纸网址

    private Integer wallpaperstate;//1（系统壁纸）2（自定义壁纸）3（网络地址）

    private String wallpapertype;//壁纸显示方式，tianchong（填充）shiying（适应）pingpu（平铺）lashen（拉伸）juzhong（居中）

    private String skin;//窗口皮肤

    private String regdt;//注册时间

    private String lastlogindt;//上次登录时间

    private String lastloginip;//上次登录IP

    private String thislogindt;//本次登陆时间

    private String thisloginip;//本次登录IP


}