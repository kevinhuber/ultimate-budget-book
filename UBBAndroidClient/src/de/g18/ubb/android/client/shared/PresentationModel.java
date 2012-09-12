package de.g18.ubb.android.client.shared;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.PropertyAccessor;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class PresentationModel<_Bean extends AbstractModel> extends AbstractModel {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTYNAME_BEFORE_BEAN_CHANGE = "beforeBeanChange";
    public static final String PROPERTYNAME_AFTER_BEAN_CHANGE = "afterBeanChange";

    private final Map<String, PropertyDescriptor> modelCache;

    private ValueModel beanChannel;


    public PresentationModel() {
        this((_Bean) null);
    }

    public PresentationModel(_Bean aBean) {
        this(new ValueModel(aBean));
    }

    public PresentationModel(ValueModel aBeanChannel) {
        beanChannel = aBeanChannel;
        modelCache = new HashMap<String, PropertyDescriptor>();

        initEventHandling();
    }

    private void initEventHandling() {
        addPropertyChangeListener(PROPERTYNAME_BEFORE_BEAN_CHANGE, new BeforeBeanChangeHandler());
        beanChannel.addValueChangeListener(new BeanChangeHandler());
        addPropertyChangeListener(PROPERTYNAME_AFTER_BEAN_CHANGE, new AfterBeanChangeHandler());
    }

    public final void setBean(_Bean aNewValue) {
        _Bean oldValue = getBean();
        fireChange(PROPERTYNAME_BEFORE_BEAN_CHANGE, oldValue, aNewValue);
        beanChannel.setValue(aNewValue);
        fireChange(PROPERTYNAME_AFTER_BEAN_CHANGE, oldValue, aNewValue);
    }

    @SuppressWarnings("unchecked")
    public final _Bean getBean() {
        return (_Bean) beanChannel.getValue();
    }

    public final void setValue(String aPropertyname, Object aNewValue) {
        getModel(aPropertyname).setValue(aNewValue);
    }

    public final Object getValue(String aPropertyname) {
        return getModel(aPropertyname).getValue();
    }

    public final ValueModel getModel(String aPropertyname) {
        if (modelCache.containsKey(aPropertyname)) {
            return modelCache.get(aPropertyname).model;
        }
        return createAndStoreModel(aPropertyname);
    }

    private ValueModel createAndStoreModel(String aPropertyname) {
        PropertyDescriptor descriptor = new PropertyDescriptor(aPropertyname);
        modelCache.put(aPropertyname, descriptor);
        return descriptor.model;
    }


    // -------------------------------------------------------------------------
    // Inner Classes
    // -------------------------------------------------------------------------

    private final class PropertyDescriptor {

        private final PropertyAccessor<Object> propertyAccessor;
        private final ValueModel model;

        private final PropertyChangeListener modelToBeanSynchronizer;
        private final PropertyChangeListener beanToModelSynchronizer;


        public PropertyDescriptor(String aPropertyname) {
            propertyAccessor = new PropertyAccessor<Object>(getBean(), aPropertyname);
            model = new ValueModel(getBean() == null ? null : propertyAccessor.invokeGetter());

            modelToBeanSynchronizer = new ModelToBeanSynchronizer(this);
            beanToModelSynchronizer = new BeanToModelSynchronizer(this);

            initEventHandling();
        }

        private void initEventHandling() {
            model.addValueChangeListener(modelToBeanSynchronizer);
            addBeanToModelSynchronizer();
        }

        public void addBeanToModelSynchronizer() {
            getBean().addPropertyChangeListener(getPropertyAccessor().getPropertyname(), beanToModelSynchronizer);
        }

        public void removeBeanToModelSynchronizer() {
            getBean().removePropertyChangeListener(getPropertyAccessor().getPropertyname(), beanToModelSynchronizer);
        }

        public ValueModel getModel() {
            return model;
        }

        public PropertyAccessor<Object> getPropertyAccessor() {
            return propertyAccessor;
        }
    }

    private final class ModelToBeanSynchronizer implements PropertyChangeListener {

        private final PropertyDescriptor entry;


        public ModelToBeanSynchronizer(PropertyDescriptor aEntry) {
            entry = aEntry;
        }

        public void propertyChange(PropertyChangeEvent event) {
            entry.getPropertyAccessor().invokeSetter(entry.getModel().getValue());
        }
    }

    private final class BeanToModelSynchronizer implements PropertyChangeListener {

        private final PropertyDescriptor entry;


        public BeanToModelSynchronizer(PropertyDescriptor aEntry) {
            entry = aEntry;
        }

        public void propertyChange(PropertyChangeEvent event) {
            entry.getModel().setValue(entry.getPropertyAccessor().invokeGetter());
        }
    }

    private final class BeforeBeanChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent aEvent) {
            for (PropertyDescriptor cacheEntry : modelCache.values()) {
                cacheEntry.removeBeanToModelSynchronizer();
            }
        }
    }

    private final class BeanChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent aEvent) {
            for (PropertyDescriptor cacheEntry : modelCache.values()) {
                cacheEntry.getPropertyAccessor().setModel(getBean());
                cacheEntry.getModel().setValue(getBean() == null ? null : cacheEntry.getPropertyAccessor().invokeGetter());
            }
        }
    }

    private final class AfterBeanChangeHandler implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent aEvent) {
            for (PropertyDescriptor cacheEntry : modelCache.values()) {
                cacheEntry.addBeanToModelSynchronizer();
            }
        }
    }
}
