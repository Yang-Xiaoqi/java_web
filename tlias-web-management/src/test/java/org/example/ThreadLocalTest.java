package org.example;

public class ThreadLocalTest {
    private static ThreadLocal<String> local = new ThreadLocal<>();

    public static void main(String[] args) {
        local.set("111222");
        new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("子线程");
                System.out.println(Thread.currentThread().getName()+":"+local.get());
            }
        }).start();
        System.out.println(Thread.currentThread().getName()+":"+local.get());
        local.remove();
        System.out.println(Thread.currentThread().getName()+":"+local.get());
    }
}
