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
 * @since 2020-12-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_id", type = IdType.AUTO)
    private Integer examId;

    private Integer courseId;

    private Integer score;

    private String time;

    private Integer version;

    private Integer deleted;


}
