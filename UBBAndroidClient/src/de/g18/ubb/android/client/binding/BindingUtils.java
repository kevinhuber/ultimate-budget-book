package de.g18.ubb.android.client.binding;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.StringUtil;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public final class BindingUtils {

    private BindingUtils() {
        // prevent instantiation
    }

    public static void bind(EditText aEditText, AbstractModel aModel, String aPropertyname) {
        new EditTextConnector(aEditText, aModel, aPropertyname);
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private static final class EditTextConnector extends AbstractPropertyConnector<String> implements TextWatcher {

        private final EditText editText;


        public EditTextConnector(EditText aEditText, AbstractModel aModel, String aPropertyname) {
            super(aModel, aPropertyname);
            editText = aEditText;

            updateComponent();
            editText.addTextChangedListener(this);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Ignore
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Ignore
        }

        public void afterTextChanged(Editable aEditable) {
            updateModel(aEditable.toString());
        }

        @Override
        void updateComponent(String aNewValue) {
            editText.setText(aNewValue);
        }
    }

    private static abstract class AbstractPropertyConnector<_PropertyType> implements PropertyChangeListener {

        private PropertyAccessor<_PropertyType> propertyAccessor;
        private boolean running;


        public AbstractPropertyConnector(AbstractModel aModel, String aPropertyname) {
            propertyAccessor = new PropertyAccessor<_PropertyType>(aModel, aPropertyname);
            aModel.addPropertyChangeListener(aPropertyname, this);
        }

        public final void propertyChange(PropertyChangeEvent event) {
            if (running) {
                return;
            }
            running = true;

            try {
                updateComponent();
            } finally {
                running = false;
            }
        }

        protected final void updateComponent() {
            _PropertyType value = propertyAccessor.invokeGetter();
            updateComponent(value);
        }

        protected final void updateModel(_PropertyType aNewValue) {
            if (running) {
                return;
            }
            running = true;

            try {
                propertyAccessor.invokeSetter(aNewValue);
            } finally {
                running = false;
            }
        }

        abstract void updateComponent(_PropertyType aNewValue);
    }

    private static final class PropertyAccessor<_PropertyType> {

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
}
