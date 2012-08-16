package de.g18.ubb.server.service;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import de.g18.ubb.common.domain.TestEntity;
import de.g18.ubb.common.service.remote.TestServiceRemote;
import de.g18.ubb.server.service.local.TestServiceLocal;

/**
 * @author <a href="mailto:kevinhuber.kh@gmail.com">Kevin Huber</a>
 */
@Local(TestServiceLocal.class)
@Remote(TestServiceRemote.class)
@Stateless
public class TestServiceImpl extends AbstractPersistedBean<TestEntity> implements TestServiceLocal, TestServiceRemote {

    @Override
    protected Class<TestEntity> getEntityClass() {
        return TestEntity.class;
    }

    @Override
    public String sayHello() {
        TestEntity e = new TestEntity();
        e.setText("Hello " + Math.random());
        save(e);

        StringBuilder b = new StringBuilder("\n");
        for (TestEntity en : getAll()) {
            b.append("Entry: " + en.getText() + "\n");
        }
        return b.toString();
    }
}
