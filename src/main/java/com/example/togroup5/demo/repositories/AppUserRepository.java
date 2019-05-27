package com.example.togroup5.demo.repositories;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import com.example.togroup5.demo.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * E' la repository custom quindi qui dobbiamo mettere tutti i metodi che sfrutteranno query
 *
 * */
@Repository("AppUserRepository")
@Transactional

public class AppUserRepository {

    @Autowired
    AppUserJpa appUserJpa;

    @Autowired
    private EntityManager entityManager;

    public AppUser findUserAccount(String userName) {
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.userName = :userName ";

            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);

            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void save(AppUser user){
        appUserJpa.save(user);
    }

    AppUser findAppUserByUserName(String userName){
        return appUserJpa.findAppUserByUserName(userName);
    }




}