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
import android.widget.TextView;

import com.example.jchaos_console.Config.Config;
import com.example.jchaos_console.R;
import com.example.jchaos_console.adapter.TerminalAdapter;
import com.example.jchaos_console.bundleBean.TerminalBundle;
import com.example.jchaos_console.http.TerminalHttp;

import java.util.Set;

public class TerminalControlActivity extends AppCompatActivity {

    private Button NmapButton;
    private TextView NmapView;
    private ActivityInfo activityInfo;
    private TerminalHttp terminalHttp;
    private TextView getOsText;
    private Handler myHandler;
    private Button StartKeyLoggerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminal_control);
        Bundle bundle = this.getIntent().getExtras();
        TerminalBundle terminalBundle = (TerminalBundle) (bundle != null ? bundle.get("TerminalInfo") : null);
        init();
        if (terminalBundle != null) {
            NmapView.setText(terminalBundle.getTerminalID());
            activityInfo.terminalID = terminalBundle.getTerminalID();
        }
        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 123:

                    case Config.Result_Code_ReturnOsInfo:
                        String jsonstring = (String) msg.getData().get("osInfoString");
                        System.out.println("wdnmd："+jsonstring);
                        //getOsText.setText("wdnmd");
                        getOsText.setText(jsonstring);
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };

    }

    public void init() {

        activityInfo = new ActivityInfo();
        terminalHttp = new TerminalHttp();
        NmapButton = findViewById(R.id.NmapButton);
        NmapView = findViewById(R.id.NmapText);
        getOsText = findViewById(R.id.TerminalGetOsInfo);
        setListener();
        initView();
    }

    public void initView() {
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                /**
                 * 初始化系统信息
                 */
                String osInfoString = terminalHttp.getOsInfo(activityInfo.terminalID);
                System.out.println(osInfoString);
                Bundle bundle = new Bundle();
                bundle.putSerializable("osInfoString", osInfoString);
                Message message = new Message();
                message.setData(bundle);
                message.what = Config.Result_Code_ReturnOsInfo;
                myHandler.sendMessage(message);
            }
        }).start();
    }

    public void setListener() {
        /**
         * Nmap Button
         */
        NmapButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        terminalHttp.sendRunNmap(activityInfo.terminalID);
                    }
                }).start();

            }
        });

    }

    class ActivityInfo {
        public String terminalID;
    }
}
