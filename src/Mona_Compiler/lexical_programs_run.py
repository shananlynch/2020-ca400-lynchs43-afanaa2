import os
def runProgram(num):
    with os.popen('java mona Lexer_Test/lexerTest'+ str(num)) as stream:
        output = stream.read()
        return (output.split('\n')[-3])
