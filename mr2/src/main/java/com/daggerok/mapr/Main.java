package com.daggerok.mapr;

import com.daggerok.mapr.wordcounter.Driver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Driver.runJob(args);
    }
}
