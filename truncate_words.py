#!/usr/bin/env python3

# Generate test_words.txt by truncating all the words in EnglishUK.txt by 70%.
# Removes all duplicates, sorts the list again and makes all words lowercase.

words = set()
with open('EnglishUK.txt', encoding='utf-8-sig') as f:
    for word in f.readlines():
        words.add(word[:int((len(word) - 1) / (1 / 0.7))].lower())

words = list(words)
words.sort()

with open('test_words.txt', 'w') as f2:
    for word in words:
        f2.write(word + '\n')
