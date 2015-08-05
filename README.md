MapR practice (gradle)
======================
    $ git clone https://github.com/daggerok/mapr.git --depth=1
    $ cd mapr
    $ gradle clean :mr1:build
    $ java -jar mr1/build/libs/mr1-1.0.jar mr1/src/test/resources/receipts.txt mr1/build/result # run job
    $ cat mr1/build/result/part-r-00000 # check result
    
    # or on hadoop:
    $ scp mr1/build/libs/mr1-1.0.jar user01@maprdemo:~/mr1.jar
    $ scp mr1/src/test/resources/receipts.txt user01@maprdemo:~/input
    $ ssh user01@maprdemo
    $ hadoop jar mr1.jar input result # run job (should done much faster)
    $ cat result/part-r-00000 # check result

to build faster *use gradle as daemon*:
    
    # mac:
    $ touch ~/.gradle/gradle.properties && echo "org.gradle.daemon=true" > ~/.gradle/gradle.properties
    # windows:
    $ (if not exist "%HOMEPATH%/.gradle" mkdir "%HOMEPATH%/.gradle") && (echo foo >> "%HOMEPATH%/.gradle/gradle.properties")
