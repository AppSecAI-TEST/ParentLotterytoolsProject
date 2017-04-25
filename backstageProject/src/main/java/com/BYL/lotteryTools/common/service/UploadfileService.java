package com.BYL.lotteryTools.common.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.common.entity.Uploadfile;

public interface UploadfileService {

	public Uploadfile getUploadfileByNewsUuid(String newsUuid);
	
	public void save(Uploadfile entity);
	
	public void update(Uploadfile entity);
	
	public void delete(Uploadfile entity,HttpSession httpSession);
	
	public Uploadfile getUploadfileById(int id);
	
	public List<Uploadfile> getUploadfilesByNewsUuid(String newsUuid);
	
	public Uploadfile uploadFiles(MultipartFile file,HttpServletRequest request) throws Exception;
	
	public Uploadfile uploadFilesbyFile(File file,HttpServletRequest request) throws Exception;
}
