#!/bin/bash
flg=0
inp=./input
out=./output
addr=143.248.135.20:35103
id=dba
pwd=dba
for i in "$@"
do
    if [ $flg -eq 1 ]
    then
	inp=$i
	flg=0
    elif [ $flg -eq 2 ]
    then
	out=$i
	flg=0
    elif [ $flg -eq 3 ]
    then
	addr=$i
	flg=0
    elif [ $flg -eq 4 ]
    then
	id=$i
	flg=0
    elif [ $flg -eq 5 ]
    then
	pwd=$i
	flg=0
    fi
    
    if [ -input == "$i" ]
    then
	flg=1
    elif [ -output == "$i" ]
    then
	flg=2
    elif [ -addr == "$i" ]
    then
	flg=3
    elif [ -id == "$i" ]
    then
	flg=4
    elif [ -pwd == "$i" ]
    then
	flg=5
    fi
done

echo "$inp $out $addr $id $pwd"

java -Dfile.encoding=UTF-8 -jar BAI2L2K.jar -ipaddr $inp
cd src
python entconv.py

