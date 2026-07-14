package edu.xcc.smartcommunity.entity.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDTO {
    private Integer id;

    @NotEmpty(message = "公告标题不能为空")
    private String title;

    private String content;

    @NotEmpty(message = "公告类型不能为空")
    private String announcementType;

    private String priority;

    @NotNull(message = "发布人ID不能为空")
    private Integer publisherId;

    private String publisherName;

    private String status;

    private LocalDateTime publishTime;

    private LocalDateTime endTime;

    private String attachmentUrl;

    private String remark;
}