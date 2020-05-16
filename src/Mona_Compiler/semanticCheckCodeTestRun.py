import os
import subprocess


def runProgram(num):
    command = ('java mona MonaFilesForSemanticChecks_Test/checkTest' +
               str(num)+'.mona')
    os.system(command)
    with open('semanticErrors.txt', 'r') as stream:
        errors = stream.readlines()
        return (errors)
