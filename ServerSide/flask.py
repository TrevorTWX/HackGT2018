from flask import Flask, render_template, request, json

app = Flask(__name__)

@app.route('/')
def hello():
    input_string = request.args.get('input')
    return ('hello ' + input_string)

@app.route('/signUp')
def signUp():
    return render_template('signup.html')

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
