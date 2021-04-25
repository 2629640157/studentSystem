package com.you.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.entity.Course;
import com.you.system.mapper.CourseMapper;
import com.you.system.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> getAllCourseByClbumId(Integer clbumId) {
        List<Course> courses = courseMapper.getAllCourseByClbumId(clbumId);
        return courses;
    }

    @Override
    public boolean addCourse(Integer courseId, Integer clbumId) {
        //判断改班级是否有改课程
        Integer count = courseMapper.courseIsExist(courseId, clbumId);
        if (count >= 1) return false;
        else {
            courseMapper.addCourse(courseId, clbumId);
            return true;
        }
    }

    @Override
    public boolean deleteCourseByCourseId(Integer courseId, Integer clbumId) {
        int i = courseMapper.deleteCourseByCourseId(courseId, clbumId);
        if (i >= 1) return true;
        return false;
    }
}
