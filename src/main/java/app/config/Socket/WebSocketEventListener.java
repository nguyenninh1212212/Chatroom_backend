package app.config.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import app.repository.MessageReponsitory;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class); // Fix lỗi Logger

    private final MessageReponsitory myRepository;

    public WebSocketEventListener(MessageReponsitory myRepository) {
        this.myRepository = myRepository;
    }
    @EventListener
    public void handleWebSocketDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId(); 
        logger.info("WebSocket session disconnected: " + sessionId);

    }
	public MessageReponsitory getMyRepository() {
		return myRepository;
	}
}
