import json
import time
import shutil
from os import path
from sys import argv


class BlenderData:
    def __init__(self, input):
        self.numOfCheckers = input.get("numOfCheckers")
        self.resolution = input.get("resolution")
        self.Samples = input.get("Samples")
        self.FigureSizeMin = input.get("FigureSizeMin")
        self.FigureSizeMax = input.get("FigureSizeMax")
        self.x_min = input.get("x_min")
        self.x_max = input.get("x_max")
        self.y_min = input.get("y_min")
        self.y_max = input.get("y_max")
        self.z_min = input.get("z_min")
        self.z_max = input.get("z_max")


def hello(obj, id):

    with open("C:/Users/yaram/PycharmProjects/pythonProject5/main/" + id + "_text.json", "w") as file:
        file.write(json.dumps(obj))
    source_path = "C:/Users/yaram/PycharmProjects/pythonProject5/main/" + id + "_text.json"
    if path.exists(source_path):
        destination = "C:/Users/yaram/IdeaProjects/Checkers/src/main/webapp/WEB-INF/images/" + id
        shutil.move(source_path, destination)
    time.sleep(2)
    file.close()


script, pathMap, id = argv
with open(pathMap, "r") as fh:
    input = json.load(fh)
hello(input, id)
fh.close()

