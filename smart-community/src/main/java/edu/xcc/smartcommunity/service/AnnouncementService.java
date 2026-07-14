package edu.xcc.smartcommunity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.xcc.smartcommunity.entity.dto.AnnouncementDTO;
import edu.xcc.smartcommunity.entity.pojo.Announcement;
import edu.xcc.smartcommunity.entity.vo.AnnouncementVO;
import edu.xcc.smartcommunity.handler.BizException;

import java.util.List;

public interface AnnouncementService extends IService<Announcement> {
    AnnouncementVO add(AnnouncementDTO dto) throws BizException;

    AnnouncementVO update(AnnouncementDTO dto) throws BizException;

    void deleteById(Integer id) throws BizException;

    AnnouncementVO getById(Integer id) throws BizException;

    List<AnnouncementVO> listByStatus(String status);

    List<AnnouncementVO> listAll();

    List<AnnouncementVO> search(String keyword, String status);

    edu.xcc.smartcommunity.entity.vo.PageResultVO<AnnouncementVO> searchPage(int page, int size, String keyword, String status);

    void publish(Integer id) throws BizException;

    void archive(Integer id) throws BizException;

    void incrementViewCount(Integer id) throws BizException;
}