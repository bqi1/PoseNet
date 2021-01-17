package org.tensorflow.lite.examples.posenet;

import org.tensorflow.lite.examples.posenet.lib.Person;

public interface WorkoutIdentification {
    public String identification(Person person, Double minConfidence,Integer left,Integer right,Integer top, Integer bottom, Float widthRatio, Float heightRatio);
}
