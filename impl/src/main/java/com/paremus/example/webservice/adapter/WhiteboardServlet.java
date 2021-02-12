package com.paremus.example.webservice.adapter;

import javax.servlet.Servlet;

import org.apache.axis.transport.http.WSGatewayServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

@Component(scope=ServiceScope.PROTOTYPE)
@HttpWhiteboardServletPattern({"/ws/*", "/services/*"})
@HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=*)")
public class WhiteboardServlet extends WSGatewayServlet implements Servlet {
	
	// This class exists to establish the bundle from which the Axis configuration and
	// JAX-WS services should be loaded.
}
