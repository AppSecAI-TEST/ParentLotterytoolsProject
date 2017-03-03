package com.BYL.lotteryTools.backstage.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.BYL.lotteryTools.backstage.user.entity.UserRelaRole;
import com.BYL.lotteryTools.common.repository.GenericRepository;

/** 
  * @ClassName: AuthRepository 
  * @Description: 权限 
  * @author songj@sdfcp.com
  * @date 2015年9月25日 上午8:24:21 
  *  
  */
public interface UserRelaRoleRepository extends GenericRepository<UserRelaRole, String>  {

	/** 
	  * @Description: 通过userId删除对应关系
	  * @author songj@sdfcp.com
	  * @date 2015年10月23日 上午8:26:38 
	  * @param userId 
	  */
	@Query("select u from UserRelaRole u where u.userId=?1")
	public List<UserRelaRole> findUserRelaRoleByUserId(String userId);
}
