package com.coding404.myweb.product.service;

import java.util.ArrayList;

import com.coding404.myweb.command.ProductVO;

public interface ProductService {
	public int productRegist(ProductVO vo);
	public ArrayList<ProductVO> getList(String writer);
	public ProductVO getDetail(int prod_id);
	public int productUpdate(ProductVO vo);
	public int productDelete(ProductVO vo);
}
