package server;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.inject.Guice;
import com.google.inject.Injector;

import spark.Response;

public class HttpServerApi {

	private static final String REQUESTING = ":requesting";
	private static final String REQUESTED = ":requested";

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MyModule());
		ServerApi api = injector.getInstance(ServerApi.class);
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
			post("/add/:requesting/:requested/:previousTopic/:newTopic", (request, response) -> {
				Result result = api.edit(request.params(REQUESTING), request.params(REQUESTED),
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
