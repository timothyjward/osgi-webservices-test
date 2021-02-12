package com.paremus.example.webservice.adapter;

import javax.servlet.Servlet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardContextSelect;
import org.osgi.service.http.whiteboard.propertytypes.HttpWhiteboardServletPattern;

import com.sun.xml.ws.transport.http.servlet.WSGatewayServlet;

@Component(scope=ServiceScope.PROTOTYPE)
// Note that this pattern prefix must be included in sun-jaxws.xml
@HttpWhiteboardServletPattern("/ws/*")
@HttpWhiteboardContextSelect("(osgi.http.whiteboard.context.name=*)")
public class WhiteboardServlet extends WSGatewayServlet implements Servlet {
	
	// This class exists to establish the bundle from which the sun-jaxws.xml and
	// JAX-WS services should be loaded.
}
