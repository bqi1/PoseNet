package org.tensorflow.lite.examples.posenet;

import android.util.Log;
import android.util.Pair;

import org.tensorflow.lite.examples.posenet.lib.BodyPart;
import org.tensorflow.lite.examples.posenet.lib.KeyPoint;
import org.tensorflow.lite.examples.posenet.lib.Person;
import org.tensorflow.lite.examples.posenet.lib.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Math;

class bodypart_position {
    Float x, y;
    String point;
    bodypart_position(String point, Float x, Float y) {
        this.point = point;
        this.x = x;
        this.y = y;
    }
}
public class PushupIdentification implements WorkoutIdentification {
    @Override
    public String identification(Person person, Double minConfidence,Integer left,Integer right,Integer top, Integer bottom,  Float widthRatio, Float heightRatio) {
        ArrayList<bodypart_position> arr=new ArrayList<bodypart_position>();
        ArrayList<String> partArr = new ArrayList<String>();
        for (KeyPoint keyPoint: person.getKeyPoints()) {
            if (keyPoint.getScore() > minConfidence) {
                Position position = keyPoint.getPosition();
                Float adjustedX = position.getX() * widthRatio + left;
                Float adjustedY = position.getY() * heightRatio + top;
                arr.add(new bodypart_position(keyPoint.getBodyPart().toString(),adjustedX,adjustedY));
                partArr.add(keyPoint.getBodyPart().toString());
//                if (keyPoint.getBodyPart().toString() == "LEFT_WRIST") {
//                    return "Left wrist detected!";
//                }
            }
        }
        boolean Down = false;
        if (partArr.contains("LEFT_WRIST") && partArr.contains("LEFT_ELBOW") && partArr.contains("LEFT_SHOULDER")){
            double wrist_x=0.0;
            double wrist_y=0.0;
            double elbow_x=0.0;
            double elbow_y=0.0;
            double shoulder_x=0.0;
            double shoulder_y = 0.00;
            for(bodypart_position bp: arr){
                if (bp.point=="LEFT_WRIST"){
                    wrist_x = bp.x;
                    wrist_y = bp.y;
                }
                if (bp.point=="LEFT_ELBOW"){
                    elbow_x = bp.x;
                    elbow_y = bp.y;
                }
                if (bp.point=="LEFT_SHOULDER"){
                    shoulder_x = bp.x;
                    shoulder_y = bp.y;
                }
            }
            if(wrist_y >= elbow_y){
                float w_to_e_x, w_to_e_y, e_to_s_x, e_to_s_y;
                w_to_e_x = (float) Math.abs(wrist_x-elbow_x);
                w_to_e_y = (float) Math.abs(wrist_y-elbow_y);
                e_to_s_x = (float) Math.abs(elbow_x-shoulder_x);
                e_to_s_y = (float) Math.abs(elbow_y-shoulder_y);
                Float dot_product = w_to_e_x * e_to_s_x + w_to_e_y * e_to_s_y;
                double magnitudes = Math.sqrt(Math.pow(w_to_e_x,2) + Math.pow(w_to_e_y,2));
                magnitudes *= (Math.sqrt(Math.pow(e_to_s_x,2) + Math.pow(e_to_s_y,2)));
                double result = Math.toDegrees(Math.acos(dot_product/magnitudes));
                if(result>30 &&result<100){
                    Down = true;
                }

            }

        }
        if (partArr.contains("RIGHT_WRIST") && partArr.contains("RIGHT_ELBOW") && partArr.contains("RIGHT_SHOULDER")){
            double wrist_x = 0.0;
            double wrist_y=0.0;
            double elbow_x=0.0;
            double elbow_y=0.0;
            double shoulder_x=0.0;
            double shoulder_y=0.0;
            for(bodypart_position bp: arr){
                if (bp.point=="RIGHT_WRIST"){
                    wrist_x = bp.x;
                    wrist_y = bp.y;
                }
                if (bp.point=="RIGHT_ELBOW"){
                    elbow_x = bp.x;
                    elbow_y = bp.y;
                }
                if (bp.point=="RIGHT_SHOULDER"){
                    shoulder_x = bp.x;
                    shoulder_y = bp.y;
                }
            }
            if(wrist_y >= elbow_y){
                float w_to_e_x, w_to_e_y, e_to_s_x, e_to_s_y;
                w_to_e_x = (float) Math.abs(wrist_x-elbow_x);
                w_to_e_y = (float) Math.abs(wrist_y-elbow_y);
                e_to_s_x = (float) Math.abs(elbow_x-shoulder_x);
                e_to_s_y = (float) Math.abs(elbow_y-shoulder_y);
                Float dot_product = w_to_e_x * e_to_s_x + w_to_e_y * e_to_s_y;
                double magnitudes = Math.sqrt(Math.pow(w_to_e_x,2) + Math.pow(w_to_e_y,2));
                magnitudes *= (Math.sqrt(Math.pow(e_to_s_x,2) + Math.pow(e_to_s_y,2)));
                double result = Math.toDegrees(Math.acos(dot_product/magnitudes));
                if(result>30 &&result<100){
                    Down = true;
                }

            }
        }
        if(Down){
            return "Downward pushup!!!";
        }else{
            return "...";
        }


    }
}
