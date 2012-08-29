package de.g18.ubb.common.domain;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import de.g18.ubb.common.domain.enumType.BookingType;
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
	    Type genericPropertyType = accessor.getSetter().getGenericParameterTypes()[0];

	    Object firstObject = getObjectFor(genericPropertyType, true);
	    accessor.invokeSetter(firstObject);
	    Assert.assertEquals(firstObject, accessor.invokeGetter());

	    Object secondObject = getObjectFor(genericPropertyType, false);
	    Assert.assertFalse(ObjectUtil.equals(firstObject, secondObject));

        accessor.invokeSetter(secondObject);
        Assert.assertEquals(secondObject, accessor.invokeGetter());
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
    private Object getObjectFor(Type aType, boolean aUseFirstObject) {
	    if (aType instanceof ParameterizedType) {
	        ParameterizedType pType = (ParameterizedType) aType;
	        Class<?> rawClass = (Class<?>) pType.getRawType();
	        if (List.class.isAssignableFrom(rawClass)) {
	            List l = new ArrayList();
	            Class<?> elementType = (Class<?>) pType.getActualTypeArguments()[0];
	            l.add(getObjectFor(elementType, aUseFirstObject));
	            return l;
	        }
	    }

	    Class<?> aClass = (Class<?>) aType;
        if (aClass.isArray()) {
            if (byte.class.isAssignableFrom(aClass.getComponentType())
                 || Byte.class.isAssignableFrom(aClass.getComponentType())) {
                return aUseFirstObject ? new byte[] {0} : new byte[] {1};
            }
            throw new IllegalArgumentException("No Object is mapped for array of type " + aClass.getComponentType().getName());
        }
        if (Enum.class.isAssignableFrom(aClass)) {
            if (BookingType.class.isAssignableFrom(aClass)) {
                return aUseFirstObject ? BookingType.REVENUE : BookingType.SPENDING;
            }
            throw new IllegalArgumentException("No Value is mapped for Enum " + aClass.getName());
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
        } else if (Date.class.isAssignableFrom(aClass)) {
            Calendar cal = Calendar.getInstance();
            if (aUseFirstObject) {
                cal.set(1970, 1, 1);
            } else {
                cal.set(1970, 1, 2);
            }
            return cal.getTime();
        } else if (Booking.class.isAssignableFrom(aClass)) {
            Booking b = new Booking();
            b.setId((Long) getObjectFor(Long.class, aUseFirstObject));
            return b;
        } else if (BudgetBook.class.isAssignableFrom(aClass)) {
            BudgetBook b = new BudgetBook();
            b.setId((Long) getObjectFor(Long.class, aUseFirstObject));
            return b;
        } else if (Category.class.isAssignableFrom(aClass)) {
            Category c = new Category();
            c.setId((Long) getObjectFor(Long.class, aUseFirstObject));
            return c;
        } else if (User.class.isAssignableFrom(aClass)) {
            User u = new User();
            u.setId((Long) getObjectFor(Long.class, aUseFirstObject));
            return u;
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
