package com.main_system_test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.main_system.RegisterPage;

public class RegisterValidationTest {

    private RegisterPage registerPage;

    @BeforeEach
    public void setUp() {
        registerPage = new RegisterPage();
    }

    @Test
    public void testIsValidName_ValidName() {
        boolean result = registerPage.isValidName("Rahul Dev Banjara");
        assertTrue(result);
    }

    @Test
    public void testIsValidName_InvalidName() {
        boolean result = registerPage.isValidName("Rahul");
        assertFalse(result);
    }

    @Test
    public void testIsValidEmail_ValidEmail() {
        boolean result = registerPage.isValidEmail("rahul@gmail.com");
        assertTrue(result);
    }

    @Test
    public void testIsValidEmail_InvalidEmail() {
        boolean result = registerPage.isValidEmail("gmail.com");
        assertFalse(result);
    }

    @Test
    public void testIsValidPassword_ValidPassword() {
        boolean result = registerPage.isValidPassword("wasuuprahul");
        assertTrue(result);
    }

    @Test
    public void testIsValidPassword_InvalidPassword() {
        boolean result = registerPage.isValidPassword("short");
        assertFalse(result);
    }

    @Test
    public void testRegisterButtonClicked_EmptyFields() {
        registerPage.nameTextField.setText("");
        registerPage.emailTextField.setText("");
        registerPage.passwordTextField.setText("");
        registerPage.ageTextField.setText("");

        assertTrue(registerPage.nameTextField.getText().isEmpty());
        assertTrue(registerPage.emailTextField.getText().isEmpty());
        assertTrue(registerPage.passwordTextField.getPassword().length == 0);
        assertTrue(registerPage.ageTextField.getText().isEmpty());
    }
}
