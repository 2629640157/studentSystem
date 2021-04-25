package com.you.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.regexp.internal.RE;
import com.you.system.entity.*;
import com.you.system.entity.vo.ClbumVO;
import com.you.system.entity.vo.ExamVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.service.*;
import com.you.system.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Lists;
import sun.security.util.Length;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
@Controller
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExamService examService;

    @GetMapping("/to/exam/report")
    public String toReportPage(Map map) {
        List<Grade> grades = gradeService.list();
        map.put("grades", grades);
        return "report";
    }

    @ResponseBody
    @RequestMapping("/getAll/by/condition")
    public ResultEntity getAllByCondition(Integer pageNum, String userName, String clbumName, Integer gradeId) {
        if (userName != null && !userName.equals("")) {
            Page<User> userPage = userService.getUserVOsByPage(pageNum, userName, null, clbumName, gradeId);
            List<User> records = userPage.getRecords();
            List<UserVO> userVOS = userService.getUserVoByUser(records);
            Page<UserVO> userVOPage = new Page<>();
            BeanUtils.copyProperties(userPage, userVOPage);
            userVOPage.setRecords(userVOS);
            return ResultEntity.successWithData(userVOPage);
        } else {
            Page<Clbum> clbumPage = clbumService.getClbumVOsByPage(pageNum, clbumName, gradeId);
            List<Clbum> records = clbumPage.getRecords();
            List<ClbumVO> clbumVOS = clbumService.getClbumVOS(records);
            Page<ClbumVO> clbumVOPage = new Page<>();
            BeanUtils.copyProperties(clbumPage, clbumVOPage);
            clbumVOPage.setRecords(clbumVOS);
            return ResultEntity.successWithData(clbumVOPage);
        }
    }

    @GetMapping("/go/clbum/report/by/{clbumId}")
    public String getReportByClbumId(@PathVariable("clbumId") Integer clbumId, Map map) {
        Clbum clbum = clbumService.getById(clbumId);
        Integer gradeId = clbum.getGradeId();
        Grade grade = gradeService.getById(gradeId);
        List<String> times = examService.selectTimes(clbumId);
        List<Course> courses = courseService.getAllCourseByClbumId(clbumId);
        map.put("clbum", clbum);
        map.put("grade", grade);
        map.put("times", times);
        map.put("courses", courses);
        return "clbum-report";
    }

    /**
     * 获取页面数据
     *
     * @param clbumId
     * @param dateStr
     * @return
     */
    @ResponseBody
    @RequestMapping("/get/result/by/clbumId/and/time")
    public ResultEntity getReportByClbumIdAndTime(Integer clbumId, String dateStr, Integer flag) {
        if (flag==null||flag.equals("")){
            flag=2;
        }
        List<UserVO> userVOS = getUserVOS(clbumId, dateStr, flag);
        return ResultEntity.successWithData(userVOS);
    }


    private List<UserVO> getUserVOS(Integer clbumId, String dateStr, Integer flag) {
        if (dateStr.equals("")) {
            //当时间条件不存在时，查找最晚的时间
            Exam exam = examService.getOne(new QueryWrapper<Exam>().orderByDesc("time").last("limit 1"));
            dateStr = exam.getTime();
        }
        List<UserVO> userVOS = examService.getAllResultByClbumId(clbumId, dateStr);
        //升序
        if (flag == 0) {
            return userVOS.stream().sorted((u1,u2)->u1.getTotalScore()-u2.getTotalScore()).collect(Collectors.toList());
        } else if (flag == 1) {
            //降序
            return userVOS.stream().sorted((u1,u2)->u2.getTotalScore()-u1.getTotalScore()).collect(Collectors.toList());
        } else {
            //默认
            return userVOS;
        }
    }

    @GetMapping("/to/add/page/{clbumId}")
    public String toAddPage(@PathVariable("clbumId") Integer clbumId, Map map) {
        Clbum clbum = clbumService.getById(clbumId);
        Integer gradeId = clbum.getGradeId();
        Grade grade = gradeService.getById(gradeId);
        List<User> users = userService.list(new QueryWrapper<User>().eq("clbum_id", clbumId));
        List<Course> courses = courseService.getAllCourseByClbumId(clbumId);
        map.put("clbum", clbum);
        map.put("grade", grade);
        map.put("users", users);
        map.put("courses", courses);
        return "report-add";
    }


    @ResponseBody
    @RequestMapping("/add/all")
    public ResultEntity addAllExams(@RequestBody Map<String, List<String>> map) {
        boolean b = false;
        List<String> userIds = map.get("userIdList");
        List<String> Scores = map.get("ScoreList");
        List<String> courseIds = map.get("courseIdsList");
        List<String> dateList = map.get("dateList");
        String dateStr = dateList.get(0);
        b = examService.addExamsWithList(userIds, Scores, courseIds, dateStr);
        if (b) return ResultEntity.successWithoutData();
        else {
            if (dateStr.equals("")) return ResultEntity.failed("时间已存在，请重新设置！");
            else return ResultEntity.failed("添加失败，请刷新后重试！");
        }
    }

    @ResponseBody
    @RequestMapping("/update/all")
    public ResultEntity updateAllExams(@RequestBody Map<String, List<Integer>> map) {
        boolean b = false;
        List<Integer> scores = map.get("scores");
        List<Integer> examIds = map.get("examIds");
        int i = 0;
        for (Integer examId : examIds) {
            examService.updateScoreByExamId(examId, scores.get(i++));
        }
        return ResultEntity.successWithoutData();

    }

    @ResponseBody
    @RequestMapping("/update/score/by/ids")
    public ResultEntity updateScoreByIds(Integer examId, Integer score, Integer userId, Integer courseId, String dateStr) {
        boolean b = false;
        b = examService.updateOrAddByExamId(examId, courseId, userId, score, dateStr);
        if (b) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("修改失败，请刷新后重试！");
    }


    @ResponseBody
    @RequestMapping("/set/time")
    public ResultEntity setReportTime(String dateStr, Integer clbumId) {
        List<User> users = userService.list(new QueryWrapper<User>().eq("clbum_id", clbumId));
        User user = users.get(0);
        Integer userId = user.getUserId();
        boolean b = examService.getResultListByTimeAndUserId(userId, dateStr);
        if (b) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("时间已存在，请重新设置！");
    }


    @RequestMapping("/get/report/figure")
    public String getReportFigure(Integer clbumId, String dateStr, Map map) {
        List<UserVO> userVOS = getUserVOS(clbumId, dateStr,2);
        List<String> userNames = new ArrayList<>();
        List<String> courseNames = new ArrayList<>();
        List<Integer> courseIds = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        List<Integer> examIds=new ArrayList<>();
        userVOS.forEach(userVO -> {
            userVO.getExamVOS().forEach(ExamVO->examIds.add(ExamVO.getExamId()));
            userNames.add(userVO.getUserName());
            if (courseNames == null || courseNames.size() == 0) {
                userVO.getExamVOS().forEach(examVO -> {
                    courseNames.add(examVO.getCourse().getCourseName());
                    courseIds.add(examVO.getCourse().getCourseId());
                    scores.add(examVO.getScore());
                });
            } else {
                userVO.getExamVOS().forEach(examVO -> {
                    scores.add(examVO.getScore());
                });
            }
        });
        List<Integer> yx = new ArrayList<>();
        List<Integer> jg = new ArrayList<>();
        List<Integer> bjg = new ArrayList<>();
        List<Integer> df = new ArrayList<>();
        //获取及格，优秀，不及格率
        courseIds.forEach(courseId->{
                    yx.add(examService.count(new QueryWrapper<Exam>().eq("course_id",clbumId).in("exam_id",examIds).gt("score",courseService.getById(courseId).getMaxScore()*0.8-0.1)));
                    jg.add(examService.count(new QueryWrapper<Exam>().eq("course_id",clbumId).in("exam_id",examIds).gt("score",courseService.getById(courseId).getMaxScore()*0.6-0.1).lt("score",courseService.getById(courseId).getMaxScore()*0.8)));
            bjg.add(examService.count(new QueryWrapper<Exam>().eq("course_id",clbumId).in("exam_id",examIds).gt("score",courseService.getById(courseId).getMaxScore()*0.36-0.1).lt("score",courseService.getById(courseId).getMaxScore()*0.6)));
                    df.add(examService.count(new QueryWrapper<Exam>().eq("course_id",clbumId).in("exam_id",examIds).lt("score",courseService.getById(courseId).getMaxScore()*0.36)));
                }
        );

        map.put("userNames", userNames);
        map.put("courseNames", courseNames);
        map.put("scores", scores);
        map.put("yx", yx);
        map.put("jg", jg);
        map.put("bjg", bjg);
        map.put("df", df);
        return "report-figure";
    }


    @ResponseBody
    @RequestMapping("/get/result/by/userId/and/time")
    public ResultEntity getResultByUserId(Integer userId, String dateStr, Integer clbumId) {
        List<ExamVO> examVOS = examService.getAllResultByUserId(userId, dateStr, clbumId);
        return ResultEntity.successWithData(examVOS);
    }
}
