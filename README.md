MapR practice (gradle)
======================
    # do some code
    $ gradle clean build
    $ scp mapreduce-$n/build/libs/mapreduce-$n-1.0.jar user01@maprdemo:~/mapreduce-$n.jar
    $ ssh user01@maprdemo
    $ hadoop -jar mapreduce-$n.jar ...

where n is example number
