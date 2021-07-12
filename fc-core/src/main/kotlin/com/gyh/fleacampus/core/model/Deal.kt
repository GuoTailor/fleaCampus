package com.gyh.fleacampus.core.model

import java.math.BigDecimal
import java.util.*

/**
 * fc_deal
 * @author
 */
open class Deal(
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
    imgs: String? = null,
): Post(imgs = imgs)