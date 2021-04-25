package com.you.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.entity.Approve;
import com.you.system.entity.Clbum;
import com.you.system.entity.Grade;
import com.you.system.entity.User;
import com.you.system.entity.vo.ClbumVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.service.*;
import com.you.system.util.PageUtil;
import com.you.system.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.xml.transform.Result;
import java.time.LocalDateTime;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private ExamService examService;
    @Autowired
    private ApproveService approveService;

    @PostMapping("/do/login")
    public String userLogin(String loginAccount, String password, HttpSession session) {
        User user = userService.login(loginAccount, password);
        if (user != null) {
            Integer auth = user.getAuth();
            session.setAttribute("user", user);
            session.removeAttribute("msg");
            if (auth == 0) {//学生
                return "redirect:/user/to/student/main";
            } else if (auth == 1) {//老师
                return "redirect:to/main";
            } else if (auth == 2) {//管理员
                return "";
            }
        }
        session.setAttribute("msg", "账号或密码错误，请重新输入！！！！！");
        session.setAttribute("loginAccount", loginAccount);
        return "redirect:to/login";
    }

    @RequestMapping("/do/login/out")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "login";
    }


    @GetMapping("/get/all/{pageNum}")
    public String getAllByPage(@PathVariable("pageNum") Integer pageNum, String userName, Integer sex, String clbumName, Integer gradeId, Map map) {
        if (pageNum == null) pageNum = 1;
        if (userName == null) userName = "";
        if (sex == null) sex = 2;
        if (clbumName == null) clbumName = "";
        if (gradeId == null) gradeId = 0;
        Page<User> userPage = userService.getUserVOsByPage(pageNum, userName, sex, clbumName, gradeId);
        List<User> users = userPage.getRecords();
        long totalPage = PageUtil.getTotalPage(userPage.getTotal(), userPage.getSize());
        List<Grade> grades = gradeService.list();
        List<UserVO> userVos = userService.getUserVoByUser(users);
        if (userVos.size() == 0) {
            userVos = null;
        }
        map.put("userVos", userVos);
        map.put("pageNum", pageNum);
        map.put("totalPage", totalPage);
        map.put("grades", grades);
        //把条件在传进去
        map.put("userName", userName);
        map.put("sex", sex);
        map.put("clbumName", clbumName);
        map.put("gradeId", gradeId);
        return "user";
    }


    @RequestMapping("/get/by/{userId}")
    public String getByUserId(@PathVariable("userId") Integer userId, Map map) {
        User user = userService.getById(userId);
        UserVO detailed = userService.getUserVO(user);
        BeanUtils.copyProperties(user, detailed);
        map.put("detailed", detailed);
        if (detailed.getAuth() == 0) {
            return "student-main";
        } else if (detailed.getAuth() == 1) {
            return "edit";
        } else {
            return "";
        }

    }

    //检查并注册账号（学生）
    @ResponseBody
    @RequestMapping("/do/user/checked")
    public ResultEntity<String> register(@RequestBody User user) {
        user.setAuth(0);
        User loginAccount = userService.getOne(new QueryWrapper<User>().eq("login_account", user.getLoginAccount()));
        if (loginAccount != null) return ResultEntity.failed("该账号已存在，请重新输入！");
        User phone = userService.getOne(new QueryWrapper<User>().eq("phone", user.getPhone()));
        if (phone != null) return ResultEntity.failed("该手机号已被使用，请重新输入！");
        user.setUserName(user.getLoginAccount());
        boolean save = userService.save(user);
        if (save) return ResultEntity.successWithoutData();
        return ResultEntity.failed("系统出现问题，请联系管理员！");
    }

    //学生端
    @RequestMapping("/to/student/main")
    public String toStudentPage(HttpSession session, Map map) {
        User user = (User) session.getAttribute("user");
        List<Grade> grades = gradeService.list();
        List<String> times = null;
        UserVO detailed = userService.getUserVO(user);
        BeanUtils.copyProperties(user, detailed);
        ClbumVO clbumVO = detailed.getClbumVO();
        List<Clbum> clbums = null;
        Integer gradeId = null;
        Integer clbumId = null;
        if (clbumVO != null) {
            clbumId = clbumVO.getClbumId();
            gradeId = clbumVO.getGrade().getGradeId();
            clbums = clbumService.list(new QueryWrapper<Clbum>().eq("grade_id", gradeId));
            times = examService.selectTimes(clbumId);
        }
        map.put("gradeId", gradeId);
        map.put("clbumId", clbumId);
        map.put("detailed", detailed);
        map.put("clbums", clbums);
        map.put("times", times);
        map.put("grades", grades);
        return "student-main";
    }

    @ResponseBody
    @RequestMapping("get/clbums")
    public ResultEntity getClbumsByGradeId(Integer gradeId) {
        List<Clbum> clbums = clbumService.list(new QueryWrapper<Clbum>().eq("grade_id", gradeId));
        return ResultEntity.successWithData(clbums);
    }

    @ResponseBody
    @RequestMapping("save/info")
    public ResultEntity saveUserInfo(Integer clbumId, String userName, Integer userId, HttpSession session) {
        User user = userService.getById(userId);
        user.setUserName(userName);
        if (clbumId==0){
            return ResultEntity.failed("请输入用的的信息！");
        }
        user.setClbumId(clbumId);
        boolean b = userService.updateById(user);
        if (b) {
            session.setAttribute("user", user);
            return ResultEntity.successWithoutData();
        } else {
            return ResultEntity.failed("保存信息失败，请重试！");
        }
    }

    @RequestMapping("/change/info")
    public String saveChange(User user, Map map, HttpSession session) {
        boolean b = userService.updateById(user);
        if (b) {
            session.setAttribute("user", user);
            return "redirect:/user/to/student/main";
        } else {
            map.put("msg", "信息修改失败，请重试！");
            return "student-main";
        }
    }

    @RequestMapping("/to/apply/{auth}")
    public String toApplyPage(@PathVariable("auth") Integer auth, Map map) {
        map.put("auth", auth);
        return "apply";
    }

    @RequestMapping("/to/apply1")
    public String toApply1Page(User user, Map map) {
        map.put("user", user);
        return "apply-1";
    }


    @RequestMapping("/finsh/approve")
    public String finishApprove(User user) {
        Approve approve = new Approve();
        BeanUtils.copyProperties(user,approve);
        approve.setSubmitTime(LocalDateTime.now());
        approve.setState(0);
        boolean save = approveService.save(approve);
        return "apply-2";

    }
}
