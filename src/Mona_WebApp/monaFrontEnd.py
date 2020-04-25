from flask import Flask, render_template, url_for, flash, redirect
from forms import CodeCompilerForm
import os

rootDirectory = os.path.dirname(__file__)[:-11]
inputCodeFilePath = os.path.join(rootDirectory, "Mona_Compiler/Lexer_Test/inputCode.mona")
compilerDirectory = os.path.join(rootDirectory, "Mona_Compiler/")


def compileCode(code,compilerDirectory, inputCodeFilePath):
    with open(inputCodeFilePath, 'w') as file:
        file.write(str(code))
    os.chdir(compilerDirectory)
    os.system("./script")
    codeOutput = os.popen("java mona Lexer_Test/inputCode.mona").read()
    return str(codeOutput)


app = Flask(__name__)

app.config['SECRET_KEY'] = '5791628bb0b13ce0c676dfde280ba245'


@app.route("/", methods=['GET', 'POST'])
def home():
    form = CodeCompilerForm()
    if form.validate_on_submit():
        compiledCode = compileCode(form.validateCode.data, compilerDirectory, inputCodeFilePath)
        flash(f'{compiledCode}!', 'success')
    return render_template('compiler.html', title='Mona Compiler', form=form)


@app.route("/documentation")
def about():
    return render_template('about.html', title='Documentation')


if __name__ == '__main__':
    app.run(debug=True)
