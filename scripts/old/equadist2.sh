#!/usr/bin/env bash

#modded for a milly
OUTPERTEXT=100000
STARTYEAR=1975
ENDYEAR=2014
curr_year=${STARTYEAR}
e_year=1979
tester="yrtest.py"

echo "import gensim" > ${tester}

while [ ${curr_year} -le ${ENDYEAR} ]
do
    echo "hi ${curr_year}"

    let e_year=${curr_year}+4
    ncorpus=../corpora/f${curr_year}to${e_year}.txt
    nmodel=../models/fmodel_${curr_year}to${e_year}
    grepped=../models/g${curr_year}to${e_year}
    curr_no=${curr_year}
    echo " " > tmp.txt
    for i in {1..2}
    do
        while [ ${curr_no} -le ${e_year} ]
        do
            shuf -n ${OUTPERTEXT} ../pubmed_${curr_no}.txt | tr '[:upper:]' '[:lower:]' | sed s/"'s"/" "/g | sed s/"\[this corrects.*"/" "/g >> tmp.txt
            ((curr_no++))
        done
    done
    echo "did ${curr_year} to ${e_year} squish"
    cat tmp.txt > ${ncorpus}
    rm tmp.txt
    echo "did ${curr_year} sample "

    time ./word2vec -train ${ncorpus} -output ${nmodel}
    echo "training done for ${curr_year}"

    echo "2 100" > ${grepped}
    grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
    echo "grepping done for ${curr_year}"

    echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
    echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
    echo " " >> ${tester}
    echo "${tester} done for ${curr_year}"

    let curr_year=${curr_year}+5
done

