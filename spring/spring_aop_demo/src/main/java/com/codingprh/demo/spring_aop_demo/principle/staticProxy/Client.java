package com.codingprh.demo.spring_aop_demo.principle.staticProxy;

/**
 * 描述:
 * client端
 *
 * @author codingprh
 * @create 2018-12-21 3:05 PM
 */
public class Client {

    public static void main(String[] args) {
        Subject subject = new Proxy(new RealSubject());
        subject.request();


    }

}