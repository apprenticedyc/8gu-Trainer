package com.hex.mianshi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hex.mianshi.model.entity.Question;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
* @author DYC666
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2026-01-27 17:48:06
* @Entity com.hex.mianshi.model.entity.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查询包括已经删除的所有题目数据 用于增量ES完全同步
     */
    @Select("select * from question where updateTime >= #{minUpdateTime}")
    List<Question> listQuestionWithDelete(Date minUpdateTime);
}




