package edu.xcc.smartcommunity.entity.pojo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * Community实体类：和community表建立映射关系
 */
@Data
@TableName("community")
public class Community implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "community_id", type = IdType.AUTO)
    private Integer communityId;

    @TableField("community_name")
    private String communityName;

    @TableField("address")
    private String address;

    @TableField("scale")
    private Integer scale;

    @TableField("leader")
    private String leader;

    @TableField("phone")
    private String phone;

    @TableField("description")
    private String description;

    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
}