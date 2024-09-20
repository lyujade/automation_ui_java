package com.runners.testautomation.Utility;

import org.testng.annotations.DataProvider;

import java.io.IOException;

public class TestDataProvider {

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {
        return ExcelHandler.getTestData("src/test/resources/testdata/LoginData.xlsx", "Sheet1");
    }

}

