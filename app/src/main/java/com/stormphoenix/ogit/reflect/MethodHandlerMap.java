package com.stormphoenix.ogit.reflect;

//import java.lang.invoke.MethodHandle;
//import java.lang.invoke.MethodHandles;
//import java.lang.invoke.MethodType;

import com.stormphoenix.ogit.mvp.model.interactor.user.UserInteractor;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by StormPhoenix on 17-4-17.
 * StormPhoenix is a intelligent Android developer.
 */

public class MethodHandlerMap {
    private static final MethodHandlerMap INSTANCE;
    private Map<String, Method> methodMap;

    static {
        INSTANCE = new MethodHandlerMap();
    }

    private MethodHandlerMap() {
        methodMap = new HashMap<>();
    }

}
