package com.coding404.myweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductService;
import com.coding404.myweb.util.Criteria;
import com.coding404.myweb.util.PageVO;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	
	@GetMapping("/productList")
	public String list(ProductVO vo, Model model, Criteria cri ) {
		//로그인 기능이 없으므로, admin이라는 계정기반으로 조회
		String writer = "admin";
		
		//1st
//		ArrayList<ProductVO> list = productService.getList(writer);
//		model.addAttribute("list",list);
		
		//2st
		ArrayList<ProductVO> list = productService.getList(writer,cri);
		model.addAttribute("list",list);
		int total = productService.getTotal(writer,cri);
		PageVO pageVO = new PageVO(cri, total);
		model.addAttribute("pageVO",pageVO);
		System.out.println(pageVO.toString());
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
	public String registForm(ProductVO vo, RedirectAttributes ra,
							 @RequestParam("file") List<MultipartFile> list) {
		//1.빈객체검사
		list = list.stream().filter(t -> t.isEmpty() == false).collect(Collectors.toList());
		
		//2.확장자검사
		for(MultipartFile file : list) {
			if(file.getContentType().contains("image") == false ) {
				ra.addFlashAttribute("msg","이미지 파일만 올려주세요");
				return "redirect:/product/productList";
			};
		}
		//3. 파일을 처리하는 작업은 service위임 => 애시당초에 controller Request객체를 받고 service위임전략.
		
		//		System.out.println(vo);
		int result = productService.productRegist(vo,list);
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
