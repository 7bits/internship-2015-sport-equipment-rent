package it.sevenbits;

import it.sevenbits.qa_tests.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(
                _10571_00_00.class,
                _10571_01_00.class,
                _10571_01_01.class,
                _10571_01_02.class,
                _10571_01_03.class,
                _10571_01_04.class,
                _10571_02_00.class,
                _10571_02_01.class,
                _10571_02_02.class,
                _10571_02_03.class,
                _10571_02_04.class,
                _10571_03_00.class,
                _10571_03_01.class,
                _10571_05_00.class,
                _10571_05_01.class,
                _10571_07_00.class
        );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
