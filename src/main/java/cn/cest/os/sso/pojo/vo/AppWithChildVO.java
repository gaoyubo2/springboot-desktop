package cn.cest.os.sso.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppWithChildVO implements Serializable {

    private Integer tbid;//主键

    private String name; //图标名称

    private String icon;//图标图片

    private String url;//图标链接

    private List<AppWithChildVO> child;

}