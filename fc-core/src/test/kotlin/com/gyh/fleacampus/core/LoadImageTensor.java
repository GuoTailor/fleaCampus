package com.gyh.fleacampus.core;

import com.gyh.fleacampus.core.service.tensorflow.TFModel;
import org.junit.jupiter.api.Test;

import java.io.File;

public class LoadImageTensor {
    @Test
    public void test() {
        File file = new File("./");
        System.out.println(file.getAbsolutePath());
        TFModel.INSTANCE.classify("F:\\新建文件夹\\IMG_0420.JPG").forEach(it -> System.out.println(it.getPorn()));
    }
}