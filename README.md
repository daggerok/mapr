MapR practice (gradle)
======================
    # do some code
    $ gradle clean build
    $ scp mapreduce-$n/build/libs/mapreduce-$n-1.0.jar user01@maprdemo:~/mapreduce-$n.jar
    $ ssh user01@maprdemo
    $ hadoop -jar mapreduce-$n.jar ...

where n is example number

build faster. *use gradle as daemon*:
    
    # mac:
    $ touch ~/.gradle/gradle.properties && echo "org.gradle.daemon=true" > ~/.gradle/gradle.properties
    # windows:
    $ (if not exist "%HOMEPATH%/.gradle" mkdir "%HOMEPATH%/.gradle") && (echo foo >> "%HOMEPATH%/.gradle/gradle.properties")
