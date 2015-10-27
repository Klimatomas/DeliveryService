package cz.muni.fi.pa165.deliveryservice.dao;

import cz.muni.fi.pa165.deliveryservice.dao.access.DBHandler;
import cz.muni.fi.pa165.deliveryservice.entity.DBPerson;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Matej Leško on 2015-10-26.
 * Email: lesko.matej.pu@gmail.com, mlesko@redhat.com
 * Phone: +421 949 478 066
 * <p/>
 * Project: delivery-service
 */
@Transactional
@Repository
public abstract class Person<E extends DBPerson> extends Entity<E> implements PersonTemplate<E> {

    public Person(Class<E> eClass) {
        super(eClass);
    }

    @Override
    public List<E> findByName(String name) {
        if (dbHandler == null)
            dbHandler =  new DBHandler<E>(em, eClass);
        return dbHandler.findByName(name);
    }

    @Override
    public E findByEmail(String email) {
        if (dbHandler == null)
            dbHandler =  new DBHandler<E>(em, eClass);
        return dbHandler.findByEmail(email);
    }
}
