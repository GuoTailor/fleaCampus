package com.gyh.fleacampus

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@MapperScan("com.gyh.fleacampus.mapper")
@EnableTransactionManagement
class FleaCampusApplication

fun main(args: Array<String>) {
    runApplication<FleaCampusApplication>(*args)

    /*val imageFile: Path = Paths.get("1.jpg")
    val img: Image = ImageFactory.getInstance().fromFile(imageFile)
    val criteria = Criteria.builder()
        .optDevice(Device.cpu())
        .setTypes(Image::class.java, Classifications::class.java) // defines input and output data type
        //.optTranslator(ImageClassificationTranslator.builder().build())
        .optModelUrls("file:///mnt/e/AndroidStudioProjects/open_nsfw_android-dev/app/src/main/assets/nsfw") // search models in specified path
        .optModelName("nsfw") // specify model file prefix
        .build()
    ModelZoo.listModels().forEach { (t, u) -> println(t.toString() + u) }
    ModelZoo.loadModel(criteria).use { model ->
        model.newPredictor().use { predictor ->
            val detection = predictor.predict(img)
            println(detection)
        }
    }*/
}
