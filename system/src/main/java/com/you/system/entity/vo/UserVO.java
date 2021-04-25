package com.you.system.entity.vo;

import com.you.system.entity.Exam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

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
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private String nation;

    private String card;

    /**
     * 0表示学生，1表示老师，2表示管理员
     */
    private Integer auth;

    private ClbumVO clbumVO;

    private List<ExamVO> examVOS;

    //总分（用来排序的）
    private Integer totalScore;


}
