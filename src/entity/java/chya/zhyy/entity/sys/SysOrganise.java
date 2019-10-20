package chya.zhyy.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;
import chya.zhyy.entity.annotation.Option;

@Title("组织机构")
@Entity
@Table(name = "sys_organise")
public class SysOrganise extends BaseEntity{
    
	private static final long serialVersionUID = 8181491005328409813L;

    public SysOrganise() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
    private Integer id;	

	@Title("组织机构名称")
    @Column(name = "organise_name",length = 100)
    private String organiseName;

	@Title("组织机构代码")
    @Column(name = "organise_code",length = 100)
    private String organiseCode;
	
    @Title("组织机构类型")
    @Option(name = "HPS_O2O_TYPE", editable = false)
    @Column(name = "organise_type", precision = 1)
	private Integer organiseType;

    @Title("父组织机构ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_org_id", nullable = true)
    private SysOrganise parentOrg;
        
    @Title("序号")
    @Column(name = "order_no")
    private Integer orderNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrganiseName() {
		return organiseName;
	}

	public void setOrganiseName(String organiseName) {
		this.organiseName = organiseName;
	}

	public String getOrganiseCode() {
		return organiseCode;
	}

	public void setOrganiseCode(String organiseCode) {
		this.organiseCode = organiseCode;
	}

	public Integer getOrganiseType() {
		return organiseType;
	}

	public void setOrganiseType(Integer organiseType) {
		this.organiseType = organiseType;
	}
	
	public SysOrganise getParentOrg() {
		return parentOrg;
	}

	public void setParentOrg(SysOrganise parentOrg) {
		this.parentOrg = parentOrg;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
}
