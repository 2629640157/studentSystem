package com.you.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.entity.Clbum;
import com.you.system.entity.User;
import com.you.system.entity.vo.ExamVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.mapper.ClbumMapper;
import com.you.system.mapper.ExamMapper;
import com.you.system.mapper.UserMapper;
import com.you.system.service.ClbumService;
import com.you.system.service.GradeService;
import com.you.system.service.UserService;
import com.you.system.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class SystemApplicationTests {
    @Autowired
    DataSource dataSource;
    @Autowired
    GradeService gradeService;
    @Autowired
    ClbumService clbumService;
    @Autowired
    ClbumMapper clbumMapper;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ExamMapper examMapper;


    @Test
    void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println("connection = " + connection);
    }

    @Test
    public void test1() {
       /*  List<User> list = userService.list();
         list.forEach(System.out::println);*/
        List<Integer> list = userMapper.selectClbumIdByName("汉");
        for (Integer integer : list) {
            System.out.println("integer = " + integer);
        }
    }


    @Test
    public void user() {
        Page<User> userPage = userMapper.selectListByUserVo("", 0, "", 0, 0, new Page(1, 3));
        List<User> records = userPage.getRecords();
        for (User record : records) {
            System.out.println("record = " + record);
        }
    }


    @Test
    public void update() {
        Clbum clbum = new Clbum();
        clbum.setStudentNum(20);
        clbum.setGradeId(3);
        clbum.setClbumName("物理");
        clbum.setClbumId(1);
        boolean b = clbumService.updateById(clbum);
        System.out.println("b = " + b);
    }

    @Test
    public void test10() {
        ExamVO examVO = examMapper.selectListByUserIdAndCourseId(2, 3, "2020-12-10");
        System.out.println("examVO = " + examVO);
    }
}
