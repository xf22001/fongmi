package com.fongmi.quickjs.utils;

import com.whl.quickjs.wrapper.JSCallFunction;
import com.whl.quickjs.wrapper.JSFunction;
import com.whl.quickjs.wrapper.JSObject;

import java.util.concurrent.Future;

public class Async {

    private final SettableFuture<Object> future;
    private final JSCallFunction success;
    private final JSCallFunction error;

    private Async() {
        this.future = new SettableFuture<>();
        
        this.success = args -> {
            this.future.set(args != null && args.length > 0 ? args[0] : null);
            return null;
        };

        this.error = args -> {
            String msg = args != null && args.length > 0 && args[0] != null ? args[0].toString() : "";
            this.future.setException(new Exception(msg));
            return null;
        };
    }

    public static Future<Object> run(JSObject object, String name, Object... args) {
        return new Async().call(object, name, args);
    }

    private Future<Object> call(JSObject object, String name, Object... args) {
        JSFunction func = object.getJSFunction(name);
        if (func == null) return empty();
        call(func, args);
        return future;
    }

    private Future<Object> empty() {
        future.set(null);
        return future;
    }

    private void call(JSFunction func, Object... args) {
        try {
            Object result = func.call(args);
            if (result instanceof JSObject) then((JSObject) result);
            else future.set(result);
        } catch (Throwable e) {
            future.setException(e);
        } finally {
            func.release();
        }
    }

    private void then(JSObject promise) {
        JSFunction then = promise.getJSFunction("then");
        if (then == null) {
            future.set(promise);
        } else {
            consume(then, success);
            consume(promise.getJSFunction("catch"), error);
        }
    }

    private void consume(JSFunction func, JSCallFunction callback) {
        if (func == null) return;
        try {
            func.call(callback);
        } finally {
            func.release();
        }
    }
}
