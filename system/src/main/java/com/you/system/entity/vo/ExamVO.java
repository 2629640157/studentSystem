package com.you.system.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.you.system.entity.Course;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 游斌
 * @create 2020-12-25  22:09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ExamVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "exam_id", type = IdType.AUTO)
    private Integer examId;

    private Course Course;

    private Integer score;

    private String time;


}
