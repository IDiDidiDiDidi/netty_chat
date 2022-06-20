package com.liudi.nettychat;

import com.liudi.nettychat.websocket.WebSocketServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author liuD
 * @Date 2022/6/20 9:24 上午
 * @PackageName:com.liudi.nettychat
 * @ClassName: NettyBooter
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            WebSocketServer.getInstance().start();
        }
    }
}
