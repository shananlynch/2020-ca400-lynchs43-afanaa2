import unittest
import lexical_programs_run
import  pytest
class testRun(unittest.TestCase):
    def __init__(self, testName, i):
        super(testRun, self).__init__(testName)  # calling the super class init varies for different python versions.  This works for 2.7
        self.i = i


    def test_lexer(self):
            result = lexical_programs_run.runProgram(self.i)
            self.assertEqual(result, 'mona Parser: mona program parsed successfully.')
            print(self.i);
if __name__ == '__main__':
    suite = unittest.TestSuite()
    for i in range(1,51):
        suite.addTest(testRun('test_lexer', i))

    unittest.TextTestRunner(verbosity=2).run(suite)
