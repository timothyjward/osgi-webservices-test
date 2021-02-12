package com.paremus.example.webservice.client;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientMain {
	
	public static void main(String[] args) throws Exception {
		
		if(args.length != 4) {
			printUsage();
			return;
		}
		
		String xml = String.format("<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"
				+ "  <env:Header/>\n"
				+ "  <env:Body>\n"
				+ "	   <%1$s>\n"
				+ "	     <arg0>%2$s</arg0>\n"
				+ "	     <arg1>%3$s</arg1>\n"
				+ "	   </%1$s>\n"
				+ "	 </env:Body>\n"
				+ "</env:Envelope>", args[1], args[2], args[3]);
		
		System.out.println("Sending " + xml);
		
		HttpURLConnection conn = (HttpURLConnection) new URL(args[0]).openConnection();
		
		try {
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("Accept", "application/xml");
			conn.setRequestProperty("SOAPAction", "CalculatorService");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try(Writer writer = new OutputStreamWriter(conn.getOutputStream(), UTF_8)) {
				writer.write(xml);
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), UTF_8))) {
				reader.lines().forEach(System.out::println);
			}
		} finally {
			conn.disconnect();
		}
		
		return;
	}

	private static void printUsage() {
		System.out.println("Please use this class by passing: ");
		System.out.println("1. The CalculatorService URL (normally http://localhost:8080/services/CalculatorService)");
		System.out.println("2. The method to call, one of add, subtract, multiply or divide");
		System.out.println("3. The first numeric argument");
		System.out.println("4. The first numeric argument");
		
	}
}
