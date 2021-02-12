package com.paremus.example.webservice.impl;

public class CalculatorServiceImpl implements CalculatorService {

	@Override
	public int add(int a, int b) throws ArithmeticException {
		if((a + b) != (((long) a) + ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a + b;
	}

	@Override
	public int subtract(int a, int b) throws ArithmeticException {
		if((a - b) != (((long) a) - ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a - b;
	}

	@Override
	public int multiply(int a, int b) throws ArithmeticException {
		if((a * b) != (((long) a) * ((long) b))) {
			throw new ArithmeticException("Overflow occurred");
		}
		return a * b;
	}

	@Override
	public int divide(int a, int b) throws ArithmeticException {
		return a / b;
	}
}