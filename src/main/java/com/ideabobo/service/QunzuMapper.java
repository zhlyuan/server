package com.ideabobo.service;

import com.ideabobo.model.Qunzu;

public interface QunzuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qunzu record);

    int insertSelective(Qunzu record);

    Qunzu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qunzu record);

    int updateByPrimaryKey(Qunzu record);
}