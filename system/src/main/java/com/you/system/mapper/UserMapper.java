package com.you.system.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.entity.User;
import com.you.system.entity.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author youbin
 * @since 2020-12-08
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> selectListByUserVo(@Param("userName") String userName, @Param("sex") Integer sex, @Param("clbumName") String clbumName, @Param("gradeId") Integer gradeId, @Param("auth") Integer auth, Page page);

    List<Integer> selectClbumIdByName(@Param("clbumName") String clbumName);

    List<User> selectListByScores(@Param("clbumId") Integer clbumId,@Param("dateStr") String dateStr);
}
