package com.paremus.example.webservice.impl;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService
public interface CalculatorService {

    @WebMethod
    @WebResult(name="_return")
    int add(int a, int b) throws ArithmeticException;

    @WebMethod
    @WebResult(name="_return")
    int subtract(int a, int b) throws ArithmeticException;

    @WebMethod
    @WebResult(name="_return")
    int multiply(int a, int b) throws ArithmeticException;

    @WebMethod
    @WebResult(name="_return")
    int divide(int a, int b) throws ArithmeticException;

}