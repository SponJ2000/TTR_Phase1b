package communication;

/**
 * Created by jalton on 10/1/18.
 */

public class Result {
    boolean success;
    Object data;
    String errorInfo;

    public Result(boolean success, Object data, String errorInfo) {
        this.success = success;
        this.data = data;
        this.errorInfo = errorInfo;
    }
}
