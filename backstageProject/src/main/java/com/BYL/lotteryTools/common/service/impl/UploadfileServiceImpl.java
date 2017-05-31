package com.BYL.lotteryTools.common.service.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.BYL.lotteryTools.common.entity.Uploadfile;
import com.BYL.lotteryTools.common.repository.UploadfileRepository;
import com.BYL.lotteryTools.common.service.UploadfileService;
import com.BYL.lotteryTools.common.util.Constants;
import com.BYL.lotteryTools.common.util.LoginUtils;

@Service("/uploadfileService")
@Transactional(propagation = Propagation.REQUIRED)
public class UploadfileServiceImpl implements UploadfileService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UploadfileServiceImpl.class);
	
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
	
	public void delete(Uploadfile entity,HttpSession httpSession) {
		//删除服务器上的附件文件
		String savePath = httpSession.getServletContext().getRealPath("upload");//获取项目根路径
//	    savePath = savePath +File.separator+ "upload"+File.separator;
		 //删除附件文件相关s
		 File dirFile = null;
		 boolean deleteFlag = false;//删除附件flag
		//2.删除附件
		dirFile = new File(savePath+entity.getUploadRealName());
       // 如果dir对应的文件不存在，或者不是一个目录，则退出
		deleteFlag = dirFile.delete();
		entity.setDeleteServiceFile(deleteFlag);//放置是否删除服务器附件成功的标记位
		entity.setIsDeleted(Constants.IS_DELETED);//放置删除标记
		entity.setModify(entity.getCreator());
		entity.setModifyTime(new Timestamp(System.currentTimeMillis()));
		uploadfileRepository.delete(entity);
	}
	
	public Uploadfile uploadFilesbyFile(File file,HttpServletRequest request) throws Exception
	{
		String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getName();
        
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
        
       FileUtils.copyFile(file,targetFile);
        
        //上传文件
//        file.transferTo(targetFile); //传入文件对象调用上传方法，将文件上传到目录中
		
		return uploadfile;
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
	public Uploadfile uploadFiles(MultipartFile file,HttpServletRequest request,String newsUuid) throws Exception
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
//        uploadfile.setNewsUuid(UUID.randomUUID().toString());
        uploadfile.setNewsUuid(newsUuid);
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

	/**
	 * 
	* @Title: deleteUploadFileByNewsUuid 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param uploadfiles
	* @param @param httpSession    设定文件 
	* @author banna
	* @date 2017年5月17日 上午11:32:21 
	* @throws
	 */
	public void deleteUploadFile(List<Uploadfile> uploadfiles,HttpSession httpSession) {

		//删除
		 if(null != uploadfiles)
		 {
			//①：删除附件的数据时要把当前附件数据对于的附件文件也删除
			 String savePath = httpSession.getServletContext().getRealPath("");//获取项目根路径
		    
		     //删除附件文件相关s
			 File dirFile = null;
			 boolean deleteFlag = false;//删除附件flag
			 
			 for (Uploadfile uploadfile : uploadfiles) 
			 {
				 if(!"0".equals(uploadfile.getNewsUuid()))
				 {
					 savePath = savePath +uploadfile.getUploadfilepath();
					 	//2.删除附件
				 		dirFile = new File(savePath+uploadfile.getUploadRealName());//uploadfile.getUploadRealName()
				 		LOG.info("待删除文件路径："+dirFile);
				        // 如果dir对应的文件不存在，或者不是一个目录，则退出
			        	deleteFlag = dirFile.delete();
			        	if(deleteFlag)
			        	{//删除附件(清空附件关联newsUuid)
			        		LOG.info("deleteImg==删除原附件文件数据--附件id="+uploadfile.getId()+"--操作人="+LoginUtils.getAuthenticatedUserId(httpSession));
			        	}
			        	else
			        	{
			        		LOG.error("deleteImg ERROR==没有找到要删除的附件文件或删除失败，附件路径为="+savePath+";File.exists="+dirFile.exists());
			        	}
					    //删除附件e
				   		 uploadfile.setModify(uploadfile.getNewsUuid());//放置附件关联uuid
				   		 uploadfile.setModifyTime(new Timestamp(System.currentTimeMillis()));
				   		 uploadfile.setIsDeleted(Constants.IS_DELETED);//删除标记
				   		 uploadfile.setDeleteServiceFile(deleteFlag);//存储是否成功删除文件标记
				   		 this.update(uploadfile);
				 }
				 	
			 }
			 
			
		 }
	}
	

}
