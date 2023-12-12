package com.ideabobo.service;

import com.ideabobo.model.Zuoyereplay;

public interface ZuoyereplayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Zuoyereplay record);

    int insertSelective(Zuoyereplay record);

    Zuoyereplay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Zuoyereplay record);

    int updateByPrimaryKey(Zuoyereplay record);
}