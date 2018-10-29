package server;

import static spark.Spark.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.multibindings.Multibinder;

import activities.ActivityService;
import activities.simple.SimpleActivityService;
import authentication.Authenticator;
import authentication.simple.SimpleAuthenticator;
import roles.Role;
import spark.Response;
import users.Topic;
import users.User;
import users.UserService;
import users.simple.SimpleUserService;

public class HttpServerApi {

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MyModule());
		ServerApi api = injector.getInstance(ServerApi.class);
		path("/topics", () -> {
			get("/view/:requesting/:requested", (request, response) -> {
				Result result = api.view(request.params(":requesting"), request.params(":requested"));
				return handleResult(response, result);
			});
			put("/add/:requesting/:requested/:topic", (request, response) -> {
				Result result = api.add(request.params(":requesting"), request.params(":requested"),
						request.params(":topic"));
				return handleResult(response, result);
			});
			delete("/delete/:requesting/:requested/:topic", (request, response) -> {
				Result result = api.delete(request.params(":requesting"), request.params(":requested"),
						request.params(":topic"));
				return handleResult(response, result);
			});
			post("/add/:requesting/:requested/:previousTopic/:newTopic", (request, response) -> {
				Result result = api.edit(request.params(":requesting"), request.params(":requested"),
						request.params(":previousTopic"), request.params(":newTopic"));
				return handleResult(response, result);
			});
		});
	}

	private static Object handleResult(Response response, Result result) {
		response.type("application/json");
		if (result.isStatus()) {
			response.status(200);
		} else {
			response.status(500);
		}
		return result.getMessage();
	}
}
