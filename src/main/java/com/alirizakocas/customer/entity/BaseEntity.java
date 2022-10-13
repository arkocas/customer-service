package com.alirizakocas.customer.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class BaseEntity<T> {

    @Transient
    public abstract T getId();

    @Column(name = "create_date")
    @ApiModelProperty(value = "createDate field of BaseEntity object")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    @ApiModelProperty(value = "lastUpdateDate field of BaseEntity object")
    private LocalDateTime lastUpdateDate;

    @Column(name = "active")
    @ApiModelProperty(value = "active field of BaseEntity object")
    private boolean active = true;
}
