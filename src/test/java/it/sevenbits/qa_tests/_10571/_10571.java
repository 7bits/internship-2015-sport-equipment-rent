package it.sevenbits.qa_tests._10571;

import junit.framework.Test;
import junit.framework.TestSuite;

public class _10571 extends TestSuite {

  public static Test suite() {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(_10571_00_00.class);
    suite.addTestSuite(_10571_01_00.class);
    suite.addTestSuite(_10571_01_01.class);
    suite.addTestSuite(_10571_01_02.class);
    suite.addTestSuite(_10571_01_03.class);
    suite.addTestSuite(_10571_01_04.class);
      suite.addTestSuite(_10571_02_00.class);
      suite.addTestSuite(_10571_02_01.class);
      suite.addTestSuite(_10571_02_02.class);
      suite.addTestSuite(_10571_02_03.class);
      suite.addTestSuite(_10571_02_04.class);
      suite.addTestSuite(_10571_02_05.class);
      suite.addTestSuite(_10571_02_06.class);
      suite.addTestSuite(_10571_02_07.class);
      suite.addTestSuite(_10571_02_08.class);
      suite.addTestSuite(_10571_02_09.class);
      suite.addTestSuite(_10571_02_10.class);
      suite.addTestSuite(_10571_03_00.class);
      suite.addTestSuite(_10571_03_01.class);
      suite.addTestSuite(_10571_04_00.class);
      suite.addTestSuite(_10571_04_01.class);
      suite.addTestSuite(_10571_05_00.class);
      suite.addTestSuite(_10571_07_00.class);
    return suite;
  }

  public static void main(String[] args) {
    junit.textui.TestRunner.run(suite());
  }
}
