package server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response.ResponseListener;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpVersion;
import org.junit.Test;

import users.Topic;

public class TestHttpServer {

	@Test
	public void testAllEndpointsWork() throws Exception {
		HttpClient httpClient = new HttpClient();
		try {
			httpClient.start();
			HttpServerApi.main(null);
			Set<Topic> set = new HashSet<>();
			ContentResponse viewed = safelyView(httpClient);
			long start = System.currentTimeMillis();
			while (viewed.getStatus() != 200) {
				 if (start  + 10000 < System.currentTimeMillis()) {
					 fail("Couldn't log");
				 } else {
					 Thread.sleep(100);
					 viewed = safelyView(httpClient);
				 }
			}
			assertEquals(200, viewed.getStatus());
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + HttpServerApi.PORT + "/topics/add/adam/adam/topic1").method(HttpMethod.PUT).send();
			viewed = safelyView(httpClient);
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic1"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + HttpServerApi.PORT + "/topics/add/adam/adam/topic2").method(HttpMethod.PUT).send();
			viewed = safelyView(httpClient);
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + HttpServerApi.PORT + "/topics/delete/adam/adam/topic2").method(HttpMethod.DELETE).send();
			viewed = safelyView(httpClient);
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:" + HttpServerApi.PORT + "/topics/edit/adam/adam/topic1/topic3").method(HttpMethod.POST).send();
			viewed = safelyView(httpClient);
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic1"));
			set.add(new Topic("topic3"));
			assertEquals(set.toString(), viewed.getContentAsString());
		} finally {
			HttpServerApi.stopSpark();
			httpClient.stop();
		}
	}

	private ContentResponse safelyView(HttpClient httpClient) {
		try {
			return httpClient.GET("http://0.0.0.0:" + HttpServerApi.PORT + "/topics/view/adam/adam");
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

}
