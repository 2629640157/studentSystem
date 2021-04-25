package com.you.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String loginAccount;

    private String password;

    private String userName;

    /**
     * 0表示男，1表示女
     */
    private Integer sex;

    private Integer age;

    private String phone;

    private String email;

    private Integer clbumId;

    private String nation;

    private String card;

    /**
     * 0表示学生，1表示老师，2表示管理员
     */
    private Integer auth;

    private String version;

    /**
     * 0表示存在，1表示删除
     */
    private Integer deleted;

}
