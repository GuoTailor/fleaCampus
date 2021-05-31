package com.gyh.fleacampus.service.tensorflow

import org.tensorflow.Operand
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.op.Ops
import org.tensorflow.types.TUint8

/**
 * Created by GYH on 2021/5/25
 */
interface TFPrediction {
    fun loadModel(): Session

    fun processImage(operand: TUint8, tf: Ops): Operand<*>

    fun prediction(session: Session, t: Tensor): TFPredictionResult
}