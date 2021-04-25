package com.you.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.entity.Clbum;
import com.you.system.entity.Grade;
import com.you.system.entity.User;
import com.you.system.entity.vo.ClbumVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.mapper.ClbumMapper;
import com.you.system.mapper.GradeMapper;
import com.you.system.mapper.UserMapper;
import com.you.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClbumMapper clbumMapper;
    @Autowired
    private GradeMapper gradeMapper;


    /*不用判断是否唯一，注册时重复无法注册，所有唯一*/
    //登陆
    @Override
    public User login(String loginAccount, String password) {
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("login_account", loginAccount));
        if (user.getPassword().equals(password)) return user;
        return null;
    }


    public Page<User> getUserVOsByPage(int pageNum, String userName, Integer sex, String clbumName, Integer gradeId) {
        int pageSize = 3;
        Page<User> users = userMapper.selectListByUserVo(userName, sex, clbumName, gradeId,
                0, new Page(pageNum, pageSize));

        return users;
    }


    public List<UserVO> getUserVoByUser(List<User> users) {
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : users) {
            UserVO userVO = getUserVO(user);
            userVOS.add(userVO);
        }
        return userVOS;
    }

    public UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        Integer clbumId = user.getClbumId();
        if (clbumId != null) {
            Clbum clbum = clbumMapper.selectById(clbumId);
            Integer gradeId = clbum.getGradeId();
            Grade grade = gradeMapper.selectById(gradeId);
            ClbumVO clbumVO = new ClbumVO();
            BeanUtils.copyProperties(clbum, clbumVO);
            clbumVO.setGrade(grade);
            userVO.setClbumVO(clbumVO);
        }
        return userVO;
    }
}
