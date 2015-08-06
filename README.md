MapR practice (gradle)
======================
    $ git clone https://github.com/daggerok/mapr.git --depth=1
    $ cd mapr
    $ gradle clean :mr1:jar
    $ java -jar mr1/build/libs/mr1-1.0.jar mr1/src/test/resources/receipts.txt mr1/build/result
    $ cat mr1/build/result/part-r-00000 # check job result
    
now try with hadoop (download and run mapr vm sandbox):

    $ scp mr1/build/libs/mr1-1.0.jar user01@maprdemo:~/mr1.jar
    $ scp mr1/src/test/resources/receipts.txt user01@maprdemo:~/in1
    $ ssh user01@maprdemo
    $ hadoop jar mr1.jar in1 out1 # job should be done much faster)
    $ cat out1/part-r-00000 # check result

get more information about MapR here: https://www.mapr.com/

**note1**: you must configure needed mode in build.gradle for running tasks

1. to run job locally (ie: java -jar $jarFile) you must build **fat** jar file:

        $ gradle jatFar

2. to run job on mapr cluster (ie: hadoop jar $jarFile) you can use regular jar file:

        $ gradle jar
    
**note2**: you use windows and fatJar command fails with error:
    
    $ java -jar mr1/build/libs/mr1-1.0.jar <some_args>
    SEVERE: PriviledgedActionException as:$user cause:java.io.IOException: 
    Failed to set permissions of path: \tmp\hadoop-$user\mapred\staging\$user$someNumbers\.staging to 0700

*solution*: run it on unix:) use sandbox

**note3**: to build faster **use gradle as daemon**:

*unix:*

    $ touch ~/.gradle/gradle.properties && echo "org.gradle.daemon=true" >> ~/.gradle/gradle.properties

*windows:*

    $ if not exist "%HOMEPATH%/.gradle" mkdir "%HOMEPATH%/.gradle"
    $ echo org.gradle.daemon=true >> "%HOMEPATH%/.gradle/gradle.properties"
