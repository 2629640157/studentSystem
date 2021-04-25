package com.you.system.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.you.system.entity.Clbum;
import com.you.system.entity.vo.ClbumVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author youbin
 * @since 2020-12-13
 */
public interface ClbumService extends IService<Clbum> {

    Page<Clbum> getClbumVOsByPage(int pageNum, String clbumName, Integer gradeId);

    List<ClbumVO> getClbumVOS(List<Clbum> clbums);

}
