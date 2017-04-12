package com.BYL.lotteryTools.backstage.prediction.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 基础预测类型
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月27日 下午3:47:32
 */
@Entity
@Table(name="T_LT_BASE_PREDICTION_TYPE")
public class BasePredictionType extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@ManyToOne
	@JoinColumn(name="ORGINDATA_RULE_ID",referencedColumnName="id")
	private OriginDataRule originDataRule;
	
	@Column(name="ORIGIN_DATA_SIZE", length=45)
	private String originDataSize;//源码数据量要求
	
	@Column(name="N_PLAN", length=45)
	private String nPlan;//当前预测是几期计划（n期计划就获取源码后几期流码，n>=1,当前n=1时则是当期预测）
	
	@Column(name="FLOW_DATA_SIZE", length=45)
	private String flowDataSize;//流码数据量要求是根据源码需求和n期计划计算出的，根据源码和N期计划计算出的最大结果

	
	@Column(name="BASE_PREDICTION_NAME", length=45)
	private String basePredictionName;//基础预测类型名称
	
	@Column(name="METHOD_NAME", length=45)
	private String methodName;//预测方法池中对应的预测方法名称
	
	@Column(name="YUCE_FENLEI", length=45)
	private String yuceFenlei;//预测分类
	
	//一个基础预测类型下可以有n个扩展的预测类型数据
	@OneToMany(mappedBy="basePredictionType" ,fetch=FetchType.LAZY)
	private List<PredictionType> predictionTypes;
	
	

	public String getYuceFenlei() {
		return yuceFenlei;
	}

	public void setYuceFenlei(String yuceFenlei) {
		this.yuceFenlei = yuceFenlei;
	}

	public List<PredictionType> getPredictionTypes() {
		return predictionTypes;
	}

	public void setPredictionTypes(List<PredictionType> predictionTypes) {
		this.predictionTypes = predictionTypes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public OriginDataRule getOriginDataRule() {
		return originDataRule;
	}

	public void setOriginDataRule(OriginDataRule originDataRule) {
		this.originDataRule = originDataRule;
	}

	public String getOriginDataSize() {
		return originDataSize;
	}

	public void setOriginDataSize(String originDataSize) {
		this.originDataSize = originDataSize;
	}

	public String getnPlan() {
		return nPlan;
	}

	public void setnPlan(String nPlan) {
		this.nPlan = nPlan;
	}

	public String getFlowDataSize() {
		return flowDataSize;
	}

	public void setFlowDataSize(String flowDataSize) {
		this.flowDataSize = flowDataSize;
	}

	public String getBasePredictionName() {
		return basePredictionName;
	}

	public void setBasePredictionName(String basePredictionName) {
		this.basePredictionName = basePredictionName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	
}
