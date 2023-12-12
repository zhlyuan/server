package com.ideabobo.service;

import com.ideabobo.model.Yzmessage;

public interface YzmessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Yzmessage record);

    int insertSelective(Yzmessage record);

    Yzmessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Yzmessage record);

    int updateByPrimaryKey(Yzmessage record);
}