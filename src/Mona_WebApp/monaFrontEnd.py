from flask import Flask, render_template, url_for, flash, redirect
from forms import CodeCompilerForm
import os

rootDirectory = os.path.dirname(__file__)[:-11]
inputCodeFilePath = os.path.join(
    rootDirectory, "Mona_Compiler/Lexer_Test/inputCode.mona")
compilerDirectory = os.path.join(rootDirectory, "Mona_Compiler/")
checksOutputFile = os.path.join(compilerDirectory, "semanticErrors.txt")


def compileCode(code, compilerDirectory, inputCodeFilePath):
    with open(inputCodeFilePath, 'w') as file:
        file.write(str(code))
    os.chdir(compilerDirectory)
    with open(checksOutputFile, 'w') as filetowrite:
        filetowrite.write('')
    os.system("java mona Lexer_Test/inputCode.mona")
    checkFilesize = os.path.getsize(checksOutputFile)
    if checkFilesize != 0:
        with open(checksOutputFile, 'r') as filetoread:
            lines = filetoread.readlines()
            cCode = "\n".join(lines)

    else:
        cCode = os.popen("lli irFileName").read()
    print("Is this working?")
    print(cCode)
    return cCode


app = Flask(__name__)

app.config['SECRET_KEY'] = '5791628bb0b13ce0c676dfde280ba245'


@app.route("/", methods=['GET', 'POST'])
def home():
    form = CodeCompilerForm()
    compiledCode = ""
    if form.validate_on_submit():
        compiledCode = str(compileCode(form.validateCode.data,
                                       compilerDirectory, inputCodeFilePath))

    return render_template('compiler.html', title='Mona Compiler', form=form, compiledCode=compiledCode)


@app.route("/documentation")
def about():
    return render_template('about.html', title='Documentation')


if __name__ == '__main__':
    app.run(debug=True)
