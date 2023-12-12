package com.ideabobo.service;

import com.ideabobo.model.Kaoshi;

public interface KaoshiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Kaoshi record);

    int insertSelective(Kaoshi record);

    Kaoshi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Kaoshi record);

    int updateByPrimaryKey(Kaoshi record);
}