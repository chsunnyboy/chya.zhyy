package chya.zhyy;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {

	private static final long serialVersionUID = 1059993327396263278L;

	/**
     * 请求成功失败标识，默认为true
     */
    private boolean success = true;

    /**
     * 请求返回错误信息
     */
    private String error;

    /**
     * 数据总数量
     */
    private Long total;

    /**
     * 查询返回页面结果集合
     */
    private List<T> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.success = false;
        this.error = error;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
