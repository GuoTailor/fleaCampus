package com.gyh.fleacampus.core.common;


import org.tensorflow.op.Ops;
import org.tensorflow.types.TUint8;


public class LoadBirdsearchModel {

    /**
     * return a 4D tensor from 3D tensor
     *
     * @param tUint8Tensor 3D tensor
     * @return 4D tensor
     */
    public static TUint8 reshapeTensor(TUint8 tUint8Tensor) {
        Ops tf = Ops.create();
        return tf.reshape(tf.constant(tUint8Tensor),
                tf.array(1,
                        tUint8Tensor.shape().asArray()[0],
                        tUint8Tensor.shape().asArray()[1],
                        tUint8Tensor.shape().asArray()[2]
                )
        ).asTensor();
    }

}
