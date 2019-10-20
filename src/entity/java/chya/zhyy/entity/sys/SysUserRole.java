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


@Title("用户角色表")
@Entity
@Table(name = "sys_user_role", indexes = {@Index(columnList = "user_id"),@Index(columnList = "role_id")},
	uniqueConstraints= {@UniqueConstraint(columnNames= {"user_id","role_id"})})
public class SysUserRole extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;
	
	@Title("用户")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id",nullable=false)
	private SysUser user;
	
	@Title("角色")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id",nullable=false)
	private SysRole role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}
}
