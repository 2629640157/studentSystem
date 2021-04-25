package com.you.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author youbin
 * @since 2021-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Approve implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "approve_id", type = IdType.AUTO)
    private Integer approveId;

    private Integer userId;

    private String userName;

    private String phone;

    private String card;

    private String email;

    private String version;

    private Integer conductor;

    private Integer auth;

    private LocalDateTime submitTime;

    private LocalDateTime dealTime;

    private String msg;

    private Integer state;


}
