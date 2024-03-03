//package com.noah.dubbo.service;
//
//import org.apache.dubbo.config.annotation.DubboReference;
//import org.apache.dubbo.config.annotation.DubboService;
//import org.apache.dubbo.config.annotation.Method;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@DubboService
//@Component
//public class PayFacadeImpl implements PayFacade {
//    @Autowired
//    @DubboReference (
//            /** 为 DemoRemoteFacade 的 sayHello 方法设置事件通知机制 **/
//            methods = {@Method(
//                    name = "sayHello",
//                    oninvoke = "eventNotifyService.onInvoke",
//                    onreturn = "eventNotifyService.onReturn",
//                    onthrow = "eventNotifyService.onThrow")}
//    )
//    private DemoRemoteFacade demoRemoteFacade;
//
//    // 商品支付功能：一个大方法
//    @Override
//    public PayResp recvPay(PayReq req){
//        // 支付核心业务逻辑处理
//        method1();
//        // 返回支付结果
//        return buildSuccResp();
//    }
//    private void method1() {
//        // 省略其他一些支付核心业务逻辑处理代码
//        demoRemoteFacade.sayHello(buildSayHelloReq());
//    }
//}
//interface  PayFacade {
//    // 商品支付功能：一个大方法
//    public PayResp recvPay(PayReq req);
//}
//
//class PayReq{
//
//}
//
//class PayResp{
//
//}
//
//// 假设定制底层标准接口
//public interface FrameworkNotifyService {
//    // 调用之前
//    void onInvoke(Request req);
//    // 调用之后
//    void onReturn(Response resp, Request req);
//    // 调用异常
//    void onThrow(Throwable ex, Request req);
//}
//
//// 专门为 demoRemoteFacade.sayHello 该Dubbo接口准备的事件通知处理类
//@Component("eventNotifyService")
//public class EventNotifyServiceImpl implements EventNotifyService {
//    // 调用之前
//    @Override
//    public void onInvoke(String name) {
//        System.out.println("[事件通知][调用之前] onInvoke 执行.");
//    }
//    // 调用之后
//    @Override
//    public void onReturn(String result, String name) {
//        System.out.println("[事件通知][调用之后] onReturn 执行.");
//        // 埋点已支付的商品信息
//        method2();
//        // 发送支付成功短信给用户
//        method3();
//        // 通知物流派件
//        method4();
//    }
//    // 调用异常
//    @Override
//    public void onThrow(Throwable ex, String name) {
//        System.out.println("[事件通知][调用异常] onThrow 执行.");
//    }
//}