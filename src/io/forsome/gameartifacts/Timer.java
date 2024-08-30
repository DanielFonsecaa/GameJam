package io.forsome.gameartifacts;

import java.util.TimerTask;

public class Timer extends TimerTask {

    public volatile int timer = 60;

    public void run() {
        --timer;
    }
}
