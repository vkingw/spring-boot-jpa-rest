package com.vincent.example.authorization.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.vincent.example.config.Constants.SOCKET_SESSION_USER_ID;

/**
 * Created : vincent
 * Date : 2017/10/23 上午10:16
 * Email : wangxiao@wafersystems.com
 */
@Service
public class WebSocketInterceptor implements HandshakeInterceptor {

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
    WebSocketHandler handler, Exception exception) {

  }

  /**
   * 将HttpSession中对象放入WebSocketSession中
   *
   * @param request  request
   * @param response response
   * @param handler  handler
   * @param map      map
   * @return boolean
   */
  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler handler,
    Map<String, Object> map) {
    if (request != null) {
      ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
      HttpSession session = servletRequest.getServletRequest().getSession();
      if (session != null) {
        //区分socket连接以定向发送消息
        map.put(SOCKET_SESSION_USER_ID, request.getPrincipal().getName());
      }
    }
    return true;
  }
}
