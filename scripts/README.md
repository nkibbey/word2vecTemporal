# Scripts

I divided scripts I used into this folder and old/

### in this folder 
```
batch_cb.py	    -> will generate cosine history in result log of whatever pairs in its list
batch_custb.sh  -> used by batch_cb.py to run individual comparison ex. ` ./batch_custb.sh something wild `
retrain.sh      -> was used to train the paritions that had been lemmatized already
simpcoco.sh     -> does a simple co-occurence between two parameters in abstracts ex. ' ./simpcoco.sh something wild ' 
squish_outyears.sh  -> concats all the raw pubmeds together into a grouping ex. './squish_outyears.sh 1975 1980 '
thenIFreakedIt.sh  -> gives frequencies of all words in given abstract ex. './thenIFreakedIt.sh somethingWild.txt '
```

### in old
```
bigszs.sh       -> ran models with a greater number of features
by_year_trainer -> created models for all years
equa_dist 1&2 .sh -> spaced partitions into 5 years; original just concated the 5 years and shuffled while 2nd does 2 round of shuffling corpora
greppy2.sh      -> useful for gensim if you don't want to load in
pubmedxmltotextbyyear.sh -> was original solution prior to parse/pubmedparser
uglysmaple      -> played with shuf and how sampling of partitions affects models
```