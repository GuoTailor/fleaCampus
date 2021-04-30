package com.gyh.fleacampus

import ai.djl.Application
import ai.djl.modality.Classifications
import ai.djl.modality.cv.Image
import ai.djl.modality.cv.ImageFactory
import ai.djl.modality.cv.output.DetectedObjects
import ai.djl.repository.zoo.Criteria
import ai.djl.repository.zoo.ModelZoo
import ai.djl.training.util.ProgressBar
import com.gyh.fleacampus.common.LoadBirdsearchModel
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
import org.tensorflow.types.TFloat32
import org.tensorflow.types.TUint8
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel

fun main() {
    FleaCampusApplicationTests().contextLoads()
}

//@SpringBootTest
class FleaCampusApplicationTests {

    @Test
    fun contextLoads() {
        val graph = Graph()
        val parseFrom = GraphDef.parseFrom(File("E:\\IdeaProjects\\NSFW-Python\\model\\frozen_nsfw.pb").inputStream())
        graph.importGraphDef(parseFrom)
        //graph.operations().forEach { println("$it ${it.type()} ${it.numOutputs()}") }

        // load saved model
        //val model = SavedModelBundle.load("E:\\IdeaProjects\\NSFW-Python\\model", "serve")

        val session = Session(graph)
        //run(graph, session)
        val time = System.currentTimeMillis()
        run(graph, session)
        println(System.currentTimeMillis() - time)
        session.close()
        graph.close()
    }

    fun run(graph: Graph, session: Session) {
        val tf = Ops.create(graph)
        val fileName = tf.constant("C:\\Users\\GYH\\Pictures\\4.jpeg")
        val readFile = tf.io.readFile(fileName)
        var runner = session.runner()
        session.run(tf.init())
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
        
    }

    fun swap(buffer: FloatDataBuffer) {
        for (i in 0 until buffer.size() step 3) {
            buffer.setFloat(buffer.getFloat(i + 0) - 123, i + 2)
            buffer.setFloat(buffer.getFloat(i + 1) - 117, i + 1)
            buffer.setFloat(buffer.getFloat(i + 2) - 104, i + 0)
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

    @Test
    fun nmka() {

        val imageFile: Path = Paths.get("1.jpg")
        println(imageFile.fileName)
        val img: Image = ImageFactory.getInstance().fromFile(imageFile)
        println(img.height)

        val criteria: Criteria<Image, DetectedObjects> = Criteria.builder()
            .optApplication(Application.CV.OBJECT_DETECTION)
            .setTypes(Image::class.java, DetectedObjects::class.java)
            .optFilter("backbone", "resnet50")
            .optProgress(ProgressBar())
            .build()
        ModelZoo.listModels().forEach { println(it) }
        ModelZoo.loadModel<Image, DetectedObjects>(criteria).use { model ->
            model.newPredictor().use { predictor ->
                val detection = predictor.predict(img)
                println(detection)
            }
        }
    }

    @Test
    fun nmka2() {
        val imageFile: Path = Paths.get("1.jpg")
        val img: Image = ImageFactory.getInstance().fromFile(imageFile)
        val criteria = Criteria.builder()
            .setTypes(Image::class.java, Classifications::class.java) // defines input and output data type
            //.optTranslator(ImageClassificationTranslator.builder().build())
            .optModelUrls("file:///E:/AndroidStudioProjects/open_nsfw_android-dev/app/src/main/assets/") // search models in specified path
            .optModelName("nsfw") // specify model file prefix
            .build()

        ModelZoo.loadModel(criteria).use { model ->
            model.newPredictor().use { predictor ->
                val detection = predictor.predict(img)
                println(detection)
            }
        }
    }
}
