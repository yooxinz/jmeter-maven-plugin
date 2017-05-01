package com.lazerycode.jmeter.analyzer.statistics;

/**
 * Created by star on 2017/5/1.
 */
public class IndexObject {


    private String testName;

    private String testResult;

    private int total;

    private int success;

    private int fail;

    public IndexObject(String testName, String testResult) {
        this.testName = testName;
        this.testResult = testResult;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestName() {
        return testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

}
