package chya.zhyy;

import java.io.Serializable;

public class RequestResult<T> implements Serializable {

	private static final long serialVersionUID = 9209924592079080938L;

	/**
     * 请求成功失败标识，默认为true
     */
    private boolean success = true;

    /**
     * 请求返回错误信息
     */
    private String error;

    /**
     * 请求返回数据，一条数据时
     */
    private T data;

    /**
     * 数据总数量
     */
    private int total;


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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
