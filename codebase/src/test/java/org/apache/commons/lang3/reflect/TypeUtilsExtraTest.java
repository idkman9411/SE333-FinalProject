package org.apache.commons.lang3.reflect;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * Extra tests for TypeUtils to increase coverage on generic-related utilities.
 */
public class TypeUtilsExtraTest {

    // Fields used to obtain ParameterizedType / WildcardType / GenericArrayType
    @SuppressWarnings("unused")
    private Map<String, Integer> mapStringInteger;

    @SuppressWarnings("unused")
    private List<? extends Number> listExtendsNumber;

    @SuppressWarnings("unused")
    private List<? super Integer> listSuperInteger;

    @SuppressWarnings("unused")
    private List<String>[] listStringArray;

    @Test
    public void testGetTypeArgumentsForMap() throws Exception {
        final Type t = this.getClass().getDeclaredField("mapStringInteger").getGenericType();
        assertTrue(t instanceof ParameterizedType);
        final ParameterizedType pt = (ParameterizedType) t;

        final java.util.Map<java.lang.reflect.TypeVariable<?>, Type> args = TypeUtils.getTypeArguments(pt);
        assertNotNull(args);
        // Map has two type parameters (K,V)
        assertEquals(2, args.size());
        boolean sawString = false, sawInteger = false;
        for (Type value : args.values()) {
            if (value.equals(String.class)) sawString = true;
            if (value.equals(Integer.class)) sawInteger = true;
        }
        assertTrue("Should have seen String as type arg", sawString);
        assertTrue("Should have seen Integer as type arg", sawInteger);
    }

    @Test
    public void testNormalizeUpperBoundsRemovesRedundant() {
        final Type[] input = new Type[] { List.class, ArrayList.class };
        final Type[] out = TypeUtils.normalizeUpperBounds(input);
        assertNotNull(out);
        assertEquals(1, out.length);
        assertEquals(ArrayList.class, out[0]);
    }

    @Test
    public void testWildcardImplicitBounds() throws Exception {
        final ParameterizedType ptExt = (ParameterizedType) this.getClass().getDeclaredField("listExtendsNumber").getGenericType();
        final Type wtExt = ptExt.getActualTypeArguments()[0];
        assertTrue(wtExt instanceof WildcardType);
        final WildcardType wildcardExt = (WildcardType) wtExt;
        final Type[] upper = TypeUtils.getImplicitUpperBounds(wildcardExt);
        assertEquals(1, upper.length);
        assertEquals(Number.class, upper[0]);

        final ParameterizedType ptSup = (ParameterizedType) this.getClass().getDeclaredField("listSuperInteger").getGenericType();
        final Type wtSup = ptSup.getActualTypeArguments()[0];
        assertTrue(wtSup instanceof WildcardType);
        final WildcardType wildcardSup = (WildcardType) wtSup;
        final Type[] lower = TypeUtils.getImplicitLowerBounds(wildcardSup);
        assertEquals(1, lower.length);
        assertEquals(Integer.class, lower[0]);
    }

    @Test
    public void testIsArrayTypeAndGetArrayComponentType() throws Exception {
        final Type arrType = this.getClass().getDeclaredField("listStringArray").getGenericType();
        assertTrue(TypeUtils.isArrayType(arrType));
        final Type comp = TypeUtils.getArrayComponentType(arrType);
        assertNotNull(comp);
        // component should be a ParameterizedType with raw type List
        assertTrue(comp instanceof ParameterizedType);
        final ParameterizedType pt = (ParameterizedType) comp;
        assertEquals(List.class, pt.getRawType());
    }

    @Test
    public void testIsInstanceAndIsAssignableBasic() {
        assertTrue(TypeUtils.isInstance("hello", String.class));
        // null is allowed for non-primitive classes
        assertTrue(TypeUtils.isInstance(null, Integer.class));
        // basic assignability: String assignable to Object
        assertTrue(TypeUtils.isAssignable(String.class, Object.class));
    }

}
