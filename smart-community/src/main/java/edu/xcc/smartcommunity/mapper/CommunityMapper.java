package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.xcc.smartcommunity.entity.pojo.Community;
import edu.xcc.smartcommunity.entity.vo.ScaleStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CommunityMapper extends BaseMapper<Community> {

    /**
     * 按规模分组统计社区数量
     */
    @Select("SELECT " +
            "CASE " +
            "   WHEN scale < 500 THEN '小型社区(<500户)' " +
            "   WHEN scale >= 500 AND scale < 1500 THEN '中型社区(500-1500户)' " +
            "   ELSE '大型社区(≥1500户)' " +
            "END as scaleGroup, " +
            "COUNT(*) as count, " +
            "AVG(scale) as avgScale " +
            "FROM community " +
            "GROUP BY scaleGroup " +
            "ORDER BY MIN(scale)")
    List<ScaleStatisticsVO> statisticsByScale();
}