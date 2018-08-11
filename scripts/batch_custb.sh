#!/usr/bin/env bash

t1=${1}
t2=${2}
pyprog=custest.py

echo "import gensim" > ${pyprog}

year_label=1975_1984
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=1985_1990
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=1991_1995
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=1996_1999
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"



year_label=2000_2003
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=2004_2006
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=2007_2008
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=2009_2010
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"



year_label=2011_2012
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


year_label=2013_2014
nmodel=../../models/model_${year_label}
grepped=../../models/g${year_label}con${t1}y${t2}
#--------------GREP MODEL
echo "2 100" > ${grepped}
grep -e "^${t1} " -e  "^${t2} " ${nmodel} >> ${grepped}
echo "grepping done for ${year_label}"
#--------------PREP PYTHON
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${pyprog}
echo "print(model.similarity('${t1}', '${t2}'))" >> ${pyprog}
echo " " >> ${pyprog}
echo "${pyprog} done for ${year_label}"


echo "${t1}<>${t2}" >> resultlog.txt
python ${pyprog} >> resultlog.txt
