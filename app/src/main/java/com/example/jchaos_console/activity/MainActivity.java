package com.example.jchaos_console.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.jchaos_console.R;
import com.example.jchaos_console.adapter.TerminalAdapter;
import com.example.jchaos_console.http.TerminalHttp;
import com.example.jchaos_console.netty.chat.Client;
import com.example.jchaos_console.service.ControlService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import io.netty.channel.Channel;

public class MainActivity extends AppCompatActivity {
    ControlService controlService;
    private Button button;
    private EditText editText;
    private ListView listView;
    private TerminalHttp terminalHttp;
    private Handler myHandler;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
            setLisenter();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 123:
                        String jsonstring = (String) msg.getData().get("paintData");
                        System.out.println(jsonstring);
                        Set<String> set = gson.fromJson(jsonstring, Set.class);
                        if (!set.isEmpty()){
                            listView.setAdapter(new TerminalAdapter(set, MainActivity.this));
                        }


                    case 202:

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };


        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {

            }
        }).start();


    }

    public void setLisenter() {
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
//                String msg = editText.getText().toString();
//                System.out.println(msg);
//                controlService.sendMesg(132, "wdnmd");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String jsonstring = terminalHttp.getAllTerminalInfo();
                        System.out.println("-----------------------"+jsonstring);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("paintData", jsonstring);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = 123;
                        myHandler.sendMessage(message);
                    }
                }).start();

            }
        });

    }

    private void init() throws InterruptedException, IOException {
        controlService = new ControlService();
        terminalHttp = new TerminalHttp();
        gson = new Gson();
        //ServerSocket serverSocket = new ServerSocket(8080);


        // controlService.sendMesg(123, "wdnmd");
        button = findViewById(R.id.search_button);
        editText = findViewById(R.id.edit_text);
        listView = findViewById(R.id.list_item);


    }
}
