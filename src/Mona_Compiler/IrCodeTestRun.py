import os
import subprocess
def runProgram(num):
    command = ('java mona Ircode_test/IrcodeTest'+ str(num))
    p = subprocess.Popen(command, shell=True, stdin=subprocess.PIPE, stderr=subprocess.STDOUT, stdout=subprocess.PIPE, close_fds=True)
    out,err = p.communicate()
    with os.popen('lli irFileName' ) as stream:
        output = stream.read().rstrip()
        return (output)
