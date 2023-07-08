import bpy
import mathutils
import importlib
from bpy_extras.object_utils import world_to_camera_view

import json
import random
import argparse
import sys

from pathlib import Path
from colorsys import hsv_to_rgb


def point_camera_at(
        camera: bpy.types.Object,
        target: mathutils.Vector,
        track_axis: str = 'Z',
        up_axis: str = 'Y'
):
    vector = camera.location - target
    camera.rotation_euler = vector.to_track_quat(
        track_axis,
        up_axis
    ).to_euler()


def randomize(lst: list) -> tuple:
    return tuple((round(random.uniform(lst[0][i], lst[-1][i]), 2) for i in range(3)))


def spotRotation(
        spot: bpy.types.Object,
        target: tuple = (0, 0, 0)
):
    vector = spot.location - mathutils.Vector(target)
    spot.rotation_euler = vector.to_track_quat('Z', 'Y').to_euler()


def random_generate_squares(squares_list, squares_list_random):
    random_number = random.choice(squares_list)
    squares_list_random.append(random_number)
    squares_list.remove(random_number)


def random_generate_captured(captured_list, captured_list_random):
    random_number_captured = random.choice(captured_list)
    captured_list_random.append(random_number_captured)
    captured_list.remove(random_number_captured)


def random_generate_pieces(pieces_list, pieces_list_random):
    random_number_pieces = random.choice(pieces_list)
    pieces_list_random.append(random_number_pieces)
    pieces_list.remove(random_number_pieces)


def arr_chess_pieces(i, pieces_list_random, squares_list_random):
    bpy.data.objects[pieces_list_random[i]].location = bpy.data.objects[squares_list_random[i]].location
    x, y, z = bpy.data.objects[squares_list_random[i]].dimensions  # размер клетки
    x1, y1, z1 = bpy.data.objects[pieces_list_random[i]].dimensions  # размер фигуры
    max_dx = (x - x1) / 2
    max_dy = (y - y1) / 2

    dx = random.uniform(-max_dx, max_dx)
    dy = random.uniform(-max_dy, max_dy)
    dz = 0
    move = (bpy.data.objects[squares_list_random[i]].location.x + dx,
            bpy.data.objects[squares_list_random[i]].location.y + dy,
            bpy.data.objects[squares_list_random[i]].location.z + dz)
    bpy.data.objects[pieces_list_random[i]].location = move


def arr_chess_captured(i, pieces_list, captured_list_random, u):
    bpy.data.objects[pieces_list[i]].location = bpy.data.objects[captured_list_random[i]].location
    x, y, z = bpy.data.objects[captured_list_random[i]].dimensions  # размер клетки
    x1, y1, z1 = bpy.data.objects[pieces_list[i]].dimensions  # размер фигуры
    max_dx = u / 1000
    max_dy = u / 1000
    # max_dx = (x - x1) / u # максимальное передфижение по Ох
    # max_dy = (y - y1) / u # максимальное передфижение по Оy

    dx = random.uniform(-max_dx, max_dx)
    dy = random.uniform(-max_dy, max_dy)
    dz = 0
    move = (bpy.data.objects[captured_list_random[i]].location.x + dx,
            bpy.data.objects[captured_list_random[i]].location.y + dy,
            bpy.data.objects[captured_list_random[i]].location.z + dz)
    bpy.data.objects[pieces_list[i]].location = move


def prepare_camera(n, camera_location):
    min_camera_location = camera_location[0]
    max_camera_location = camera_location[1]

    bpy.data.objects['Camera'].location = (random.uniform(min_camera_location[0], max_camera_location[0]),
                                           random.uniform(min_camera_location[1], max_camera_location[1]),
                                           random.uniform(min_camera_location[2], max_camera_location[2]))

    point_camera_at(bpy.data.objects['Camera'], mathutils.Vector((0, 0, 0)))
    camera1 = bpy.data.cameras['Camera']
    camera1.lens = n


def main(json_path, blend_path):
    print("98")
    bpy.ops.wm.open_mainfile(filepath=blend_path)
    json_file = Path(json_path)
    pid = json_file.parent.name
    output_image_path = (json_file.parent / pid).with_suffix(".png")
    output_json_path = (json_file.parent / (pid + "_out")).with_suffix(".json")
    print(str(json_file))
    pieces_list = bpy.data.collections['pieces'].all_objects.keys()
    squares_list = bpy.data.collections['squares.black'].all_objects.keys()
    captured_list = bpy.data.collections['captured'].all_objects.keys()

    squares_list_random = []
    captured_list_random = []
    pieces_list_random = []
    with open(json_file) as file1:
        config = json.load(file1)
    viewLayer = bpy.context.view_layer
    amount = config['numOfCheckers']  # кол-во фигур на доске
    camera_location = [[0.6, 0.6, 0.7], [0.6, 0.9, 0.7]]
    n = 40
    u = 10

    NumOfLight = [[0, 3],  # POINT
                  [1, 1],  # SUN
                  [0, 3]]  # SPOT

    LightType = ["POINT", "SUN", "SPOT"]

    print("128")
    lightLocationRange = [[(-10, -10, 0),
                           (10, 10, 10)],  # POINT
                          [(-10, -10, 0),
                           (10, 10, 10)],  # SUN
                          [(0, 0, 0),
                           (10, 10, 10)]]  # SPOT

    lightColorRange = [[(0, 0, 0),
                        (360, 1, 1)],  # POINT
                       [(0, 0, 0),
                        (360, 1, 1)],  # SUN
                       [(0, 0, 0),
                        (360, 1, 1)]]  # SPOT

    lightEnergy = [[50, 200],  # POINT
                   [2, 6],  # SUN
                   [100, 500]]  # SPOT

    prepare_camera(n, camera_location)

    # цикл вызова генератора фигур на доске
    for i in range(0, amount):
        random_generate_squares(squares_list, squares_list_random)
        random_generate_pieces(pieces_list, pieces_list_random)
        arr_chess_pieces(i, pieces_list_random, squares_list_random)

    print("156")
    # цикл вызова генератора фигур вне доски
    for i in range(0, (len(bpy.data.collections['pieces'].all_objects.keys()) - amount)):
        random_generate_captured(captured_list, captured_list_random)
        arr_chess_captured(i, pieces_list, captured_list_random, u)

    numOfLights = [random.randint(*i) for i in NumOfLight]
    lightData = [[bpy.data.lights.new(
        name=LightType[j] + str(i),
        type=LightType[j])
        for i in range(numOfLights[j])] for j in range(len(LightType))]

    lightObjects = [[bpy.data.objects.new(
        name=lightData[j][i].name,
        object_data=lightData[j][i])
        for i in range(numOfLights[j])] for j in range(len(lightData))]

    print("173")
    for j in range(len(lightData)):
        for i in range(numOfLights[j]):
            viewLayer.active_layer_collection.collection.objects.link(lightObjects[j][i])
            lightObjects[j][i].location = randomize(lightLocationRange[j])
            lightData[j][i].energy = random.randint(*lightEnergy[j])
            lightData[j][i].color = hsv_to_rgb(*randomize(lightColorRange[j]))
            lightObjects[j][i].select_set(True)
            viewLayer.objects.active = lightObjects[j][i]

    print("182")
    print(config.__str__())
    for spot in lightObjects[-1]:
        spotRotation(spot)

    viewLayer.update()
    print("188")
    scene = bpy.context.scene
    samples = config["samples"]
    print(191)
    width = int(config["resolution"].split("x")[0])
    print(192)
    height = int(config["resolution"].split("x")[1])

    transparent = True
    color_mode = "RGBA"
    compression = 0
    bpy.context.scene.render.engine = 'CYCLES'
    scene.cycles.samples = samples
    scene.render.resolution_x = width
    scene.render.resolution_y = height
    scene.render.film_transparent = transparent
    scene.render.image_settings.color_mode = color_mode
    scene.render.image_settings.compression = compression
    print("204")
    scene.render.filepath = str(output_image_path)
    bpy.ops.render.render(write_still=True)
    white_positions = []
    black_positions = []
    camera = bpy.data.objects["Camera"]
    for piece in bpy.data.collections["pieces.white"].all_objects.values():
        proj = world_to_camera_view(scene, camera, piece.location)
        item = {"x": proj.x, "y": proj.y}
        white_positions.append(item)

    print("216")
    for piece in bpy.data.collections["pieces.black"].all_objects.values():
        proj = world_to_camera_view(scene, camera, piece.location)
        item = {"x": proj.x, "y": proj.y}
        black_positions.append(item)
    print("Here")
    with open(output_json_path, "w") as file:
        json.dump({"white": white_positions, "black": black_positions}, file)


if __name__ == "__main__":
    print("completing")
    importlib.reload(bpy)
    parser = argparse.ArgumentParser()
    parser.add_argument("--json", type=str, required=True, help="json")
    print("completing2")
    parser.add_argument("--blend", type=str, required=True, help="blend")
    parsed = parser.parse_args()
    print("completing3")
    main(parsed.json, parsed.blend)