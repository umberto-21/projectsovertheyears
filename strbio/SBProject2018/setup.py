from setuptools import setup, find_packages
from os import path

here = path.abspath(path.dirname(__file__))

with open(path.join(here, 'README.md'), 'r') as readmefile:
    description = readmefile.read()


setup(
    name='TrSolenoidsGeo',
    version='0.0.0',
    description='Structural Bioinformatics Project',
    long_description=description,
    author='Umberto Andria',
    classifiers=[
        'Development Status :: 3 - Alpha',

        'Programming Language :: Python :: 2.7'
    ],
    packages=find_packages(),  # todo specify package directories
    install_requires=["biopython", "numpy", "pymol", "click"],  # todo specify other packages
    entry_points={
        'console_scripts': [
            'trsolenoidsgeo=Project2018.Commands:trsolenoidsgeo',
            'rwfiles=Project2018.Commands:rwfiles',  # todo
            'centerofmass=Project2018.Commands:centerofmass',
            'distances_com=Project2018.Commands:distances_com',
            'distances_ca=Project2018.Commands:distances_ca',
            'handedness=Project2018.Commands:handedness',
            'twist=Project2018.Commands:twist',
            'curvatureandpitch=Project2018.Commands:curvatureandpitch',
            'start_pymol=Project2018.Commands:start_pymol'

        ]
    }

)
