package web.listener;

import redis.embedded.RedisServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

//@WebListener
public class MyListener implements ServletContextListener {
    private static RedisServer redisServer = new RedisServer();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        redisServer.start();
        System.out.println("redis启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        redisServer.stop();
        System.out.println("redis停止");
    }
}
