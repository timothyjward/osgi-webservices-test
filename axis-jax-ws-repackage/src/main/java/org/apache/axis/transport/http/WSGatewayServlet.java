package org.apache.axis.transport.http;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// A servlet which can be extended to register JAX-WS services from a bundle 

public abstract class WSGatewayServlet extends AxisServlet {
	
	interface Action {
		void act() throws Exception;
	}
	
	protected final ClassLoader unionClassLoader;
			
	protected WSGatewayServlet() {
		ClassLoader bundleLoader = getClass().getClassLoader();
		unionClassLoader = new ClassLoader(AxisServlet.class.getClassLoader()) {

			@Override
			protected Class<?> findClass(String name) throws ClassNotFoundException {
				return bundleLoader.loadClass(name);
			}

			@Override
			protected URL findResource(String name) {
				return bundleLoader.getResource(name);
			}

			@Override
			protected Enumeration<URL> findResources(String name) throws IOException {
				return bundleLoader.getResources(name);
			}
			
		};
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doActionWithTCCL(() -> super.doGet(request, response));
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doActionWithTCCL(() -> super.doPost(request, response));
	}

	@Override
	public void init() throws ServletException {
		doActionWithTCCL(() -> super.init());
	}

	private void doActionWithTCCL(Action action) {
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(unionClassLoader);
			action.act();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
		}
	}
	
}
