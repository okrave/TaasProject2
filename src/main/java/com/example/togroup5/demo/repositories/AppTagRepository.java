package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.GroupTag;
import org.glassfish.jersey.jaxb.internal.XmlJaxbElementProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class AppTagRepository {

    @Autowired
    private AppTagJpa appTagJpa;

    @Autowired
    private EntityManager entityManager;


    //basic methods

    public void save(AppTag tag) {
        //AppTag supportTag =
        appTagJpa.save(tag);
    }

    public boolean delete(Long tagId) {
        appTagJpa.deleteById(tagId);
        return true;
    }

    // query

    public AppTag findAppTagByID(Long tagId) {
        return appTagJpa.getOne(tagId);
    }

    public List<AppTag> findAll() {
        return appTagJpa.findAll();
    }


    // query

    public AppTag findTagByName(String name) {
        try {
            String sql = "Select t from " + AppTag.class.getName() + " t " //
                    + " Where t.name = :name ";

            Query query = entityManager.createQuery(sql, AppTag.class);
            query.setParameter("name", name);

            return (AppTag) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AppTag> listTagsByGroupId(Long groupId) {
        try {
            String sql = "Select t from " + AppTag.class.getName() + //
                    " t JOIN " + GroupTag.class.getName()
                    + " gt ON t.tagId = gt.tagId WHERE gt.groupId = :groupId";
            Query query = entityManager.createQuery(sql, AppTag.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
