package com.daggerok.mapr;

import com.daggerok.mapr.cloudera.Driver;

public class Main {
    public static void main(String[] args) throws Exception {
        Driver.runJob(args);
    }
}
