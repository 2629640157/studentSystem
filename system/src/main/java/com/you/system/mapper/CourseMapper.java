package com.you.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.you.system.entity.Course;
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
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> getAllCourseByClbumId(@Param("clbumId") Integer clbumId);

    Integer courseIsExist(@Param("courseId") Integer courseId, @Param("clbumId") Integer clbumId);

    void addCourse(@Param("courseId") Integer courseId, @Param("clbumId") Integer clbumId);

    int deleteCourseByCourseId(@Param("courseId") Integer courseId, @Param("clbumId") Integer clbumId);
}
