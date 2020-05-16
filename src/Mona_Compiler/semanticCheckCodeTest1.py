import unittest
import semanticCheckCodeTestRun
import pytest


class testRun(unittest.TestCase):
    def __init__(self, testName, i):
        super(testRun, self).__init__(testName)
        self.i = i

    def test_SemanticChecks(self):
        result = semanticCheckCodeTestRun.runProgram(self.i)
        if self.i == 1:
            self.assertEqual(result, ["Type: unsupported operand type(s) for +:string and Integer\n", "Type: unsupported operand type(s) for +:string and Integer\n",
                                      "Type : Integer is incompatible with type : string\n", "Invalid assignment with type Integer to string\n"])
        if self.i == 2:
            self.assertEqual(
                result, ["incompatible types: Integer in element 1 != string in element 0\n", "incompatible types: Integer in element 1 != string in element 0\n"])
        if self.i == 3:
            self.assertEqual(
                result, ["Type : Integer is incompatible with type : string\n", "Type : Integer is incompatible with type : string\n", "incompatible types: Integer string\n"])
        if self.i == 4:
            self.assertEqual(result, ["Paremeter Integer is not compatible with array type string\n",
                                      "Paremeter Integer is not compatible with array type string\n"])
        if self.i == 5:
            self.assertEqual(result, [
                             "a has already been declared in scope main\n", "a has already been declared in scope main\n"])
        if self.i == 6:
            self.assertEqual(result, ["Type : Integer is incompatible with type : string\n",
                                      "Type : Integer is incompatible with type : string\n", "incompatible types: Integer string\n"])
        if self.i == 7:
            self.assertEqual(result, ["Type Array: Integer is incompatible with type Array: string\n",
                                      "Type Array: Integer is incompatible with type Array: string\n", "incompatible types: [Array, Integer] [Array, string]\n"])
        if self.i == 8:
            self.assertEqual(result, ["Type : string is incompatible with type : Integer\n",
                                      "Type : string is incompatible with type : Integer\n", "incompatible types: string Integer\n"])

        print(self.i)


if __name__ == '__main__':
    suite = unittest.TestSuite()
    for i in range(1, 9):
        suite.addTest(testRun('test_SemanticChecks', i))

    unittest.TextTestRunner(verbosity=2).run(suite)
