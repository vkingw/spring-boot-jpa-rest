package com.vincent.example.config;

import com.vincent.example.authorization.interceptor.WebSocketInterceptor;
import com.vincent.example.handler.SocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.standard.WebSocketContainerFactoryBean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_NORMAL;
import static com.vincent.example.config.Constants.SOCKET_PUSH_PATH_ROOT;

/**
 * Created : vincent
 * Date : 2017/10/23 上午8:17
 * Email : alfa.king+git@gmail.com
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private final SocketHandler socketHandler;

  @Autowired
  public WebSocketConfig(SocketHandler socketHandler) {
    this.socketHandler = socketHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(socketHandler, SOCKET_PUSH_PATH_NORMAL).addInterceptors(new WebSocketInterceptor());

    registry.addHandler(socketHandler, SOCKET_PUSH_PATH_ROOT + SOCKET_PUSH_PATH_NORMAL).addInterceptors(new
      WebSocketInterceptor()).setAllowedOrigins("*").withSockJS();
  }

  @Bean
  public WebSocketContainerFactoryBean createWebSocketContainer() {
    WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
    container.setMaxTextMessageBufferSize(8192);
    container.setMaxBinaryMessageBufferSize(8192);
    return container;
  }
}
