package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TypeUtilsMoreTest {

    @SuppressWarnings("unused")
    private List<?> listUnbounded;

    @SuppressWarnings("unused")
    private List<String>[] listStringArray2;

    public static class MyMap extends java.util.HashMap<String, Integer> {}

    @Test
    public void testGetTypeArgumentsForMyMap() {
        final Map<java.lang.reflect.TypeVariable<?>, Type> args = TypeUtils.getTypeArguments(MyMap.class, java.util.Map.class);
        assertNotNull(args);
        boolean sawString=false, sawInteger=false;
        for (Type t : args.values()) {
            if (t.equals(String.class)) sawString=true;
            if (t.equals(Integer.class)) sawInteger=true;
        }
        assertTrue("Should contain String and Integer as type arguments", sawString && sawInteger);
    }

    @Test
    public void testNormalizeUpperBoundsMultiple() {
        final Type[] input = new Type[] { java.util.Collection.class, java.util.List.class, java.util.ArrayList.class };
        final Type[] out = TypeUtils.normalizeUpperBounds(input);
        assertNotNull(out);
        assertEquals(1, out.length);
        assertEquals(java.util.ArrayList.class, out[0]);
    }

    @Test
    public void testUnboundedWildcardImplicitUpperBounds() throws Exception {
        final java.lang.reflect.Type t = this.getClass().getDeclaredField("listUnbounded").getGenericType();
        assertTrue(t instanceof ParameterizedType);
        final ParameterizedType pt = (ParameterizedType) t;
        final Type arg = pt.getActualTypeArguments()[0];
        assertTrue(arg instanceof WildcardType);
        final Type[] upper = TypeUtils.getImplicitUpperBounds((WildcardType) arg);
        assertEquals(1, upper.length);
        assertEquals(Object.class, upper[0]);
    }

    @Test
    public void testIsArrayTypeAndComponentForClassArray() throws Exception {
        final Type arr = String[].class;
        assertTrue(TypeUtils.isArrayType(arr));
        final Type comp = TypeUtils.getArrayComponentType(arr);
        assertEquals(String.class, comp);
    }

    @Test
    public void testIsInstanceNullPrimitive() {
        assertFalse(TypeUtils.isInstance(null, int.class));
        assertTrue(TypeUtils.isInstance(null, Integer.class));
    }

}
