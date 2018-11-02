package websocket;

import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class TwitterWebSocket {

	private final static Logger logger = LoggerFactory.getLogger(TwitterWebSocket.class);
	
    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session session, String message) throws IOException {
        logger.info("Got: {}", message);   // Print message
        session.getRemote().sendString(message); // and send it back
    }
    
    public static void broadcast(String message) {
    	sessions.forEach(session -> {
			try {
				session.getRemote().sendString(message);
			} catch (IOException e) {
				logger.error("", e);
			}
		});
    }

}