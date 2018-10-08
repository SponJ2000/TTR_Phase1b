package com.obfuscation.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import communication.ICommand;
import communication.Result;

/**
 * Created by jalton on 10/1/18.
 */

public class GenericCommand implements ICommand {

    private String className;
    private String methodName;
    private String[] parameterType;
    private Object[] parameterValue;

    public GenericCommand(String className, String methodName, String[] parameterType, Object[] parameterValue) {
        this.className = className;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.parameterValue = parameterValue;
    }

    @Override
    public Result execute() {
        try {
            Class<?> retClass = Class.forName(className);

            //gets singleton
            Method getInstance = retClass.getMethod("getInstance");
            Object serverFacadeInstance = getInstance.invoke(null, null);

            Class<?>[] paramTypeClass = new Class<?>[parameterType.length];

            for (int i = 0; i < parameterType.length; i++) {
                try {
                    paramTypeClass[i] = Class.forName(parameterType[i]);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return new Result(false, null, "Error: "+e.getMessage());
                }

            }

            Method method = retClass.getMethod(methodName, paramTypeClass);
            Object results = method.invoke(serverFacadeInstance, parameterValue);

            return (Result)results;
        }
        catch (InvocationTargetException e) {
            return new Result(false, null, e.getCause().getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false, null, e.getMessage());
        }
    }
}
