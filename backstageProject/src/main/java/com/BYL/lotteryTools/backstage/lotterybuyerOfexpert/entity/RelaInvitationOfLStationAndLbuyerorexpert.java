package com.BYL.lotteryTools.backstage.lotterybuyerOfexpert.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.BYL.lotteryTools.backstage.lotteryStation.entity.LotteryStation;
import com.BYL.lotteryTools.common.entity.BaseEntity;

/**
 * 彩票站邀请专家成为驻店专家关联表
* @Description: TODO(这里用一句话描述这个方法的作用) 
* @author banna
* @date 2017年2月28日 下午4:38:41
 */
@Entity
@Table(name="RELA_INVITATION_OF_LOTTERYSTATION_AND_LOTTERYBUYEROREXPERT")
public class RelaInvitationOfLStationAndLbuyerorexpert extends BaseEntity
{
	@Id
	@Column(name="ID", nullable=false, length=45)
	@GenericGenerator(name="idGenerator", strategy="uuid")//uuid由机器生成的主键
	@GeneratedValue(generator="idGenerator")	
	private String id;
	
	@ManyToOne
	@JoinColumn(name="LBOE_ID",referencedColumnName="id")
	private LotterybuyerOrExpert lotterybuyerOrExpert;
	
	@ManyToOne
	@JoinColumn(name="LS_ID",referencedColumnName="id")
	private LotteryStation lotteryStation;
	
	@Column(name="IS_INSTORE",length=10)
	private String isInstore;//是否驻店（0：不驻店1：驻店）
	
	@Column(name="INSTORE_MODE")
	private String instoreMode;//驻店方式(0：免费 1：年、月、日分成2：按销售量分成)
	
	@Column(name="INSTORE_STARTTIME")
	private Timestamp instoreStarttime;//驻店开始时间
	
	@Column(name="INSTORE_ENDTIME")
	private Timestamp instoreEndtime;//驻店结束时间
	
	@Column(name="INSTORE_START_ISSUENUM")
	private Integer instoreStartIssuenum;//驻店开始期号
	
	@Column(name="INSTORE_END_ISSUENUM")
	private Integer instoreEndIssuenum;//驻店结束期号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LotterybuyerOrExpert getLotterybuyerOrExpert() {
		return lotterybuyerOrExpert;
	}

	public void setLotterybuyerOrExpert(LotterybuyerOrExpert lotterybuyerOrExpert) {
		this.lotterybuyerOrExpert = lotterybuyerOrExpert;
	}

	public LotteryStation getLotteryStation() {
		return lotteryStation;
	}

	public void setLotteryStation(LotteryStation lotteryStation) {
		this.lotteryStation = lotteryStation;
	}

	public String getIsInstore() {
		return isInstore;
	}

	public void setIsInstore(String isInstore) {
		this.isInstore = isInstore;
	}

	public String getInstoreMode() {
		return instoreMode;
	}

	public void setInstoreMode(String instoreMode) {
		this.instoreMode = instoreMode;
	}

	public Timestamp getInstoreStarttime() {
		return instoreStarttime;
	}

	public void setInstoreStarttime(Timestamp instoreStarttime) {
		this.instoreStarttime = instoreStarttime;
	}

	public Timestamp getInstoreEndtime() {
		return instoreEndtime;
	}

	public void setInstoreEndtime(Timestamp instoreEndtime) {
		this.instoreEndtime = instoreEndtime;
	}

	public Integer getInstoreStartIssuenum() {
		return instoreStartIssuenum;
	}

	public void setInstoreStartIssuenum(Integer instoreStartIssuenum) {
		this.instoreStartIssuenum = instoreStartIssuenum;
	}

	public Integer getInstoreEndIssuenum() {
		return instoreEndIssuenum;
	}

	public void setInstoreEndIssuenum(Integer instoreEndIssuenum) {
		this.instoreEndIssuenum = instoreEndIssuenum;
	}
	
	
	
	
}
