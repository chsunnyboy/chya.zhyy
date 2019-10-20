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


@Title("角色表")
@Entity
@Table(name = "sys_role", indexes = {@Index(columnList = "role_code")})
public class SysRole extends BaseEntity{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;
	
	@Title("角色名称")
    @Column(name = "role_name",length = 100, nullable = false)
	private String roleName;
	
	@Title("角色编码")
    @Column(name = "role_code",length = 100, nullable = false,unique=true)
	private String roleCode;
	
	@Title("序号")
    @Column(name = "order_no")
	private Integer orderNo;
	
	@Title("助记码")
    @Column(name = "role_opcode",length = 100, nullable = false)
	private String roleOpcode;
	
	@Title("状态")
    @Column(name = "status")
	private Integer status;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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
	public String getRoleOpcode() {
		return roleOpcode;
	}
	public void setRoleOpcode(String roleOpcode) {
		this.roleOpcode = roleOpcode;
	}
	
	
}
