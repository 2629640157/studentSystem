package com.you.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.you.system.entity.Course;
import com.you.system.entity.Exam;
import com.you.system.entity.User;
import com.you.system.entity.vo.ExamVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.mapper.ClbumMapper;
import com.you.system.mapper.CourseMapper;
import com.you.system.mapper.ExamMapper;
import com.you.system.mapper.UserMapper;
import com.you.system.service.ClbumService;
import com.you.system.service.ExamService;
import com.you.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {
    @Autowired
    private ClbumMapper clbumMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<UserVO> getAllResultByClbumId(Integer clbumId, String dateStr) {
        //获取所有课程
        List<Course> courses = courseMapper.getAllCourseByClbumId(clbumId);
//        List<User> users =  userMapper.selectListByScores(clbumId,dateStr);
        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("clbum_id", clbumId));
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            Integer userId = user.getUserId();
            UserVO userVO = new UserVO();
            userVO.setTotalScore(0);
            List<ExamVO> examVOS = new ArrayList<>();
            //每个用户按上面课程集合获取的顺序获取对应的成绩封装成集合，设置到对应用户中
            //这样保证了每个用户遍历时输出成绩顺序相同
            for (Course course : courses) {
                Integer courseId = course.getCourseId();
                ExamVO examVO = examMapper.selectListByUserIdAndCourseId(userId, courseId, dateStr);
                if (examVO != null) {
                    examVO.setCourse(course);
                    examVOS.add(examVO);
                    userVO.setTotalScore(userVO.getTotalScore()+examVO.getScore());
                   userVO.setTotalScore(userVO.getTotalScore()+examVO.getScore());
                } else {
                    ExamVO examVO1 = new ExamVO();
                    examVO1.setCourse(course);
                    examVO1.setScore(0);
                    examVOS.add(examVO1);
                }
            }
            BeanUtils.copyProperties(user, userVO);
            userVO.setExamVOS(examVOS);
            userVOS.add(userVO);
        }
        return userVOS;
    }

    @Override
    public List<String> selectTimes(Integer clbumId) {
        List<String> times = examMapper.selectTimes(clbumId);
        return times;
    }

    @Override
    public boolean addExamByUserIdAndScoreIdAndScore(Integer userId, Integer courseId, Integer score) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter date = DateTimeFormatter.ISO_DATE;
        String dataStr = now.format(date);
        Exam exam = new Exam();
        exam.setScore(score);
        exam.setTime(dataStr);
        exam.setCourseId(courseId);
        int insert = examMapper.insert(exam);
        if (insert != 1) return false;
        Integer examId = exam.getExamId();
        int count = examMapper.addExamByUserId(userId, examId);
        if (count == 1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateScoreByExamId(Integer examId, Integer score) {
        Exam exam = new Exam();
        exam.setExamId(examId);
        exam.setScore(score);
        int i = examMapper.updateById(exam);
        if (i == 1) return true;
        return false;
    }

    @Override
    public boolean addExamsWithList(List<String> userIds, List<String> scores, List<String> courseIds, String dateStr) {
        if (dateStr == null || dateStr.equals("")) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter date = DateTimeFormatter.ISO_DATE;
            dateStr = now.format(date);
            Integer list = examMapper.getResultListByTimeAndUserId(Integer.parseInt(userIds.get(0)), dateStr);
            if (list > 0) return false;
        }
        for (int i = 0, j = 0; i < scores.size(); ) {
            if (j >= userIds.size()) break;
            Integer userId = Integer.parseInt(userIds.get(j++));
            for (int k = 0; k < courseIds.size(); k++, i++) {
                Integer courseId = Integer.parseInt(courseIds.get(k));
                String scoreStr = scores.get(i);
                Integer score = 0;
                if (scoreStr != null && !scoreStr.equals("")) {
                    score = Integer.parseInt(scores.get(i));
                }
                Exam exam = new Exam();
                exam.setScore(score);
                exam.setTime(dateStr);
                exam.setCourseId(courseId);
                int insert = examMapper.insert(exam);
                if (insert != 1) return false;
                Integer examId = exam.getExamId();
                examMapper.addExamByUserId(userId, examId);
            }
        }
        return true;
    }

    @Override
    public boolean getResultListByTimeAndUserId(Integer userId, String dateStr) {
        Integer count = examMapper.getResultListByTimeAndUserId(userId, dateStr);
        if (count > 0) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateOrAddByExamId(Integer examId, Integer courseId, Integer userId, Integer score, String dateStr) {
        boolean b = false;
        if (examId != null && examId != 0) {
            b = updateScoreByExamId(examId, score);
        } else {
            if (dateStr.equals("")) {
                Exam exam = examMapper.selectOne(new QueryWrapper<Exam>().orderByDesc("time").last("limit 1"));
                dateStr = exam.getTime();
            }
            Exam exam = new Exam();
            exam.setScore(score);
            exam.setTime(dateStr);
            exam.setCourseId(courseId);
            examMapper.insert(exam);
            Integer newId = exam.getExamId();
            Integer integer = examMapper.addExamByUserId(userId, newId);
            if (integer > 0) {
                b = true;
            }
        }
        return b;
    }

    @Override
    public Map<Integer, List<Integer>> getResultByUsersAndCoursesAnddateStr(List<User> users, List<Course> courses,String dateStr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (User user : users) {
            Integer userId = user.getUserId();
            List<Integer> list = new ArrayList<>();
            for (Course course : courses) {
                Integer courseId = course.getCourseId();
               Integer score= examMapper.getResultByCourseIdAndUserId(userId,courseId,dateStr);
               list.add(score);
            }
            map.put(userId,list);
        }
        return map;
    }

    @Override
    public List<ExamVO> getAllResultByUserId(Integer userId, String time,Integer clbumId) {
        List<ExamVO> examVOS=new ArrayList<>();
        List<Course> courses = courseMapper.getAllCourseByClbumId(clbumId);
        courses.forEach(course -> {
            Integer courseId = course.getCourseId();
            ExamVO examVO = examMapper.selectListByUserIdAndCourseId(userId, courseId, time);
            if (examVO==null){
                examVO=   new ExamVO();
            }
            examVO.setCourse(course);
            examVOS.add(examVO);
        });
        return examVOS;
    }
}
