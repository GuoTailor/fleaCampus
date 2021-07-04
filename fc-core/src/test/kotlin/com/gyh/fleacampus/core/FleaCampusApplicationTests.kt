package com.gyh.fleacampus.core

import com.gyh.fleacampus.core.common.LoadBirdsearchModel
import org.junit.jupiter.api.Test
import org.tensorflow.Graph
import org.tensorflow.SavedModelBundle
import org.tensorflow.Session
import org.tensorflow.Tensor
import org.tensorflow.ndarray.Shape
import org.tensorflow.ndarray.buffer.FloatDataBuffer
import org.tensorflow.op.Ops
import org.tensorflow.op.image.DecodeImage
import org.tensorflow.proto.framework.GraphDef
import org.tensorflow.types.TUint8
import java.awt.image.BufferedImage
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel


//@SpringBootTest
class FleaCampusApplicationTests {

    @Test
    fun contextLoads() {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(File("E:\\IdeaProjects\\nsfw_model\\mobilenet_v2_140_224\\frozen_graph.pb").inputStream())
        graph.importGraphDef(parseFrom)
        //graph.operations().forEach { println("$it ${it.type()} ${it.numOutputs()}") }

        // load saved model
        //val model = SavedModelBundle.load("E:\\IdeaProjects\\NSFW-Python\\model", "serve")

        val session = Session(graph)
        //run(graph, session)
        var time = System.currentTimeMillis()
        run2(graph, session,"C:\\Users\\GYH\\Pictures\\2.jpg")
        println(System.currentTimeMillis() - time)
        time = System.currentTimeMillis()
        run2(graph, session, "C:\\Users\\GYH\\Pictures\\6.jpg")
        println(System.currentTimeMillis() - time)
        time = System.currentTimeMillis()
        run2(graph, session, "E:\\AndroidStudioProjects\\open_nsfw_android-dev\\app\\src\\main\\assets\\img\\aaa.png")
        println(System.currentTimeMillis() - time)
        session.close()
        graph.close()
    }

    @Test
    fun testTensor() {
        //run(graph, session)
        var time = System.currentTimeMillis()
        run("C:\\Users\\GYH\\Pictures\\2.jpg")
        println(System.currentTimeMillis() - time)
        time = System.currentTimeMillis()
        run("C:\\Users\\GYH\\Pictures\\6.jpg")
        println(System.currentTimeMillis() - time)
        time = System.currentTimeMillis()
        run("E:\\AndroidStudioProjects\\open_nsfw_android-dev\\app\\src\\main\\assets\\img\\aaa.png")
        println(System.currentTimeMillis() - time)
    }

    fun run(path: String) {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(File("E:\\IdeaProjects\\NSFW-Python\\model\\frozen_nsfw.pb").inputStream())
        graph.importGraphDef(parseFrom)
        //graph.operations().forEach { println("$it ${it.type()} ${it.numOutputs()}") }

        // load saved model
        //val model = SavedModelBundle.load("E:\\IdeaProjects\\NSFW-Python\\model", "serve")

        val session = Session(graph)
        val tf = Ops.create(graph)
        val fileName = tf.constant(path)
        val readFile = tf.io.readFile(fileName)
        var runner = session.runner()
        //session.run(tf.init())
        val options = DecodeImage.channels(3L)
        val decodeImage = tf.image.decodeImage(readFile.contents(), options)
        (runner.fetch(decodeImage).run()[0] as TUint8).use { outputImage ->
            val reshape = LoadBirdsearchModel.reshapeTensor(outputImage)

            //fresh runner for reshape
            runner = session.runner()
            session.run(tf.init())
            runner.fetch(tf.image.resizeBilinear(tf.constant(reshape), tf.constant(intArrayOf(224, 224)))).run()[0].use { f ->
                swap(f.asRawTensor().data().asFloats())
                val run = runner.feed("input", f).fetch("predictions").run()
                //toImage(run[0].asRawTensor().data().asFloats())
                run[1].use {
                    //println(it.asRawTensor().data().asFloats().getFloat(0))
                    println(it.numBytes())
                    for (i in 0 until it.asRawTensor().data().asFloats().size()) {
                        println(it.asRawTensor().data().asFloats().getFloat(i))
                    }
                }
            }
        }
        session.close()
        graph.close()
    }

    fun run3(path: String) {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(File("E:\\IdeaProjects\\nsfw_model\\mobilenet_v2_140_224\\quant_nsfw_mobilenet.pb").inputStream())
        graph.importGraphDef(parseFrom)
        //graph.operations().forEach { println("$it ${it.type()} ${it.numOutputs()}") }

        // load saved model
        //val model = SavedModelBundle.load("E:\\IdeaProjects\\NSFW-Python\\model", "serve")

        val session = Session(graph)
        val tf = Ops.create(graph)
        val fileName = tf.constant(path)
        val readFile = tf.io.readFile(fileName)
        var runner = session.runner()
        //session.run(tf.init())
        val options = DecodeImage.channels(3L)
        val decodeImage = tf.image.decodeImage(readFile.contents(), options)
        (runner.fetch(decodeImage).run()[0] as TUint8).use { outputImage ->
            val reshape = LoadBirdsearchModel.reshapeTensor(outputImage)

            //fresh runner for reshape
            runner = session.runner()
            session.run(tf.init())
            runner.fetch(tf.image.resizeBilinear(tf.constant(reshape), tf.constant(intArrayOf(224, 224)))).run()[0].use { f ->
                swap(f.asRawTensor().data().asFloats())
                val run = runner.feed("input_1", f).fetch("dense_3/Softmax").run()
                //toImage(run[0].asRawTensor().data().asFloats())
                run[1].use {
                    //println(it.asRawTensor().data().asFloats().getFloat(0))
                    println(it.numBytes())
                    for (i in 0 until it.asRawTensor().data().asFloats().size()) {
                        println(it.asRawTensor().data().asFloats().getFloat(i))
                    }
                }
            }
        }
        session.close()
        graph.close()
    }

    fun run2(graph: Graph, session: Session, path: String) {
        val graph2 = Graph()
        val session2 = Session(graph2)
        val tf = Ops.create(graph2)
        val fileName = tf.constant(path)
        val readFile = tf.io.readFile(fileName)
        var runner = session2.runner()
        //session.run(tf.init())
        val options = DecodeImage.channels(3L)
        val decodeImage = tf.image.decodeImage(readFile.contents(), options)
        (runner.fetch(decodeImage).run()[0] as TUint8).use { outputImage ->
            val reshape = LoadBirdsearchModel.reshapeTensor(outputImage)

            //fresh runner for reshape
            runner = session2.runner()
            session2.run(tf.init())
            runner.fetch(tf.math.div(tf.image.resizeBilinear(tf.constant(reshape), tf.constant(intArrayOf(224, 224))), tf.constant(255f))).run()[0].use { f ->
                //swap(f.asRawTensor().data().asFloats())
                val run = session.runner().feed("self", f).fetch("sequential/prediction/Softmax").run()
                //toImage(run[0].asRawTensor().data().asFloats())
                run[0].use {
                    //println(it.asRawTensor().data().asFloats().getFloat(0))
                    println(it.numBytes())
                    for (i in 0 until it.asRawTensor().data().asFloats().size()) {
                        println(it.asRawTensor().data().asFloats().getFloat(i))
                    }
                }
            }
        }
    }

    fun swap(buffer: FloatDataBuffer) {
        for (i in 0 until buffer.size() step 3) {
            buffer.setFloat(buffer.getFloat(i + 0) / 255, i + 0)
            buffer.setFloat(buffer.getFloat(i + 1) / 255, i + 1)
            buffer.setFloat(buffer.getFloat(i + 2) / 255, i + 2)
        }
    }

    fun toImage(buffer: FloatDataBuffer) {
        val bufferedImage = BufferedImage(224, 224, BufferedImage.TYPE_INT_RGB)
        for (i in 0 until buffer.size() step 3) {
            val rgb = 0xff000000 or (buffer.getFloat(i).toLong() shl 16) or (buffer.getFloat(i + 1).toLong() shl 8) or buffer.getFloat(i + 2).toLong()
            bufferedImage.setRGB((i / 3 % 224).toInt(), (i / 3 / 224).toInt(), rgb.toInt())
        }
        val jframe = JFrame()
        val jPanel = JLabel(ImageIcon(bufferedImage))
        jframe.add(jPanel)
        jframe.setSize(400, 300)
        jframe.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jframe.isVisible = true
    }

    @Test
    fun testTf2() {
        val imagePath = "1.jpg"
        // get path to model folder
        val modelPath = "E:/IdeaProjects/NSFW-Python/model"
        // load saved model
        val model = SavedModelBundle.load(modelPath)
        Graph().use { g ->
            Session(g).use { s ->
                val tf = Ops.create(g)
                val fileName = tf.constant(imagePath)
                val readFile = tf.io.readFile(fileName)
                var runner = s.runner()
                s.run(tf.init())
                val options = DecodeImage.channels(3L)
                val decodeImage = tf.image.decodeImage(readFile.contents(), options)
                (runner.fetch(decodeImage).run()[0] as TUint8).use { outputImage ->
                    val imageShape: Shape = outputImage.shape()
                    //dimensions of test image
                    val shapeArray = imageShape.asArray()
                    //reshape the tensor to 4D for input to model
                    val reshape = tf.reshape<TUint8>(
                        tf.constant(outputImage),
                        tf.array(
                            1,
                            outputImage.shape().asArray()[0],
                            outputImage.shape().asArray()[1],
                            outputImage.shape().asArray()[2]
                        )
                    )
                    //fresh runner for reshape
                    runner = s.runner()
                    s.run(tf.init())
                    (runner.fetch(reshape).run()[0] as TUint8).use { reshapeTensor ->
                        val feedDict: MutableMap<String, Tensor> = HashMap()
                        //The given SavedModel SignatureDef input
                        feedDict["input_tensor"] = reshapeTensor
                        //The given SavedModel MetaGraphDef key
                        val outputTensorMap = model.function("serving_default").call(feedDict)
                        outputTensorMap.forEach { t, u ->
                            println(t + " " + u)
                        }
                    }
                }
            }
        }
    }

}
