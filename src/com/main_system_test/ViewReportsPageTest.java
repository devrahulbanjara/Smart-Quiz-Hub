package com.main_system_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import com.main_system.ViewReportsPage;

public class ViewReportsPageTest {
    private ViewReportsPage viewReportsPage;

    @BeforeEach
    public void setUp() {
        viewReportsPage = new ViewReportsPage();
        viewReportsPage.setVisible(true); // Make sure the frame is visible
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
        assertNotNull(fullReportButton, "Full Report button should exist.");
    }

    @Test
    public void testPreviousPageButtonExists() {
        JButton previousPageButton = getButtonByText(viewReportsPage, "Previous Page");
        assertNotNull(previousPageButton, "Previous Page button should exist.");
    }

    private JButton getButtonByText(JFrame frame, String buttonText) {
        // Iterate through all components in the frame
        for (java.awt.Component component : frame.getContentPane().getComponents()) {
            if (component instanceof JPanel) {
                JPanel panel = (JPanel) component;
                // Iterate through all components in the panel
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
        return null; // Return null if the button is not found
    }
}
