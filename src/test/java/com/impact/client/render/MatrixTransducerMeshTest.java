package com.impact.client.render;

import com.impact.client.render.models.MatrixTransducerMesh;
import com.impact.client.render.models.MatrixTransducerMesh.Face;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MatrixTransducerMeshTest {
    @Test
    void crystalStaysInsideTheExistingBoundsAndFacesPointOutward() {
        assertTrue(MatrixTransducerMesh.getFaces().size() < 1000, "Chunk mesh must remain small");
        Set<Long> planes = new HashSet<>();
        for (Face face : MatrixTransducerMesh.getFaces()) {
            double[][] points = new double[3][3];
            for (int corner = 0; corner < 4; corner++) {
                for (int axis = 0; axis < 3; axis++) {
                    double value = face.coordinate(corner, axis, 0);
                    assertTrue(value >= MatrixTransducerMesh.MIN - 1e-9);
                    assertTrue(value <= MatrixTransducerMesh.MAX + 1e-9);
                    if (corner < 3) {
                        points[corner][axis] = value;
                    }
                }
            }
            int u = (face.axis + 1) % 3;
            int v = (face.axis + 2) % 3;
            double normal = (points[1][u] - points[0][u]) * (points[2][v] - points[0][v])
                    - (points[1][v] - points[0][v]) * (points[2][u] - points[0][u]);
            assertTrue(normal * face.sign > 0, "Backface culling must not hide the crystal");
            planes.add(Math.round(face.coordinate(0, face.axis, 0) * 10000));
        }
        assertTrue(planes.size() >= 8, "The crystal needs actual stepped geometry");
    }

    @Test
    void allSixSidesHaveTheSameFacetsAndColors() {
        Set<String> reference = signature(0, 1);
        for (int axis = 0; axis < 3; axis++) {
            for (int sign : new int[] { -1, 1 }) {
                assertEquals(reference, signature(axis, sign));
            }
        }
    }

    private Set<String> signature(int axis, int sign) {
        Set<String> result = new HashSet<>();
        for (Face face : MatrixTransducerMesh.getFaces()) {
            if (face.axis != axis || face.sign != sign) {
                continue;
            }
            Set<String> corners = new TreeSet<>();
            for (int corner = 0; corner < 4; corner++) {
                long depth = Math.round((face.coordinate(corner, axis, 0) - 0.5D) * sign * 10000);
                long u = Math.round(face.coordinate(corner, (axis + 1) % 3, 0) * 10000);
                long v = Math.round(face.coordinate(corner, (axis + 2) % 3, 0) * 10000);
                corners.add(depth + ":" + u + ":" + v);
            }
            result.add(corners + ":" + face.getColor());
        }
        return result;
    }
}
