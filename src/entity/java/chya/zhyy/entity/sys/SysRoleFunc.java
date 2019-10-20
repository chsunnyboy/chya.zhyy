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
import javax.persistence.UniqueConstraint;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;



@Title("角色功能表")
@Entity
@Table(name = "sys_role_func", indexes = {@Index(columnList = "role_id"),@Index(columnList = "func_id")},
	uniqueConstraints= {@UniqueConstraint(columnNames= {"role_id","func_id"})})
public class SysRoleFunc extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;
	
	@Title("角色")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id",nullable=false)
	private SysRole role;
	
	@Title("功能")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="func_id",nullable=false)
	private SysFunc func;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	public SysFunc getFunc() {
		return func;
	}

	public void setFunc(SysFunc func) {
		this.func = func;
	}
	
}
