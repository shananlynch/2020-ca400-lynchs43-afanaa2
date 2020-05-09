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
            if self.i  == 5 :
                    self.assertEqual(result, "0")
            if self.i  == 6 :
                    self.assertEqual(result, "4")
            if self.i  == 7 :
                    self.assertEqual(result, "1")
            if self.i  == 8 :
                    self.assertEqual(result, "0")
            if self.i  == 9 :
                    self.assertEqual(result, "8")

            print(self.i);
if __name__ == '__main__':
    suite = unittest.TestSuite()
    for i in range(1,10):
        suite.addTest(testRun('test_Ir', i))

    unittest.TextTestRunner(verbosity=2).run(suite)
