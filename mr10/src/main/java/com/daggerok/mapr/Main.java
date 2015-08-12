package com.daggerok.mapr;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        $ gradle :mr10:fatJar
        $ scp mr10/build/libs/mr10-1.0.jar user01@maprdemo:~/mr10.jar && scp mr10/src/test/resources/university.txt user01@maprdemo:~/i10
        $ ssh user01@maprdemo
        $ rm -rf stats.txt && out10* && hadoop jar j10.jar -D var1="verbal" -D var2="math" /user/user01/i10 /user/user01/out101 /user/user01/out102
        $ cat stats.txt out101/part-r-00000 out102/part-r-00000
        */
        Driver.runJob(args);
    }
}
