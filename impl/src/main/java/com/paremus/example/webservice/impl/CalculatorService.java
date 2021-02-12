package com.paremus.example.webservice.impl;

public interface CalculatorService {

    int add(int a, int b) throws ArithmeticException;

    int subtract(int a, int b) throws ArithmeticException;

    int multiply(int a, int b) throws ArithmeticException;

    int divide(int a, int b) throws ArithmeticException;

}