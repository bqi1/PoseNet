package org.tensorflow.lite.examples.posenet;

import android.util.Log;
import android.util.Pair;

import org.tensorflow.lite.examples.posenet.lib.BodyPart;
import org.tensorflow.lite.examples.posenet.lib.KeyPoint;
import org.tensorflow.lite.examples.posenet.lib.Person;
import org.tensorflow.lite.examples.posenet.lib.Position;

import java.util.ArrayList;

public class PushupIdentification implements WorkoutIdentification {
    @Override
    public String identification(Person person, Double minConfidence,Integer left,Integer right,Integer top, Integer bottom,  Float widthRatio, Float heightRatio) {
        for (KeyPoint keyPoint: person.getKeyPoints()) {
            if (keyPoint.getScore() > minConfidence) {
                Position position = keyPoint.getPosition();
                Float adjustedX = position.getX() * widthRatio + left;
                Float adjustedY = position.getY() * heightRatio + top;
                if (keyPoint.getBodyPart().toString() == "LEFT_WRIST") {
                    return "Left wrist detected!";
                }

            }
        }
        /** This block checks if the line has 2 valid points*/
        return "Invalid position!";
    }
}
