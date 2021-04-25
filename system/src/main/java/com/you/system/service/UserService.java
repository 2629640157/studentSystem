package com.you.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.entity.User;
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
public interface UserService extends IService<User> {
    //注册要重写方法
    //登陆
    User login(String loginAccount, String passWord);

    //分页获取user
//    Page<User> getUserVOsByPage(int pageNum,UserVO userVO);
    Page<User> getUserVOsByPage(int pageNum, String userName, Integer sex, String clbumName, Integer gradeId);

    //user转userVo
    List<UserVO> getUserVoByUser(List<User> users);

    UserVO getUserVO(User user);
}
