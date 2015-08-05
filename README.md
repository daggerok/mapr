MapR practice (gradle)
======================
    $ git clone https://github.com/daggerok/mapr.git --depth=1
    $ cd mapr
    $ gradle clean :mr1:build
    $ java -jar mr1/build/libs/mr1-1.0.jar mr1/src/test/resources/receipts.txt build/result

to build faster *use gradle as daemon*:
    
    # mac:
    $ touch ~/.gradle/gradle.properties && echo "org.gradle.daemon=true" > ~/.gradle/gradle.properties
    # windows:
    $ (if not exist "%HOMEPATH%/.gradle" mkdir "%HOMEPATH%/.gradle") && (echo foo >> "%HOMEPATH%/.gradle/gradle.properties")
