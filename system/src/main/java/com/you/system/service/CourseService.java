package com.you.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.entity.Course;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
public interface CourseService extends IService<Course> {

    List<Course> getAllCourseByClbumId(Integer clbumId);

    boolean addCourse(Integer courseId, Integer clbumId);

    boolean deleteCourseByCourseId(Integer courseId, Integer clbumId);
}
