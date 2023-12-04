package tests;

import io.qameta.allure.Allure;
import integration.loggers.CustomLogger;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

public class BaseTest implements IHookable {

    @Override
    public void run(IHookCallBack iHookCallBack, ITestResult iTestResult) {
        CustomLogger.logTestName(iTestResult.getTestClass().getName());
        iHookCallBack.runTestMethod(iTestResult);
        Allure.getLifecycle().updateTestCase(
                testResult -> testResult.setName(
                        iTestResult.getMethod().getMethodName().replaceFirst("test", "")
                )
        );
        CustomLogger.logTestEnd(iTestResult.getTestClass().getName());
    }
}