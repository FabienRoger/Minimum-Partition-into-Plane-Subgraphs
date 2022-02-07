import enum
import matplotlib.pyplot as plt
import os
import numpy as np

NUMBER_OF_TESTS = 225

directory = "summaries"


def get_scores(summary):
    return [int(line.split(" ")[-4]) for line in summary]


def mean(list):
    return sum(list) / len(list)


def plot_bar(data):
    height = [mean(d[1]) for d in data]
    bars = [d[0] for d in data]
    y_pos = np.arange(len(bars))

    # Create bars
    plt.barh(y_pos, height)

    plt.yticks(y_pos, bars, rotation=20)
    plt.ylabel("method")
    plt.xlabel("average score")
    # plt.subplots_adjust(left=+0.2)
    plt.subplots_adjust(bottom=0.2)

    for i, v in enumerate(height):
        plt.text(v + 2, i, f"{v:.2f}")

    # Show graphic
    plt.show()


def plot_average_scores(data):
    plot_bar(data)


def plot_number_of_bests(data):
    best_scores = [1e10] * NUMBER_OF_TESTS

    for i in range(NUMBER_OF_TESTS):
        best_scores[i] = min([d[1][i] for d in data])

    top_1s = [0] * len(data)

    for i, d in enumerate(data):
        for j, score in enumerate(d[1]):
            if score == best_scores[j]:
                top_1s[i] += 1

    height = top_1s
    bars = [d[0] for d in data]
    y_pos = np.arange(len(bars))

    # Create bars
    plt.bar(y_pos, height)

    plt.xticks(y_pos, bars, rotation=20)
    plt.xlabel("method")
    plt.ylabel("number of best score")

    plt.subplots_adjust(bottom=0.2)

    # Show graphic
    plt.show()


if __name__ == "__main__":

    data = []

    for filename in os.listdir(directory):
        f = os.path.join(directory, filename)
        # checking if it is a file
        if os.path.isfile(f):
            with open(f, "r") as file:
                summary = file.readlines()
                scores = get_scores(summary)
                print(filename.split(".")[0], len(scores))

                # Consider only complete summaries
                if len(scores) == NUMBER_OF_TESTS:
                    data.append((filename.split(".")[0], scores))

    data.sort(key=lambda d: mean(d[1]), reverse=False)
    plot_number_of_bests(data)
    # plot_average_scores(data)
