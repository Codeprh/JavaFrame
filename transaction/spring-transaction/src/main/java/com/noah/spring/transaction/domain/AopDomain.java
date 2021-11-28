package com.noah.spring.transaction.domain;

import com.noah.spring.transaction.entity.AopEntity;
import com.noah.spring.transaction.service.AopService;
import com.noah.spring.transaction.service.impl.LettuceConfigServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 描述:
 * 测试切面
 *
 * @author Noah
 * @create 2021-11-25 11:50 上午
 */
@Service
public class AopDomain extends AopService {

    @Resource
    AopEntity aopEntity;

    @Resource(name = "lettuceConfigServiceImpl")
    LettuceConfigServiceImpl configService;

    /**
     * aop事务
     */
    @Transactional
    public void aopTransactional() {

        //1、自身服务，主业务完成调用，此时事务已经提交了，耶稣都回滚不了
        configService.right(101);

        //2、发起远程dubbo调用，但是异常被切面catch了。
        //2-1、导致自身主服务，事务回滚失败
        //2-2、解决方案1：dubbo远程调用失败了，dubbo不抛出任何异常，自己包裹住，然后返回boolean值给我自身服务
        //2-3、解决方案2：在切面回滚事务。
        //2-3-1：不可行，因为自身的事务已经提交了。No transaction aspect-managed TransactionStatus in scope
        //2-4、解决方案3：自身服务调用完dubbo之后，检查返回结果，再判定本次调用是否成功。
        //3、如果加上了重试逻辑？？
        aopEntity.testAopEntity();
    }

    /**
     * 手动提交事务
     */
    public void manualCommit() {
        //todo：手动提交事务
        //org.springframework.transaction.interceptor.TransactionAspectSupport#invokeWithinTransaction

    }
}
