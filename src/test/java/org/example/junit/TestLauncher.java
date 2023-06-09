package org.example.junit;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

public class TestLauncher {
    public static void main(String[] args) {
        Launcher launcher = LauncherFactory.create();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(DiscoverySelectors.selectPackage("org.example.junit.service"))
                .build();
        SummaryGeneratingListener summaryGeneratingListener = new SummaryGeneratingListener();
        launcher.execute(request, summaryGeneratingListener);
        try (PrintWriter printWriter = new PrintWriter(System.out)) {
            summaryGeneratingListener.getSummary().printTo(printWriter);
        }
    }
}
