#!/usr/bin/env bash

cdir=../corpora/

echo "import gensim" > stest.py
DATASZ=100000

root=sampy1975to1984
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y1975to1984.txt > ${ncorpus}
echo "sampling done for 1"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 1"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 1"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 1"

root=sampy1985to1990
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y1985to1990.txt > ${ncorpus}
echo "sampling done for 2"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 2"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 2"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 2"

root=sampy1991to1995
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y1991to1995.txt > ${ncorpus}
echo "sampling done for 3"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 3"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 3"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 3"

root=sampy1996to1999
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y1996to1999.txt > ${ncorpus}
echo "sampling done for 4"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 4"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 4"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 4"

root=sampy2000to2003
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2000to2003.txt > ${ncorpus}
echo "sampling done for 5"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 5"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 5"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 5"

root=sampy2004to2006
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2004to2006.txt > ${ncorpus}
echo "sampling done for 6"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 6"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 6"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 6"

root=sampy2007to2008
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2007to2008.txt > ${ncorpus}
echo "sampling done for 7"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 7"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 7"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 7"

root=sampy2009to2010
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2009to2010.txt > ${ncorpus}
echo "sampling done for 8"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 8"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 8"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 8"

root=sampy2011to2012
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2011to2012.txt > ${ncorpus}
echo "sampling done for 9"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 9"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 9"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 9"


root=sampy2013to2014
ncorpus=${cdir}${root}.txt
nmodel=../models/sm_${root}
grepped=../models/g${root}
shuf -n ${DATASZ} ${cdir}y2013to2014.txt > ${ncorpus}
echo "sampling done for 10"
time ./word2vec -train ${ncorpus} -output ${nmodel}
echo "training done for 10"
echo "2 100" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 10"
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> stest.py
echo "print(model.similarity('gaucher', 'parkinson'))" >> stest.py
echo " " >> stest.py
echo "stester done for 10"

