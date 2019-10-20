package chya.zhyy.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import chya.zhyy.entity.sys.SysUser;

@MappedSuperclass
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -4473357768062305705L;

    @Basic(fetch = FetchType.LAZY)
    @CreatedDate
    private Date createTime;

    @Basic(fetch = FetchType.LAZY)
    @LastModifiedDate
    private Date lastModifyTime;
    
    @Basic(fetch = FetchType.LAZY)
    @LastModifiedBy
    private SysUser lastModifyUser;
    
    @Basic(fetch = FetchType.LAZY)
    @Version
    private int version;
    
    @Column(name = "memo",length = 100, nullable = true)
    private String memo;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public SysUser getLastModifyUser() {
		return lastModifyUser;
	}

	public void setLastModifyUser(SysUser lastModifyUser) {
		this.lastModifyUser = lastModifyUser;
	}
	
}
