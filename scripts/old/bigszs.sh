#!/usr/bin/env bash

tester=bgtest.py
cdir=../corpora/
echo "import gensim" > ${tester}
bign=1216177
sz=200

root=big1975to1984_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y1975to1984.txt > ${ncorpus}
echo "sampling done for 1" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 1" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 1" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 1" >> bglog

##############
root=bign1985to1990_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y1985to1990.txt > ${ncorpus}
echo "sampling done for 2" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz} ##
echo "training done for 2" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 2" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 2" >> bglog

root=bign1991to1995_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y1991to1995.txt > ${ncorpus}
echo "sampling done for 3" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz} ##
echo "training done for 3" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 3" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 3" >> bglog

root=bign1996to1999_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y1996to1999.txt > ${ncorpus}
echo "sampling done for 4" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 4" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 4" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 4" >> bglog

root=bign2000to2003_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2000to2003.txt > ${ncorpus}
echo "sampling done for 5" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 5" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 5" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 5" >> bglog

root=bign2004to2006_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2004to2006.txt > ${ncorpus}
echo "sampling done for 6" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 6" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 6" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 6" >> bglog

root=bign2007to2008_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2007to2008.txt > ${ncorpus}
echo "sampling done for 7" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 7" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 7" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 7" >> bglog

root=bign2009to2010_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2009to2010.txt > ${ncorpus}
echo "sampling done for 8" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 8" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 8" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 8" >> bglog

root=bign2011to2012_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2011to2012.txt > ${ncorpus}
echo "sampling done for 9" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 9" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 9" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 9" >> bglog

root=bign2013to2014_${sz}
ncorpus=${cdir}${root}.txt
nmodel=../models/b_${root}
grepped=../models/g${root}
shuf -n ${bign} ${cdir}y2013to2014.txt > ${ncorpus}
echo "sampling done for 10" >> bglog
time ./word2vec -train ${ncorpus} -output ${nmodel} -size ${sz}
echo "training done for 10" >> bglog
echo "2 ${sz}" > ${grepped}
grep -e "^gaucher " -e  "^parkinson " ${nmodel} >> ${grepped}
echo "grepping done for 10" >> bglog
echo "model = gensim.models.KeyedVectors.load_word2vec_format('${grepped}', binary=False)" >> ${tester}
echo "print(model.similarity('gaucher', 'parkinson'))" >> ${tester}
echo " " >> ${tester}
echo "${tester} done for 10" >> bglog







