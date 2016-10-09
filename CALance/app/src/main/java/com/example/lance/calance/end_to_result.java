package com.example.lance.calance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2016/7/25.
 */
public class end_to_result {

    public String end_to_result(List<String> b){

        List<String> x = new ArrayList<String>(); //创建一个新的ArrayList；

        int y = b.size();
        int z = y;
        double a;

        while(y!=0){            //将b中的元素倒入新的ArrayList x中;
            x.add(b.get(y-1));
            y--;
        }
        //开始进行将元素进行计算;
        for(int i=z-1;i>=0;i--) {
            if (x.get(i).equals("+")) {
                if(i==x.size()-1||i==x.size()-2){
                    return "ERROR";
                }
                a = Double.valueOf(x.get(i + 2)) + Double.valueOf(x.get(i + 1));
                x.remove(i + 2);
                x.remove(i + 1);
                x.set(i,a + "");
            } else if (x.get(i).equals("-")) {
                if(i==x.size()-1||i==x.size()-2){
                    return "ERROR";
                }
                a = Double.valueOf(x.get(i + 2)) - Double.valueOf(x.get(i + 1));
                x.remove(i + 2);
                x.remove(i + 1);
                x.set(i, a + "");
            } else if (x.get(i).equals("*")) {
                if(i==x.size()-1||i==x.size()-2){
                    return "ERROR";
                }
                a = Double.valueOf(x.get(i + 2)) * Double.valueOf(x.get(i + 1));
                x.remove(i + 2);
                x.remove(i + 1);
                x.set(i, a + "");
            } else if (x.get(i).equals("/")) {
                if(i==x.size()-1||i==x.size()-2){
                    return "ERROR";
                }
                a = Double.valueOf(x.get(i + 2)) / Double.valueOf(x.get(i + 1));
                x.remove(i + 2);
                x.remove(i + 1);
                x.set(i,a + "");
            } else {
                continue;
            }
        }
        if(x.size() == 0)
            return 0+"";        //如果文本内容的长度为0，则输出0;
        return Double.valueOf(x.get(0))+"";
    }
}


