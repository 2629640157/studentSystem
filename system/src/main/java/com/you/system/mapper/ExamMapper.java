package com.you.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.entity.Exam;
import com.you.system.entity.vo.ExamVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
public interface ExamMapper extends BaseMapper<Exam> {

    ExamVO selectListByUserIdAndCourseId(@Param("userId") Integer userId, @Param("courseId") Integer courseId, @Param("dateStr") String dateStr);

    List<String> selectTimes(@Param("clbumId") Integer clbumId);

    Integer addExamByUserId(@Param("userId") Integer userId, @Param("examId") Integer examId);

    int selectIsExist(@Param("userId") Integer userId, @Param("examId") Integer examId);

    Integer getResultListByTimeAndUserId(@Param("userId") Integer userId, @Param("dateStr") String dateStr);

    Integer getResultByCourseIdAndUserId(@Param("userId")Integer userId,@Param("courseId") Integer courseId,@Param("dateStr") String dateStr);

    List<Exam> getResultByTimeAndUserId(@Param("userId")Integer userId, @Param("time")String time);
}
