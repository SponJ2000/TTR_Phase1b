package server;

/**
 * Created by hao on 10/3/18.
 */

public class Result {
    boolean success;
    Object data;
    String errorInfo;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Result(boolean success, String errorInfo, Object data) {
        this.success = success;
        this.data = data;
        this.errorInfo = errorInfo;
    }
}
