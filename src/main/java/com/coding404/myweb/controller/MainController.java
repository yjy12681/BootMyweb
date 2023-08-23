package com.coding404.myweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.coding404.myweb.command.TestVO;
import com.coding404.myweb.list.service.ListService;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	@Qualifier("listService")
	private ListService listService;

	@GetMapping("/main")
	public String main() {
		
		return "main";
	}
	
	@GetMapping("/test")
	public String test() {
		
		return "test";
	}
	
	
	@PostMapping("/testList")	
	public ResponseEntity<ArrayList<TestVO>> testList() {
		ArrayList<TestVO> vo = listService.testList();
		return ResponseEntity.ok(vo);
	}
	
}
