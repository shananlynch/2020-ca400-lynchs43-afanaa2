import unittest
import IrCodeTestRun
import  pytest
class testRun(unittest.TestCase):

    def test_Ir(self):
            result = IrCodeTestRun.runProgram(1)
            self.assertEqual(result, "hello world")
if __name__ == '__main__':
    unittest.main()
