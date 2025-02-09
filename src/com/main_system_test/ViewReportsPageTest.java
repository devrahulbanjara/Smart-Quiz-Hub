package com.main_system_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.main_system.ViewReportsPage;

import javax.swing.*;

public class ViewReportsPageTest {
    private ViewReportsPage viewReportsPage;

    @BeforeEach
    public void setUp() {
        viewReportsPage = new ViewReportsPage();
    }

    @Test
    public void testFrameInitialization() {
        assertNotNull(viewReportsPage);
        assertEquals("View Reports", viewReportsPage.getTitle());
        assertTrue(viewReportsPage.isVisible());
    }

    @Test
    public void testFullReportButtonExists() {
        JButton fullReportButton = getButtonByText(viewReportsPage, "Full Report");
        assertNotNull(fullReportButton);
    }

    @Test
    public void testPreviousPageButtonExists() {
        JButton previousPageButton = getButtonByText(viewReportsPage, "Previous Page");
        assertNotNull(previousPageButton);
    }

    private JButton getButtonByText(JFrame frame, String buttonText) {
        for (java.awt.Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                for (java.awt.Component panelComponent : panel.getComponents()) {
                    if (panelComponent instanceof JButton) {
                        JButton button = (JButton) panelComponent;
                        if (button.getText().equals(buttonText)) {
                            return button;
                        }
                    }
                }
            }
        }
        return null;
    }
}
