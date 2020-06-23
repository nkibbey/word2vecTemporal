#!/usr/bin/env bash


#takes two parameters ex. squish_outyears 1970 1975 will produce y1970to1975 text file to be trained
if [ -z ${2+x} ]; then
    echo "missing the two year parameters"
else
    nfile=../corpora/y${1}to${2}.txt
    touch ${nfile}
    curr_no=${1}
    cat ../pubmed_${curr_no}.txt | tr '[:upper:]' '[:lower:]' | sed s/"'s"/" "/g | sed s/"\[this corrects.*"/" "/g > ${nfile}
    echo "did primero ${curr_no}"
    ((curr_no++))
        while [ ${curr_no} -le ${2} ]
        do
            cat ../pubmed_${curr_no}.txt | tr '[:upper:]' '[:lower:]' | sed s/"'s"/" "/g | sed s/"\[this corrects.*"/" "/g >> ${nfile}
            echo "did ${curr_no}"
	    ((curr_no++))
        done
fi
