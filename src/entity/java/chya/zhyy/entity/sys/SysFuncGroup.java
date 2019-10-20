package chya.zhyy.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;



@Title("功能组")
@Entity
@Table(name = "sys_func_group", indexes = {@Index(columnList = "group_code")})
public class SysFuncGroup extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;
	
	@Title("功能组名称")
    @Column(name = "group_name",length = 100, nullable = false)
	private String groupName;
	
	@Title("功能组编码")
    @Column(name = "group_code",length = 100, nullable = false,unique=true)
	private String groupCode;
	
	@Title("序号")
    @Column(name = "order_no")
	private Integer orderNo;
	
	@Title("助记码")
    @Column(name = "group_opcode",length = 100, nullable = false)
	private String groupOpcode;
	
	@Title("状态")
    @Column(name = "status")
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getGroupOpcode() {
		return groupOpcode;
	}

	public void setGroupOpcode(String groupOpcode) {
		this.groupOpcode = groupOpcode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
