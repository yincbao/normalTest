package com.cpw.concurrent.future;
/** 
 *
Title: LoonFramework

 *
Description:利用Future模式进行处理

 *
Copyright: Copyright (c) 2007

 *
Company: LoonFramework

 * @author chenpeng 
 * @email：ceponline@yahoo.com.cn 
 * @version 0.1
 */
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
public abstract class FutureProxy<T> {
    private final class CallableImpl implements Callable<T> {
        public T call() throws Exception {
            return FutureProxy.this.createInstance();
        }
    }
    private static class InvocationHandlerImpl<T> implements InvocationHandler {
        private Future<T> future;
        
        private volatile T instance;
        
        InvocationHandlerImpl(Future<T> future){
            this.future = future;
        }
        
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            synchronized(this){
                if(this.future.isDone()){
                    this.instance = this.future.get();
                }else{
                    while(!this.future.isDone()){
                        try{
                            this.instance = this.future.get();
                        }catch(InterruptedException e){
                            Thread.currentThread().interrupt();
                        }
                    }
                }
                
                return method.invoke(this.instance, args);
            }
        }
    }
    /** 
     * 实现java.util.concurrent.ThreadFactory接口
     * @author chenpeng
     *
     */
    private static final class ThreadFactoryImpl implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }
    private static ExecutorService service = Executors.newCachedThreadPool(new ThreadFactoryImpl());
    protected abstract T createInstance();
    protected abstract Class<? extends T> getInterface();
    
    /**
     * 返回代理的实例
     * @return
     */
    @SuppressWarnings("unchecked")
    public final T getProxyInstance() {
        Class<? extends T> interfaceClass = this.getInterface();
        if (interfaceClass == null || !interfaceClass.isInterface()) {
            throw new IllegalStateException();
        }
        Callable<T> task = new CallableImpl();
        Future<T> future = FutureProxy.service.submit(task);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[] { interfaceClass }, new InvocationHandlerImpl(future));
    }
}