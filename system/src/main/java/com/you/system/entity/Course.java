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
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "course_id", type = IdType.AUTO)
    private Integer courseId;

    private String courseName;

    private Integer maxScore;

    private Integer version;

    private Integer deleted;


}
