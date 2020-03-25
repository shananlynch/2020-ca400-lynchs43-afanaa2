from flask import Flask, render_template, url_for
from forms import CodeCompilerForm
app = Flask(__name__)

posts = [
    {
        'author': 'Corey Schafer',
        'title': 'Blog Post 1',
        'content': 'First post content',
        'date_posted': 'April 20, 2018'
    },
    {
        'author': 'Jane Doe',
        'title': 'Blog Post 2',
        'content': 'First post content',
        'date_posted': 'April 21, 2018'
    }
]

@app.route("/")
@app.route("/home")
def home():
    return render_template('home.html', posts=posts)

@app.route("/about")
def about():
    return render_template('about.html',title='About')

@app.route("/compiler")
def compiler():
    form = CodeCompilerForm()
    return render_template('compiler.html',title='Mona Compiler', form=form)



if __name__ == '__main__':
    app.run(debug=True)