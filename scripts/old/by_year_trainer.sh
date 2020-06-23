#!/usr/bin/env bash


STARTYEAR=1975
ENDYEAR=2017
curr_year=${STARTYEAR}

while [ ${curr_year} -le ${ENDYEAR} ]
do
    ncorpus=../corpora/y${curr_year}.txt
    nmodel=../models/model_${curr_year}
    grepped=../models/g${curr_year}

    cat ../pubmed_${curr_year}.txt | tr '[:upper:]' '[:lower:]'  | sed s/"'s"/" "/g | sed s/"\[this corrects.*"/" "/g > ${ncorpus}
    echo "corpus done for ${curr_year}"
    
    time ./word2vec -train ${ncorpus} -output ${nmodel}
    echo "training done for ${curr_year}"
    
    echo "2 100" > ${grepped}
    grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
    echo "grepping done for ${curr_year}"
    
    echo "models = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> gtest.py
    echo "print(model.similarity('gaucher', 'parkinson'))" >> gtest.py
    echo " " >> gtest.py
    echo "gtester done for ${curr_year}" 
    
    ((curr_year++))
done


