package com.kyuwankim.android.threadbasic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    Button btn_start, btn_stop;

    //핸들러 메시지에 담겨오는 what 에 대한 정의
    public static final int SET_TEXT = 100;

    Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case SET_TEXT:
                    tv.setText(msg.arg1 + "");
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.textview);
        btn_start = (Button) findViewById(R.id.button);
        btn_stop = (Button) findViewById(R.id.button3);

        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);

        /*
        Thread cus = new CustomThread();
        Thread cus2 = new CustomThread2();

        cus2.start();
        cus.start();
*/


    }

    boolean flag = false;

    public void runProgram() {
        int i = 0;
        while (flag) {
            i++;
            if (i % 100 == 0)
                tv.setText((i / 100) + "");
        }

    }

    public void stopProgram() {
        flag = false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:
                if (flag == true) {
                    Toast.makeText(MainActivity.this, "실행중", Toast.LENGTH_SHORT).show();
                } else {
                    Thread cus = new CustomThread();
                    flag = true;
                    cus.start();
                }


                break;

            case R.id.button3:
                stopProgram();
                break;
        }

    }


    class CustomThread extends Thread {

        @Override
        public void run() {
            int sec = 0;
            // 스레드 안에서 무한반복할때는
            // 스레드를 중단시킬 수 있는 키값을 꼭 세팅해서
            // 메일 스레드가 종료시에 같이 종료될 수 있도록 해야한다
            // 왜 : 경우에따라 interrupt로 스레드가 종료되지 않을 수 있기 때문에
            int i = 0;
            while (flag) {
                i++;

                Message msg = new Message();
                msg.what = SET_TEXT;
                msg.arg1 = sec;

                handler.sendMessage(msg);
                sec++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }


    }

    @Override
    protected void onDestroy() {
        flag = false;
    }
}
