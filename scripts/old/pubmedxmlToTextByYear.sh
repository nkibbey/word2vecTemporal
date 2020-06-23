#!/usr/bin/env bash

YEARSTART=1990
YEAREND=2000
curr_year=${YEARSTART}


:<<NAH      #takes 4 seconds per xml out to year
while [ ${curr_year} -le ${YEAREND} ] #first iteration writes files
do
    cat <<EOF | sh
touch outs${curr_year}.txt
xmlstarlet sel -t -v "//PubmedArticle[.//PubDate/Year=${curr_year}]//AbstractText" medline17n0001.xml > outs${curr_year}.txt
EOF
    ((curr_year++))
done


for curr_lib in {0875..0875}
do
    curr_year=${YEARSTART}
    while [ ${curr_year} -le ${YEAREND} ] #later iterations append files
    do
        cat <<EOF | sh
touch outs${curr_year}.txt
xmlstarlet sel -t -v "//PubmedArticle[.//PubDate/Year=${curr_year}]//AbstractText" medline17n${curr_lib}.xml >> outs${curr_year}.txt
EOF
        ((curr_year++))
    done
done
NAH

:<<NAH      #takes 5 seconds per xml to out year
while [ ${curr_year} -le ${YEAREND} ] #first iteration writes files
do
    ab_file=outs${curr_year}.txt
    curr_file=medline17n0001.xml

    sed -n '/<PubDate>/,/<\/PubDate>/p; /<PubmedArticle>/p; /<\/PubmedArticle>/p; /<AbstractText>.*<\/AbstractText>/p' ${curr_file} | sed -n '/<Year>'${curr_year}'/,/<\/PubmedArticle>/p' | sed -n '/<AbstractText>.*<\/AbstractText>/p' | sed -e 's/<AbstractText>\(.*\)<\/AbstractText>/\1/' | tr '[:upper:]' '[:lower:]' | sed s/"'s"/" "/g > ${ab_file}
    echo ${curr_year}
    ((curr_year++))
done


for curr_lib in {0002..0005}
do
    curr_year=${YEARSTART}

    while [ ${curr_year} -le ${YEAREND} ] #later iterations append files
    do
        ab_file=outs${curr_year}.txt
        curr_file=medline17n0001.xml

        sed -n '/<PubDate>/,/<\/PubDate>/p; /<PubmedArticle>/p; /<\/PubmedArticle>/p; /<AbstractText>.*<\/AbstractText>/p' ${curr_file} > temp
        sed -n '/<Year>'${curr_year}'/,/<\/PubmedArticle>/p' temp > tmp
        sed -n '/<AbstractText>.*<\/AbstractText>/p' tmp > temp
        sed -e 's/<AbstractText>\(.*\)<\/AbstractText>/\1/' temp | tr '[:upper:]' '[:lower:]' | sed s/"'s"/" "/g > ${ab_file}

        ((curr_year++))
    done
done
NAH