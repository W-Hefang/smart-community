package edu.xcc.smartcommunity.entity.vo;

import lombok.Data;
import java.util.List;

@Data
public class CommunityPageLikeVO<T> {
    private List<T> data;
    private Long total;
}