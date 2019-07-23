package com.example.togroup5.demo.repositories;



import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.example.togroup5.demo.entities.*;
import com.example.togroup5.demo.entities.payloadsResults.UserLoginPayload;
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
    private UserLoginPayload userEnailPassword;

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
                    " u JOIN " + GroupUser.class.getName()
                    + " gu ON u.userId = gu.userId WHERE gu.groupId = :groupId";
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("groupId", groupId);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public AppUser findByEmailPassword(UserLoginPayload userEnailPassword){
        //return appUserJpa.findAppUserByUserName()
        try {
            String sql = "Select u from " + AppUser.class.getName() + //
                    " WHERE u.userEmail = :email ";// AND u.password = :pwd";

            System.out.println("Query: " + sql);
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("email", userEnailPassword.getEmail());
            //query.setParameter("pwd", userEnailPassword.getPassword());
            return  (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public AppUser findByEmailOrUsernameAndPassword(UserLoginPayload userEnailPassword){
        try {
            String sql = "Select u from " + AppUser.class.getName() + //
                    " u WHERE (u.userEmail = :email OR u.userName = :email) ";// AND u.encrytedPassword = :pwd";

            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("email", userEnailPassword.getEmail());
            return  (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    public AppUser findByUserNameAndPassword(String userName, String password){
        try {
            String sql= "Select u from " + AppUser.class.getName() + //
                    " u WHERE (u.userName = :username AND ";// u.encrytedPassword = :pwd)";

            System.out.println("Query: " + sql);
            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("username", userName);
            //query.setParameter("pwd", password);
            return  (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }




}