package com.noah.spring.transaction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.noah.spring.transaction.entity.LettuceConfig;
import com.noah.spring.transaction.mapper.LettuceConfigMapper;
import com.noah.spring.transaction.service.ILettuceConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * <p>
 * KV配置表 服务实现类
 * </p>
 *
 * @author noah
 * @since 2021-11-15
 */
@Service("lettuceConfigServiceImpl")
@Slf4j
public class LettuceConfigServiceImpl extends ServiceImpl<LettuceConfigMapper, LettuceConfig> implements ILettuceConfigService {

    @Resource
    SubLettuceConfigServiceImpl subLettuceConfigService;

    //==================================
    //============事务没有生效===========
    //==================================

    /**
     * 回滚失效:@Transactional作用在privete方法上
     *
     * @param type
     */
    public void saveWrong(Integer type) {

        try {
            //privateSave(type);
            thisSave(type);
        } catch (Exception e) {
            log.error("saveM error", e);
        }
    }

    /**
     * 正确事务回滚
     *
     * @param type
     */
    @Transactional
    public void right(Integer type) {
        save(generatorConfig(type));

        if (type == 100) {
            throw new RuntimeException("系统异常");
        }
    }

    /**
     * this方法调用
     *
     * @param type
     */
    @Transactional
    public void thisSave(Integer type) {

        this.save(generatorConfig(type));

        if (type == 100) {
            throw new RuntimeException("系统异常");
        }
    }

    /**
     * 注解作用在私有方法上面
     */
    @Transactional
    private void privateSave(Integer type) {

        save(generatorConfig(type));

        if (type == 100) {
            throw new RuntimeException("系统异常");
        }
    }

    //==================================
    //==========事务生效，不一定回滚=======
    //==================================

    /**
     * 自身业务捕获了所有异常，无法回滚
     *
     * @param type
     */
    @Transactional
    public void tryException(Integer type) {

        //1、TransactionAspectSupport#invokeWithinTransaction，捕获了异常，实现机制
        //2、org.springframework.transaction.interceptor.DefaultTransactionAttribute#rollbackOn，默认处理的异常

        try {

            save(generatorConfig(type));
            if (type == 100) {
                throw new RuntimeException("系统异常");
            }

        } catch (Exception e) {
            log.error("error", e);
        }
    }

    /**
     * 手动回滚异常
     *
     * @param type
     */
    @Transactional
    public void tryExceptionRight(Integer type) {

        try {

            save(generatorConfig(type));
            if (type == 100) {
                throw new RuntimeException("系统异常");
            }

        } catch (Exception e) {
            log.error("error", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * PROPAGATION_REQUIRED,ISOLATION_DEFAULT,-java.lang.Exception
     * 事务传播机制
     * {@link Propagation.REQUIRED}：事务默认执行级别，事务不存在，创建事务。事务存在加入当前事务
     * {@link Propagation.SUPPORTS}：事务存在，加入当前事务。事务不存在，非事务方式执行
     * {@link Propagation.MANDATORY}: 必须要以事务的方式执行，不存在事务直接抛异常
     * {@link Propagation.REQUIRES_NEW}: 挂起当前事务，创建新的事务，执行逻辑
     * {@link Propagation.NOT_SUPPORTED}: 挂起当前事务，以非事务的方式执行
     * {@link Propagation.NEVER}: 非事务方式运行，存在事务抛异常
     * {@link Propagation.NESTED}: 如果事务存在，嵌套执行。行为，类似：REQUIRES_NEW
     *
     * @param type
     */
    @Transactional()
    public void propagate(Integer type) {

        save(LettuceConfigServiceImpl.generatorConfig(type));

        //不影响上面的业务执行，requires_new
        subLettuceConfigService.subThrow(100);
    }

    /**
     * 生成随机配置
     *
     * @return
     */
    public static LettuceConfig generatorConfig(int type) {

        LettuceConfig config = new LettuceConfig();

        config.setCode(UUID.randomUUID().toString());
        config.setName("name" + System.currentTimeMillis());
        config.setValue("{}");
        config.setType(type);
        config.setGmtCreated(LocalDateTime.now());
        config.setGmtModified(LocalDateTime.now());
        config.setDeleteMark(1);

        return config;
    }
}
