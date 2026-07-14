package edu.xcc.smartcommunity.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Menu implements Serializable {
    private Integer id;
    private String name;
    private String remark;
    private String type;
    private String url;
    private String icon;
    private Integer pid;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
