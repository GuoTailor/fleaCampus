package com.gyh.fleacampus.service.tensorflow.model

import com.gyh.fleacampus.service.tensorflow.TFPrediction
import com.gyh.fleacampus.service.tensorflow.TFPredictionResult
import org.springframework.core.io.ClassPathResource
import org.tensorflow.Graph
import org.tensorflow.Operand
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.ndarray.buffer.FloatDataBuffer
import org.tensorflow.op.Ops
import org.tensorflow.proto.framework.GraphDef
import org.tensorflow.types.TUint8

/**
 * Created by GYH on 2021/5/25
 */
class TFModel2 : TFPrediction {
    override fun loadModel(): Session {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(ClassPathResource("model/frozen_nsfw.pb").inputStream)
        graph.importGraphDef(parseFrom)
        return Session(graph)
    }

    override fun processImage(operand: TUint8, tf: Ops): Operand<*> {
        return tf.image.resizeBilinear(tf.constant(operand), tf.constant(intArrayOf(224, 224)))
    }

    override fun prediction(session: Session, t: Tensor): TFPredictionResult {
        swap(t.asRawTensor().data().asFloats())
        val run = session.runner().feed("input", t).fetch("predictions").run()
        run[0].use {
            val asFloats = it.asRawTensor().data().asFloats()
            return TFPredictionResult(asFloats.getFloat(1), asFloats.getFloat(0))
        }
    }

    fun swap(buffer: FloatDataBuffer) {
        for (i in 0 until buffer.size() step 3) {
            buffer.setFloat(buffer.getFloat(i + 0) - 123, i + 2)
            buffer.setFloat(buffer.getFloat(i + 1) - 117, i + 1)
            buffer.setFloat(buffer.getFloat(i + 2) - 104, i + 0)
        }
    }
}