package edu.xcc.smartcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.xcc.smartcommunity.entity.dto.CommunityDTO;
import edu.xcc.smartcommunity.entity.pojo.Community;
import edu.xcc.smartcommunity.entity.vo.CommunityVO;
import edu.xcc.smartcommunity.entity.vo.ScaleStatisticsVO;
import edu.xcc.smartcommunity.mapper.CommunityMapper;
import edu.xcc.smartcommunity.service.CommunityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CommunityServiceimpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    /**
     * 新增社区
     */
    @Override
    public boolean addCommunity(CommunityDTO communityDTO) {
        Community community = new Community();
        BeanUtils.copyProperties(communityDTO, community);
        community.setCreateTime(LocalDateTime.now());
        community.setUpdateTime(LocalDateTime.now());
        int insert = communityMapper.insert(community);
        return insert > 0;
    }

    /**
     * 批量新增社区
     */
    @Override
    public boolean addCommunityBatch(List<CommunityDTO> communityDTOList) {
        List<Community> communityList = new ArrayList<>();
        for (CommunityDTO dto : communityDTOList) {
            Community community = new Community();
            BeanUtils.copyProperties(dto, community);
            community.setCreateTime(LocalDateTime.now());
            community.setUpdateTime(LocalDateTime.now());
            communityList.add(community);
        }
        for (Community community : communityList) {
            communityMapper.insert(community);
        }
        return true;
    }

    /**
     * 根据id删除
     */
    @Override
    public boolean deleteById(Integer communityId) {
        int i = communityMapper.deleteById(communityId);
        return i > 0;
    }

    /**
     * 批量删除
     */
    @Override
    public boolean deleteBatch(List<Integer> ids) {
        int i = communityMapper.deleteByIds(ids);
        return i > 0;
    }

    /**
     * 根据id修改
     */
    @Override
    public boolean updateById(Integer communityId, CommunityDTO communityDTO) {
        Community community = communityMapper.selectById(communityId);
        if (community == null) {
            throw new RuntimeException("社区不存在，修改失败");
        }
        BeanUtils.copyProperties(communityDTO, community);
        community.setUpdateTime(LocalDateTime.now());
        int i = communityMapper.updateById(community);
        return i > 0;
    }

    /**
     * 根据id查询
     */
    @Override
    public CommunityVO getById(Integer communityId) {
        Community community = communityMapper.selectById(communityId);
        if (community == null) {
            return null;
        }
        CommunityVO communityVO = new CommunityVO();
        BeanUtils.copyProperties(community, communityVO);
        return communityVO;
    }

    /**
     * 查询所有
     */
    @Override
    public List<CommunityVO> getAll() {
        List<Community> communityList = communityMapper.selectList(null);
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : communityList) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 根据名称模糊查询
     */
    @Override
    public List<CommunityVO> getByNameLike(String name) {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, name);
        List<Community> communityList = communityMapper.selectList(wrapper);
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : communityList) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 多条件模糊查询
     */
    @Override
    public List<CommunityVO> getByMultiCondition(String keyword) {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, keyword)
                .or()
                .like(Community::getAddress, keyword)
                .or()
                .like(Community::getLeader, keyword);
        List<Community> communityList = communityMapper.selectList(wrapper);
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : communityList) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        return voList;
    }

    /**
     * 分页查询
     */
    @Override
    public Page<CommunityVO> pageQuery(Integer pageNum, Integer pageSize) {
        Page<Community> page = new Page<>(pageNum, pageSize);
        Page<Community> resultPage = communityMapper.selectPage(page, null);

        Page<CommunityVO> voPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : resultPage.getRecords()) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        voPage.setRecords(voList);
        return voPage;
    }

    /**
     * 分页 + 名称模糊查询 + 按id降序
     */
    @Override
    public Page<CommunityVO> pageQueryByNameLike(Integer pageNum, Integer pageSize, String name) {
        Page<Community> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, name);
        wrapper.orderByDesc(Community::getCommunityId);

        Page<Community> resultPage = communityMapper.selectPage(page, wrapper);

        Page<CommunityVO> voPage = new Page<>(resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : resultPage.getRecords()) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        voPage.setRecords(voList);
        return voPage;
    }
    /**
     * 统计社区规模分布
     */
    @Override
    public List<ScaleStatisticsVO> getScaleStatistics() {
        return communityMapper.statisticsByScale();
    }

    /**
     * 获取社区总数
     */
    @Override
    public long getTotalCount() {
        return this.count();
    }

    /**
     * 获取指定规模范围的社区
     */
    @Override
    public List<CommunityVO> getByScaleRange(Integer minScale, Integer maxScale) {
        if (minScale == null) minScale = 0;
        if (maxScale == null) maxScale = Integer.MAX_VALUE;

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.between(Community::getScale, minScale, maxScale);
        wrapper.orderByDesc(Community::getScale);

        List<Community> communityList = this.list(wrapper);
        List<CommunityVO> voList = new ArrayList<>();
        for (Community community : communityList) {
            CommunityVO vo = new CommunityVO();
            BeanUtils.copyProperties(community, vo);
            voList.add(vo);
        }
        return voList;
    }
}