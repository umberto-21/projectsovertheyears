from RDBModule import RDBParser
from Bio.PDB import PDBParser
from Bio.PDB import PDBIO
from UnitSelection import UnitSelection

import json
import click
import DrawOnPymol
import ProteinGeometry

canvas = DrawOnPymol.DrawOnPymol()
pg = ProteinGeometry.ProteinGeometry()
output_dir = ""


@click.group()
def trsolenoidsgeo():
    '''
    Show all the commands
    '''
    pass


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def centerofmass(set_json, pdb, rdb, output):
    '''
    Calculate the center of mass for each unit
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    if set_json:
        print json.dumps(centers, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in centers:
            print "Unit: " + key + " COM: " + str(centers[key])
            print ""


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def distances_com(set_json, pdb, rdb, output):
    '''
    Calculate all the distances between the centers of mass
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    distances = pg.center_of_mass_distances(centers)
    if set_json:
        print json.dumps(distances, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in distances:
            print key + " COM is distant: "
            print ""
            for each in distances[key]:
                print str(each[0]) + " from unit " + str(each[1]) + " COM"
                print ""
            print ""


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def distances_ca(set_json, pdb, rdb, output):
    '''
    Calculate the distances between each alfa carbon and the center of mass
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    distances = pg.calculate_distances(centers)
    if set_json:
        print json.dumps(distances, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in distances:
            print "The distances from " + key + " COM are: "
            print ""
            for each in distances[key]:
                print each
                print ""
            print ""


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def handedness(set_json, pdb, rdb, output):
    '''
    Calculate the handedness (L or R) for each unit
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    handedness = pg.calculate_handedness(centers)
    if set_json:
        print json.dumps(handedness, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in handedness:
            print "The " + key + " handedness is " + handedness[key]


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def twist(set_json, pdb, rdb, output):
    '''
    Calculate the rotation between units
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    twist = pg.calculate_twist(centers)
    if set_json:
        print json.dumps(twist, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in twist:
            print key + " is rotated of " + str(twist[key][0]) + " degree from " + twist[key][1]


@trsolenoidsgeo.command()
@click.option("--set_json", default=False, help="True if you want to print in the json format")
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def curvatureandpitch(set_json, pdb, rdb, output):
    '''
    Calculate the curvature and the pitch of a unit compared with the previous one
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    cap = pg.calculate_curvature_and_pitch(centers)
    if set_json:
        print json.dumps(cap, sort_keys=True, indent=4, separators=(',', ': '))
    else:
        for key in cap[0]:
            print "The curvature angle between vectors linking " + key + " with " + cap[0][key][0] + " and " + \
                  cap[0][key][0] + " with " + cap[0][key][2] + " is " + str(cap[0][key][1])
            print ""
        print ""
        for key in cap[1]:
            print "The pitch angle between vectors linking " + key + " with " + cap[1][key][0] + " and " + \
                  cap[1][key][0] + " with " + cap[1][key][2] + " is " + str(cap[1][key][1])
            print ""


@trsolenoidsgeo.command()
@click.argument('pdb', type=click.Path(exists=True))
@click.argument('rdb', type=click.Path(exists=True))
@click.argument('output', type=click.Path(exists=True))
def start_pymol(pdb, rdb, output):
    '''
    Start a session in pymol and draw the the units, the com, and the distances between them
    '''
    rwfiles(pdb, rdb, output)
    centers = pg.calculate_center_of_mass()
    canvas.start_pymol()
    canvas.draw_units()
    canvas.draw_center_of_mass(centers)
    canvas.draw_distances_com(centers)


def rwfiles(pdb, rdb, output):
    '''
    Read the rdb and pdb file in order to create a pdb file for each unit
    '''
    rdb_object = RDBParser().parse_rdb(rdb)

    p = PDBParser()
    pdb_structure = p.get_structure("PDB_structure", pdb)

    io = PDBIO()
    io.set_structure(pdb_structure[0][rdb_object.chain])
    unit_number = 0
    for each in rdb_object.units:
        from_to = str(each[0]) + "-" + str(each[1])
        io.save(output + "/unit" + str(unit_number) + "_" + from_to + ".pdb", UnitSelection(each[0], each[1]))
        unit_number += 1
    global output_dir
    output_dir = output
