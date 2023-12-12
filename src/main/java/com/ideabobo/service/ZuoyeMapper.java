package com.ideabobo.service;

import com.ideabobo.model.Zuoye;

public interface ZuoyeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Zuoye record);

    int insertSelective(Zuoye record);

    Zuoye selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Zuoye record);

    int updateByPrimaryKey(Zuoye record);
}