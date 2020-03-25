from flask_wtf import FlaskForm
from wtforms import SelectField, SubmitField
from wtforms.validators import DataRequired, Length

class CodeCompilerForm(FlaskForm):
    validateCode = SelectField(
        'Enter Code', validators=[DataRequired(), Length(min=3)])
    compileCode = SubmitField('Compile')
