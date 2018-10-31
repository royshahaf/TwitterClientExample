package server;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.junit.Test;

import roles.Role;
import spark.Spark;
import users.Topic;
import users.User;

public class TestHttpServer {
	private User adminUser = new User("adam", Collections.<Topic>emptySet(),
			new HashSet<>(Arrays.asList(Role.ADMIN, Role.REGULAR)));
	private User regularUser = new User("roy", Collections.<Topic>emptySet(),
			new HashSet<>(Arrays.asList(Role.REGULAR)));

	public Map<String, User> getTestUsers() {
		Map<String, User> testUsers = new HashMap<>();
		testUsers.put(adminUser.getName(), adminUser);
		testUsers.put(regularUser.getName(), regularUser);
		return testUsers;
	}

	@Test
	public void testAllEndpointsWork() throws Exception {
		HttpClient httpClient = new HttpClient();
		HttpServerApi httpServer = new HttpServerApi();
		try {
			httpClient.start();
			httpServer.start(getTestUsers(), getParams());
			Spark.awaitInitialization();
			Set<Topic> set = new HashSet<>();
			ContentResponse viewed = safelyView(httpClient, httpServer.port);
			assertEquals(200, viewed.getStatus());
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + httpServer.port + "/topics/add/adam/adam/topic1")
					.method(HttpMethod.PUT).send();
			viewed = safelyView(httpClient, httpServer.port);
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic1"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + httpServer.port + "/topics/add/adam/adam/topic2")
					.method(HttpMethod.PUT).send();
			viewed = safelyView(httpClient, httpServer.port);
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + httpServer.port + "/topics/delete/adam/adam/topic2")
					.method(HttpMethod.DELETE).send();
			viewed = safelyView(httpClient, httpServer.port);
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + httpServer.port + "/topics/edit/adam/adam/topic1/topic3")
					.method(HttpMethod.POST).send();
			viewed = safelyView(httpClient, httpServer.port);
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic1"));
			set.add(new Topic("topic3"));
			assertEquals(set.toString(), viewed.getContentAsString());
			viewed = httpClient.GET("http://0.0.0.0:" + httpServer.port + "/topics/view/adam/null");
			assertEquals(500, viewed.getStatus());
		} finally {
			httpServer.stopSpark();
			httpClient.stop();
		}
	}
	private ContentResponse safelyView(HttpClient httpClient, int port) {
		try {
			return httpClient.GET("http://0.0.0.0:" + port + "/topics/view/adam/adam");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			return new ContentResponse() {
				
				@Override
				public HttpVersion getVersion() {
					return null;
				}
				
				@Override
				public int getStatus() {
					return 400;
				}
				
				@Override
				public Request getRequest() {
					return null;
				}
				
				@Override
				public String getReason() {
					return null;
				}
				
				@Override
				public <T extends ResponseListener> List<T> getListeners(Class<T> listenerClass) {
					return null;
				}
				
				@Override
				public HttpFields getHeaders() {
					return null;
				}
				
				@Override
				public boolean abort(Throwable cause) {
					return false;
				}
				
				@Override
				public String getMediaType() {
					return null;
				}
				
				@Override
				public String getEncoding() {
					return null;
				}
				
				@Override
				public String getContentAsString() {
					return null;
				}
				
				@Override
				public byte[] getContent() {
					return null;
				}
			};
		}
	}

	private SecurityParams getParams() {
		SecurityParams params = new SecurityParams();
		params.secure = false;
		return params;
	}

}
