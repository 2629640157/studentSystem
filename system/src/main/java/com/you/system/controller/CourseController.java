package com.you.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.you.system.entity.Course;
import com.you.system.service.CourseService;
import com.you.system.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/to/course/page/{clbumId}")
    public String toCoursePage(@PathVariable("clbumId") Integer clbumId, Map map) {
        map.put("clbumId", clbumId);
        return "course";
    }

    @ResponseBody
    @RequestMapping("/getAll/by/clbumId")
    public ResultEntity getAllCourseByClbumId(Integer clbumId) {
        List<Course> courses = courseService.getAllCourseByClbumId(clbumId);
        return ResultEntity.successWithData(courses);
    }


    @ResponseBody
    @RequestMapping("/save/one")
    public ResultEntity saveCourseByClbumId(String courseName, Integer maxScore, Integer clbumId) {
        Course course = new Course();
        course.setCourseName(courseName);
        course.setMaxScore(maxScore);
        Integer courseId;
        /**/
        Course one = courseService.getOne(new QueryWrapper<Course>().eq("course_name", courseName).eq("max_score", maxScore));
        //判断课程是否存在
        if (one == null) {
            boolean save = courseService.save(course);
            courseId = course.getCourseId();
        } else {
            courseId = one.getCourseId();
        }
        //添加
        boolean b = courseService.addCourse(courseId, clbumId);
        if (b) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("该课程已存在，请重新添加");
    }


    @ResponseBody
    @RequestMapping("/get/by/courseId")
    public ResultEntity getCourseByCourseId(Integer courseId) {
        Course course = courseService.getById(courseId);
        if (course != null) return ResultEntity.successWithData(course);
        else return ResultEntity.failed("获取数据失败，请重试！");
    }


    @ResponseBody
    @RequestMapping("/update/by/courseId")
    public ResultEntity updateCourseByCourseId(Integer courseId, Integer maxScore) {
        Course course = new Course();
        course.setCourseId(courseId);
        course.setMaxScore(maxScore);
        boolean b = courseService.updateById(course);
        if (b) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("修改失败，请重试！");
    }


    @ResponseBody
    @RequestMapping("/delete/by/courseId")
    public ResultEntity deleteCourseByCourseId(Integer courseId, Integer clbumId) {
        boolean b = courseService.deleteCourseByCourseId(courseId, clbumId);
        if (b) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("删除失败，请重试！");
    }
}
