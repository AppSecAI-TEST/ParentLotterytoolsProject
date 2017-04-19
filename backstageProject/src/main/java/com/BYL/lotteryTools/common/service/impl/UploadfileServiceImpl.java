package com.BYL.lotteryTools.common.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.repository.UploadfileRepository;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.Constants;

@Service("uploadfileService")
@Transactional(propagation = Propagation.REQUIRED)
public class UploadfileServiceImpl implements UploadfileService {
	
	@Autowired
	private UploadfileRepository uploadfileRepository;

	public Uploadfile getUploadfileByNewsUuid(String newsUuid) {
		return uploadfileRepository.getUploadfileByNewsUuid(newsUuid);
	}
	
	
	public List<Uploadfile> getUploadfilesByNewsUuid(String newsUuid)
	{
		return uploadfileRepository.getUploadfilesByNewsUuid(newsUuid);
	}
	
	public Uploadfile getUploadfileById(int id) {
		return uploadfileRepository.getUploadfileById(id);
	}

	public void save(Uploadfile entity) {

		uploadfileRepository.save(entity);
	}

	public void update(Uploadfile entity) {

		uploadfileRepository.save(entity);
	}
	
	public void delete(Uploadfile entity) {

		uploadfileRepository.delete(entity);
	}
	
	/**
	 * 上传file文件到服务器
	* @Title: uploadFiles 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param file
	* @param @return    设定文件 
	* @author banna
	* @date 2017年4月19日 上午8:57:36 
	* @return String    返回类型 ,返回关联newsuuid
	* @throws
	 */
	public Uploadfile uploadFiles(MultipartFile file,HttpServletRequest request) throws Exception
	{
//		boolean flag = false;
		String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();
        Uploadfile uploadfile = new Uploadfile();
        String extName="";//扩展名
        //扩展名格式： 
        if (fileName.lastIndexOf(".") >= 0) {
            extName = fileName.substring(fileName.lastIndexOf("."));
        }
        uploadfile.setCreateTime(new Timestamp(System.currentTimeMillis()));
        uploadfile.setCreator("app");
        uploadfile.setIsDeleted(Constants.IS_NOT_DELETED);
        uploadfile.setModify("app");
        uploadfile.setModifyTime(new Timestamp(System.currentTimeMillis()));
        uploadfile.setNewsUuid(UUID.randomUUID().toString());
        uploadfile.setUploadContentType(extName);
        uploadfile.setUploadFileName(fileName);
        uploadfile.setUploadfilepath("/upload/");
        uploadfile.setUploadRealName(UUID.randomUUID().toString()+extName);//真是存储姓名
        this.save(uploadfile);
        
        File targetFile = new File(path, uploadfile.getUploadRealName());//设置上传目录以及存储的名称
        if(!targetFile.exists()){  //若上传目录不存在，则创建目录
            targetFile.mkdirs();  
        }  
        //上传文件
        file.transferTo(targetFile); //传入文件对象调用上传方法，将文件上传到目录中
		
		return uploadfile;
	}
	

}
