from flask import Flask, render_template, request, json

app = Flask(__name__, static_url_path='')

@app.route('/')
def hello():
    input_string = request.args.get('input')
    return ('hello ' + input_string)

# Reroute for maps download
@app.route('/map')
def map_download():
    """
    html request should be  ..../map?map='submap_name'%file='needed_file_type'
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
        file_name == 'edgelist.txt'
    if file_type == 'encoded':
        file_name == 'encoded.txt'
    path = 'localhost/map_files/' + submap_name + '/' + file_name
    return (path)

@app.route('/signUpUser', methods=['POST'])
def signUpUser():
    asd = request.json
    # print(asd)
    # user =  request.form['username'];
    # password = request.form['password'];
    # return json.dumps({'status':'OK','user':user,'pass':password});
    user = asd['username']
    password = asd['password']
    status = asd['status']
    return json.dumps({'status':status,'user':user,'pass':password})

if __name__=="__main__":
    app.run()
