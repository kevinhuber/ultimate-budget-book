package de.g18.ubb.android.client.shared;

import java.util.HashMap;
import java.util.Map;

import de.g18.ubb.common.domain.AbstractModel;
import de.g18.ubb.common.util.PropertyAccessor;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class PresentationModel<_Bean extends AbstractModel> extends AbstractModel {

    private static final long serialVersionUID = 1L;

    private final Map<String, ValueModel> modelCache;

    private ValueModel beanChannel;


    public PresentationModel() {
        this((_Bean) null);
    }

    public PresentationModel(_Bean aBean) {
        this(new ValueModel(aBean));
    }

    public PresentationModel(ValueModel aBeanChannel) {
        beanChannel = aBeanChannel;
        modelCache = new HashMap<String, ValueModel>();
    }

    @SuppressWarnings("unchecked")
    public final _Bean getBean() {
        return (_Bean) getBeanChannel().getValue();
    }

    public final ValueModel getBeanChannel() {
        return beanChannel;
    }

    public final void setValue(String aPropertyname, Object aNewValue) {
        getModel(aPropertyname).setValue(aNewValue);
    }

    public final Object getValue(String aPropertyname) {
        return getModel(aPropertyname).getValue();
    }

    public final ValueModel getModel(String aPropertyname) {
        if (modelCache.containsKey(aPropertyname)) {
            return modelCache.get(aPropertyname);
        }
        return createAndStoreModel(aPropertyname);
    }

    // TODO (huber): Korrigieren!
    private ValueModel createAndStoreModel(String aPropertyname) {
        PropertyAccessor<Object> accessor = new PropertyAccessor<Object>(getBean(), aPropertyname);
        ValueModel model = new ValueModel(accessor.invokeGetter());
        modelCache.put(aPropertyname, model);
        return model;
    }
}
