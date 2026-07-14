package edu.xcc.smartcommunity.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import edu.xcc.smartcommunity.entity.pojo.Menu;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserLoginVO implements Serializable {
    private Integer userId;

    private String userName;

    private String userPhone;

    private String idCard;

    private String userType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer userStatus;

    private String token;
    private List<Menu> menuList;
}
