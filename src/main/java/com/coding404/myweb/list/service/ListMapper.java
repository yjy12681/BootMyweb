package com.coding404.myweb.list.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.coding404.myweb.command.TestVO;

@Mapper
public interface ListMapper {
	public ArrayList<TestVO> testList();
}
