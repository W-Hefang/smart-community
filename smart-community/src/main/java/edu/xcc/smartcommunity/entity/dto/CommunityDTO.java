package edu.xcc.smartcommunity.entity.dto;

import lombok.Data;

@Data
public class CommunityDTO {
    private String communityName;
    private String address;
    private Integer scale;
    private String leader;
    private String phone;
    private String description;
}