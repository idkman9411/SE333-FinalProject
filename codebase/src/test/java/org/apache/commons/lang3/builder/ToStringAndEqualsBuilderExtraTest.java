package org.apache.commons.lang3.builder;

import static org.junit.Assert.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.Test;

public class ToStringAndEqualsBuilderExtraTest {

    static class Base {
        private int a = 1;
        @Override
        public String toString(){
            return "Base[a="+a+"]";
        }
    }

    static class Sub extends Base {
        private String b = "x";
    }

    static class HasTransient {
        private transient int secret = 42;
        private int normal = 7;
    }

    @Test
    public void testAppendSuperAndAppendToString() {
        final Sub s = new Sub();
        final String manual = new ToStringBuilder(s, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(((Base)s).toString())
                .append("b", "x")
                .toString();
        assertNotNull(manual);
        assertTrue(manual.contains("b=x") || manual.contains("b=\"x\""));
    }

    @Test
    public void testReflectionToStringIncludeTransients() {
        final HasTransient h = new HasTransient();
        final String without = ReflectionToStringBuilder.toString(h, ToStringStyle.DEFAULT_STYLE, false, false);
        final String with = ReflectionToStringBuilder.toString(h, ToStringStyle.DEFAULT_STYLE, true, false);
        assertNotNull(without);
        assertNotNull(with);
        // when including transients, secret should appear
        assertFalse(without.contains("secret"));
        assertTrue(with.contains("secret") || with.contains("42"));
    }

    @Test
    public void testEqualsBuilderArraysAndObjects() {
        int[] a1 = new int[] {1,2,3};
        int[] a2 = new int[] {1,2,3};
        assertTrue(new EqualsBuilder().append(a1, a2).isEquals());

        Object o1 = "abc";
        Object o2 = "abc";
        assertTrue(new EqualsBuilder().append(o1, o2).isEquals());
    }

}
