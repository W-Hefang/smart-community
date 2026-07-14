package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.xcc.smartcommunity.entity.pojo.Announcement;

public interface AnnouncementMapper extends BaseMapper<Announcement> {
    
    long countByCondition(String keyword, String status);
}