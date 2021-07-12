package com.gyh.fleacampus.core.service

import com.gyh.fleacampus.core.mapper.DealMapper
import com.gyh.fleacampus.core.mapper.PostMapperInterface
import com.gyh.fleacampus.core.model.Deal
import com.gyh.fleacampus.core.model.view.response.DealResponse
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/7/11
 */
@Service
class DealService : PostServiceAbstract<Deal, DealResponse>() {
    @Resource
    lateinit var dealMapper: DealMapper

    override fun getMapper(): PostMapperInterface<Deal, DealResponse> {
        return dealMapper
    }

}