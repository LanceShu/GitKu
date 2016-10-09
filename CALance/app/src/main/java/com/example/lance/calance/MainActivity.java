package com.example.lance.calance;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_0;            //0按钮
    Button bt_1;            //1按钮
    Button bt_2;            //2按钮
    Button bt_3;            //3按钮
    Button bt_4;            //4按钮
    Button bt_5;            //5按钮
    Button bt_6;            //6按钮
    Button bt_7;            //7按钮
    Button bt_8;            //8按钮
    Button bt_9;            //9按钮
    Button bt_point;        //小数点按钮
    Button bt_plus;         //加号按钮
    Button bt_minus;        //减号按钮
    Button bt_multiply;     //乘号按钮
    Button bt_divide;       //除号按钮
    Button bt_equal;        //等于号按钮
    Button bt_clear;        //清除按钮
    Button bt_del;          //删除按钮
    Button bt_zuo;          //左括号按钮
    Button bt_you;          //右括号按钮
    TextView tv;            //输出文本框
    boolean clear;          //是否清空

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_0 = (Button)findViewById(R.id.but_0);    //0的实例化
        bt_1 = (Button)findViewById(R.id.but_1);    //1的实例化
        bt_2 = (Button)findViewById(R.id.but_2);    //2的实例化
        bt_3 = (Button)findViewById(R.id.but_3);    //3的实例化
        bt_4 = (Button)findViewById(R.id.but_4);    //4的实例化
        bt_5 = (Button)findViewById(R.id.but_5);    //5的实例化
        bt_6 = (Button)findViewById(R.id.but_6);    //6的实例化
        bt_7 = (Button)findViewById(R.id.but_7);    //7的实例化
        bt_8 = (Button)findViewById(R.id.but_8);    //8的实例化
        bt_9 = (Button)findViewById(R.id.but_9);    //9的实例化
        bt_point = (Button)findViewById(R.id.but_point);    //小数点的实例化
        bt_plus = (Button)findViewById(R.id.but_plus);      //加号的实例化
        bt_minus = (Button)findViewById(R.id.but_minus);    //减号的实例化
        bt_multiply = (Button)findViewById(R.id.but_multiply);  //乘号的实例化
        bt_divide = (Button)findViewById(R.id.but_divide);  //除号的实例化
        bt_clear = (Button)findViewById(R.id.but_clear);    //清除的实例化
        bt_equal = (Button)findViewById(R.id.but_equal);    //等于号的实例化
        bt_del = (Button)findViewById(R.id.but_del);        //删除的实例化
        bt_zuo = (Button)findViewById(R.id.but_zuo);        //左括号的实例化
        bt_you = (Button)findViewById(R.id.but_you);        //右括号的实例化
        tv = (TextView)findViewById(R.id.cal_input);        //文本框的实例化

        bt_0.setOnClickListener(this);      //0点击事件
        bt_1.setOnClickListener(this);      //1点击事件
        bt_2.setOnClickListener(this);      //2点击事件
        bt_3.setOnClickListener(this);      //3点击事件
        bt_4.setOnClickListener(this);      //4点击事件
        bt_5.setOnClickListener(this);      //5点击事件
        bt_6.setOnClickListener(this);      //6点击事件
        bt_7.setOnClickListener(this);      //7点击事件
        bt_8.setOnClickListener(this);      //8点击事件
        bt_9.setOnClickListener(this);      //9点击事件
        bt_point.setOnClickListener(this);  //小数点点击事件
        bt_plus.setOnClickListener(this);   //加号点击事件
        bt_minus.setOnClickListener(this);  //减号点击事件
        bt_multiply.setOnClickListener(this);   //乘号点击事件
        bt_divide.setOnClickListener(this); //除号点击事件
        bt_clear.setOnClickListener(this);  //清空点击事件
        bt_del.setOnClickListener(this);    //删除点击事件
        bt_equal.setOnClickListener(this);  //等于点击事件
        bt_zuo.setOnClickListener(this);    //左括号点击事件
        bt_you.setOnClickListener(this);    //右括号点击事件

    }

    @Override
    public void onClick(View v) {
        String str =  tv.getText().toString();  //获得文本框里的内容
        switch (v.getId())
        {
            case R.id.but_0:
            case R.id.but_1:
            case R.id.but_2:
            case R.id.but_3:
            case R.id.but_4:
            case R.id.but_5:
            case R.id.but_6:
            case R.id.but_7:
            case R.id.but_8:
            case R.id.but_9:
            case R.id.but_point:
            case R.id.but_plus:
            case R.id.but_minus:
            case R.id.but_multiply:
            case R.id.but_divide:
            case R.id.but_zuo:
            case R.id.but_you:
                if(clear)       //如果有清空信号的话，将文本内容清空；
                {
                    clear = false;
                    tv.setText("");
                    str = "";
                }
                tv.setText(str+((Button)v).getText());      //将点击到的按钮在文本框上显示出来；
                break;
            case R.id.but_clear:
                clear = false;
                tv.setText("");     //当点击清空按钮时，将文本框的内容清空；
                break;
            case R.id.but_del:
                if(clear)
                {
                    clear = false;
                    tv.setText("");
                }
                else if(str.length()>0) {   //当文本内容的长度大于0并且点击后退按钮时，执行文本内容减一；
                    tv.setText(str.substring(0, str.length()- 1));
                }
                break;
            case R.id.but_equal:
                getresult();        //当点击等于按钮时，调用getresult();函数进行运算；
                break;
        }
    }
    private void getresult()
    {
        String t;
        String a = tv.getText().toString() + "=";   // 在文本内容后强制加上一个'='号;
        if(a == null||a.equals(""))
        {
            return;
        }
        clear = true;
        Log.i("tag",a); //在编译器上显示当前的内容；
        char[] str = a.toCharArray();      //将所得的文本内容转化成字符数组；
        t = new jiequ_str().jiequ_str(str); //调用函数进行运算;
        if(t.equals("ERROR")){      //若结果为RTTOR，则输出"出错"；
            tv.setText("出错");
        }else {
            tv.setText("");         //否则，输出所得的结果；
            tv.setText(t);
        }

    }
}
