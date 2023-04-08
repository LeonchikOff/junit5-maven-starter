package org.example.junit.extension;

import org.junit.jupiter.api.extension.*;

public class GlobalExtension
        implements
        BeforeAllCallback,
        BeforeEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback,
        AfterEachCallback,
        AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        System.out.println("BeforeAll Callback");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        System.out.println("BeforeEach Callback");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        System.out.println("BeforeTestExecution Callback");

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        System.out.println("AfterTestExecution Callback");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        System.out.println("AfterEach Callback");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        System.out.println("AfterAll Callback");
    }
}
