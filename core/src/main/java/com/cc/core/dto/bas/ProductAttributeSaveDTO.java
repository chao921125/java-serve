package com.cc.core.dto.bas;

import lombok.Data;

import java.util.List;

/**
 * 商品属性批量保存
 */
@Data
public class ProductAttributeSaveDTO {

    /** 商品ID */
    private Long productId;

    /** 属性值列表 */
    private List<Item> attributes;

    @Data
    public static class Item {
        /** 属性ID */
        private Long attributeId;

        /** 预设属性值ID */
        private Long attributeValueId;

        /** 手动输入值 */
        private String manualValue;
    }
}
