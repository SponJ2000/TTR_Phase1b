package server;

import communication.*;

/**
 * Created by hao on 10/5/18.
 */

public class GenericCommand implements ICommand {
    private String className;
    private String methodName;
    private String[] parameterType;
    private Object[] parameterValue;

    public Result execute() {
        return null;
    }
}

