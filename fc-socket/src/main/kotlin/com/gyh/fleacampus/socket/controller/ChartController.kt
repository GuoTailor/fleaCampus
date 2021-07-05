package com.gyh.fleacampus.socket.controller

import com.gyh.fleacampus.socket.entity.ResponseInfo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Mono

/**
 * Created by gyh on 2021/7/3
 */
@Controller
class ChartController {

    @RequestMapping("/echo")
    fun echo(@RequestParam value: String): Mono<ResponseInfo<Map<String, String>>> {
        return ResponseInfo.ok(Mono.just(mapOf("value" to value)))
    }

}