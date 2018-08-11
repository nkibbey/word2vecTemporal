#!/usr/bin/env bash

#get frequency of unique words in given texts
CORPUS=$1

cat <<EOF | sh
touch freqOut.txt
cat  ${CORPUS} | tr '[:punct:]' ' ' | tr 'A-Z' 'a-z' | tr -s ' ' | tr ' ' '\n' | sort | uniq -c | sort -rn > freqOut.txt
EOF
