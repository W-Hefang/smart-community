package edu.xcc.smartcommunity.service;

import edu.xcc.smartcommunity.SmartCommunityApplication;
import edu.xcc.smartcommunity.entity.dto.CommunityDTO;
import edu.xcc.smartcommunity.entity.vo.CommunityVO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SmartCommunityApplication.class)
@RunWith(SpringRunner.class)
public class CommunityServiceTest {

    @Autowired
    private CommunityService communityService;

    @Test
    public void testAddCommunity() {
        CommunityDTO dto = new CommunityDTO();
        dto.setCommunityName("测试社区");
        dto.setAddress("测试地址");
        dto.setScale(500);
        dto.setLeader("测试负责人");
        dto.setPhone("13800138000");
        dto.setDescription("测试描述");

        boolean result = communityService.addCommunity(dto);
        Assert.assertTrue(result);
        System.out.println("新增社区成功");
    }

    @Test
    public void testAddCommunityBatch() {
        List<CommunityDTO> dtoList = new ArrayList<>();

        CommunityDTO dto1 = new CommunityDTO();
        dto1.setCommunityName("批量社区1");
        dto1.setAddress("批量地址1");
        dto1.setScale(100);
        dto1.setLeader("批量负责人1");
        dto1.setPhone("13800138001");

        CommunityDTO dto2 = new CommunityDTO();
        dto2.setCommunityName("批量社区2");
        dto2.setAddress("批量地址2");
        dto2.setScale(200);
        dto2.setLeader("批量负责人2");
        dto2.setPhone("13800138002");

        dtoList.add(dto1);
        dtoList.add(dto2);

        boolean result = communityService.addCommunityBatch(dtoList);
        Assert.assertTrue(result);
        System.out.println("批量新增成功");
    }

    @Test
    public void testDeleteById() {
        // 先新增再删除
        CommunityDTO dto = new CommunityDTO();
        dto.setCommunityName("待删除社区");
        dto.setAddress("待删除地址");
        communityService.addCommunity(dto);

        // 需要获取实际ID，这里假设ID为1，实际使用时需调整
        boolean result = communityService.deleteById(1);
        System.out.println("删除结果: " + result);
    }

    @Test
    public void testUpdateById() {
        CommunityDTO dto = new CommunityDTO();
        dto.setCommunityName("修改后的名称");
        dto.setAddress("修改后的地址");
        dto.setScale(999);
        dto.setLeader("修改后的负责人");
        dto.setPhone("13900139000");
        dto.setDescription("修改后的描述");

        boolean result = communityService.updateById(1, dto);
        Assert.assertTrue(result);
        System.out.println("修改成功");
    }

    @Test
    public void testGetById() {
        CommunityVO vo = communityService.getById(1);
        if (vo != null) {
            System.out.println("查询结果: " + vo);
        } else {
            System.out.println("社区不存在");
        }
    }

    @Test
    public void testGetAll() {
        List<CommunityVO> list = communityService.getAll();
        System.out.println("所有社区: " + list);
    }

    @Test
    public void testGetByNameLike() {
        List<CommunityVO> list = communityService.getByNameLike("阳光");
        System.out.println("模糊查询结果: " + list);
    }

    @Test
    public void testGetByMultiCondition() {
        List<CommunityVO> list = communityService.getByMultiCondition("花园");
        System.out.println("多条件查询结果: " + list);
    }

    @Test
    public void testPageQuery() {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<CommunityVO> page =
                communityService.pageQuery(1, 5);
        System.out.println("总记录数: " + page.getTotal());
        System.out.println("当前页数据: " + page.getRecords());
    }

    @Test
    public void testPageQueryByNameLike() {
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<CommunityVO> page =
                communityService.pageQueryByNameLike(1, 5, "阳光");
        System.out.println("总记录数: " + page.getTotal());
        System.out.println("查询结果: " + page.getRecords());
    }
}