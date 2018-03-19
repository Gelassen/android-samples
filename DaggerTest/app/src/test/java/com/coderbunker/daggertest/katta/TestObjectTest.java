package com.coderbunker.daggertest.katta;


import com.coderbunker.daggertest.BaseTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class TestObjectTest extends BaseTest {

    private TestObject testObject;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Captor
    ArgumentCaptor<String> captor;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        testObject = Mockito.mock(TestObject.class);
    }

    @Test
    public void testObject() throws Exception {
        when(testObject.getName()).thenReturn("Dmitry");
        assertEquals("Dmitry", testObject.getName());
    }

    @Test
    public void testObjectDoReturn() throws Exception {
        doReturn("Dmitry").when(testObject).getName();
        assertEquals("Dmitry", testObject.getName());
    }

    @Test
    public void testObjectMethodCorrectness() throws Exception {
        int result = testObject.processNumbers(2, 2);
        assertNotEquals(2, result);
    }

    @Test
    public void testObjectMethodCorrectnessRealMethod() throws Exception {
        when(testObject.getName()).thenCallRealMethod();
        when(testObject.getName(anyString())).thenCallRealMethod();
        when(testObject.processNumbers(2, 2)).thenCallRealMethod();
        assertEquals(2, testObject.processNumbers(2, 2));
        assertEquals("Dmitry", testObject.getName("Dmitry"));
        assertNull(testObject.getName()); // call null object
    }

    @Test
    public void testObjectMethodCorrectnessSpy() throws Exception {
        TestObject spy = Mockito.spy(TestObject.class);
        int result = spy.processNumbers(2, 2);
        assertEquals(2, result);
    }

    @Test
    public void testCaptorArguments() throws Exception {
        // TODO test captor arguments
        when(testObject.getName(anyString())).thenCallRealMethod();

        testObject.getName("Dmitry");

        verify(testObject).getName(captor.capture());

        String value = captor.getValue();
        assertEquals("Dmitry", value);

    }

    @Test
    public void testAnswer() throws Exception {
        // test answer
        when(testObject.getName("Dmitry")).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                String firstArg = invocation.getArgument(0);
                // TODO process param and reply
                return firstArg;
            }
        });

        assertEquals("Dmitry", testObject.getName("Dmitry"));
    }
}
