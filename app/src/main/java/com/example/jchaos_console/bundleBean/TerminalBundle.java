package com.example.jchaos_console.bundleBean;

import java.io.Serializable;

public class TerminalBundle implements Serializable {
    private String TerminalID;

    public TerminalBundle() {
    }

    public TerminalBundle(String terminalID) {
        TerminalID = terminalID;
    }

    public String getTerminalID() {
        return TerminalID;
    }

    public void setTerminalID(String terminalID) {
        TerminalID = terminalID;
    }
}
