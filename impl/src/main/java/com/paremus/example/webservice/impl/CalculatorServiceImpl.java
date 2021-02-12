package com.paremus.example.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(serviceName = "CalculatorService", endpointInterface = "com.paremus.example.webservice.impl.CalculatorService")
public class CalculatorServiceImpl implements CalculatorService {

	@Override
	@WebMethod
	public int add(int a, int b) throws ArithmeticException {
		if((a + b) != (((long) a) + ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a + b;
	}

	@Override
	@WebMethod
	public int subtract(int a, int b) throws ArithmeticException {
		if((a - b) != (((long) a) - ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a - b;
	}

	@Override
	@WebMethod
	public int multiply(int a, int b) throws ArithmeticException {
		if((a * b) != (((long) a) * ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a * b;
	}

	@Override
	@WebMethod
	public int divide(int a, int b) throws ArithmeticException {
		return a / b;
	}
}