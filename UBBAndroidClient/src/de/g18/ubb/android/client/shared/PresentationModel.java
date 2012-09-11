package de.g18.ubb.android.client.shared;

import de.g18.ubb.common.domain.AbstractModel;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
public class PresentationModel<_Bean extends AbstractModel> extends AbstractModel {

    private static final long serialVersionUID = 1L;

    private ValueModel<_Bean> beanChannel;


    public PresentationModel() {
        this((_Bean) null);
    }

    public PresentationModel(_Bean aBean) {
        this(new ValueModel<_Bean>(aBean));
    }

    public PresentationModel(ValueModel<_Bean> aBeanChannel) {
        beanChannel = aBeanChannel;
    }

    public final _Bean getBean() {
        return getBeanChannel().getValue();
    }

    public final ValueModel<_Bean> getBeanChannel() {
        return beanChannel;
    }
}
