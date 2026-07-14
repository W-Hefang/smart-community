package edu.xcc.smartcommunity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import edu.xcc.smartcommunity.common.Response;
import edu.xcc.smartcommunity.entity.dto.CommunityPageLikeDTO;
import edu.xcc.smartcommunity.entity.pojo.Community;
import edu.xcc.smartcommunity.entity.vo.CommunityPageLikeVO;
import edu.xcc.smartcommunity.entity.vo.CommunityVO;
import edu.xcc.smartcommunity.entity.vo.ScaleStatisticsVO;
import edu.xcc.smartcommunity.handler.BizException;
import edu.xcc.smartcommunity.service.CommunityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/getById")
    public Response<CommunityVO> getById(@RequestParam Integer id) {
        CommunityVO byId = communityService.getById(id);
        return Response.success(byId);
    }

    @GetMapping("/getLikeName")
    public Response<List<CommunityVO>> getLikeName(@RequestParam String name) {
        List<CommunityVO> communityList = communityService.getByNameLike(name);
        return Response.success(communityList);
    }

    @PostMapping("/add")
    public Response<Community> add(@Valid @RequestBody Community community) throws BizException {
        // 检查社区名称是否已存在
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        Community one = communityService.getOne(wrapper.eq(Community::getCommunityName, community.getCommunityName()));
        if (one != null) {
            throw new BizException("社区名称已经存在，添加失败");
        }

        boolean save = communityService.save(community);
        if (save) {
            return Response.success(community);
        }
        return Response.failed();
    }

    @PostMapping("/deleteById")
    public Response<Void> deleteById(@RequestParam Integer id) throws BizException {
        boolean b = communityService.removeById(id);
        if (b) {
            return Response.success(null);
        }
        throw new BizException("id不存在，删除失败");
    }

    @PostMapping("/updateById")
    public Response<Community> updateById(@RequestBody Community community) throws BizException {
        boolean b = communityService.updateById(community);
        if (b) {
            return Response.success(community);
        }
        throw new BizException("更新失败，社区不存在");
    }

    @PostMapping("/pageLikeName")
    public Response<CommunityPageLikeVO<CommunityVO>> pageLikeName(@RequestBody CommunityPageLikeDTO dto) {
        // 调用 Service 的分页查询方法
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<CommunityVO> page =
                communityService.pageQueryByNameLike(dto.getPage(), dto.getSize(), dto.getCondition());

        // 封装VO
        CommunityPageLikeVO<CommunityVO> vo = new CommunityPageLikeVO<>();
        vo.setData(page.getRecords());
        vo.setTotal(page.getTotal());

        return Response.success(vo);
    }
    @GetMapping("/statistics")
    public Response<List<ScaleStatisticsVO>> getStatistics() {
        List<ScaleStatisticsVO> statistics = communityService.getScaleStatistics();
        return Response.success(statistics);
    }

    /**
     * 获取社区总数
     */
    @GetMapping("/getTotalCount")
    public Response<Map<String, Long>> getTotalCount() {
        long totalCount = communityService.getTotalCount();
        Map<String, Long> result = new HashMap<>();
        result.put("totalCount", totalCount);
        return Response.success(result);
    }

    /**
     * 按规模范围查询社区
     */
    @GetMapping("/getByScaleRange")
    public Response<List<CommunityVO>> getByScaleRange(@RequestParam(required = false) Integer minScale,
                                                       @RequestParam(required = false) Integer maxScale) {
        List<CommunityVO> communityList = communityService.getByScaleRange(minScale, maxScale);
        return Response.success(communityList);
    }
}