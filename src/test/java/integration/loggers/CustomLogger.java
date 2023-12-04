package integration.loggers;

import io.qameta.allure.Step;
import logging.Logger;

public class CustomLogger {

    public CustomLogger() {}

    public static void info(String message) {
        Logger.getInstance().info(message);
    }

    public static void warn(String message) {
        Logger.getInstance().warn(message);
    }

    public static void logStep(int number, String title) {
        logStep(String.format("%d. %s", number, title));
    }

    public static void logStep(String message) {
        info(String.format(
                "---------------------------------------==[ Step: %s ]==------------------------------------------",
                message)
        );
    }

    @Step("{message}")
    public static void logAllureStep(String message) {
        logStep(message);
    }

    @Step("{message}")
    public static void logAllureStep(String message, Runnable runnable) {
        logStep(message);
        runnable.run();
    }

    public static void logTestName(String testName) {
        info("-------------------------------------------------------------------------------------------------------");
        info(String.format("=====================  Test case: : '%s' =====================", testName));
        info("-------------------------------------------------------------------------------------------------------");
    }

    public static void logTestEnd(String testName) {
        info("***********************************************************************************************");
        info(String.format("***** Test case: : '%s' %s! *****", testName, "FINISHED"));
        info("***********************************************************************************************");
    }
}