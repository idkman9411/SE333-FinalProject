package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TypeUtilsDeepTest {

    // fields for reflection
    @SuppressWarnings("unused")
    private java.lang.Iterable<String> iterableString;

    @SuppressWarnings("unused")
    private List<Integer> listInteger;

    @SuppressWarnings("unused")
    private List<? extends Number> listExtendsNumberLocal;

    // simple generic type to obtain a TypeVariable with bound
    public static class A<T extends Number> {}

    @Test
    public void testDetermineTypeArgumentsArrayListToIterable() throws Exception {
        final Type iterableType = this.getClass().getDeclaredField("iterableString").getGenericType();
        assertTrue(iterableType instanceof ParameterizedType);
        final ParameterizedType pt = (ParameterizedType) iterableType;

        final Map<TypeVariable<?>, Type> mapping = TypeUtils.determineTypeArguments(ArrayList.class, pt);
        assertNotNull(mapping);
        boolean foundString = false;
        for (Type t : mapping.values()) {
            if (t.equals(String.class)) foundString = true;
        }
        assertTrue("ArrayList should determine Iterable<String> mapping to String", foundString);
    }

    @Test
    public void testIsAssignableWithWildcard() throws Exception {
        final Type listInt = this.getClass().getDeclaredField("listInteger").getGenericType();
        final Type listExt = this.getClass().getDeclaredField("listExtendsNumberLocal").getGenericType();
        assertTrue(TypeUtils.isAssignable(listInt, listExt));
    }

    @Test
    public void testTypesSatisfyVariablesPositive() {
        final TypeVariable<?>[] vars = A.class.getTypeParameters();
        assertEquals(1, vars.length);
        final Map<TypeVariable<?>, Type> assign = new HashMap<TypeVariable<?>, Type>();
        assign.put(vars[0], Integer.class);
        assertTrue(TypeUtils.typesSatisfyVariables(assign));
    }

    @Test
    public void testTypesSatisfyVariablesNegative() {
        final TypeVariable<?>[] vars = A.class.getTypeParameters();
        final Map<TypeVariable<?>, Type> assign = new HashMap<TypeVariable<?>, Type>();
        // String is not a Number, should fail
        assign.put(vars[0], String.class);
        assertFalse(TypeUtils.typesSatisfyVariables(assign));
    }

}
