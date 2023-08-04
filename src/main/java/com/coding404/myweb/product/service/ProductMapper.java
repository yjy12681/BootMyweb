package com.coding404.myweb.product.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper
public interface ProductMapper {
	public int productRegist(ProductVO vo); //상품등록
	public void productFileRegist(ProductUploadVO vo); //파일등록
//	public ArrayList<ProductVO> getList(String writer); //조회
	public ArrayList<ProductVO> getList(@Param("writer") String writer, @Param("cri") Criteria cri);
	public int getTotal(@Param("writer") String writer, @Param("cri") Criteria cri);
	public ProductVO getDetail(int prod_id);
	public int productUpdate(ProductVO vo);
	public int productDelete(ProductVO vo);
	
	
	public ArrayList<CategoryVO> getCategory();
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo);
	
	//이미지데이터 조회
	public ArrayList<ProductUploadVO> getAjaxImg(int prod_id);
	
}
