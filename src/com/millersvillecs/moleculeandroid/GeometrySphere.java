package com.millersvillecs.moleculeandroid;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
import com.millersvillecs.moleculeandroid.util.math.Vector3;

public class GeometrySphere
{

    private ArrayList<Vector3> vertices = new ArrayList<Vector3>();
    private ArrayList<Integer> indices = new ArrayList<Integer>();
    private int index;
    private Map<Integer, Integer> middlePointIndexCache;

    // add vertex to mesh, fix position to be on unit sphere, return index
    private int addVertex(float x, float y, float z)
    {
        double length = Math.sqrt(x * x + y * y + z * z);
        vertices.add(new Vector3((float) (x/length), (float) (y/length), (float) (z/length)));
        return index++;
    }

    // return index of point in the middle of p1 and p2
    private int getMiddlePoint(int p1, int p2)
    {
        // first check if we have it already
        boolean firstIsSmaller = p1 < p2;
        int smallerIndex = firstIsSmaller ? p1 : p2;
        int greaterIndex = firstIsSmaller ? p2 : p1;
        int key = (smallerIndex << 32) + greaterIndex;

        int ret;
        if (this.middlePointIndexCache.TryGetValue(key, out ret))
        {
            return ret;
        }

        // not in cache, calculate it
        Vector3 point1 = vertices.get(p1);
        Vector3 point2 = vertices.get(p2);
        Vector3 middle = new Vector3(
        	(float)((point1.x + point2.x) / 2.0), 
        	(float)((point1.y + point2.y) / 2.0), 
        	(float)((point1.z + point2.z) / 2.0));

        // add vertex makes sure point is on unit sphere
        int i = addVertex(middle); 

        // store it, return index
        this.middlePointIndexCache.Add(key, i);
        return i;
    }

    public MeshGeometry3D Create(int recursionLevel)
    {
        this.middlePointIndexCache = new HashMap<Integer, Integer>();
        this.index = 0;

        // create 12 vertices of a icosahedron
        var t = (1.0 + Math.Sqrt(5.0)) / 2.0;

        addVertex(new Vector3(-1,  t,  0));
        addVertex(new Vector3( 1,  t,  0));
        addVertex(new Vector3(-1, -t,  0));
        addVertex(new Vector3( 1, -t,  0));

        addVertex(new Vector3( 0, -1,  t));
        addVertex(new Vector3( 0,  1,  t));
        addVertex(new Vector3( 0, -1, -t));
        addVertex(new Vector3( 0,  1, -t));

        addVertex(new Vector3( t,  0, -1));
        addVertex(new Vector3( t,  0,  1));
        addVertex(new Vector3(-t,  0, -1));
        addVertex(new Vector3(-t,  0,  1));


        // create 20 triangles of the icosahedron
        var faces = new ArrayList<Vector3>();

        // 5 faces around point 0
        faces.Add(new Vector3(0, 11, 5));
        faces.Add(new Vector3(0, 5, 1));
        faces.Add(new Vector3(0, 1, 7));
        faces.Add(new Vector3(0, 7, 10));
        faces.Add(new Vector3(0, 10, 11));

        // 5 adjacent faces 
        faces.Add(new Vector3(1, 5, 9));
        faces.Add(new Vector3(5, 11, 4));
        faces.Add(new Vector3(11, 10, 2));
        faces.Add(new Vector3(10, 7, 6));
        faces.Add(new Vector3(7, 1, 8));

        // 5 faces around point 3
        faces.Add(new Vector3(3, 9, 4));
        faces.Add(new Vector3(3, 4, 2));
        faces.Add(new Vector3(3, 2, 6));
        faces.Add(new Vector3(3, 6, 8));
        faces.Add(new Vector3(3, 8, 9));

        // 5 adjacent faces 
        faces.Add(new Vector3(4, 9, 5));
        faces.Add(new Vector3(2, 4, 11));
        faces.Add(new Vector3(6, 2, 10));
        faces.Add(new Vector3(8, 6, 7));
        faces.Add(new Vector3(9, 8, 1));


        // refine triangles
        for (int i = 0; i < recursionLevel; i++)
        {
            List<Vector3> faces2 = new ArrayList<Vector3>();
            for(Vector3 tri : faces)
            {
                // replace triangle by 4 triangles
                int a = getMiddlePoint(tri.x, tri.y);
                int b = getMiddlePoint(tri.y, tri.z);
                int c = getMiddlePoint(tri.z, tri.x);

                faces2.add(new Vector3(tri.x, a, c));
                faces2.add(new Vector3(tri.y, b, a));
                faces2.add(new Vector3(tri.z, c, b));
                faces2.add(new Vector3(a, b, c));
            }
            faces = faces2;
        }

        // done, now add triangles to mesh
        for(Vector3 tri : faces)
        {
            this.geometry.TriangleIndices.Add(tri.v1);
            this.geometry.TriangleIndices.Add(tri.v2);
            this.geometry.TriangleIndices.Add(tri.v3);
        }

        return this.geometry;        
    }
}
*/