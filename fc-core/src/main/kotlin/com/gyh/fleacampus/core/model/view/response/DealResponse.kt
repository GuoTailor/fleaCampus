package com.gyh.fleacampus.core.model.view.response

import java.math.BigDecimal

/**
 * Created by gyh on 2021/7/11
 */
class DealResponse (
    /**
     * 价格
     */
    var price: BigDecimal? = null,

    /**
     * 原价
     */
    var originalPrice: BigDecimal? = null,

    /**
     * 想要
     */
    var want: Int? = null,
): PostResponse()