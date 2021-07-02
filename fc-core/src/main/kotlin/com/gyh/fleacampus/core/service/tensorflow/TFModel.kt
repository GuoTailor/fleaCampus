package com.gyh.fleacampus.core.service.tensorflow

import com.gyh.fleacampus.core.service.tensorflow.model.TFModel1
import com.gyh.fleacampus.core.service.tensorflow.model.TFModel2
import com.gyh.fleacampus.core.service.tensorflow.model.TFModel3
import org.tensorflow.Graph
import org.tensorflow.Session
import org.tensorflow.op.Ops
import org.tensorflow.op.image.DecodeImage
import org.tensorflow.types.TUint8
import java.util.stream.Collectors

/**
 * Created by GYH on 2021/5/25
 */
object TFModel {
    private val prophets: MutableMap<TFPrediction, Session> = HashMap()

    init {
        loadModels()
    }

    private fun loadModels() {
        val t1 = TFModel1()
        val t2 = TFModel2()
        val t3 = TFModel3()
        prophets[t1] = t1.loadModel()
        prophets[t2] = t2.loadModel()
        prophets[t3] = t3.loadModel()
    }

    fun classify(path: String): MutableList<TFPredictionResult> {
        val graph = Graph()
        val session = Session(graph)
        val tf = Ops.create(graph)
        val fileName = tf.constant(path)
        val readFile = tf.io.readFile(fileName)
        val runner = session.runner()
        val options = DecodeImage.channels(3L)
        val decodeImage = tf.image.decodeImage(readFile.contents(), options)
        (runner.fetch(decodeImage).run()[0] as TUint8).use { outputImage ->
            return prophets.entries
                .parallelStream()
                .map { (k, v) ->
                    println(Thread.currentThread().name + System.currentTimeMillis())
                    val graph2 = Graph()
                    val session2 = Session(graph2)
                    val tf2 = Ops.create(graph2)
                    val processImage = k.processImage(reshapeTensor(outputImage), tf2)
                    session2.runner().fetch(processImage).run()[0].use { f -> k.prediction(v, f) }
                }.collect(Collectors.toList())
        }
    }

    private fun reshapeTensor(tUint8Tensor: TUint8): TUint8 {
        val tf = Ops.create()
        return tf.reshape(
            tf.constant(tUint8Tensor),
            tf.array(
                1,
                tUint8Tensor.shape().asArray()[0],
                tUint8Tensor.shape().asArray()[1],
                tUint8Tensor.shape().asArray()[2]
            )
        ).asTensor()
    }
}