package com.ideabobo.service;

import com.ideabobo.model.Qiandaoreplay;

public interface QiandaoreplayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Qiandaoreplay record);

    int insertSelective(Qiandaoreplay record);

    Qiandaoreplay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Qiandaoreplay record);

    int updateByPrimaryKey(Qiandaoreplay record);
}