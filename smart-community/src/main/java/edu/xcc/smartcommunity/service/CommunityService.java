package edu.xcc.smartcommunity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.xcc.smartcommunity.entity.dto.CommunityDTO;
import edu.xcc.smartcommunity.entity.pojo.Community;
import edu.xcc.smartcommunity.entity.vo.CommunityVO;
import edu.xcc.smartcommunity.entity.vo.ScaleStatisticsVO;

import java.util.List;
import java.util.Map;

public interface CommunityService extends IService<Community> {
    // 新增
    boolean addCommunity(CommunityDTO communityDTO);

    // 批量新增
    boolean addCommunityBatch(List<CommunityDTO> communityDTOList);

    // 根据id删除
    boolean deleteById(Integer communityId);

    // 批量删除
    boolean deleteBatch(List<Integer> ids);

    // 根据id修改
    boolean updateById(Integer communityId, CommunityDTO communityDTO);

    // 根据id查询
    CommunityVO getById(Integer communityId);

    // 查询所有
    List<CommunityVO> getAll();

    // 根据名称模糊查询
    List<CommunityVO> getByNameLike(String name);

    // 多条件模糊查询
    List<CommunityVO> getByMultiCondition(String keyword);

    // 分页查询
    Page<CommunityVO> pageQuery(Integer pageNum, Integer pageSize);

    // 分页 + 名称模糊查询 + 排序
    Page<CommunityVO> pageQueryByNameLike(Integer pageNum, Integer pageSize, String name);

    List<ScaleStatisticsVO> getScaleStatistics();
    long getTotalCount();
    List<CommunityVO> getByScaleRange(Integer minScale, Integer maxScale);
}