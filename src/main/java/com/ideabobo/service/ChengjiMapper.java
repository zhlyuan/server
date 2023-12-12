package com.ideabobo.service;

import com.ideabobo.model.Chengji;

public interface ChengjiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chengji record);

    int insertSelective(Chengji record);

    Chengji selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Chengji record);

    int updateByPrimaryKey(Chengji record);
}