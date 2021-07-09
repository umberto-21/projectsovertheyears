from Bio.PDB import PDBParser
from Bio.PDB import vectors

import math
import numpy as np
import DrawOnPymol

from pymol.cgo import *


class ProteinGeometry:
    '''
    Class used to calculate all the geometric parameters on the units
    '''

    def init_pymol(self, canvas):
        '''
        Generic method used to initialize a pymol istance in order to draw the results
        :param canvas: the istance of object DrawOnPymol
        :return:
        '''
        canvas.start_pymol()
        canvas.draw_units()
        centers = self.calculate_center_of_mass()
        canvas.draw_center_of_mass(centers)

    def open_directory(self):
        '''
        Generic method used to retrieve a sorted list of the files in output directory
        :return:
        '''
        import Commands
        if not os.path.exists(Commands.output_dir):
            file_list = list()
        else:
            file_list = os.listdir(Commands.output_dir)
        return sorted(file_list)

    def calculate_center_of_mass(self):
        '''
        Calculate the center of mass for each unit
        :return: a dictionary containing the centers of mass
        '''
        import Commands
        file_list = self.open_directory()
        centers = {}
        for each in file_list:
            url = Commands.output_dir + "/" + each
            pdb_parser = PDBParser()
            unit_structure = pdb_parser.get_structure(each, url)
            total_mass = 0
            coord_mass = (0, 0, 0)
            for atom in unit_structure.get_atoms():
                x = atom.mass*atom.coord[0]
                y = atom.mass*atom.coord[1]
                z = atom.mass*atom.coord[2]
                (c_x, c_y, c_z) = coord_mass
                c_x += x
                c_y += y
                c_z += z
                coord_mass = (c_x, c_y, c_z)
                total_mass += atom.mass

            (x, y, z) = coord_mass
            x = x/total_mass
            y = y/total_mass
            z = z/total_mass
            coord_mass = (x, y, z)
            centers[each] = coord_mass

        return centers

    def calculate_distance(self, first_point, second_point):
        '''
        Calculate the distance between two point
        :param first_point: the first point
        :param second_point: the second point
        :return: an integer which is the distance calculated between the two points
        '''
        (f_x, f_y, f_z) = first_point
        (s_x, s_y, s_z) = second_point
        x = math.pow(f_x - s_x, 2)
        y = math.pow(f_y - s_y, 2)
        z = math.pow(f_z - s_z, 2)
        return math.sqrt(x + y + z)

    def center_of_mass_distances(self, centers):
        '''
        Calculate all the distances between the centers of mass
        :param centers: a dictionary containing the centers of mass
        :return: a dictionary containing tuples that represents the distances between centers of mass
        '''
        distances = {}
        for key in centers:
            distance_list = list()
            distances[key] = distance_list
            for to in centers:
                dist = self.calculate_distance(centers[key], centers[to])
                distance_list.append((dist, to))
        return distances

    def select_atom(self, id, structure):
        '''
        Isolate the atoms equal to the atom passed as argument
        :param id: the atom to be isolated
        :param structure: the structure to be changed
        :return: a list of the atom equal to the amot passed as argument
        '''
        atoms_list = list()
        for atom in structure.get_atoms():
            if atom.get_name() == id:
                atoms_list.append(atom)
        return atoms_list

    def calculate_distances(self, centers):
        '''
        Calculate the distances between each alfa carbon and the center of mass
        :param centers: the dictionary of the centers of mass
        :return: a dictionary containg lists, each list contains the distances calculated
        '''
        import Commands
        file_list = self.open_directory()
        distances_dic = {}
        for each in file_list:
            url = Commands.output_dir + "/" + each
            pdb_parser = PDBParser()
            atoms_list = self.select_atom("CA", pdb_parser.get_structure(each, url))
            com = centers[each]
            distances = list()
            for atom in atoms_list:
                d = self.calculate_distance(com, atom.get_coord())
                distances.append((atom.get_coord(), d))
            distances_dic[each] = distances
        return distances_dic

    def calculate_handedness(self, centers):
        '''
        Calculate the handedness (L or R) for each unit
        :param centers: the dictionary containing the centers of mass
        :return: return a dictionary. dict[unit] = handedness
        '''
        import Commands
        canvas = DrawOnPymol.DrawOnPymol()
        self.init_pymol(canvas)

        file_list = self.open_directory()
        handedness_dic = {}
        for each in range(0, len(file_list)):
            url = Commands.output_dir + "/" + file_list[each]
            pdb_parser = PDBParser()
            pdb_structure = pdb_parser.get_structure(each, url)
            atoms_list = self.select_atom("CA", pdb_structure)

            vector_d_0 = np.array(atoms_list[0].get_coord() - centers[file_list[each]])
            canvas.draw_vector(np.array(atoms_list[0].get_coord()), np.array(centers[file_list[each]]))

            vector_d_1 = np.array(atoms_list[1].get_coord() - centers[file_list[each]])
            canvas.draw_vector(np.array(atoms_list[1].get_coord()), np.array(centers[file_list[each]]))

            if each == len(file_list) - 1:
                head = np.array(centers[file_list[each]])
                tail = np.array(centers[file_list[each - 1]])
                vector_z = np.array(tail - head)
                vector_z = 1 - vector_z
            else:
                head = np.array(centers[file_list[each]])
                tail = np.array(centers[file_list[each + 1]])
                vector_z = np.array(tail - head)

            vector_y = np.cross(vector_z, vector_d_0)

            coord_system = np.array([vector_d_0, vector_y, vector_z]).T

            d_0_trans = np.linalg.solve(coord_system, vector_d_0)
            d_1_trans = np.linalg.solve(coord_system, vector_d_1)
            cr_product_trans = np.cross(d_0_trans, d_1_trans)
            handedness = ""
            if cr_product_trans[2] >= 0:
                handedness = "R"
            else:
                handedness = "L"
            handedness_dic[file_list[each]] = handedness

        return handedness_dic

    def calculate_twist(self, centers):
        '''
        Calculate the rotation between units
        :param centers: the dictionary containing the center of mass
        :return: a dictionary. dict[unit] = (angle, unit from)
        '''
        import Commands
        canvas = DrawOnPymol.DrawOnPymol()
        self.init_pymol(canvas)

        file_list = self.open_directory()
        t_dictionary = {}
        for each in range(1, len(file_list)):
            url = Commands.output_dir + "/" + file_list[each]
            url_pre = Commands.output_dir + "/" + file_list[each - 1]
            pdb_parser = PDBParser()
            pdb_structure = pdb_parser.get_structure(file_list[each], url)
            pdb_structure_pre = pdb_parser.get_structure(file_list[each - 1], url_pre)

            first_atom = self.select_atom("CA", pdb_structure_pre)[0].get_vector()
            second_one = vectors.Vector(centers[file_list[each-1]])
            canvas.draw_vector(first_atom, second_one, True)
            third_one = vectors.Vector(centers[file_list[each]])
            canvas.draw_vector(second_one, third_one, True)
            fourth_one = self.select_atom("CA", pdb_structure)[0].get_vector()
            canvas.draw_vector(third_one, fourth_one, True)
            canvas.draw_vector(fourth_one, first_atom, True)
            angle = vectors.calc_dihedral(first_atom, second_one, third_one, fourth_one)
            degrees = np.degrees(angle)

            t_dictionary[file_list[each]] = (degrees, file_list[each-1])

        return t_dictionary

    def calculate_angle(self, vector0, vector1):
        '''
        Calculate the angle between two vectors
        :param vector0: the first vector
        :param vector1: the second vector
        :return: the angle in degree
        '''
        norm0 = np.linalg.norm(vector0)
        norm1 = np.linalg.norm(vector1)
        tot_norm = norm0 * norm1
        dot = np.dot(vector0, vector1)
        cos = dot / tot_norm
        angle = np.arccos(cos)
        return np.degrees(angle)

    def normalize_vector(self, vector):
        '''
        Normalize a vector
        :param vector: the vector to be normalized
        :return: the normalized vector
        '''
        norm = np.linalg.norm(vector)
        if norm == 0:
            return vector
        return vector/norm

    def calculate_curvature_and_pitch(self, centers):
        '''
        Calculate the curvature and the pitch of a unit compared with the previous one
        :param centers: the dictionary containing the centers of mass
        :return: two dictionaries containing one the curvature and the other the pitch angles in degrees
        '''
        import Commands
        canvas = DrawOnPymol.DrawOnPymol()
        self.init_pymol(canvas)

        file_list = self.open_directory()
        c_dictionary = {}
        p_dictionary = {}

        if len(file_list) - 1 <= 2:
            return (c_dictionary, p_dictionary)

        for each in range(0, len(file_list) - 2):
            url = Commands.output_dir + "/" + file_list[each]
            url_next = Commands.output_dir + "/" + file_list[each + 1]
            url_nn = Commands.output_dir + "/" + file_list[each+2]
            pdb_parser = PDBParser()
            pdb_structure = pdb_parser.get_structure(file_list[each], url)
            pdb_structure_next = pdb_parser.get_structure(file_list[each + 1], url_next)
            pdb_structure_nn = pdb_parser.get_structure(file_list[each + 2], url_nn)

            # 0 - Create the plane
            com = np.array(centers[file_list[each]])
            a_c_list = self.select_atom("CA", pdb_structure)
            a_c = np.array(a_c_list[len(a_c_list)-1].get_coord())
            axis_x = self.normalize_vector(np.array(com - a_c))
            canvas.draw_vector(com, a_c, True)

            a_c_list_next = self.select_atom("CA", pdb_structure_next)
            a_c_next = np.array(a_c_list_next[len(a_c_list_next)-1].get_coord())
            axis_z = self.normalize_vector(np.array(a_c_next - a_c))
            canvas.draw_vector(a_c_next, a_c, True)

            axis_y = self.normalize_vector(np.cross(axis_x, axis_z))

            coord_system = np.array([axis_x, axis_y, axis_z]).T

            # 1 - calculate the vector which angle describe the curvature
            a_c_list_nn = self.select_atom("CA", pdb_structure_nn)
            a_c_nn = np.array(a_c_list_nn[len(a_c_list_nn)-1].get_coord())
            the_vector = self.normalize_vector(a_c_nn - a_c_next)
            canvas.draw_vector(a_c_nn, a_c_next, True)

            # 2 - transpose the vector, the axis_z and the axis_x in the new coordinate system created above
            the_vector_trans = self.normalize_vector(np.linalg.solve(coord_system, the_vector))
            axis_z_trans = self.normalize_vector(np.linalg.solve(coord_system, axis_z))
            axis_x_trans = self.normalize_vector(np.linalg.solve(coord_system, axis_x))

            # 3 - calculate the curvature and pitch angles
            curvature = self.calculate_angle(axis_z_trans, the_vector_trans)
            angle_0 = self.calculate_angle(axis_x_trans, the_vector_trans)
            angle_1 = self.calculate_angle(axis_x_trans, axis_z_trans)
            pitch = angle_0 - angle_1

            c_dictionary[file_list[each]] = (file_list[each+1], curvature, file_list[each+2])
            p_dictionary[file_list[each]] = (file_list[each+1], pitch, file_list[each+2])

        return (c_dictionary, p_dictionary)








