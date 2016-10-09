package com.example.lance.calance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lance on 2016/7/25.
 */
public class jiequ_str {

    public String jiequ_str(char[] b){
        int count_zuo = 0,count_you = 0;
        String t;
        List<String> c = new ArrayList<String>();       //利用集合List中的ArrayList来存储字符数组;
        String a = "";

        for (int i = 0; i < b.length; i++) {
            if(b[i] == '+'){
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");

            }else if(b[i] == '-'){
                if(i==0||b[i-1] == '('){
                    a += b[i];
                }else{
                    if(!a.equals("")){
                        c.add(a);
                        a = "";
                    }
                    c.add(b[i]+"");
                }
            }else if(b[i] == '*'){
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");
            }else if(b[i] == '/'){
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");
            }else if(b[i] == '='){
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");
            }else if(b[i] == '(')
            {
                if(count_you>count_zuo){    //判断，如果右括号比左括号先出现的话，则输出"ERROR";
                    return "ERROR";
                }
                count_zuo++;        //累计左括号的数量；
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");
            }else if(b[i] == ')'){
                count_you++;        //累计右括号的数量；
                if(!a.equals("")){
                    c.add(a);
                    a = "";
                }
                c.add(b[i]+"");
            }else if(b[i]=='出'){    //如果字符数组的第一个字符等于"出"，则输出"出错"；
                return "出错";
            }
            else {
                if ((b[i] == '.' && i > 0 && i < b.length-1 && (b[i+1]=='.'))||( i== 0&&b[i] == '.')
                        ||(b[i] == '.' && i > 0 && i < b.length-3 && (b[i+1]=='.'||b [i+2]=='.')) ){
                    return "ERROR";  //如果小数点连续出现或者间接出现的话，则输出"ERROR"；
                }
                a += b[i];
            }
        }
        if(count_you != count_zuo)      //如果左括号与右括号的数量不一致的话，则输出"ERROR";
        return "ERROR";

        for(int i=0;i<c.size();i++)     //在编译器上显示;
            System.out.println(c.get(i));

       t = new mid_to_end().mid_to_end(c);
        return t;
    }
}
