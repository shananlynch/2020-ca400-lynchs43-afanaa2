import os
def runProgram(num):
    os.system('java mona Ircode_test/IrcodeTest'+ str(num))
    with os.popen('lli irFileName' ) as stream:
        output = stream.read().rstrip()
        return (output)

print (runProgram(1))
