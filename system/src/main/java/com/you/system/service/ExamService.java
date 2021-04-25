package com.you.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.entity.Course;
import com.you.system.entity.Exam;
import com.you.system.entity.User;
import com.you.system.entity.vo.ExamVO;
import com.you.system.entity.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
public interface ExamService extends IService<Exam> {

    List<UserVO> getAllResultByClbumId(Integer clbumId, String dateStr);

    List<String> selectTimes(Integer clbumId);

    boolean addExamByUserIdAndScoreIdAndScore(Integer userId, Integer courseId, Integer score);

    boolean updateScoreByExamId(Integer examId, Integer score);

    boolean addExamsWithList(List<String> userIds, List<String> scores, List<String> courseIds, String dateStr);

    boolean getResultListByTimeAndUserId(Integer userId, String dateStr);

    boolean updateOrAddByExamId(Integer examId, Integer courseId, Integer userId, Integer score, String dateStr);

    Map<Integer,List<Integer>> getResultByUsersAndCoursesAnddateStr(List<User> users, List<Course> courses,String dateStr);

    List<ExamVO> getAllResultByUserId(Integer userId, String time,Integer clbumId);
}
