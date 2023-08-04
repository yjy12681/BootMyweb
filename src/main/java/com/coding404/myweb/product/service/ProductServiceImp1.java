package com.coding404.myweb.product.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImp1 implements ProductService{

	@Autowired
	private ProductMapper productMapper;
	
	@Value("${project.upload.path}")
	String uploadPath;
	
	//폴더생성함수
	private String makeFolder() {
		String path = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
//		String path = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE); // yyyyMMdd와 같음
		File file = new File(uploadPath + "/" + path);
		if(file.exists() == false) { //존재하면 true, false
			file.mkdirs();			
		}
		return path; //날짜폴더명 반환
	}
	
	
	//하나의 메서드에서 여러CRUD작업이 일어나는 경우에 한부분이 에러가 발생하면 그 에러를 처리하고, 롤백처리를 대신해줌
	@Override
	@Transactional(rollbackFor = Exception.class)
	public int productRegist(ProductVO vo, List<MultipartFile> list) {
		//product 테이블 처리
		int result = productMapper.productRegist(vo);
		
		//업로드 처리				
		for(MultipartFile file : list) {
			
			//파일 이름을 받습니다.
			String originName = file.getOriginalFilename();
			//브라우저 별로 파일의 경로가 다를 수 있기 때문에 \\ 기준으로 파일명만 잘라서 다시 저장
			String filename = originName.substring(originName.lastIndexOf("\\") + 1);
			System.out.println(originName);
			System.out.println(filename);
			
			//파일사이즈
//			long size = file.getSize();
			//바이트코드 - try catch
//			byte[] arr = file.getBytes();
			//동일한 파일을 재업로드시 기존파일 덮어버리기 때문에, 난수이름으로 파일명을 바꿔서 올림
			String uuid = UUID.randomUUID().toString();
			
			//날짜별로 새로운 폴더 생성
			String filepath = makeFolder();
			
			//세이브할 경로
			String savepath = uploadPath + "/" + filepath + "/" + uuid + "_" + filename;
					
//			System.out.println(size);
			System.out.println(uuid);
			System.out.println(uploadPath);
			System.out.println("세이브할 경로"+ savepath);
			System.out.println("------------------------------");
			
			try {
				File savefile = new File(savepath);
				file.transferTo(savefile);
			} catch (Exception e) {
				e.printStackTrace();
				return 0; //실패의 의미
			}
			//productUpload테이블에 파일의 경로 insert
			productMapper.productFileRegist(ProductUploadVO.builder()
														   .filename(filename)
														   .filepath(filepath)
														   .uuid(uuid)
														   .prod_writer(vo.getProd_writer())
														   .prod_id(vo.getProd_id())
														   .build());
		}//for문의 마지막
		return result;
	}


//	@Override
//	public ArrayList<ProductVO> getList(String writer) {
//		
//		return productMapper.getList(writer);
//	}


	@Override
	public ProductVO getDetail(int prod_id) {
		
		return productMapper.getDetail(prod_id);
	}


	@Override
	public int productUpdate(ProductVO vo) {
		
		return (int)productMapper.productUpdate(vo);
	}


	@Override
	public int productDelete(ProductVO vo) {
		
		return (int)productMapper.productDelete(vo);
	}


	@Override
	public ArrayList<ProductVO> getList(String writer, Criteria cri) {
		// TODO Auto-generated method stub
		return productMapper.getList(writer,cri);
	}


	@Override
	public int getTotal(String writer, Criteria cri) {
		
		return productMapper.getTotal(writer, cri);
	}


	@Override
	public ArrayList<CategoryVO> getCategory() {
		
		return productMapper.getCategory();
	}


	@Override
	public ArrayList<CategoryVO> getCategoryChild(CategoryVO vo) {
		
		return productMapper.getCategoryChild(vo);
	}


	@Override
	public ArrayList<ProductUploadVO> getAjaxImg(int prod_id) {
		return productMapper.getAjaxImg(prod_id);
	}


	

}
