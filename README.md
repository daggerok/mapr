MapR practice (gradle)
======================
    $ git clone https://github.com/daggerok/mapr.git --depth=1
    $ cd mapr
    $ gradle clean :mr1:build
    $ java -jar mr1/build/libs/mr1-1.0.jar mr1/src/test/resources/receipts.txt mr1/build/result
    $ cat mr1/build/result/part-r-00000 # check job result
    
now try with hadoop (download and run mapr vm sandbox):

    $ scp mr1/build/libs/mr1-1.0.jar user01@maprdemo:~/mr1.jar
    $ scp mr1/src/test/resources/receipts.txt user01@maprdemo:~/in1
    $ ssh user01@maprdemo
    $ hadoop jar mr1.jar in1 out1 # job should be done much faster)
    $ cat out1/part-r-00000 # check result

to build faster **use gradle as daemon**:

*mac:*

    $ touch ~/.gradle/gradle.properties && echo "org.gradle.daemon=true" >> ~/.gradle/gradle.properties

*windows:*

    $ (if not exist "%HOMEPATH%/.gradle" mkdir "%HOMEPATH%/.gradle") && (echo foo >> "%HOMEPATH%/.gradle/gradle.properties")

get more information about MapR here: https://www.mapr.com/
