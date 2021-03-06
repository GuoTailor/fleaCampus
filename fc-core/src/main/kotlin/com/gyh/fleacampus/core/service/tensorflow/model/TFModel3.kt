package com.gyh.fleacampus.core.service.tensorflow.model

import com.gyh.fleacampus.core.service.tensorflow.TFPrediction
import com.gyh.fleacampus.core.service.tensorflow.TFPredictionResult
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.FileSystemResource
import org.tensorflow.Graph
import org.tensorflow.Operand
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.op.Ops
import org.tensorflow.proto.framework.GraphDef
import org.tensorflow.types.TUint8
import kotlin.math.max

/**
 * Created by GYH on 2021/5/25
 */
class TFModel3 : TFPrediction {
    override fun loadModel(): Session {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(FileSystemResource("./model/quant_nsfw_mobilenet.pb").inputStream)
        graph.importGraphDef(parseFrom)
        return Session(graph)
    }

    override fun processImage(operand: TUint8, tf: Ops): Operand<*> {
        return tf.math.div(
            tf.image.resizeBilinear(tf.constant(operand), tf.constant(intArrayOf(224, 224))),
            tf.constant(255f)
        )
    }

    override fun prediction(session: Session, t: Tensor): TFPredictionResult {
        val run = session.runner().feed("input_1", t).fetch("dense_3/Softmax").run()
        run[0].use {
            val asFloats = it.asRawTensor().data().asFloats()
            val hentai = asFloats.getFloat(1)
            val neutral = asFloats.getFloat(2)
            val porn = asFloats.getFloat(3)
            val sexy = asFloats.getFloat(4)
            return TFPredictionResult(max(hentai, max(porn, sexy)), neutral)
        }
    }
}