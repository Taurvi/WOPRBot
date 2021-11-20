package WOPRBot.clients;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {
    @Test void testConstructor() {
        HelloWorld sut = new HelloWorld();
        assertEquals("hello world", sut.get());
    }
}
