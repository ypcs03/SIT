#!/bin/bash

if [ $# -eq 1 ]; then 
    if echo $1 | grep ".cpp" > /tmp/null ; then
        source=$1;
        exe=${source%*\.cpp}
    else
        exe=$1;
        source=$exe.cpp
    fi
        g++-4.9 $source -o ../exe/$exe
    if [ $? -eq 0 ]; then
        echo "Compile succeed"
        ../exe/$exe
    fi

else
   echo "Input parameter error!"
fi
