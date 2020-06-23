# uncomment if gensim is installed
#!pip install gensim

import gensim
# Need the interactive Tools for Matplotlib
#%matplotlib notebook
#import matplotlib.pyplot as plt

import numpy as np

from sklearn.manifold import TSNE


# to train sentences = corpus. size (100) ranges 10-1000,[degrees of free]  inc. num -> inc. accuracy requires more data
# window (5) ranges 2-10 refers to skip gram ex. 2 a, b, target, c, d -> (target, a), (target, b)...
# min_count (5) ranges 0-100; inc. num  -> inc. min required frequency in data for vocab [remove junk]
# workers (1) -> parallelization 1 refers to none, more requires Cython
# https://radimrehurek.com/gensim/models/word2vec.html#gensim.models.word2vec.Word2Vec for full
# >>> model = Word2Vec(sentences, size=100, window=5, min_count=5, workers=4)
# >>> model.save(fname)
# >>> model = Word2Vec.load(fname)  # you can continue training with the loaded model

# load pre-trained word2vec embeddings
# The embeddings can be downloaded from command prompt:
# wget -c "https://s3.amazonaws.com/dl4j-distribution/GoogleNews-vectors-negative300.bin.gz"
#model = gensim.models.KeyedVectors.load_word2vec_format('GoogleNews-vectors-negative300.bin.gz', binary=True)

# Test the loaded word2vec model in gensim
# We will need the raw vector for a word
#print(model['computer'])

# We will also need to get the words closest to a word
#model.similar_by_word('computer')

def closestwords_jsout(model, word):

#    arr = np.empty((0,300), dtype='f')
    word_labels = [word]

    # get close words
    # add the vector for each of the closest words to the array
   # arr = np.append(arr, np.array([model[word]]), axis=0)


    close_words = model.similar_by_word(word)
    """    cus = unicode('poland')
    cus_val = model.similarity(word, 'poland')
    close_words.append((cus, cus_val))
    #repeat because off by one shenanigans
    close_words.append((cus, cus_val))
"""
    arr = np.empty((0, len(model[word])), dtype='f')
    for wrd_score in close_words:
        wrd_vector = model[wrd_score[0]]
        word_labels.append(wrd_score[0])
        arr = np.append(arr, np.array([wrd_vector]), axis=0)

    # find tsne coords for 2 dimensions
    tsne = TSNE(n_components=2, random_state=0)
    np.set_printoptions(suppress=True)
    Y = tsne.fit_transform(arr)

    x_coords = Y[:, 0]
    y_coords = Y[:, 1]

    f = open("plots.js", "w+")
    f.write("plot = [ \n")
    for label, x, y in zip(word_labels, x_coords, y_coords):

        # little json y
        # empty write to plots.js: plot = [];
        # prints {"name":"$name", "x":"$x", "y":"$y"},
        # note: trailing comma destroys internet explorer
        f.write("{ \"name\": \"%s\", " % (label))
        f.write("\"x\": \"%s\", \"y\":\"%s\" }, \n" % (x, y))

    f.write("];\n")
    f.close()

    """
    # display scatter plot
    plt.scatter(x_coords, y_coords)

    for label, x, y in zip(word_labels, x_coords, y_coords):
        plt.annotate(label, xy=(x, y), xytext=(0, 0), textcoords='offset points')
    plt.xlim(x_coords.min()+0.00005, x_coords.max()+0.00005)
    plt.ylim(y_coords.min()+0.00005, y_coords.max()+0.00005)
    plt.show()
"""
def compare_words(model, w1, w2):
    """ans = "Similarity of " + w1 + " and " + w2 + " is: " + model.similarity(w1, w2)
    if (do_save):
        f = open("sim.txt", "w+")
        f.write(ans)
        f.close()
    else:
        print ans
"""
    print(model.similarity(w1, w2))

model = gensim.models.KeyedVectors.load_word2vec_format('t.txt', binary=False)

#closestwords_jsout(model, 'parkinson')
compare_words(model, 'poland', 'parkinson')