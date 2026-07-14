package edu.xcc.smartcommunity.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageLikeDTO extends PageDTO {
    private String condition;  // 查询条件
}