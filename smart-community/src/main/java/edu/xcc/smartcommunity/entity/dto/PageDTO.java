package edu.xcc.smartcommunity.entity.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer page = 1;
    private Integer size = 10;
}