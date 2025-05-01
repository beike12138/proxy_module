package com.han;

public interface MyHandler {

    String functionBody(String methodName);

    default void setProxy(MyInterface myInterface) {
    }
}
