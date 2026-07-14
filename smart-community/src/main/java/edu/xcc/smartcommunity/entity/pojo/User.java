package edu.xcc.smartcommunity.entity.pojo;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 创建User实体类：和tb_user表建立映射关系
 * 规范：
 * 1. 实现序列化接口
 * 2. 声明字段并封装, 数据类型要匹配
 * 3. 数据类型应该用封装类型（Integer、Long、Boolean），而放弃基本类型（int、long、boolean）
 * 4.@TableId: 映射id字段, 通过type设定自动增长
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @TableField("user_name")
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @TableField("user_phone")
    @Pattern(regexp = "^1[3-9]\\d{9}$",message = "手机号不合法")
    private String userPhone;

    @TableField("user_password")
    @NotEmpty(message = "密码不能为空")
    private String userPassword;

    @TableField("id_card")
    private String idCard;

    @TableField("user_type")
    private String userType;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;

    @TableField("user_status")
    private Integer userStatus;
}
