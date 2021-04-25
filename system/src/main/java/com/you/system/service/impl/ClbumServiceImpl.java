package com.you.system.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.you.system.entity.Clbum;
import com.you.system.entity.Grade;
import com.you.system.entity.User;
import com.you.system.entity.vo.ClbumVO;
import com.you.system.mapper.ClbumMapper;
import com.you.system.mapper.GradeMapper;
import com.you.system.service.ClbumService;
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
 * @since 2020-12-13
 */
@Service
public class ClbumServiceImpl extends ServiceImpl<ClbumMapper, Clbum> implements ClbumService {
    @Autowired
    private ClbumMapper clbumMapper;
    @Autowired
    private GradeMapper gradeMapper;

    public Page<Clbum> getClbumVOsByPage(int pageNum, String clbumName, Integer gradeId) {
        int pageSize = 3;
        Page<Clbum> clbumPage = clbumMapper.selectListByClbumVO(clbumName, gradeId, new Page(pageNum, pageSize));
        return clbumPage;
    }

    public List<ClbumVO> getClbumVOS(List<Clbum> clbums) {
        List<ClbumVO> clbumVOS = new ArrayList<>();
        for (Clbum clbum : clbums) {
            ClbumVO clbumVO = new ClbumVO();
            Grade grade = gradeMapper.selectById(clbum.getGradeId());
            BeanUtils.copyProperties(clbum, clbumVO);
            clbumVO.setGrade(grade);
            clbumVOS.add(clbumVO);
        }
        return clbumVOS;
    }

}
