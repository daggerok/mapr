#!/bin/bash

export CONTRIB=/opt/mapr/hadoop/hadoop-*/contrib/streaming
export STREAMINGJAR=hadoop-*-streaming.jar
export JARFILE=$CONTRIB/$STREAMINGJAR
export MAPPER=$(which cat)
export REDUCER=$(which cat)

rm -rf out11 2>>/dev/null && hadoop jar $JARFILE -input file:///etc/passwd -output out11 -mapper $MAPPER -reducer $REDUCER
