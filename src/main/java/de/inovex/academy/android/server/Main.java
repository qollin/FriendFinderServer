package de.inovex.academy.android.server;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

public class Main {
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://ec2-50-17-60-135.compute-1.amazonaws.com/").port(9998).build();
	}

	public static final URI BASE_URI = getBaseURI();

	protected static HttpServer startServer() throws IOException {
		System.out.println("Starting grizzly...");
		ResourceConfig rc = new PackagesResourceConfig(
				"de.inovex.academy.android.server.resources");
		return GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
		if (args.length > 0 &&
			"sample".equals(args[0])) {
			createSampleData();
		}
		
		HttpServer httpServer = startServer();
		System.out.println(String.format("Jersey app started with WADL available at "
								+ "%sapplication.wadl\nTry out %shelloworld\nHit enter to stop it...",
								BASE_URI, BASE_URI));
		System.in.read();
		httpServer.stop();
	}

	private static void createSampleData() throws ClassNotFoundException, SQLException, IOException {
		new SampleData().create();
	}
}
