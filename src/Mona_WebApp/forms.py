from flask_wtf import FlaskForm
from wtforms import TextAreaField, SubmitField
from wtforms.validators import DataRequired, Length

class CodeCompilerForm(FlaskForm):
    validateCode = TextAreaField(
        'Enter Code', validators=[DataRequired(), Length(max=1000)])
    compileCode = SubmitField('Compile')
