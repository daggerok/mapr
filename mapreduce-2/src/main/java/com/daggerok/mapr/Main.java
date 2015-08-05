package com.daggerok.mapr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        if (null == args || args.length != 2) {
            System.err.println("illegal argumetns");
            System.exit(-1);
        }

        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);

        if (Files.notExists(input)) {
            System.err.println("input file wasn't found");
            System.exit(-1);
        }
        Files.deleteIfExists(output);
        // do something else...
        System.exit(0);
    }
}
