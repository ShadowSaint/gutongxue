package com.gutongxue.www.domain;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * Created by Shadow on 2016/11/15.
 */

public class Mail extends Authenticator {
    String userName = null;
    String password = null;
    public Mail() {
    }
    public Mail(String username, String password) {
        this.userName = username;
        this.password = password;
    }
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}

