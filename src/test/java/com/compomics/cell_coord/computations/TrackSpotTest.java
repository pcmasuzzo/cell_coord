/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.cell_coord.computations;

import com.compomics.cell_coord.entity.TrackSpot;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.runner.RunWith;

/**
 * A simple test on Euclidean Distance between two points in 2D and in 3D.
 *
 * @author Paola
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:mySpringXMLConfig.xml")
public class TrackSpotTest {

    // two 2D points ont the plane
    private static TrackSpot q;
    private static TrackSpot r;

    /**
     * Before class, create geometric points.
     */
    @BeforeClass
    public static void createPoints() {
        q = new TrackSpot(2.5, 3.5);
        r = new TrackSpot(7.7, 6.8);
    }

    @Test
    public void test2DEuclideanDistance() {
        double euclideanDistanceTo = q.euclideanDistance2DTo(r);
        Assert.assertEquals(6.158733636065128, euclideanDistanceTo);
        double zeroDistance = q.euclideanDistance2DTo(q);
        Assert.assertEquals(0.0, zeroDistance);
    }
}
