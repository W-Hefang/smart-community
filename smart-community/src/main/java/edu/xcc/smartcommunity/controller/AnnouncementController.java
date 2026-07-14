package edu.xcc.smartcommunity.controller;

import edu.xcc.smartcommunity.common.Response;
import edu.xcc.smartcommunity.entity.dto.AnnouncementDTO;
import edu.xcc.smartcommunity.entity.vo.AnnouncementVO;
import edu.xcc.smartcommunity.handler.BizException;
import edu.xcc.smartcommunity.service.AnnouncementService;
import edu.xcc.smartcommunity.entity.vo.PageResultVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @PostMapping("/add")
    public Response<AnnouncementVO> add(@Valid @RequestBody AnnouncementDTO dto) throws BizException {
        AnnouncementVO vo = announcementService.add(dto);
        return Response.success(vo);
    }

    @PostMapping("/update")
    public Response<AnnouncementVO> update(@RequestBody AnnouncementDTO dto) throws BizException {
        AnnouncementVO vo = announcementService.update(dto);
        return Response.success(vo);
    }

    @PostMapping("/deleteById")
    public Response<Void> deleteById(@RequestParam Integer id) throws BizException {
        announcementService.deleteById(id);
        return Response.success(null);
    }

    @GetMapping("/getById")
    public Response<AnnouncementVO> getById(@RequestParam Integer id) throws BizException {
        AnnouncementVO vo = announcementService.getById(id);
        announcementService.incrementViewCount(id);
        return Response.success(vo);
    }

    @GetMapping("/listByStatus")
    public Response<List<AnnouncementVO>> listByStatus(@RequestParam String status) {
        List<AnnouncementVO> list = announcementService.listByStatus(status);
        return Response.success(list);
    }

    @GetMapping("/listAll")
    public Response<List<AnnouncementVO>> listAll() {
        List<AnnouncementVO> list = announcementService.listAll();
        return Response.success(list);
    }

    @GetMapping("/search")
    public Response<List<AnnouncementVO>> search(@RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        List<AnnouncementVO> list = announcementService.search(keyword, status);
        return Response.success(list);
    }

    @GetMapping("/page")
    public Response<PageResultVO<AnnouncementVO>> page(
            @RequestParam(defaultValue = "1") int page, 
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword, 
            @RequestParam(required = false) String status) {
        PageResultVO<AnnouncementVO> pageResult = announcementService.searchPage(page, size, keyword, status);
        return Response.success(pageResult);
    }

    @PostMapping("/publish")
    public Response<Void> publish(@RequestParam Integer id) throws BizException {
        announcementService.publish(id);
        return Response.success(null);
    }

    @PostMapping("/archive")
    public Response<Void> archive(@RequestParam Integer id) throws BizException {
        announcementService.archive(id);
        return Response.success(null);
    }
}