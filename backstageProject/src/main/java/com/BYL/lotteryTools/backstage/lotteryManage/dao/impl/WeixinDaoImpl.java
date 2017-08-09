package com.BYL.lotteryTools.backstage.lotteryManage.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.BYL.lotteryTools.backstage.lotteryManage.dao.WeixinDao;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryDiPinPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.entity.LotteryPlay;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryDiPinPlayService;
import com.BYL.lotteryTools.backstage.lotteryManage.service.LotteryOtherNumCalService;


/**
 * JdbcTemplate的执行dao层类
 * @author Administrator
 *
 */
@Repository("weixinDao")
public class WeixinDaoImpl implements WeixinDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LotteryDiPinPlayService lotteryDiPinPlayService;
	
	@Autowired
	private LotteryOtherNumCalService lotteryOtherNumCalService;//彩种的其他值计算方法service层
	
	/**
	 * JdbcTemplate测试方法
	 * 
	 */
	public List findALL() {
		
		List list = new ArrayList();
       /*
        * //1.测试查询sql
        *  String sql = "select * from T_SDF_STATIONS";
        return jdbcTemplate.queryForList(sql); */   
        
        //2.测试插入数据sql
       /* jdbcTemplate.update("insert into STUDENT(id,name,number) values(?,?,?)",   
                new PreparedStatementSetter(){  
              
                    public void setValues(PreparedStatement ps) throws SQLException {  
                        ps.setInt(1, 2);  
                        ps.setString(2, "test");  
                        ps.setInt(3, 123);  
                    }  
        });   */
        
        return list;
    }
	
	/**
	 * 校验当前期号是否可用
	 * @param tableName
	 * @return
	 */
	public boolean checkIssueNumber(String tableName,String issueNum)
	{
		boolean flag = true;
		
		
		StringBuffer sql = new StringBuffer("select * from ");
		sql.append(" "+tableName + " u where u.ISSUE_NUMBER = '"+issueNum+"'");
		
		List list = jdbcTemplate.queryForList(sql.toString());
		
		if(list.size()>0)//若当前期号已经有对应的数据，则不能使用当前期号对当前彩种进行补录操作
		{
			flag =false;
		}
		
		return flag;
	}

	/**
	 * 添加补录信息
	 *  * @param lotteryPlay:补录信息id
	 * @param issueNum：期号
	 * @param numJ：开奖号码
	 */
	public boolean addLpBuluPlan(String lotteryPlay, String issueNum, String numJ) 
	{
		boolean updateFlag = false;
		
		
		
		//初始化基础内容
		LotteryDiPinPlay lp = lotteryDiPinPlayService.getLotteryDiPinPlayById(lotteryPlay);
		String tableName = lp.getCorrespondingTable();//获取关联的表名
		
		
		StringBuffer sql = new StringBuffer("insert into "+tableName);//(id,name,number) values
		List<String>  params = new ArrayList<String>();//用于组装参数，长度是根据sql中的字段决定的，且位置固定
		List<Object>  paramsValue = new ArrayList<Object>();//用于组装参数值，长度是根据sql中的字段决定的，且位置固定(因为参数的类型不同，所以使用object作为泛型)
		
		
		//1.处理补录的开奖号码
		String[] numArr = numJ.split(" ");//按照分隔符将开奖号码整理为数组
		
		//2.获取补录彩种信息的相关信息
		String numOrChar = "0";
		//数字方案
			//1.组装基础数据部分，包括：开奖号码，创建时间，期号
			String snum = lp.getStartNumber();
			int startNum = 0;
			if(null != snum && !"".equals(snum))
			{
				startNum = Integer.parseInt(snum);
			}
			//①开奖号码
//			int differ = startNum - 0;//计算开始号码和0的差，用于生成params，即参数
			int differ = 1;
			for (int i = 0 ; i < numArr.length ; i++) 
			{
				paramsValue.add(numArr[i]);//放置参数值
				params.add("NO"+(i+differ));//放置参数，即表字段名称
				
			}
			//②创建时间（在生成sql时最后添加）
			paramsValue.add(new Date());//放置参数值，mysql的datetime放置值使用new Date()，可以正确插入数据并执行
			params.add("CREATE_TIME");//放置参数，即表字段名称
			
			//③期号
			paramsValue.add(issueNum);//放置参数值
			params.add("ISSUE_NUMBER");//放置参数，即表字段名称
			
			
			//TODO:④组装其他计算参数
			Map<String,Object> objects = new HashMap<String, Object>();//获取计算数据的参数
			
			/*String[] otherNum = lp.getLotteryPlayBulufangan().getOtherNum().split(",");//获取其他需要计算的字段及计算调用的方法
			
			if(otherNum.length>0)
			{
				String methodName = otherNum[0];
				
				Class clazz = lotteryOtherNumCalService.getClass(); 
				Method m2;
				
				try 
				{
					m2 = clazz.getDeclaredMethod(methodName, String.class);//使用反射获取方法名，这种使用的前提是已知类并且已经实例化类对象
					objects.put("issueId", issueNum);//放置期号参数
					for(int i=1;i<=numArr.length;i++)
					{
						objects.put("no"+i, numArr[i-1]);
					}
					JSONObject json = JSONObject.fromObject(objects);//将参数转换为object
					Map<String,Object> result = (Map<String,Object>)m2.invoke(lotteryOtherNumCalService,
							json.toString());//使用反射执行方法
					//放置计算参数
					for(int i=1;i<otherNum.length;i++)
					{//数组中第一个数据是方法名
						paramsValue.add(result.get(otherNum[i]));//放置参数值
						params.add(otherNum[i]);//放置参数，即表字段名称
					}
					
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //参数顺序为：期号，no1~no n(n由当前开奖号个数控制)，动态
			}*/
			
	
		
		
		
		
		//组装sql
		//①装载参数,new StringBuffer("insert into "+tableName);//(id,name,number) values
		for (int i = 0 ;i < params.size() ; i++) 
		{
			if(i == 0)
			{
				sql.append(" ( "+params.get(i) + ",");//开始参数
			}
			else
			{
				if(i == params.size()-1)
				{
					sql.append(""+params.get(i) + ")");//结束参数
				}
				else
				{
					sql.append(""+params.get(i) + ",");//中间参数
				}
			}
		}
		//拼接参数中间的sql
		sql.append(" values");
		//②装载参数值的对应位置的sql，用“？”填充要进行插入的参数位置
		for (int i = 0 ;i < params.size() ; i++) 
		{
			if(i == 0)
			{
				sql.append("( ?,");//开始参数
			}
			else
			{
				if(i == params.size()-1)
				{
					sql.append("?)");//结束参数
				}
				else
				{
					sql.append("?,");//中间参数
				}
			}
		}
		
		
		//COMMON :组装参数值
		/*Demo1: jdbcTemplate.update("insert into tb_test1(name,password) values(?,?)",   
        new Object[]{user.getUsername(),user.getPassword()});  */
		Object[] object = new Object[paramsValue.size()];//参数值数组，数组的长度为参数值list的长度
		for(int i=0;i<paramsValue.size();i++)
		{
			object[i] = paramsValue.get(i);
		}
		
		
		int updateCount = jdbcTemplate.update(sql.toString(),object); //object为sql中要插入的参数值
		
		 //Demo:2.测试插入数据sql
	    /* int updateCount = jdbcTemplate.update("insert into STUDENT(id,name,number) values(?,?,?)",   
	                new PreparedStatementSetter(){  
	              
	                    public void setValues(PreparedStatement ps) throws SQLException {  
	                        ps.setInt(1, 2);  
	                        ps.setString(2, "test");  
	                        ps.setInt(3, 123);  
	                    }  
	        });   */
	     if(updateCount>0)
	     {
	    	 updateFlag = true;//是否更新数据的flag
	     }
		return updateFlag;
	}
	
	
}
