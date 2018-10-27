from flask import Flask, render_template, request, json, send_from_directory, send_file
import csv
from Path_Finder import *

app = Flask(__name__)

PATH = '/Users/trevor/Documents/My_Files/Projects/HackGT2018/ServerSide'
# PATH = '/home/trevor/projects/HackGT2018/ServerSide'

@app.route('/')
def hello():
    input_string = request.args.get('input')
    return ('hello ' + input_string)

# Reroute for maps download
@app.route('/map')
def map_download():
    """
    html request should be  ..../map?map='submap_name'&file='needed_file_type&attach='boolean''
    will return the path for download the needed type of file
    type:   PNG
            edgelist
            encoded
    attach will decide return as a file or direct output
    """
    submap_name = request.args.get('map')
    file_type = request.args.get('file')
    attached = request.args.get('attach')
    if file_type == 'PNG':
        file_name = 'map.PNG'
    if file_type == 'edgelist':
        file_name = 'edgelist.csv'
    if file_type == 'encoded':
        file_name = 'encoded.csv'
    # path = 'localhost/map_files/' + submap_name + '/' + file_name
    # return (path)
    return send_file(PATH + '/static/map_files/' + submap_name + '/' + file_name, as_attachment=attached)

# find the best path
@app.route('/findpath')
def findpath():
    """
    html request should be ..../findpath?map='submap_name'&level='level'&start='start_point'&end='end_point'
    will return the best path
    """
    submap_name = request.args.get('map')
    start_point = request.args.get('start')
    end_point = request.args.get('end')
    level = request.args.get('level')

    # edgelist_path = PATH + '/static/map_files/' + submap_name + '/edgelist.csv'
    map_path = PATH + '/static/map_files/' + submap_name + '/' + level + '/withpath.PNG'

    map = getMatrix(map_path)

    # with open(edgelist_path, 'rb') as f:
    #     reader = csv.reader(f)
    #     edgelist = list(reader)
    #
    # pre_typed = [[0, 1, 2], [0, 3, 5], [1, 2, 3], [2, 3, 10]]
    #
    # # print 'the edgelist is ',edgelist

    # result = get_path(edgelist, int(start_point), int(end_point))
    result = get_path(map, int(start_point), int(end_point))
    print result

    # print 'result is: ',result
    return_result = str(result[0])
    for i in result[1:]:
        return_result = return_result + ' ' + str(i)
    return return_result

@app.route('/getAdaptors')
def getAdaptors():
    """
    html request should be ..../getAdaptors?map='submap_name'
    11111111,name\
    """
    submap_name = request.args.get('map')
    path = PATH + "/static/map_files/" + submap_name + "/encoded.csv"
    with open(path, 'rb') as f:
        reader = csv.reader(f)
        encoded_list = list(reader)

    result = ""
    for row in encoded_list:
        result = result + row[0] + "," + row[1].replace("'","") + "/"
    return result

@app.route('/highlightpath')
def highlightpath():
    """
    html request should be ..../highlightpath?map='submap_name'&start='start_point'&end='end_point'&attach='boolean'
    will return the best path
    """
    submap_name = request.args.get('map')
    start_point = request.args.get('start')
    end_point = request.args.get('end')
    attached = request.args.get('attach')
    print submap_name, start_point, end_point, attached

    edgelist_path = PATH + '/static/map_files/' + submap_name + '/edgelist.csv'
    with open(edgelist_path, 'rb') as f:
        reader = csv.reader(f)
        edgelist = list(reader)

    pre_typed = [[0, 1, 2], [0, 3, 5], [1, 2, 3], [2, 3, 10]]

    # print 'the edgelist is ',edgelist
    result = get_path(edgelist, int(start_point), int(end_point))
    # print 'result is: ',result
    source = PATH + '/static/map_files/' + submap_name + '/map.PNG'
    target = PATH + '/static/map_files/' + submap_name + '/map_1.PNG'
    # copy file
    shutil.copyfile(source,target)
    # add line
    img = Image.open(target)
    pre_x = result[0]/10000
    pre_y = result[0]-result[0]/10000*10000
    for item in result[1:]:
        cur_x = item/10000
        cur_y = item - item/10000*10000
        k1 = (cur_y-pre_y)/(cur_x-pre_x+0.0)
        k2 = (cur_x-pre_x+0.0)/(cur_y-pre_y)
        print pre_x,pre_y,cur_x,cur_y,k1,k2
        if (cur_y-pre_y)*(cur_y-pre_y)<(cur_x-pre_x)*(cur_x-pre_x):
            if pre_x < cur_x:
                j = pre_y
                for i in range(pre_x,cur_x+1,5):
                    j = j+k1*5
                    img = draw(img,i,j)
            else:
                j = cur_y
                for i in range(cur_x,pre_x+1,5):
                    j = j+k1*5
                    img = draw(img,i,j)
        else:
            if pre_y < cur_y:
                j = pre_x
                for i in range(pre_y,cur_y+1,5):
                    j = j+k2*5
                    img = draw(img,j,i)
            else:
                j = cur_x
                for i in range(cur_y,pre_y+1,5):
                    j = j+k2*5
                    img = draw(img,j,i)
        pre_x = cur_x
        pre_y = cur_y
    img = img.convert("RGB")
    img.save(target)
    return send_file(target, as_attachment=attached)


if __name__=="__main__":
    #app.run(host='0.0.0.0',port=80)
    app.run()
    # app.run(host='136.59.238.149',port=80)
