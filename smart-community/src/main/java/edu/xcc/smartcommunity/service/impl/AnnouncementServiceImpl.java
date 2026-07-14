package edu.xcc.smartcommunity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.xcc.smartcommunity.entity.dto.AnnouncementDTO;
import edu.xcc.smartcommunity.entity.pojo.Announcement;
import edu.xcc.smartcommunity.entity.vo.AnnouncementVO;
import edu.xcc.smartcommunity.entity.vo.PageResultVO;
import edu.xcc.smartcommunity.handler.BizException;
import edu.xcc.smartcommunity.mapper.AnnouncementMapper;
import edu.xcc.smartcommunity.service.AnnouncementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement> implements AnnouncementService {

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public AnnouncementVO add(AnnouncementDTO dto) throws BizException {
        Announcement announcement = new Announcement();
        BeanUtils.copyProperties(dto, announcement);
        announcement.setStatus("draft");
        announcement.setViewCount(0);
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());

        boolean saved = save(announcement);
        if (!saved) {
            throw new BizException("添加公告失败");
        }

        return convertToVO(announcement);
    }

    @Override
    public AnnouncementVO update(AnnouncementDTO dto) throws BizException {
        Announcement announcement = announcementMapper.selectById(dto.getId());
        if (announcement == null) {
            throw new BizException("公告不存在");
        }

        BeanUtils.copyProperties(dto, announcement);
        announcement.setUpdateTime(LocalDateTime.now());

        boolean updated = updateById(announcement);
        if (!updated) {
            throw new BizException("更新公告失败");
        }

        return convertToVO(announcement);
    }

    @Override
    public void deleteById(Integer id) throws BizException {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BizException("公告不存在");
        }

        boolean deleted = removeById(id);
        if (!deleted) {
            throw new BizException("删除公告失败");
        }
    }

    @Override
    public AnnouncementVO getById(Integer id) throws BizException {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BizException("公告不存在");
        }
        return convertToVO(announcement);
    }

    @Override
    public List<AnnouncementVO> listByStatus(String status) {
        LambdaQueryWrapper<Announcement> wrapper = Wrappers.lambdaQuery(Announcement.class);
        wrapper.eq(Announcement::getStatus, status);
        wrapper.orderByDesc(Announcement::getPublishTime);

        List<Announcement> announcements = list(wrapper);
        return convertToVOList(announcements);
    }

    @Override
    public List<AnnouncementVO> listAll() {
        LambdaQueryWrapper<Announcement> wrapper = Wrappers.lambdaQuery(Announcement.class);
        wrapper.orderByDesc(Announcement::getPublishTime);
        List<Announcement> announcements = list(wrapper);
        return convertToVOList(announcements);
    }

    @Override
    public List<AnnouncementVO> search(String keyword, String status) {
        LambdaQueryWrapper<Announcement> wrapper = Wrappers.lambdaQuery(Announcement.class);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(Announcement::getTitle, keyword)
                            .or()
                            .like(Announcement::getContent, keyword));
        }
        
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq(Announcement::getStatus, status);
        }
        
        wrapper.orderByDesc(Announcement::getPublishTime);
        List<Announcement> announcements = list(wrapper);
        return convertToVOList(announcements);
    }

    @Override
    public PageResultVO<AnnouncementVO> searchPage(int page, int size, String keyword, String status) {
        LambdaQueryWrapper<Announcement> wrapper = Wrappers.lambdaQuery(Announcement.class);
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            wrapper.and(w -> w.like(Announcement::getTitle, keyword)
                            .or()
                            .like(Announcement::getContent, keyword));
        }
        
        if (status != null && !status.trim().isEmpty()) {
            wrapper.eq(Announcement::getStatus, status);
        }
        
        wrapper.orderByDesc(Announcement::getPublishTime);
        
        int offset = (page - 1) * size;
        wrapper.last("LIMIT " + offset + ", " + size);
        
        List<Announcement> announcements = list(wrapper);
        List<AnnouncementVO> records = convertToVOList(announcements);
        
        long total = announcementMapper.countByCondition(keyword, status);
        
        return new PageResultVO<>(records, total, page, size);
    }

    @Override
    public void publish(Integer id) throws BizException {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BizException("公告不存在");
        }

        announcement.setStatus("published");
        announcement.setPublishTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());

        boolean updated = updateById(announcement);
        if (!updated) {
            throw new BizException("发布公告失败");
        }
    }

    @Override
    public void archive(Integer id) throws BizException {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BizException("公告不存在");
        }

        announcement.setStatus("archived");
        announcement.setUpdateTime(LocalDateTime.now());

        boolean updated = updateById(announcement);
        if (!updated) {
            throw new BizException("归档公告失败");
        }
    }

    @Override
    public void incrementViewCount(Integer id) throws BizException {
        Announcement announcement = announcementMapper.selectById(id);
        if (announcement == null) {
            throw new BizException("公告不存在");
        }

        announcement.setViewCount(announcement.getViewCount() + 1);
        updateById(announcement);
    }

    private AnnouncementVO convertToVO(Announcement announcement) {
        AnnouncementVO vo = new AnnouncementVO();
        BeanUtils.copyProperties(announcement, vo);
        vo.setId(announcement.getAnnouncementId());
        return vo;
    }

    private List<AnnouncementVO> convertToVOList(List<Announcement> announcements) {
        List<AnnouncementVO> voList = new ArrayList<>();
        for (Announcement announcement : announcements) {
            voList.add(convertToVO(announcement));
        }
        return voList;
    }
}