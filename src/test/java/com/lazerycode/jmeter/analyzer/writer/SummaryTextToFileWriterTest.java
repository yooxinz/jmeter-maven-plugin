package com.lazerycode.jmeter.analyzer.writer;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static com.lazerycode.jmeter.analyzer.config.Environment.ENVIRONMENT;
import static com.lazerycode.jmeter.analyzer.writer.WriterTestHelper.getMockedTestResults;
import static com.lazerycode.jmeter.analyzer.writer.WriterTestHelper.normalizeFileContents;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Tests for {@link SummaryTextToFileWriter}
 */
public class SummaryTextToFileWriterTest {

  private final StringWriter out = new StringWriter();
  private SummaryTextToFileWriter testling;

  @Before
  public void setUp() throws Exception {

    ENVIRONMENT.initializeFreemarkerConfiguration();

    testling = new LocalSummaryTextToFileWriter();
  }

  @Test
  public void testWrite() throws Exception {

    testling.write(getMockedTestResults());

    File expected = new File(getClass().getResource("test.txt").getFile());

    String actual = out.toString();

    assertThat("output does not match: ",
            normalizeFileContents(actual),
            is(equalTo(normalizeFileContents(expected))));

  }

  //=====================================

  /**
   * Local test class overrides methods to isolate this test from other classes
   */
  private class LocalSummaryTextToFileWriter extends SummaryTextToFileWriter {

    @Override
    protected File getFile(String name) throws IOException {
      return new File("");
    }

    @Override
    protected java.io.Writer getWriter(File file) throws IOException {
      return out;
    }
  }
}
