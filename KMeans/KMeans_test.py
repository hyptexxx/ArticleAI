import numpy as np
from sklearn.datasets import make_blobs
import matplotlib.pyplot as plt
from ArticleAI.KMeans.KMeans_program import KMean

X, y = make_blobs(centers=3, n_samples=500, n_features=2, shuffle=True, random_state=48)
print(X.shape)

clusters = len(np.unique(y))
print(clusters)

y_pred = KMean().predict(X)
