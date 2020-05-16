from monaFrontEnd import home
from monaFrontEnd import app
import unittest


class FlaskTestCase(unittest.TestCase):

    def test_response(self):
        tester = app.test_client(self)
        response = tester.get('/', content_type='html/text')
        self.assertEqual(response.status_code, 200)

    def test_PostTest(self):
        tester = app.test_client(self)
        response = tester.post('/', data=f"main{{ var int a = 2; print(a); }}")
        self.assertIn(b'2', response.data)


if __name__ == '__main__':
    unittest.main()
