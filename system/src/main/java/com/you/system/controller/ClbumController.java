package com.you.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.rngom.parse.host.Base;
import com.you.system.entity.Clbum;
import com.you.system.entity.Grade;
import com.you.system.entity.vo.ClbumVO;
import com.you.system.entity.vo.UserVO;
import com.you.system.service.ClbumService;
import com.you.system.service.GradeService;
import com.you.system.util.PageUtil;
import com.you.system.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author youbin
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/clbum")
public class ClbumController {
    @Autowired
    private ClbumService clbumService;
    @Autowired
    private GradeService gradeService;

    /*
        @GetMapping("/get/all/{pageNum}")
        public String getAllClbum(@PathVariable("pageNum") Integer pageNum,Integer clbumId, Integer gradeId, Map map) {
            if (pageNum == null) pageNum = 1;
            if (clbumId == null) clbumId = 0;
            if (gradeId == null) gradeId = 0;
            List<Grade> grades = gradeService.list();
            List<Clbum> clbums = clbumService.list();
            Page<Clbum> clbumPage = clbumService.getClbumVOsByPage(pageNum, clbumId, gradeId);
            long totalPage = PageUtil.getTotalPage(clbumPage.getTotal(), clbumPage.getSize());
            List<ClbumVO> clbumVOS = getClbumVOS(clbums);
            map.put("clbumVOS", clbumVOS);
            map.put("pageNum", pageNum);
            map.put("totalPage", totalPage);
            map.put("grades", grades);
            map.put("clbums", clbums);
            return "clbum";
        }*/
    @ResponseBody
    @RequestMapping("/get/all")
    public ResultEntity getAllClbum(Integer pageNum, String clbumName, Integer gradeId) {
        Page<Clbum> clbumPage = clbumService.getClbumVOsByPage(pageNum, clbumName, gradeId);
        List<Clbum> records = clbumPage.getRecords();
//        List<ClbumVO> clbumVOS = getClbumVOS(records);
        List<ClbumVO> clbumVOS = clbumService.getClbumVOS(records);
        Page<ClbumVO> clbumVOPage = new Page<>();
        BeanUtils.copyProperties(clbumPage, clbumVOPage);
        clbumVOPage.setRecords(clbumVOS);
        return ResultEntity.successWithData(clbumVOPage);
    }

    @GetMapping("/to/clbum/main")
    public String getCondition(Map map) {
        List<Grade> grades = gradeService.list();
        map.put("grades", grades);
        return "clbum";

    }

    @ResponseBody
    @RequestMapping("/save/one")
    public ResultEntity saveClbum(Integer studentNum, Integer gradeId, String clbumName) {
        Clbum clbum = new Clbum();
        clbum.setStudentNum(studentNum);
        clbum.setGradeId(gradeId);
        clbum.setClbumName(clbumName);
        //判断是否存在
        Clbum one = clbumService.getOne(new QueryWrapper<Clbum>().eq("clbum_name", clbumName).eq("grade_id", gradeId));
        if (one != null) return ResultEntity.failed("该班级已经存在，请重新输入！");
        boolean save = clbumService.save(clbum);
        if (save) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("添加失败，请重试！");
    }

    @ResponseBody
    @RequestMapping("/delete/by/clbumId")
    public ResultEntity deleteClbumById(Integer clbumId) {
        boolean remove = clbumService.removeById(clbumId);
        if (remove) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("删除失败，请重试！");
    }

    // 修改前获取数据，回显
    @ResponseBody
    @RequestMapping("/get/by/clbumId")
    public ResultEntity getClbumById(Integer clbumId) {
        Clbum clbum = clbumService.getById(clbumId);
        if (clbum != null) return ResultEntity.successWithData(clbum);
        else return ResultEntity.failed("获取数据失败，请重试！");
    }

    ///clbum/update/by/clbumId
    @ResponseBody
    @RequestMapping("/update/by/clbumId")
    public ResultEntity updateClbumById(Integer studentNum, Integer gradeId, String clbumName, Integer clbumId) {
        Clbum clbum = new Clbum();
        clbum.setStudentNum(studentNum);
        clbum.setGradeId(gradeId);
        clbum.setClbumName(clbumName);
        clbum.setClbumId(clbumId);
        //是否存在
//        clbumService.getOne()
        boolean update = clbumService.updateById(clbum);
        if (update) return ResultEntity.successWithoutData();
        else return ResultEntity.failed("修改失败，请重试！");
    }

 /*   private List<ClbumVO> getClbumVOS(List<Clbum> clbums) {
        List<ClbumVO> clbumVOS = new ArrayList<>();
        for (Clbum clbum : clbums) {
            ClbumVO clbumVO = new ClbumVO();
            Grade grade = gradeService.getById(clbum.getGradeId());
            BeanUtils.copyProperties(clbum, clbumVO);
            clbumVO.setGrade(grade);
            clbumVOS.add(clbumVO);
        }
        return clbumVOS;
    }*/


  /*  @ResponseBody
    @RequestMapping("/get/by/condition")
    public ResultEntity getClbumByCondition(Integer pageNum, String clbumName, Integer gradeId,String userName) {
        Page<Clbum> clbumPage = clbumService.getClbumByCondition(pageNum, clbumName, gradeId,userName);

        List<Clbum> records = clbumPage.getRecords();
        List<ClbumVO> clbumVOS = getClbumVOS(records);
        Page<ClbumVO> clbumVOPage = new Page<>();
        BeanUtils.copyProperties(clbumPage, clbumVOPage);
        clbumVOPage.setRecords(clbumVOS);
        return ResultEntity.successWithData(clbumVOPage);
    }*/
}
