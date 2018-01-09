package com.vincent.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import static com.vincent.example.config.Constants.SOCKET_SESSION_USER_ID;

/**
 * Created : vincent
 * Date : 2017/10/23 上午9:22
 * Email : wangxiao@wafersystems.com
 */
@Service
@Slf4j
public class SocketHandler implements WebSocketHandler {

  private static final ConcurrentHashMap<String, WebSocketSession> userHashMap = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    String userId = session.getAttributes().get(SOCKET_SESSION_USER_ID).toString();
    userHashMap.put(userId, session);
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {

  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    if (session.isOpen()) {
      session.close();
    }
    removeSession(session);
    String userId = session.getAttributes().get(SOCKET_SESSION_USER_ID).toString();
    log.error(userId + "连接出现错误:" + exception.toString());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
    String userId = session.getAttributes().get(SOCKET_SESSION_USER_ID).toString();
    userHashMap.remove(userId);
    removeSession(session);
    log.info(userId + ": WebSocket连接已关闭");
  }

  public void closeSession(String userId) throws Exception {
    WebSocketSession session = userHashMap.get(userId);
    if (session.isOpen()) {
      session.close();
    }
  }

  private void removeSession(WebSocketSession session) {
    String userId = session.getAttributes().get(SOCKET_SESSION_USER_ID).toString();
    userHashMap.remove(userId);
  }

  @Override
  public boolean supportsPartialMessages() {
    return false;
  }

  /**
   * @param userId        send to
   * @param socketMessage message body
   * @throws IOException send error
   */
  public void sendMessageToUser(String userId, String socketMessage) throws Exception {
    WebSocketSession webSocketSession = userHashMap.get(userId);
    if (null != webSocketSession) {
      TextMessage textMessage = new TextMessage(socketMessage);
      webSocketSession.sendMessage(textMessage);
      log.info("推送个人消息成功，推送给用户： {} , 内容为 :{}", userId, socketMessage);
    } else {
      log.error("推送个人消息失败，用户Socket 链接不存在，推送给用户： {} , 内容为 :{}", userId, socketMessage);
    }
  }

}
