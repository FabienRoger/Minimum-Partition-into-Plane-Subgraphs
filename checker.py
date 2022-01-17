from cgshop2022utils.verify import verify_coloring
from cgshop2022utils.io import read_instance, read_solution

import os

for dirname, _, filenames in os.walk("."):
    for filename in filenames:
        if filename[:3] not in ["out", "sum"]:
            print(filename.split(".")[0])
            try:
                instance = read_instance(filename)
                solution = read_solution("output" + filename.split(".")[0] + ".json")
                error, num_colors = verify_coloring(
                    instance,
                    solution["colors"],
                    expected_num_colors=solution["num_colors"],
                )
                if error is None:
                    pass
                else:
                    print("BAD COL")
            except UnicodeDecodeError:
                print("ERR")
