package com.sun.xml.ws.transport.http.servlet;

import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.ws.WebServiceException;

import com.sun.xml.ws.resources.WsservletMessages;
import com.sun.xml.ws.transport.http.DeploymentDescriptorParser;

// A servlet which can be extended to register JAX-WS services from a bundle 

public abstract class WSGatewayServlet extends WSServlet {

	 private static final Logger logger =
		        Logger.getLogger(
		            com.sun.xml.ws.util.Constants.LoggingDomain + ".server.http");
	
	private WSServletDelegate delegate;
	private List<ServletAdapter> adapters;

	@Override
	public void destroy() {
		ServletContext context = getServletContext();
		super.destroy();

		if (delegate != null) { // the deployment might have failed.
			delegate.destroy();
			delegate = null;
		}

		if (adapters != null) {

			for (ServletAdapter a : adapters) {
				try {
					a.getEndpoint().dispose();
				} catch (Throwable e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			}
			adapters = null;
		}

		if (logger.isLoggable(Level.INFO)) {
			logger.info(WsservletMessages.LISTENER_INFO_DESTROY());
		}
		// Clean up the ServletContext for the next time this servlet gets registered
		// with a whiteboard
		context.removeAttribute(JAXWS_RI_RUNTIME_INFO);
	}

	@Override
	public void init(ServletConfig cfg) throws ServletException {
		ServletContext context = cfg.getServletContext();

		// Parent delegate to the bundle publishing this instance, so local
		// resources/classes are preferred
		ClassLoader toUse = new ClassLoader(getClass().getClassLoader()) {
			protected Class<?> findClass(String className) throws ClassNotFoundException {
				// If not found in the parent, try this bundle
				return WSServlet.class.getClassLoader().loadClass(className);
			}
		};

		String prop = System.getProperty("javax.xml.soap.SAAJMetaFactory");
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(toUse);
			// Set the SOAP Factory used in the repackaged bundle
			System.setProperty("javax.xml.soap.SAAJMetaFactory", "com.sun.xml.messaging.saaj.soap.SAAJMetaFactoryImpl");
			URL sunJaxWsXml = getClass().getResource("/WEB-INF/sun-jaxws.xml");
			if (sunJaxWsXml == null) {
				throw new WebServiceException("Unable to locate the configuration xml file");
			}

			// Parse the descriptor file and build endpoint infos
			DeploymentDescriptorParser<ServletAdapter> parser = new DeploymentDescriptorParser<ServletAdapter>(toUse,
					new ServletResourceLoader(context), new ServletContainer(context), new ServletAdapterList(context));
			adapters = parser.parse(sunJaxWsXml.toExternalForm(), sunJaxWsXml.openStream());
			delegate = new WSServletDelegate(adapters, context);

		} catch (Exception e) {
			throw new ServletException("Failed to load JAX-WS runtime", e);
		} finally {
			Thread.currentThread().setContextClassLoader(cl);
			if(prop == null) {
				System.clearProperty("javax.xml.soap.SAAJMetaFactory");
			} else {
				System.setProperty("javax.xml.soap.SAAJMetaFactory", prop);
			}
		}

		context.setAttribute(WSServlet.JAXWS_RI_RUNTIME_INFO, delegate);


		super.init(cfg);

	}
}
