package com.you.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.you.system.entity.Clbum;
import com.you.system.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author youbin
 * @since 2020-12-13
 */
public interface ClbumMapper extends BaseMapper<Clbum> {

    Page<Clbum> selectListByClbumVO(@Param("clbumName") String clbumName, @Param("gradeId") Integer gradeId, Page page);
}
