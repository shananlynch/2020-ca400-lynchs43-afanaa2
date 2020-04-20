from flask import Flask, render_template, url_for, flash, redirect
from forms import CodeCompilerForm
import os

def compileCode(code):
    output = str(code)
    output += str(os.popen('compileCodeBash').read())
    return output

app = Flask(__name__)

app.config['SECRET_KEY'] = '5791628bb0b13ce0c676dfde280ba245'


@app.route("/", methods=['GET', 'POST'])
def home():
    form = CodeCompilerForm()
    compiledCode = compileCode(form.validateCode.data)
    if form.validate_on_submit():
        flash(f'{compiledCode}!', 'success')
    return render_template('compiler.html', title='Mona Compiler', form=form)


@app.route("/documentation")
def about():
    return render_template('about.html', title='Documentation')


if __name__ == '__main__':
    app.run(debug=True)
