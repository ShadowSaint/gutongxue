package com.gutongxue.www.utilities;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Shadow on 2016/11/12.
 */
@Component
public class TaskUtil {
    @Async
    public void task(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
