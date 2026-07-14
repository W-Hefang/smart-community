package edu.xcc.smartcommunity.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("announcement")
public class Announcement implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "announcement_id", type = IdType.AUTO)
    private Integer announcementId;

    @TableField("title")
    @NotEmpty(message = "公告标题不能为空")
    private String title;

    @TableField("content")
    private String content;

    @TableField("announcement_type")
    @NotEmpty(message = "公告类型不能为空")
    private String announcementType;

    @TableField("priority")
    private String priority;

    @TableField("publisher_id")
    @NotNull(message = "发布人ID不能为空")
    private Integer publisherId;

    @TableField("publisher_name")
    private String publisherName;

    @TableField("status")
    private String status;

    @TableField("publish_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime publishTime;

    @TableField("end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    @TableField("view_count")
    private Integer viewCount;

    @TableField("attachment_url")
    private String attachmentUrl;

    @TableField("remark")
    private String remark;

    @TableField("create_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @TableField("update_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
}