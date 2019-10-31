package com.example.jchaos_console.http;

import java.util.Map;
import java.util.Set;

public interface Httpimpl {




    String sendGetHttp(String url);

    String sendPostHtpp(String url, Map paras);

    String getAllTerminalInfo();

    String sendRunNmap(String terminalID);

    String getOsInfo(String terminalID);
}
