from pymol.cgo import *

import os
import pymol


class DrawOnPymol:
    '''
    Class used to interact with the pymol software
    '''

    counter = 0

    def start_pymol(self):
        '''
        Start a session in pymol
        :return:
        '''
        pymol.finish_launching()

    def draw_units(self):
        '''
        Draw each unit in the pymol software
        :return:
        '''
        import Commands
        file_list = sorted(os.listdir(Commands.output_dir))
        counter = 0
        for each in file_list:
            unit_name = "unit" + str(counter)
            pymol.cmd.load(Commands.output_dir + "/" + each, unit_name)
            pymol.cmd.hide("lines")
            pymol.cmd.show("cartoon")
            counter += 1

    def draw_center_of_mass(self, centers):
        '''
        Draw the center of mass of each unit
        :param centers: a dictionary containing the coordinates for each center of mass
        :return:
        '''
        for key in centers:
            com = [COLOR, 255.0, 1.0, 1.0, SPHERE] + list(centers[key]) + [0.5]
            pymol.cmd.load_cgo(com, "com_" + key)
        pymol.cmd.reset()

    def draw_distances_com(self, centers):
        '''
        Draw the distances vectors between centers of mass
        :param centers: a dictionary containing the coordinates for each center of mass
        :return:
        '''
        sortedkeys = sorted(centers)
        for each in range(0, len(sortedkeys)-1):
            coord_x0 = centers[sortedkeys[each]][0]
            coord_y0 = centers[sortedkeys[each]][1]
            coord_z0 = centers[sortedkeys[each]][2]
            selection0 = "temp" + str(each)
            pymol.cmd.pseudoatom(selection0, pos=[coord_x0, coord_y0, coord_z0], name="A")

            coord_x1 = centers[sortedkeys[each+1]][0]
            coord_y1 = centers[sortedkeys[each+1]][1]
            coord_z1 = centers[sortedkeys[each+1]][2]
            selection1 = "temp" + str(each+1)
            pymol.cmd.pseudoatom(selection1, pos=[coord_x1, coord_y1, coord_z1], name="B")

            pymol.cmd.distance(selection0 + "////A", selection1 + "////B")

        pymol.cmd.reset()

    def draw_vector(self, x, y, axis=False):
        '''
        Draw a vector between the two points x and y represented by two numpy arrays
        :param x: the first point
        :param y: the second point
        :param axis: indicate if the vector is a plane axis or not
        :return:
        '''
        selection0 = "vector" + str(self.counter)
        pymol.cmd.pseudoatom(selection0, pos=[x[0], x[1], x[2]], name="A")

        selection1 = "vector" + str(self.counter+1)
        pymol.cmd.pseudoatom(selection1, pos=[y[0], y[1], y[2]], name="B")

        if axis:
            pymol.cmd.distance("axis", selection0 + "////A", selection1 + "////B")
            pymol.cmd.set("dash_color", "blue", "axis")
        else:
            pymol.cmd.distance(selection0 + "////A", selection1 + "////B")
            pymol.cmd.set("dash_color", "red")

        pymol.cmd.set("dash_gap", 0)
        pymol.cmd.hide("labels", "")
        pymol.cmd.reset()
        self.counter += 2
