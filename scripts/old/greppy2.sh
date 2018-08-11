#!/usr/bin/env bash


t1=${1}
t2=${2}
STARTYEAR=1975
ENDYEAR=2017
curr_year=${STARTYEAR}
pyprog=custest.py

echo "import gensim" > ${pyprog}
while [ ${curr_year} -le ${ENDYEAR} ]
do
    nmodel=../models/model_${curr_year}
    grepped=../models/g${curr_year}con${t1}y${t2}


    echo "2 100" > ${grepped}
    grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
    echo "grepping done for ${curr_year}"

    echo "models = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
    echo "print(model.similarity('gaucher', 'parkinson'))" >> ${pyprog}
    echo " " >> ${pyprog}
    echo "gtester done for ${curr_year}"

    ((curr_year++))
done


