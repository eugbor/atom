package ru.atom;

import org.junit.Test;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class HelloWorldTest {
    @Test
    public void getHelloWorld() throws Exception {
        assertEquals("Hello, World!", HelloWorld.getHelloWorld());
        assertFalse("Some strange string".equals(HelloWorld.getHelloWorld()));
=======
import static junit.framework.Assert.assertEquals;

public class HelloWorldTest {
    @Test
    public void getHelloWorld() {
        assertEquals("WRONG STRING", HelloWorld.getHelloWorld());
>>>>>>> dbefc0b47d55a0df986ff9eb159b0babab112707
    }
}