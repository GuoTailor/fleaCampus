package com.gyh.fleacampus.socket

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by GYH on 2021/4/8
 */
class CustomLocalDateTimeSerializer : StdSerializer<LocalDateTime> {

    constructor(): this(null)

    constructor(t: Class<LocalDateTime?>?) : super(t)

    override fun serialize(value: LocalDateTime, gen: JsonGenerator, sp: SerializerProvider?) {
        val timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        gen.writeNumber(timestamp)
    }

}