#!/usr/bin/env bash

STARTYEAR=1975
ENDYEAR=2017
curr_year=${STARTYEAR}

while [ ${curr_year} -le ${ENDYEAR} ]
do
  txt=../corpora/y${curr_year}.txt
  total=`grep -e "gaucher" -e "parkinson" ${txt} | wc -l`
  part=`grep -e "gaucher.*parkison\|parkinson.*gaucher" ${txt} | wc -l `

  echo "intersection = ${part}"
  echo "total = ${total} "

  python -c "print ${part}/${total}.0"
  echo "done for ${curr_year}"
  ((curr_year++))
done
