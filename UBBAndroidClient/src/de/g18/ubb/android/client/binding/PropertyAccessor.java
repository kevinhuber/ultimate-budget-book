package de.g18.ubb.android.client.binding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
final class PropertyAccessor<_PropertyType> {

    private final AbstractModel model;
    private final String propertyname;

    private Method getter;
    private Method setter;


    public PropertyAccessor(AbstractModel aModel, String aPropertyname) {
        model = aModel;
        propertyname = aPropertyname;
    }

    @SuppressWarnings("unchecked")
    public _PropertyType invokeGetter() {
        try {
            return (_PropertyType) getGetter().invoke(model);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException(getFullGetterName() + " should not have any arguments!", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(getFullGetterName() + " is not visible!", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(getFullGetterName() + " could not be invoked!", e);
        }
    }

    private String getFullGetterName() {
        return model.getClass().getName() + "#" + getGetter().getName();
    }

    private Method getGetter() {
        if (getter == null) {
            getter = resolveGetter();
        }
        return getter;
    }

    private Method resolveGetter() {
        for (Method m : model.getClass().getMethods()) {
            if (StringUtil.startsNotWith(m.getName(), "get")
                  && StringUtil.startsNotWith(m.getName(), "is")) {
                continue;
            }
            if (StringUtil.endsWithIgnoreCase(m.getName(), propertyname)) {
                return m;
            }
        }
        throw new IllegalStateException("Getter for property " + propertyname + " not found in class " + model.getClass().getName() + "!");
    }

    public void invokeSetter(_PropertyType aNewValue) {
        try {
            getSetter().invoke(model, aNewValue);
        } catch (IllegalArgumentException e) {
            String argumentType = aNewValue == null ? StringUtil.NULL : aNewValue.getClass().getName();
            throw new IllegalStateException(getFullSetterName() + " is not applicable for a argument of type "
                                            + argumentType + "!", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(getFullSetterName() + " is not visible!", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(getFullSetterName() + " could not be invoked!", e);
        }
    }

    private String getFullSetterName() {
        return model.getClass().getName() + "#" + getSetter().getName();
    }

    private Method getSetter() {
        if (setter == null) {
            setter = resolveSetter();
        }
        return setter;
    }

    private Method resolveSetter() {
        for (Method m : model.getClass().getMethods()) {
            if (StringUtil.startsNotWith(m.getName(), "set")) {
                continue;
            }
            if (StringUtil.endsWithIgnoreCase(m.getName(), propertyname)) {
                return m;
            }
        }
        throw new IllegalStateException("Setter for property " + propertyname + " not found in class " + model.getClass().getName() + "!");
    }
}
