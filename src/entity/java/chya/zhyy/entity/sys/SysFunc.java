package chya.zhyy.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;



@Title("功能列表")
@Entity
@Table(name = "sys_func", indexes = {@Index(columnList = "func_code")})
public class SysFunc extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;
	
	@Title("功能名称")
    @Column(name = "func_name",length = 100, nullable = false)
	private String funcName;
	
	@Title("功能编码")
    @Column(name = "func_code",length = 100, nullable = false,unique=true)
	private String funcCode;
	
	@Title("功能组")
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false,referencedColumnName="id")
	SysFuncGroup funcgroup;
	
	@Title("序号")
    @Column(name = "order_no")
	private Integer orderNo;
	
	@Title("状态")
    @Column(name = "status")
	private Integer status;
	
	@Title("FontAwesome")
    @Column(name = "iconCls")
	private String iconCls;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public SysFuncGroup getFuncgroup() {
		return funcgroup;
	}

	public void setFuncgroup(SysFuncGroup funcgroup) {
		this.funcgroup = funcgroup;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
