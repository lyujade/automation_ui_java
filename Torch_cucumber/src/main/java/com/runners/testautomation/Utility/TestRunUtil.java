

package com.runners.testautomation.Utility;

import org.apache.commons.lang3.StringUtils;

import io.cucumber.java.Scenario;



public class TestRunUtil {

    private static final String executionStarted = "EXECUTION STARTED FOR ";
    private static final String executionCompleted = "EXECUTION COMPLETED FOR ";


    public static String getFeatureName(Scenario scenario) {
        String[] featurePath = scenario.getUri().toString().split("/");
        return featurePath[featurePath.length-1];
    }

    public static String getScenarioExecutionStartState(Scenario scenario) {
        String featureName = "FEATURE NAME: " + getFeatureName(scenario);
        String scenarioName = "SCENARIO NAME: " + scenario.getName();
        String scenarioExecutionStarted = executionStarted + "SCENARIO";
        int maxLength = Math.max(
                Math.max(scenarioExecutionStarted.length(),
                        featureName.length()), scenarioName.length());

        return "\n" + StringUtils.repeat("*", maxLength)
                + "\n" + scenarioExecutionStarted
                + "\n" + featureName
                + "\n" + scenarioName
                + "\n" + StringUtils.repeat("*", maxLength);
    }

    public static String getScenarioExecutionEndState(Scenario scenario) {
        String featureName = "FEATURE NAME: " + getFeatureName(scenario);
        String scenarioName = "SCENARIO NAME: " + scenario.getName();
        String executionStatus = "EXECUTION STATUS: " + scenario.getStatus();
        String scenarioExecutionCompleted = executionCompleted + "SCENARIO";
        int maxLength = Math.max(
                Math.max(scenarioExecutionCompleted.length(),
                        featureName.length()), scenarioName.length());

        return "\n" + StringUtils.repeat("*", maxLength)
                + "\n" + scenarioExecutionCompleted
                + "\n" + featureName
                + "\n" + executionStatus
                + "\n" + scenarioName
                + "\n" + StringUtils.repeat("*", maxLength);

    }

    public static String getUniqueFileNameBasedScenario(Scenario scenario) {
        String resultFileName;
        String timeStamp = DateTimeUtil.getTimeStamp();
        String featureName = getFeatureName(scenario).replace(".target","");
        String fileName = featureName + scenario.getName().replaceAll("[^a-zA-Z0-9]", "");
        if(fileName.length()>130)
            resultFileName = fileName.substring(0,85) + "_" + timeStamp;
        else
            resultFileName = fileName + "_" + timeStamp;

        return resultFileName;
    }

    public static String getSuiteNameState(String suiteName, String suiteType) {
        String result = "***********%s**************";
        if(suiteType.toLowerCase().contains("before"))
            result = String.format(result,  executionStarted + "SUITE::" + suiteName);
        else
            result = String.format(result,  executionCompleted + "SUITE::" + suiteName);

        return result;
    }

    public static String getTestNameState(String testName, String testType) {
        String result = "***********%s**************";
        if(testType.toLowerCase().contains("before"))
            result = String.format(result,  executionStarted + "SUITE::" + testName);
        else
            result = String.format(result,  executionCompleted + "SUITE::" + testName);

        return result;
    }

}
