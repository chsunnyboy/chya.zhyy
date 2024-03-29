package chya.zhyy.entity.sys;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.OptionKey;
import chya.zhyy.entity.annotation.Title;



@Title("人员表")
@Entity
@Table(name = "sys_user", indexes = {@Index(columnList = "user_code")}
)
public class SysUser extends BaseEntity{

	private static final long serialVersionUID = 6458427519991428507L;

    public SysUser() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
    private Integer id;

    @Title("组织机构")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = true)
    private SysOrganise organise;
    
    @Title("用户编码")
    @Column(name = "user_code",length = 100, nullable = false,unique=true)
    private String userCode;

    @Title("用户名称")
    @Column(name = "user_name",length = 100, nullable = false)
    private String userName;

    @Title("手机号")
    @Column(name = "phone_no", length = 100)
    private String phoneNo;

    @Title("系统密码")
    @Column(name = "web_pwd", length = 100,nullable = false)
    private String webPwd;

    @Title("管理员")
    @Column(name = "admin")
    private Boolean admin;
    
    @Title("状态")
    @OptionKey(name="SYS_USER_STATUS",editable = false)
    @Column(name = "status")
    private Integer status;
    
    @Title("有效期至")
    @Temporal(TemporalType.DATE)
    @Column(name = "valid_date")
    private Date validDate;
    
    @Title("序号")
    @Column(name = "order_no")
    private Integer orderNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getWebPwd() {
        return webPwd;
    }

    public void setWebPwd(String webPwd) {
        this.webPwd = webPwd;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

	public SysOrganise getOrganise() {
		return organise;
	}

	public void setOrganise(SysOrganise organise) {
		this.organise = organise;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
    
}
