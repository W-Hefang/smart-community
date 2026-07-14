package edu.xcc.smartcommunity.entity.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CommunityPageLikeDTO extends PageDTO {
    private String condition;  // 查询条件（社区名称关键字）
}