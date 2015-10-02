package it.sevenbits;

import it.sevenbits.qa_tests._10571.*;
import it.sevenbits.qa_tests._10666.*;
import it.sevenbits.qa_tests._10676.*;
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
                _10571_02_05.class,
                _10571_02_06.class,
                _10571_02_07.class,
                _10571_02_08.class,
                _10571_02_09.class,
                _10571_02_10.class,
                _10571_03_00.class,
                _10571_03_01.class,
                _10571_04_00.class,
                _10571_04_01.class,
                _10571_05_00.class,
                _10571_07_00.class,
                _10666_01_00.class,
                _10666_01_02.class,
                _10666_01_03.class,
                _10666_02_00.class,
                _10666_02_01.class,
                _10666_02_02.class,
                _10666_02_03.class,
                _10666_02_04.class,
                _10666_02_05.class,
                _10666_02_06.class,
                _10666_02_07.class,
                _10666_02_08.class,
                _10666_02_09.class,
                _10666_02_10.class,
                _10666_02_11.class,
                _10666_02_12.class,
                _10666_02_13.class,
                _10666_03_00.class,
                _10666_03_02.class,
                _10666_05_00.class,
                _10676_00_00.class,
                _10676_01_00.class,
                _10676_02_00.class,
                _10676_04_00.class,
                _10676_04_01.class,
                _10676_04_02.class,
                _10676_04_03.class,
                _10676_05_00.class,
                _10676_05_01.class,
                _10676_05_02.class,
                _10676_06_00.class,
                _10676_06_01.class,
                _10676_06_02.class,
                _10676_06_04.class,
                _10676_06_05.class,
                _10676_08_00.class,
                _10676_09_00.class,
                _10676_10_00.class

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
