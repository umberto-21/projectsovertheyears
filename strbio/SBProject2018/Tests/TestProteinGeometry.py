from unittest import TestCase
from Project2018 import ProteinGeometry


class TestProteinGeometry(TestCase):
    '''
    Class used to test the ProteinGeometry methods
    '''
    def test_calculate_distance(self):
        '''
        Unit test for the calculate_distance method
        :return:
        '''
        pg = ProteinGeometry.ProteinGeometry()
        first = (8, 2, 6)
        second = (3, 5, 7)
        distance = pg.calculate_distance(first, second)
        val = float('%.1f' % (distance))
        self.assertEquals(val, 5.9)

    def test_calculate_angle(self):
        '''
        Unit test for the calculate_angle method
        :return:
        '''
        import numpy as np
        first_v = np.array([2, 3, 5])
        second_v = np.array([1, 6, -4])
        pg = ProteinGeometry.ProteinGeometry()
        angle = pg.calculate_angle(first_v, second_v)
        self.assertEquals(angle, 90)

    def test_normalize_vector(self):
        '''
        Unit test for the normalize_vector method
        :return:
        '''
        import numpy as np
        vector = np.array([3, -4, 0])
        pg = ProteinGeometry.ProteinGeometry()
        normalized = pg.normalize_vector(vector)
        self.assertEquals(normalized[0], 0.6)
        self.assertEquals(normalized[1], -0.8)
        self.assertEquals(normalized[2], 0)
