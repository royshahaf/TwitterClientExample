package server;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpMethod;
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
			ContentResponse viewed = httpClient.GET("http://0.0.0.0:4567/topics/view/adam/adam");
			assertEquals(200, viewed.getStatus());
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:4567/topics/add/adam/adam/topic1").method(HttpMethod.PUT).send();
			viewed = httpClient.GET("http://0.0.0.0:4567/topics/view/adam/adam");
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic1"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:4567/topics/add/adam/adam/topic2").method(HttpMethod.PUT).send();
			viewed = httpClient.GET("http://0.0.0.0:4567/topics/view/adam/adam");
			assertEquals(200, viewed.getStatus());
			set.add(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:4567/topics/delete/adam/adam/topic2").method(HttpMethod.DELETE).send();
			viewed = httpClient.GET("http://0.0.0.0:4567/topics/view/adam/adam");
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic2"));
			assertEquals(set.toString(), viewed.getContentAsString());
			httpClient.newRequest("http://0.0.0.0:4567/topics/edit/adam/adam/topic1/topic3").method(HttpMethod.POST).send();
			viewed = httpClient.GET("http://0.0.0.0:4567/topics/view/adam/adam");
			assertEquals(200, viewed.getStatus());
			set.remove(new Topic("topic1"));
			set.add(new Topic("topic3"));
			assertEquals(set.toString(), viewed.getContentAsString());
		} finally {
			HttpServerApi.stopSpark();
			httpClient.stop();
		}
	}

}
