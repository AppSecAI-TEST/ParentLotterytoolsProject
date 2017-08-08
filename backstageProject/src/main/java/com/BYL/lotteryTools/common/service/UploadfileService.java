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
	
	public Uploadfile uploadFiles(MultipartFile file,HttpServletRequest request,String newsUuid) throws Exception;
	
	public Uploadfile uploadFilesbyFile(File file,HttpServletRequest request) throws Exception;
	
	/**
	 * 删除附件数据
	* @Title: deleteUploadFile 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param uploadfiles
	* @param @param httpSession    设定文件 
	* @author banna
	* @date 2017年5月17日 上午11:32:51 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteUploadFile(List<Uploadfile> uploadfiles,HttpSession httpSession);
	
	/**
	 * 从url下载图片
	* @Title: downloadFileFromURL 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param url
	* @param @param newsUuid
	* @param @return    设定文件 
	* @author banna
	* @date 2017年8月7日 下午1:53:01 
	* @return Uploadfile    返回类型 
	* @throws
	 */
	public Uploadfile downloadFileFromURL(String url,String newsUuid,HttpServletRequest request);
}
