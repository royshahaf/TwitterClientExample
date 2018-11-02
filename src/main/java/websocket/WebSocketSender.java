package websocket;

import static spark.Spark.*;
import static spark.Spark.options;
import static spark.Spark.port;

import com.google.gson.Gson;

import app.Sender;
import twitter4j.Status;

public class WebSocketSender implements Sender<Status> {
	private static final String PREFIX = "twitter.example.";
    private Gson gson = new Gson();

	public WebSocketSender() {
		int port = getPort();
		port(port);
		webSocket("/tweets", TwitterWebSocket.class);
		enableCors();
		init();
	}
	
	private void enableCors() {
		options("/*",
		        (request, response) -> {

		            String accessControlRequestHeaders = request
		                    .headers("Access-Control-Request-Headers");
		            if (accessControlRequestHeaders != null) {
		                response.header("Access-Control-Allow-Headers",
		                        accessControlRequestHeaders);
		            }

		            String accessControlRequestMethod = request
		                    .headers("Access-Control-Request-Method");
		            if (accessControlRequestMethod != null) {
		                response.header("Access-Control-Allow-Methods",
		                        accessControlRequestMethod);
		            }

		            return "OK";
		        });

		before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
	}
	
	private static int getPort() {
		String portString = System.getenv(PREFIX + "topicsPort");
		try {
			return Integer.parseInt(portString);
		} catch (NumberFormatException e) {
			return 8090;
		}
	}
	@Override
	public void send(Status message) {
		TwitterWebSocket.broadcast(gson.toJson(message));
	}

}
