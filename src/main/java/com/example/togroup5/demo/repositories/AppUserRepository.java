package com.example.togroup5.demo.repositories;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.example.togroup5.demo.entities.*;
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

    public boolean delete(Long userId){
        appUserJpa.deleteById(userId);
        return true;
    }

    public boolean delete(String username){
        AppUser user;
        user = findAppUserByUserName(username);
        if(user == null) return false;
        appUserJpa.delete(user);
        return true;
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


    public List<AppUser> listUsersByGroupId(Long groupId) {
        try {
            String sql = "Select u from " + AppUser.class.getName() + //
                    " t JOIN " + GroupUser.class.getName()
                    + " gu ON u.userId = gu.userId WHERE gu.groupId = :groupId";
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

}