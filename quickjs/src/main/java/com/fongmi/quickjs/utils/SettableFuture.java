package com.fongmi.quickjs.utils;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.CountDownLatch;

public class SettableFuture<T> implements Future<T> {
    private final CountDownLatch latch = new CountDownLatch(1);
    private T result;
    private Throwable exception;

    public void set(T result) {
        this.result = result;
        latch.countDown();
    }

    public void setException(Throwable t) {
        this.exception = t;
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) { return false; }

    @Override
    public boolean isCancelled() { return false; }

    @Override
    public boolean isDone() { return latch.getCount() == 0; }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        latch.await();
        if (exception != null) throw new ExecutionException(exception);
        return result;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            if (exception != null) throw new ExecutionException(exception);
            return result;
        }
        throw new TimeoutException();
    }
}
