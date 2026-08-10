package com.cc.core.entity.bas;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cc.core.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;

/**
 * 门店
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bas_store")
public class BasStore extends BaseEntity {

    /** 门店编码 */
    private String storeCode;

    /** 门店名称 */
    private String storeName;

    /** 关联仓库ID */
    private Long warehouseId;

    /** 门店地址 */
    private String address;

    /** 联系电话 */
    private String phone;

    /** 店长姓名 */
    private String managerName;

    /** 启用状态 */
    private Integer isEnabled;

    /** 开业日期 */
    private LocalDate openingDate;
}
