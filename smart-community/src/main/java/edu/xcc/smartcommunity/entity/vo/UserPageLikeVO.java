package edu.xcc.smartcommunity.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPageLikeVO<T> implements Serializable {
    private List<T> data;
    private Long total;
}
