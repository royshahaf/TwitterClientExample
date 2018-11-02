package server;

import static spark.Spark.*;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.stop;

import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;

import spark.Response;
import users.User;

public class HttpServerApi {

	private int port = 8111;
	private static final String REQUESTING = ":requesting";
	private static final String REQUESTED = ":requested";

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start(Map<String, User> users, SecurityParams params) {
		Injector injector = Guice.createInjector(new MyModule(users));
		ServerApi api = injector.getInstance(ServerApi.class);
		if (params.isSecure()) {
			secure(params.getKeystoreFilePath(), params.getKeystorePassword(), params.getTruststoreFilePath(),
					params.getTruststorePassword());
		} 
		port(port);
		enableCors();

		path("/topics", () -> {
			get("/view/:requesting/:requested", (request, response) -> {
				Result result = api.view(request.params(REQUESTING), request.params(REQUESTED));
				return handleResult(response, result);
			});
			put("/add/:requesting/:requested/:topic", (request, response) -> {
				Result result = api.add(request.params(REQUESTING), request.params(REQUESTED),
						request.params(":topic"));
				return handleResult(response, result);
			});
			delete("/delete/:requesting/:requested/:topic", (request, response) -> {
				Result result = api.delete(request.params(REQUESTING), request.params(REQUESTED),
						request.params(":topic"));
				return handleResult(response, result);
			});
			post("/edit/:requesting/:requested/:previousTopic/:newTopic", (request, response) -> {
				Result result = api.edit(request.params(REQUESTING), request.params(REQUESTED),
						request.params(":previousTopic"), request.params(":newTopic"));
				return handleResult(response, result);
			});
		});
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

	public void stopSpark() {
		stop();
	}

	private Object handleResult(Response response, Result result) {
		response.type("application/json");
		if (result.isStatus()) {
			response.status(200);
		} else {
			response.status(500);
		}
		return result.getMessage();
	}
}
