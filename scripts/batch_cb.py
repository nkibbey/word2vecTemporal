#!/usr/bin/env python

import subprocess


lst = []

for i in range(0, len(lst), 2):
    value = "./batch_custb.sh " + lst[i] + " " + lst[i+1]
    subprocess.check_call(value.split())
    i = i+2
