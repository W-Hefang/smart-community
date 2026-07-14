package edu.xcc.smartcommunity.entity.vo;

import lombok.Data;

@Data
public class ScaleStatisticsVO {
    private String scaleGroup;  // 规模分组名称
    private Integer count;       // 该分组社区数量
    private Double avgScale;     // 该分组平均规模
}