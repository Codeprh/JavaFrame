package com.noah.lock.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 订单详情
 * </p>
 *
 * @author noah
 * @since 2022-10-28
 */
@TableName("order_exta")
@ApiModel(value = "OrderExta对象", description = "订单详情")
public class OrderExta implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单号")
    private String orderId;

    @ApiModelProperty("订单详情'{}'")
    private String orderExtra;

    @ApiModelProperty("删除表示")
    private Integer deleteMark;

    @ApiModelProperty("创建时间")
    private Date gmtCreated;

    @ApiModelProperty("更新时间")
    private Date gmtModified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderExtra() {
        return orderExtra;
    }

    public void setOrderExtra(String orderExtra) {
        this.orderExtra = orderExtra;
    }
    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }
    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }
    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "OrderExta{" +
            "id=" + id +
            ", orderId=" + orderId +
            ", orderExtra=" + orderExtra +
            ", deleteMark=" + deleteMark +
            ", gmtCreated=" + gmtCreated +
            ", gmtModified=" + gmtModified +
        "}";
    }
}
