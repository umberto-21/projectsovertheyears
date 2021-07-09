HANDEDNESS

In order to calculate the handedness of a unit, the algorithm uses the rigth-hand rule.

The algorithm follows the following steps for each unit:

1. We calculate two vectors: one from the first alpha carbon of the unit to the center of mass of that unit; the second from the second alpha carbon of the unit to the center of mass of that unit.
2. In order to have a space where we can compare the vectors defined above, we then define a new 3d coordinate system using three point. The axis x is a vector from the first alpha carbon of the unit to the center of mass of that unit. The axis z is a vector from the center of mass of the unit to the center of mass of the next unit (for the last unit we take the vector from the center of mass to the previous one and calculate the inverse). Finally the axis y is the cross product of axis z and axis x.
3. We define a translation matrix in order to translate the the two vector in the new coordinate system.
4. We calculate the cross product of the vectors translated in the new coordinate system
5. Finally if the third component of the cross product is positive the unit will be right-handed otherwise it will be left-handed.

TWIST

In order to calculate the twist angle between consecutive units we define two planes using three points for each one. We then calculate the dihedral angle between those two planes.

The algorithm follows the following steps:

1. We identify four points in the space: the first alpha carbon of the previuos unit; the center of mass of the previous unit; the center of mass of the unit; and the first alpha carbon of the unit.
2. We use a function from the Biopython library that calculates the angle between two planes, called dihedral angle. The first plane is identified by: the previous unit first alpha carbon, the previous unit center of mass, the unit center of mass. The second plane is identified by: the previous unit center of mass, the unit center of mass, the unit first alpha carbon.

CURVATURE AND PITCH

We choose to calculate the curvature and pitch angle in the same function becouse the first steps of the algorithm are identical.

The algorithm steps are:

1. In order to have a space where we can compare the vectors of interest we define a coordinate system. The axis x is from the unit center of mass to the last alpha carbon of that unit. The axis z is from the next unit last alpha carbon and the last alpha carbon of current unit. The axis y is the croos produc between the two axis defined as described above.
2. We define a translation matrix in order to translate the the two vector in the new coordinate system.
3. We then calculate the vector that compared with different axis of the coordinate system described above gives us the curvature and the pitch angles. The vector is from the next unit last alpha carbon to the following unit last alpha carbon.
4. We translate the vector defined above and the two axis (x and z) in the new coordinate system.
5. We calculate the angles. The curvature is the angle between the vector defined at step 3 and the axis z. The pitch is the angle between the vector of step 3 and the axis x minus the angle between the axis x and axis z.