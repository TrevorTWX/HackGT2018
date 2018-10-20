from flask import Flask, render_template, request, json, send_from_directory, send_file
import csv
from Path_Finder import *

app = Flask(__name__)

PATH = '/Users/trevor/Documents/My_Files/Projects/HackGT2018/ServerSide'

@app.route('/')
def hello():
    input_string = request.args.get('input')
    return ('hello ' + input_string)

# Reroute for maps download
@app.route('/map')
def map_download():
    """
    html request should be  ..../map?map='submap_name'&file='needed_file_type'
    will return the path for download the needed type of file
    type:   PNG
            edgelist
            encoded
    """
    submap_name = request.args.get('map')
    file_type = request.args.get('file')
    if file_type == 'PNG':
        file_name = 'map.PNG'
    if file_type == 'edgelist':
        file_name == 'edgelist.csv'
    if file_type == 'encoded':
        file_name == 'encoded.csv'
    # path = 'localhost/map_files/' + submap_name + '/' + file_name
    # return (path)
    return send_file(PATH + '/static/map_files/' + submap_name + '/' + file_name, as_attachment=True)

# find the best path
@app.route('/findpath')
def findpath():
    """
    html request should be ..../findpath?map='submap_name'&start='start_point'&end='end_point'
    will return the best path
    """
    submap_name = request.args.get('map')
    start_point = request.args.get('start')
    end_point = request.args.get('end')

    edgelist_path = PATH + '/static/map_files/submap_1/edgelist.csv'
    with open(edgelist_path, 'rb') as f:
        reader = csv.reader(f)
        edgelist = list(reader)

    pre_typed = [[0, 1, 2], [0, 3, 5], [1, 2, 3], [2, 3, 10]]

    result = get_path(edgelist, int(start_point), int(end_point))

    return_result = str(result[0])
    for i in result[1:]:
        return_result = return_result + ' ' + str(i)
    return return_result


if __name__=="__main__":
    app.run()
