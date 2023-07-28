package com.coding404.myweb.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	
	@GetMapping("/productList")
	public String list(ProductVO vo, Model model) {
		//로그인 기능이 없으므로, admin이라는 계정기반으로 조회
		String writer = "admin";
		
		
		ArrayList<ProductVO> list = productService.getList(writer);
		model.addAttribute("list",list);
		
		return "product/productList";
	}
	
	@GetMapping("/productDetail")
	public String detail(@RequestParam("prod_id") int prod_id, Model model) {
		//조회~~
		ProductVO vo = productService.getDetail(prod_id);
		model.addAttribute("vo",vo);
		return "product/productDetail";
	}
	
	@GetMapping("/productReg")
	public String reg() {
		
		return "product/productReg";
	}
	//포스트방식
	//등록요청
	@PostMapping("/registForm")
	public String registForm(ProductVO vo, RedirectAttributes ra) {
//		System.out.println(vo);
		int result = productService.productRegist(vo);
		String msg = result == 1 ? "등록 완료" : "등록 실패";
		ra.addFlashAttribute("msg", msg);
		return "redirect:/product/productList";
		
	}
	
	@PostMapping("/modifyForm")
	public String modifyForm(ProductVO vo, RedirectAttributes ra) {
//		System.out.println(vo);
		//메서드명-productUpdate
		//데이터베이스에 업데이트 작업진행
		//업데이트된 결과를 반환받아서 list화면
		//업데이트성공 메시지
		int result = productService.productUpdate(vo);
		String msg = result == 1 ? "수정 완료" : "수정 실패";
		ra.addFlashAttribute("msg",msg);
		return "redirect:/product/productList";
	}
	
	@PostMapping("/deleteForm")
	public String deleteForm(ProductVO vo, RedirectAttributes ra) {
		int result = productService.productDelete(vo);
		String msg = result == 1 ? "삭제 완료" : "삭제 실패";
		ra.addFlashAttribute("msg",msg);
		
		return "redirect:/product/productList";
	}
	
	
	
	
	
	

}
