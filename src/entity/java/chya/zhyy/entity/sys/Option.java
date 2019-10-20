package chya.zhyy.entity.sys;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;

import java.util.List;

@Entity
@Table(name = "sys_option")
@Title("选项字典")
public class Option extends BaseEntity {

	private static final long serialVersionUID = -1L;

	public Option(){
		super();
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Title("ID")
    @Column(precision = 10)
	private Integer id;

	@Title("选项编码")
	@Column(name="option_code",nullable = false, length = 50, unique = true)
	private String optionCode;

	@Title("选项名称")
	@Column(name="option_name",nullable = false, length = 50)
	private String optionName;
	
	@Title("用户选项")
	@Column(name="user_flag",nullable = false)
	private Boolean userFlag;
	
	@OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OptionItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Boolean getUserFlag() {
		return userFlag;
	}

	public void setUserFlag(Boolean userFlag) {
		this.userFlag = userFlag;
	}

	public List<OptionItem> getItems() {
        return items;
    }

    public void setItems(List<OptionItem> items) {
        this.items = items;
    }
}
