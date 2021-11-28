package com.noah.spring.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.AUTO;

/**
 * <p>
 * KV配置表
 * </p>
 *
 * @author noah
 * @since 2021-11-15
 */
@Entity
@Data
@TableName("lettuce_config")
@ApiModel(value = "LettuceConfig对象", description = "KV配置表")
public class LettuceConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    @ApiModelProperty("主键：自增id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("配置编码")
    private String code;

    @ApiModelProperty("配置名称（描述）")
    private String name;

    @ApiModelProperty("配置值（json）")
    private String value;

    @ApiModelProperty("类型：1、每日一答")
    private Integer type;

    @ApiModelProperty("创建时间")
    private LocalDateTime gmtCreated;

    @ApiModelProperty("更新时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty("逻辑删除标记")
    private Integer deleteMark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(Integer deleteMark) {
        this.deleteMark = deleteMark;
    }

    @Override
    public String toString() {
        return "LettuceConfig{" +
                "id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", value=" + value +
                ", type=" + type +
                ", gmtCreated=" + gmtCreated +
                ", gmtModified=" + gmtModified +
                ", deleteMark=" + deleteMark +
                "}";
    }
}
