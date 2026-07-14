package edu.xcc.smartcommunity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.xcc.smartcommunity.entity.pojo.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {
    
    List<Menu> selectMenusByRoleId(@Param("roleId") Integer roleId);
    
    List<Menu> selectAllMenus();
}