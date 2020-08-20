package com.qiding.driver;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ErrorCodes;

import java.lang.reflect.Constructor;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws NoSuchMethodException {

		ErrorCodes errorCodes= new ErrorCodes();

		Class<? extends WebDriverException> clazz = errorCodes.getExceptionType("no such element");

		try {
			Constructor<? extends WebDriverException> constructor = clazz.getConstructor(String.class);
			constructor.newInstance("message");
		} catch (ReflectiveOperationException e) {
			throw new WebDriverException("www");
		}

    }
}
