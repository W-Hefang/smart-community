package edu.xcc.smartcommunity.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementVO {
    private Integer id;

    private String title;

    private String content;

    private String announcementType;

    private String priority;

    private Integer publisherId;

    private String publisherName;

    private String status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime publishTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime endTime;

    private Integer viewCount;

    private String attachmentUrl;

    private String remark;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime updateTime;
}