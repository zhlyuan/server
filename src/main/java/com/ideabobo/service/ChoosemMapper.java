package com.ideabobo.service;

import com.ideabobo.model.Choosem;

public interface ChoosemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Choosem record);

    int insertSelective(Choosem record);

    Choosem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Choosem record);

    int updateByPrimaryKey(Choosem record);
}