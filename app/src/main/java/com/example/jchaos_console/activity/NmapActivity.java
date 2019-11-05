package com.example.jchaos_console.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jchaos_console.R;
import com.example.jchaos_console.adapter.TerminalAdapter;
import com.example.jchaos_console.bundleBean.TerminalBundle;
import com.example.jchaos_console.http.TerminalHttp;

import java.util.Set;

public class NmapActivity extends AppCompatActivity {
    private TerminalHttp terminalHttp;
    private TextView terminalIDText;
    private ListView NmapInfosListView;
    private String terminalID;
    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nmap);

        Bundle bundle = this.getIntent().getExtras();
        TerminalBundle terminalBundle = (TerminalBundle) (bundle != null ? bundle.get("TerminalInfo") : null);

        if (terminalBundle != null) {
            terminalIDText.setText(terminalBundle.getTerminalID());
            terminalID = terminalBundle.getTerminalID();
        }
        init();
        myHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 123:


                    case 202:

                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public void init() {
        terminalIDText = findViewById(R.id.terminalIDText);
        NmapInfosListView = findViewById(R.id.listViewNmapInfos);
        terminalHttp = new TerminalHttp();

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                terminalHttp.runNmapInfo(terminalID);
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                String jsonstring = terminalHttp.getGetAndDownNmapInfo("ad14e4db");
                System.out.println("-----------------------" + jsonstring);
                Bundle bundle = new Bundle();
                bundle.putSerializable("paintData", jsonstring);
                Message message = new Message();
                message.setData(bundle);
                message.what = 123;
                myHandler.sendMessage(message);

            }
        }).start();

    }
}
