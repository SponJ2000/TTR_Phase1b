package com.obfuscation.server;

import communication.ICommand;

/**
 * Created by jalton on 10/1/18.
 */

public class GenericCommand implements ICommand {

    String className;
    String methodName;
    String[] parameterType;
    Object[] parameterValue;

    @Override
    public void Execute() {

    }
}
