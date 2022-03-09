package com.noah.lock.transaction.domain.service;

import com.noah.lock.transaction.entity.Orders;
import com.noah.lock.transaction.entity.Product;
import com.noah.lock.transaction.service.IOrdersService;
import com.noah.lock.transaction.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class ProductDomainService {

    public final Lock lock = new ReentrantLock(true);

    @Resource
    IOrdersService ordersService;

    @Resource
    IProductService productService;

    public static void main(String[] args) {
        String name="noah";
        String namePlus="noah123";

        System.out.println(namePlus.contains(name));
        System.out.println(name.contains(namePlus));
        //System.out.println(namePlus.contains(name));
        //exceptionClass.getName().contains(this.exceptionName)
    }

    /**
     * 这是依托于mysql，stock-->0，实现在单机多线程不会出现超卖现象。
     * 也是正确扣减库存的方式
     *
     * @param id
     */
    @Transactional(noRollbackFor = ArithmeticException.class, rollbackFor = Exception.class)
    public void sellProductError(Long id) {

        try {

            lock.lock();
            int i = 100 / 0;

            String threadName = Thread.currentThread().getName();
            log.info("name:{},抢到了锁", threadName);

            //查询库存
            Product product = productService.getById(id);
            Long stock = product.getStock();

            if (stock <= 0) {
                log.info("name:{},没有库存", threadName);
                return;
            }
            //大忌：查出来更新，而不是依赖于db更新
            Product productDB = new Product();
            productDB.setStock(stock - 1);
            productDB.setId(product.getId());

            productService.updateById(productDB);

            Orders orders = new Orders();

            product.setName(orders.getName());
            orders.setPrice(-99);

            ordersService.save(orders);
            log.info("name:{},购物成功", threadName);

        } catch (Exception e) {
            log.error("抢占失败", e);
            //方式1：异常抛到transaction的处理
            throw e;

            //方式2：手动设置异常，回滚
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            lock.unlock();
            //增加锁释放延迟
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 这是依托于mysql，stock-->0，实现在单机多线程不会出现超卖现象。
     * 也是正确扣减库存的方式
     *
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    public void sellProduct(Long id) {

        try {

            lock.lock();

            String threadName = Thread.currentThread().getName();
            log.info("name:{},抢到了锁", threadName);

            //查询库存
            Product product = productService.getById(id);
            Long stock = product.getStock();

            if (stock <= 0) {
                log.info("name:{},没有库存", threadName);
                return;
            }

            productService.sellProduct(id);

            Orders orders = new Orders();

            orders.setName(product.getName());
            orders.setPrice(-99);

            ordersService.save(orders);

            log.info("name:{},购物成功", threadName);

        } catch (Exception e) {
            log.error("抢占失败", e);
        } finally {
            lock.unlock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
