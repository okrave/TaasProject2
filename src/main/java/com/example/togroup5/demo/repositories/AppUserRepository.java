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

    /**
     * Di default aggiungo un user con Role = ROLE_USER
     * Role = 1 -> ROLE_ADMIN , Role = 2 -> ROLE_USER
    */
    public void save(AppUser user){
        AppUser supportUser = appUserJpa.save(user);
        UserRole userRole = new UserRole(supportUser,new AppRole(new Long(2)));
        appUserRoleRepository.save(userRole);
    }

    public AppUser findAppUserByUserName(String userName){
        return appUserJpa.findAppUserByUserName(userName);
    }

    public List<AppUser> findAll(){
        return appUserJpa.findAll();
    }




}