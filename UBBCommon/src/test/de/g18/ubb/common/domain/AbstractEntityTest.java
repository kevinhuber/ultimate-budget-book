package de.g18.ubb.common.domain;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.util.ObjectUtil;
import de.g18.ubb.common.util.PropertyAccessor;
import de.g18.ubb.common.util.StringUtil;

/**
 * Abstrakte Klasse zum (teilweise) automatisierten Testen von Entitäten.
 *
 * @author <a href="mailto:skopatz@gmx.net">Sebastian Kopatz</a>
 */
public abstract class AbstractEntityTest<_Entity extends AbstractEntity> {

    private static final String PROPERTY_PREFIX = "PROPERTY_";

	private _Entity entity;


	@Before
	public final void setUp() {
		entity = createEntity();
		setUpTestCase();
	}

	protected void setUpTestCase() {
	}

	@Test
	public void testPropertyAccessors() {
	    for (Field field : getEntity().getClass().getFields()) {
	        if (StringUtil.startsNotWith(field.getName(), PROPERTY_PREFIX)) {
	            continue;
	        }
	        if (!String.class.isAssignableFrom(field.getType())) {
	            throw new IllegalStateException("A PROPERTY_ field must be of type String!");
	        }

	        String propertyname;
	        try {
	            propertyname = (String) field.get(null);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to read value of field " + field.getName(), e);
            }
            testPropertyAccessors(propertyname);
	    }
	}

	private void testPropertyAccessors(String aPropertyname) {
	    PropertyAccessor<Object> accessor = new PropertyAccessor<Object>(getEntity(), aPropertyname);
	    Class<?> propertyType = accessor.getSetter().getParameterTypes()[0];

	    Object firstObject = getObjectFor(propertyType, true);
	    accessor.invokeSetter(firstObject);
	    Assert.assertEquals(firstObject, accessor.invokeGetter());

	    Object secondObject = getObjectFor(propertyType, false);
	    Assert.assertFalse(ObjectUtil.equals(firstObject, secondObject));

        accessor.invokeSetter(secondObject);
        Assert.assertEquals(secondObject, accessor.invokeGetter());
	}

	private Object getObjectFor(Class<?> aClass, boolean aUseFirstObject) {
        if (aClass.isArray()) {
            if (byte.class.isAssignableFrom(aClass.getComponentType())
                 || Byte.class.isAssignableFrom(aClass.getComponentType())) {
                return aUseFirstObject ? new byte[] {0} : new byte[] {1};
            }
            throw new IllegalArgumentException("No Object is mapped for array of type " + aClass.getComponentType().getName());
        }

	    if (boolean.class.isAssignableFrom(aClass)
	         || Boolean.class.isAssignableFrom(aClass)) {
	        return aUseFirstObject;
	    } else if (long.class.isAssignableFrom(aClass)
	                || Long.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? 0L : 1L;
        } else if (int.class.isAssignableFrom(aClass)
                    || Integer.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? 0 : 1;
        } else if (float.class.isAssignableFrom(aClass)
                    || Float.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? 0.0f : 1.0f;
        } else if (double.class.isAssignableFrom(aClass)
                    || Double.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? 0.0d : 1.0d;
        } else if (String.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? "TEST1" : "TEST2";
        } else if (byte.class.isAssignableFrom(aClass)
                    || Byte.class.isAssignableFrom(aClass)) {
            return aUseFirstObject ? 0 : 1;
        }
	    throw new IllegalArgumentException("No Object is mapped for class " + aClass.getName());
	}

	protected final _Entity getEntity() {
	    return entity;
	}

    // -------------------------------------------------------------------------
    // Abstract behavior
    // -------------------------------------------------------------------------

    protected abstract _Entity createEntity();
}
