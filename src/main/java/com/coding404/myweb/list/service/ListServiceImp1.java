package com.coding404.myweb.list.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coding404.myweb.command.TestVO;

@Service("listService")
public class ListServiceImp1 implements ListService{
	
	@Autowired
	private ListMapper listMapper;

	@Override
	public ArrayList<TestVO> testList() {
		// TODO Auto-generated method stub
		return listMapper.testList();
	}

}
