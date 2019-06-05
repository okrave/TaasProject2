package com.example.togroup5.demo.repositories;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.example.togroup5.demo.entities.AppRole;
import com.example.togroup5.demo.entities.AppUser;
import com.example.togroup5.demo.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * E' la repository custom quindi qui dobbiamo mettere tutti i metodi che sfrutteranno query
 *
 * */
@Repository("AppUserRepository")
@Transactional

public class AppUserRepository {

    @Autowired
    private AppUserJpa appUserJpa;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AppUserRoleRepository appUserRoleRepository;

    // basic methods


    /**
     * Di default aggiungo un user con Role = ROLE_USER
     * Role = 1 -> ROLE_ADMIN , Role = 2 -> ROLE_USER
     */
    public void save(AppUser user) {
        //AppUser supportUser =
        appUserJpa.save(user);
    }

    public void delete(Long userId){
        appUserJpa.deleteById(userId);
    }

    // query methods

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

    public AppUser findAppUserByUserName(String userName){
        return appUserJpa.findAppUserByUserName(userName);
    }

    public AppUser findAppUserByID(Long userID){
        return appUserJpa.getOne(userID);
    }

    public List<AppUser> findAll(){
        return appUserJpa.findAll();
    }



}