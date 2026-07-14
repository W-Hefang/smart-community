package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.xcc.smartcommunity.SmartCommunityApplication;
import edu.xcc.smartcommunity.entity.pojo.Community;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SmartCommunityApplication.class)
@RunWith(SpringRunner.class)
public class CommunityMapperTest {

    @Autowired
    private CommunityMapper communityMapper;

    // ==================== 新增 ====================

    /**
     * 新增单条社区记录
     */
    @Test
    public void testInsert() {
        Community community = new Community();
        community.setCommunityName("阳光花园");
        community.setAddress("北京市朝阳区阳光路88号");
        community.setScale(1200);
        community.setLeader("张三");
        community.setPhone("13800138001");
        community.setDescription("大型现代化居住社区，配套设施完善");
        community.setCreateTime(LocalDateTime.now());
        community.setUpdateTime(LocalDateTime.now());

        int insert = communityMapper.insert(community);
        System.out.println("影响的行数: " + insert);
        Assert.assertEquals(1, insert);
    }

    /**
     * 批量新增社区记录
     */
    @Test
    public void testInsertBatch() {
        List<Community> communityList = new ArrayList<>();

        Community community1 = new Community();
        community1.setCommunityName("宁静家园");
        community1.setAddress("上海市浦东新区宁静路12号");
        community1.setScale(800);
        community1.setLeader("李四");
        community1.setPhone("13900139002");
        community1.setDescription("环境优美，安静宜居");
        community1.setCreateTime(LocalDateTime.now());
        community1.setUpdateTime(LocalDateTime.now());

        Community community2 = new Community();
        community2.setCommunityName("繁华里");
        community2.setAddress("广州市天河区繁华大道99号");
        community2.setScale(2000);
        community2.setLeader("王五");
        community2.setPhone("13700137003");
        community2.setDescription("市中心高端社区，交通便利");
        community2.setCreateTime(LocalDateTime.now());
        community2.setUpdateTime(LocalDateTime.now());

        Community community3 = new Community();
        community3.setCommunityName("山水社区");
        community3.setAddress("杭州市西湖区山水路66号");
        community3.setScale(600);
        community3.setLeader("赵六");
        community3.setPhone("13600136004");
        community3.setDescription("依山傍水，风景秀丽");
        community3.setCreateTime(LocalDateTime.now());
        community3.setUpdateTime(LocalDateTime.now());

        communityList.add(community1);
        communityList.add(community2);
        communityList.add(community3);

        for (Community community : communityList) {
            communityMapper.insert(community);
        }
        System.out.println("批量插入完成");
    }

    // ==================== 删除 ====================

    /**
     * 根据id删除社区记录
     */
    @Test
    public void testDeleteById() {
        int i = communityMapper.deleteById(11);
        Assert.assertEquals(1, i);
        System.out.println("删除成功，影响行数: " + i);
    }

    /**
     * 批量删除社区记录
     */
    @Test
    public void testDeleteBatch() {
        int i = communityMapper.deleteByIds(Lists.list(27, 33));
        Assert.assertEquals(2, i);
        System.out.println("批量删除成功，影响行数: " + i);
    }

    /**
     * 根据条件删除
     */
    @Test
    public void testDeleteByCondition() {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.eq(Community::getCommunityName, "测试社区");
        int i = communityMapper.delete(wrapper);
        System.out.println("条件删除成功，影响行数: " + i);
    }

    // ==================== 修改 ====================

    /**
     * 根据id修改社区记录
     */
    @Test
    public void testUpdateById() {
        Community community = communityMapper.selectById(4);
        if (community != null) {
            community.setLeader("新负责人");
            community.setPhone("18888888888");
            community.setScale(1500);
            community.setUpdateTime(LocalDateTime.now());
            int i = communityMapper.updateById(community);
            Assert.assertEquals("修改社区信息失败，请检查社区id是否存在", 1, i);
            System.out.println("修改成功，影响行数: " + i);
        }
    }

    /**
     * 根据条件修改
     */
    @Test
    public void testUpdateByCondition() {
        Community community = new Community();
        community.setDescription("已更新描述信息");
        community.setUpdateTime(LocalDateTime.now());

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.eq(Community::getCommunityName, "阳光花园");

        int i = communityMapper.update(community, wrapper);
        System.out.println("条件修改成功，影响行数: " + i);
    }

    /**
     * 插入或更新（存在则更新，不存在则插入）
     */
    @Test
    public void testInsertOrUpdate() {
        Community community = communityMapper.selectById(4);
        if (community != null) {
            community.setScale(1800);
            community.setUpdateTime(LocalDateTime.now());
            communityMapper.updateById(community);
        } else {
            community = new Community();
            community.setCommunityName("新社区");
            community.setAddress("新地址");
            community.setCreateTime(LocalDateTime.now());
            community.setUpdateTime(LocalDateTime.now());
            communityMapper.insert(community);
        }
        System.out.println("插入或更新完成");
    }

    // ==================== 查询 ====================

    /**
     * 根据id查询社区信息
     */
    @Test
    public void testSelectById() {
        Community community = communityMapper.selectById(1);
        System.out.println("查询结果: " + community);
    }

    /**
     * 查询所有社区列表
     */
    @Test
    public void testSelectListAll() {
        List<Community> communityList = communityMapper.selectList(null);
        System.out.println("所有社区列表: " + communityList);
    }

    /**
     * 根据条件查询（社区名称模糊查询）
     * 对应SQL: SELECT * FROM community WHERE community_name LIKE '%阳光%'
     */
    @Test
    public void testSelectListByNameLike() {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, "阳光");
        List<Community> communityList = communityMapper.selectList(wrapper);
        System.out.println("名称模糊查询结果: " + communityList);
    }

    /**
     * 根据条件查询（地址模糊查询）
     * 对应SQL: SELECT * FROM community WHERE address LIKE '%北京%'
     */
    @Test
    public void testSelectListByAddressLike() {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getAddress, "北京");
        List<Community> communityList = communityMapper.selectList(wrapper);
        System.out.println("地址模糊查询结果: " + communityList);
    }

    /**
     * 多条件模糊查询（名称 OR 地址 OR 负责人）
     * 对应SQL: SELECT * FROM community WHERE community_name LIKE '%花园%' OR address LIKE '%花园%' OR leader LIKE '%花园%'
     */
    @Test
    public void testSelectListByMultiCondition() {
        String keyword = "花园";
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, keyword)
                .or()
                .like(Community::getAddress, keyword)
                .or()
                .like(Community::getLeader, keyword);
        List<Community> communityList = communityMapper.selectList(wrapper);
        System.out.println("多条件模糊查询结果: " + communityList);
    }

    /**
     * 根据负责人精确查询
     */
    @Test
    public void testSelectListByLeader() {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.eq(Community::getLeader, "张三");
        List<Community> communityList = communityMapper.selectList(wrapper);
        System.out.println("负责人查询结果: " + communityList);
    }

    /**
     * 根据规模范围查询（大于某个值）
     */
    @Test
    public void testSelectListByScaleGreater() {
        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.ge(Community::getScale, 1000);  // 规模 >= 1000
        List<Community> communityList = communityMapper.selectList(wrapper);
        System.out.println("规模>=1000的社区: " + communityList);
    }

    // ==================== 分页查询 ====================

    /**
     * 基础分页查询
     * 参数: (当前页码, 每页条数)
     */
    @Test
    public void testPage() {
        Page<Community> page = new Page<>(1, 3);
        Page<Community> resultPage = communityMapper.selectPage(page, null);

        long total = resultPage.getTotal();
        List<Community> records = resultPage.getRecords();

        System.out.println("总记录数: " + total);
        System.out.println("当前页数据: " + records);

        Assert.assertNotNull(resultPage);
    }

    /**
     * 分页 + 名称模糊查询
     */
    @Test
    public void testPageByNameLike() {
        Page<Community> page = new Page<>(1, 5);

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, "花园");

        Page<Community> resultPage = communityMapper.selectPage(page, wrapper);

        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("当前页码: " + resultPage.getCurrent());
        System.out.println("每页条数: " + resultPage.getSize());
        System.out.println("查询结果: " + resultPage.getRecords());
    }

    /**
     * 分页 + 地址模糊查询 + 按社区id降序排序
     */
    @Test
    public void testPageByAddressLikeWithOrder() {
        Page<Community> page = new Page<>(1, 5);

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getAddress, "路");
        wrapper.orderByDesc(Community::getCommunityId);  // 逆序排序

        Page<Community> resultPage = communityMapper.selectPage(page, wrapper);

        System.out.println("分页+地址模糊查询+逆序结果:");
        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("数据: " + resultPage.getRecords());
    }

    /**
     * 分页 + 多条件模糊查询 + 按规模降序
     */
    @Test
    public void testPageByMultiCondition() {
        Page<Community> page = new Page<>(1, 4);

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.like(Community::getCommunityName, "园")
                .or()
                .like(Community::getAddress, "区");
        wrapper.orderByDesc(Community::getScale);  // 按规模降序

        Page<Community> resultPage = communityMapper.selectPage(page, wrapper);

        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("结果: " + resultPage.getRecords());
    }

    /**
     * 第二页分页查询
     */
    @Test
    public void testPageSecond() {
        Page<Community> page = new Page<>(2, 3);
        Page<Community> resultPage = communityMapper.selectPage(page, null);

        System.out.println("第2页数据:");
        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("数据: " + resultPage.getRecords());
    }

    /**
     * 分页 + 按id降序（类似于User模块的testPageLikePhone）
     */
    @Test
    public void testPageWithOrderDesc() {
        Page<Community> page = new Page<>(1, 5);

        LambdaQueryWrapper<Community> wrapper = Wrappers.lambdaQuery(Community.class);
        wrapper.orderByDesc(Community::getCommunityId);

        Page<Community> resultPage = communityMapper.selectPage(page, wrapper);

        System.out.println("分页+逆序结果:");
        System.out.println("总记录数: " + resultPage.getTotal());
        System.out.println("数据: " + resultPage.getRecords());
    }
}