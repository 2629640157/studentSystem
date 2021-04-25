package com.you.system.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.you.system.entity.Grade;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author youbin
 * @since 2020-12-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ClbumVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer clbumId;

    private String clbumName;

    private Integer studentNum;

    private Grade grade;


}
