package com.ideabobo.service;

import com.ideabobo.model.Kaoshim;

public interface KaoshimMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Kaoshim record);

    int insertSelective(Kaoshim record);

    Kaoshim selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Kaoshim record);

    int updateByPrimaryKey(Kaoshim record);
}