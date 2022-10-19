package com.breeze.boot.system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * webSocket 服务
 *
 * @author breeze
 * @date 2022-10-12
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{userId}")
public class WebSocketServer {

    /**
     * 会话 List
     */
    private final static List<Session> SESSIONS_POOL = new CopyOnWriteArrayList<>();
    /**
     * webSocket 连接 Map
     */
    private static final ConcurrentHashMap<Long, WebSocketServer> WEB_SOCKET_MAP = new ConcurrentHashMap<>();
    /**
     * 在线数
     */
    private static int onlineCount;
    /**
     * 会话
     */
    private Session session;

    /**
     * 用户id
     */
    private Long userId;

    public static synchronized int getOnlineCount() {
        return onlineCount--;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 建立连接
     *
     * @param session 会话
     * @param userId  用户id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Long userId) {
        this.session = session;
        this.userId = userId;
        SESSIONS_POOL.add(session);
        if (WEB_SOCKET_MAP.containsKey(userId)) {
            WEB_SOCKET_MAP.remove(userId);
            WEB_SOCKET_MAP.put(userId, this);
        } else {
            WEB_SOCKET_MAP.put(userId, this);
            addOnlineCount();
        }
        log.info("用户 userId={} 建立连接 , 当前连接数:{}", this.userId, getOnlineCount());
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() {
        if (WEB_SOCKET_MAP.containsKey(userId)) {
            WEB_SOCKET_MAP.remove(userId);
            subOnlineCount();
        }
        log.info("用户 userId={}  断开连接, 当前连接数: {}", userId, getOnlineCount());
    }

    /**
     * 错误
     *
     * @param session 会话
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户 userId={} 异常, 错误原因: {}", this.userId, error.getMessage());
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("用户 {} 收到消息: {}", this.userId, message);
    }

    public void sendMessage2User(String message, Long userId) {
        WebSocketServer webSocketServer = WEB_SOCKET_MAP.get(String.valueOf(userId));
        if (webSocketServer != null) {
            log.info("websocket消息推送消息,用户 userId={},message={}", userId, message);
            try {
                webSocketServer.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("用户 userId={} 发送消息失败, message={}", this.userId, message, e);
                e.printStackTrace();
            }
        }
    }

    public void sendMessage2Everyone(String message) {
        try {
            for (Session session : SESSIONS_POOL) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    log.info("用户 userId={}, 发送消息:message={}", session.getRequestParameterMap().get("userId"), message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
