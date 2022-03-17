package com.noah.ali.rocketmq;

import com.aliyun.mq.http.MQClient;
import com.aliyun.mq.http.MQTransProducer;
import com.aliyun.mq.http.common.AckMessageException;
import com.aliyun.mq.http.model.Message;
import com.aliyun.mq.http.model.TopicMessage;

import java.util.List;

public class TransProducer {


    static void processCommitRollError(Throwable e) {
        if (e instanceof AckMessageException) {
            AckMessageException errors = (AckMessageException) e;
            System.out.println("Commit/Roll transaction error, requestId is:" + errors.getRequestId() + ", fail handles:");
            if (errors.getErrorMessages() != null) {
                for (String errorHandle : errors.getErrorMessages().keySet()) {
                    System.out.println("Handle:" + errorHandle + ", ErrorCode:" + errors.getErrorMessages().get(errorHandle).getErrorCode()
                            + ", ErrorMsg:" + errors.getErrorMessages().get(errorHandle).getErrorMessage());
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        MQClient mqClient = new MQClient(
                // 设置HTTP协议客户端接入点，进入消息队列RocketMQ版控制台实例详情页面的接入点区域查看。
                "${HTTP_ENDPOINT}",
                // AccessKey ID，阿里云身份验证，在阿里云RAM控制台创建。
                "${ACCESS_KEY}",
                // AccessKey Secret，阿里云身份验证，在阿里云RAM控制台创建。
                "${SECRET_KEY}"
        );

        // 消息所属的Topic，在消息队列RocketMQ版控制台创建。
        // 不同消息类型的Topic不能混用，例如普通消息的Topic只能用于收发普通消息，不能用于收发其他类型的消息。
        final String topic = "${TOPIC}";
        // Topic所属的实例ID，在消息队列RocketMQ版控制台创建。
        // 若实例有命名空间，则实例ID必须传入；若实例无命名空间，则实例ID传入null空值或字符串空值。实例的命名空间可以在消息队列RocketMQ版控制台的实例详情页面查看。
        final String instanceId = "${INSTANCE_ID}";
        // 您在消息队列RocketMQ版控制台创建的Group ID。
        final String groupId = "${GROUP_ID}";

        final MQTransProducer mqTransProducer = mqClient.getTransProducer(instanceId, topic, groupId);

        for (int i = 0; i < 4; i++) {
            TopicMessage topicMessage = new TopicMessage();
            topicMessage.setMessageBody("trans_msg");
            topicMessage.setMessageTag("a");
            topicMessage.setMessageKey(String.valueOf(System.currentTimeMillis()));
            // 设置事务第一次回查的时间，为相对时间。单位：秒，取值范围：10~300。
            // 第一次事务回查后如果消息没有提交或者回滚，则之后每隔10s左右会回查一次，共回查24小时。
            topicMessage.setTransCheckImmunityTime(10);
            topicMessage.getProperties().put("a", String.valueOf(i));

            TopicMessage pubResultMsg = null;
            pubResultMsg = mqTransProducer.publishMessage(topicMessage);
            System.out.println("Send---->msgId is: " + pubResultMsg.getMessageId()
                    + ", bodyMD5 is: " + pubResultMsg.getMessageBodyMD5()
                    + ", Handle: " + pubResultMsg.getReceiptHandle()
            );
            if (pubResultMsg != null && pubResultMsg.getReceiptHandle() != null) {
                if (i == 0) {
                    // 发送完事务消息后能获取到半消息句柄，可以直接提交或回滚事务消息。
                    try {
                        mqTransProducer.commit(pubResultMsg.getReceiptHandle());
                        System.out.println(String.format("MessageId:%s, commit", pubResultMsg.getMessageId()));
                    } catch (Throwable e) {
                        // 如果提交或回滚事务消息时超过了TransCheckImmunityTime（针对发送事务消息的句柄）设置的时长，则会失败。
                        if (e instanceof AckMessageException) {
                            processCommitRollError(e);
                            continue;
                        }
                    }
                }
            }
        }

        // 客户端需要有一个线程或者进程来消费没有确认的事务消息。
        // 启动一个线程来检查没有确认的事务消息。
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                while (true) {
                    try {
                        if (count == 3) {
                            break;
                        }
                        List<Message> messages = mqTransProducer.consumeHalfMessage(3, 3);
                        if (messages == null) {
                            System.out.println("No Half message!");
                            continue;
                        }
                        System.out.println(String.format("Half---->MessageId:%s,Properties:%s,Body:%s,Latency:%d",
                                messages.get(0).getMessageId(),
                                messages.get(0).getProperties(),
                                messages.get(0).getMessageBodyString(),
                                System.currentTimeMillis() - messages.get(0).getPublishTime()));

                        for (Message message : messages) {
                            try {
                                if (Integer.valueOf(message.getProperties().get("a")) == 1) {
                                    // 确认提交事务消息。
                                    mqTransProducer.commit(message.getReceiptHandle());
                                    count++;
                                    System.out.println(String.format("MessageId:%s, commit", message.getMessageId()));
                                } else if (Integer.valueOf(message.getProperties().get("a")) == 2
                                        && message.getConsumedTimes() > 1) {
                                    // 确认提交事务消息。
                                    mqTransProducer.commit(message.getReceiptHandle());
                                    count++;
                                    System.out.println(String.format("MessageId:%s, commit", message.getMessageId()));
                                } else if (Integer.valueOf(message.getProperties().get("a")) == 3) {
                                    // 确认回滚事务消息。
                                    mqTransProducer.rollback(message.getReceiptHandle());
                                    count++;
                                    System.out.println(String.format("MessageId:%s, rollback", message.getMessageId()));
                                } else {
                                    // 什么都不做，下次再检查。
                                    System.out.println(String.format("MessageId:%s, unknown", message.getMessageId()));
                                }
                            } catch (Throwable e) {
                                // 如果提交或回滚消息时超过了TransCheckImmunityTime（针对发送事务消息的句柄）或者超过10s（针对consumeHalfMessage的句柄）则会失败。
                                processCommitRollError(e);
                            }
                        }
                    } catch (Throwable e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        });

        t.start();

        t.join();

        mqClient.close();
    }

}
