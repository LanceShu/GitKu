package com.example.lance.calance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2016/7/25.
 */
public class mid_to_end {

    public String mid_to_end(List<String> c){
        String t;
        int x=-1,y=-1;
        List<String> a = new ArrayList<String>();	//用List中的ArrayList来存符号;
        List<String> b = new ArrayList<String>();	//用List中的ArrayList来存数字;
        System.out.println("size:"+c.size());      //在编译器上显示;
        for(int i=0;i<c.size();i++){
            if(c.get(i).equals("(")){
                x++;
                a.add('('+"");
            }else if(c.get(i).equals(")")){
                if(a.get(x).equals("(")){
                    a.remove(x);
                    x--;
                }else{
                    while(!a.get(x).equals("(")){
                        y++;
                        b.add(a.get(x));
                        a.remove(x);
                        x--;
                    }
                    a.remove(x);
                    x--;
                }
            }else if(c.get(i).equals("+")||c.get(i).equals("-")){

                if(x!=-1&&(a.get(x).equals("+")||a.get(x).equals("-")
                        ||a.get(x).equals("*")||a.get(x).equals("/"))){
                    y++;
                    b.add(a.get(x));
                    a.remove(x);
                    a.add(c.get(i));
                }else{
                    x++;
                    a.add(c.get(i));
                }
            }else if(c.get(i).equals("*")||c.get(i).equals("/")){

                if(x!=-1&&(a.get(x).equals("*")||a.get(x).equals("/"))){
                    y++;
                    b.add(a.get(x));
                    a.remove(x);
                    a.add(c.get(i));
                }else{
                    x++;
                    a.add(c.get(i));
                }
            }else if(c.get(i).equals("=")){
                while(x!=-1){
                    y++;
                    b.add(a.get(x));
                    x--;
                }
            }else{
                y++;
                b.add(c.get(i));
            }

        }
       t = new end_to_result().end_to_result(b);
        return t;
    }
}
