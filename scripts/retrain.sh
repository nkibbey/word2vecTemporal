#!/usr/bin/env bash

#echo "doing 75 to 84"
#time ../w2v/word2vec -train ../parse/corporacleaner/lem_y1975to1984.txt -output ../../models/lem_m_1975_1984

#echo "doing 85 to 90"
#time ../w2v/word2vec -train ../parse/corporacleaner/lem_y1985to1990.txt -output ../../models/lem_m_1985_1990

echo "doing 91 to 95"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y1991to1995.txt -output ../../models/lem_m_1991_1995

echo "doing 96 to 99"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y1996to1999.txt -output ../../models/lem_m_1996_1999

echo "doing 00 to 03"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2000to2003.txt -output ../../models/lem_m_2000_2003

echo "doing 04 to 06"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2004to2006.txt -output ../../models/lem_m_2004_2006

echo "doing 07 to 08"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2007to2008.txt -output ../../models/lem_m_2007_2008

echo "doing 09 to 10"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2009to2010.txt -output ../../models/lem_m_2009_2010

echo "doing 11 to 12"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2011to2012.txt -output ../../models/lem_m_2011_2012

echo "doing 13 to 14"
time ../w2v/word2vec -train ../parse/corporacleaner/lem_y2013to2014.txt -output ../../models/lem_m_2013_2014

