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
            if self.i  == 10 :
                self.assertEqual(result, "2")
            if self.i  == 11 :
                self.assertEqual(result, "2.000000")
            if self.i  == 12 :
                self.assertEqual(result, "-1.100000")
            if self.i  == 13 :
                self.assertEqual(result, "2.100000")
            if self.i  == 14 :
                self.assertEqual(result, "2.000000")
            if self.i  == 15 :
                self.assertEqual(result, "1.900000")
            if self.i  == 16 :
                self.assertEqual(result, "2.200000")
            if self.i  == 17 :
                self.assertEqual(result, "")
            if self.i  == 18 :
                self.assertEqual(result, "")
            if self.i  == 19 :
                self.assertEqual(result, "")
            if self.i  == 20 :
                self.assertEqual(result, "5")
            if self.i  == 21 :
                self.assertEqual(result, "hello")
            if self.i  == 22 :
                self.assertEqual(result, "1")
            if self.i  == 23 :
                self.assertEqual(result, "1.000000")
            if self.i  == 24 :
                self.assertEqual(result, "hi")
            if self.i  == 25 :
                self.assertEqual(result, "6")
            if self.i  == 26 :
                self.assertEqual(result, "6.000000")
            if self.i  == 27 :
                self.assertEqual(result, "if(10 == 10) is true")
            if self.i  == 28 :
                self.assertEqual(result, "if(10 < 11 )is true")
            if self.i  == 29 :
                self.assertEqual(result, "if(12 > 11 )is true")
            if self.i  == 30 :
                self.assertEqual(result, "if(12 >= 11 )is true")
            if self.i  == 31 :
                self.assertEqual(result, "if(10 <= 11 )is true")
            if self.i  == 32 :
                self.assertEqual(result, "1")
            if self.i  == 33 :
                self.assertEqual(result, "3")
            if self.i  == 34 :
                self.assertEqual(result, "0")
            if self.i  == 35 :
                result = result.split("\n")
                self.assertEqual(result, ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9'])
            if self.i  == 36 :
                self.assertEqual(result,str(len("hello")))
            if self.i  == 37 :
                self.assertEqual(result,"true")
            if self.i  == 38 :
                self.assertEqual(result,"true")
            if self.i  == 39 :
                self.assertEqual(result,"true")
            if self.i  == 40 :
                self.assertEqual(result,"h")
            if self.i  == 41 :
                self.assertEqual(result,"helloWorld")
            if self.i  == 42 :
                self.assertEqual(result,"hello")
            if self.i  == 43 :
                self.assertEqual(result,"10")
            if self.i  == 44 :
                self.assertEqual(result,"1.000000")
            if self.i  == 45 :
                result = result.split("\n")
                self.assertEqual(result,["1","2","3","4","5"])
            if self.i  == 46 :
                result = result.split("\n")
                self.assertEqual(result,["hello","hello","hello","hello","hello"])
            if self.i  == 47 :
                result = result.split("\n")
                self.assertEqual(result,["1.000000 ","2.000000 ","3.000000 ","4.000000 ","5.000000"])
            if self.i  == 48 :
                self.assertEqual(result,"hello")
            if self.i  == 49 :
                self.assertEqual(result,"10")
            if self.i  == 50 :
                self.assertEqual(result,"1.000000")
            if self.i  == 51 :
                self.assertEqual(result,"1")
            if self.i  == 52 :
                result = result.split("\n")
                self.assertEqual(result,["1","2","3","4","5"])
            if self.i  == 53 :
                result = result.split("\n")
                self.assertEqual(result,["1.000000 ","2.000000 ","3.000000 ","4.000000 ","5.000000"])
            if self.i  == 54 :
                result = result.split("\n")
                self.assertEqual(result,["1","1.000000 ","hello"])
            print(self.i);
if __name__ == '__main__':
    suite = unittest.TestSuite()
    for i in range(1,55):
        suite.addTest(testRun('test_Ir', i))

    unittest.TextTestRunner(verbosity=2).run(suite)
