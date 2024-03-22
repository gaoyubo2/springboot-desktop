package cn.cest.os.sso.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author gaoyubo
 * @since 2024-03-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "tbid", type = IdType.AUTO)
    private Integer tbid;

    private String username;

    private String password;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;

    private Integer isDelete;


}
