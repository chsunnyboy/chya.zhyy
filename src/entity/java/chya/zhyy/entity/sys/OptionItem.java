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

import org.hibernate.validator.constraints.UniqueElements;

import chya.zhyy.entity.BaseEntity;
import chya.zhyy.entity.annotation.Title;

@Entity
@Title("选项明细")
@Table(name = "sys_option_item", indexes={@Index(columnList="option_id,item_id")},
	uniqueConstraints= {@UniqueConstraint(columnNames= {"option_id","item_id"})}
)
public class OptionItem extends BaseEntity {

	private static final long serialVersionUID = -1L;

	public OptionItem(){
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Title("ID")
	@Column(precision=10)
	private Integer id;

	@Title("选项ID")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="option_id",nullable = false)
	private Option option;

	@Title("选项显示值")
	@Column(name="item_name",length = 200,nullable = false)
	private String itemName;

	@Title("选项值")
	@Column(name="item_id",length = 50,nullable = false)
	private String itemId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
