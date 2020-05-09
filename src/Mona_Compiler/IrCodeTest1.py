import unittest
import IrCodeTestRun
import  pytest

class testRun(unittest.TestCase):
    def __init__(self, testName, i):
        super(testRun, self).__init__(testName)  # calling the super class init varies for different python versions.  This works for 2.7
        self.i = i


    def test_Ir(self):
            result = IrCodeTestRun.runProgram(self.i)
            if self.i  == 1 :
                self.assertEqual(result, "hello world")
            if self.i  == 2 :
                self.assertEqual(result, "10")
            if self.i  == 3 :
                self.assertEqual(result, "10.100000")
            if self.i  == 4 :
                self.assertEqual(result, "2")


            print(self.i);
if __name__ == '__main__':
    suite = unittest.TestSuite()
    for i in range(1,5):
        suite.addTest(testRun('test_Ir', i))

    unittest.TextTestRunner(verbosity=2).run(suite)
