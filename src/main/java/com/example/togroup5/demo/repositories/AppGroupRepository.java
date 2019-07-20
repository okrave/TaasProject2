package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.AppGroup;
import com.example.togroup5.demo.entities.AppTag;
import com.example.togroup5.demo.entities.GroupTag;
import com.example.togroup5.demo.entities.GroupUser;
import com.example.togroup5.demo.entities.payloadsResults.GroupSearchAdvPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository("AppGroupRepository")
@Transactional
public class AppGroupRepository {

    @Autowired
    private AppGroupJpa appGroupJpa;

    @Autowired
    private EntityManager entityManager;

    //basic methods

    public void save(AppGroup group) {
        //AppGroup supportUser =
        appGroupJpa.save(group);
    }

    public boolean delete(Long groupId) {
        appGroupJpa.deleteById(groupId);
        return true;
    }

    // query

    public AppGroup findAppGroupByID(Long groupId) {
        return appGroupJpa.getOne(groupId);
    }

    public List<AppGroup> findAll() {
        return appGroupJpa.findAll();
    }


    public List<AppGroup> findDistinctByCreator(String creator) {
        try {
            String sql = "Select e from " + AppGroup.class.getName() + " e " //
                    + " Where e.creator = :creator ";

            Query query = entityManager.createQuery(sql, AppGroup.class);
            query.setParameter("creator", creator);

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public AppGroup findDistinctByGroupName(String groupName) {
        try {
            String sql = "Select e from " + AppGroup.class.getName() + " e " //
                    + " Where e.groupName = :groupName ";

            Query query = entityManager.createQuery(sql, AppGroup.class);
            query.setParameter("groupName", groupName);

            return (AppGroup) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AppGroup> listGroupsByUserId(Long userId){
        try {
            String sql = "Select g from " + AppGroup.class.getName() //
                    + " g JOIN " + GroupUser.class.getName() + " gu ON (gu.groupId = g.groupId)" //
                    + " Where gu.userId = :userId ";

            Query query = entityManager.createQuery(sql, AppGroup.class);
            query.setParameter("userId", userId);

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AppGroup> advancedSearch(GroupSearchAdvPayload filters) {
        boolean isNotFirstFilter, hasStartDate, hasEndDate;
        String orderTagMatchAmountClause;
        List<SingleFilter> appliedFields;
        StringBuilder sb;
        String andConnector, sql;
        TypedQuery<AppGroup> query;

        if (filters == null) return null;
        try {
            orderTagMatchAmountClause = null;
            andConnector = " AND ";
            appliedFields = new LinkedList<>();

            // TODO: add location and, if exists, add optionally maxDistance
            //if(filters.getLocation() != null) {}

            if (filters.getGroupName() != null && (!filters.getGroupName().equals(""))) {
                appliedFields.add(new SingleFilter("g.groupName = :groupName", "groupName", filters.getGroupName()));
            }

            if (filters.getCreator() != null && (!filters.getCreator().equals(""))) {
                appliedFields.add(new SingleFilter("g.creator = :creator", "creator", filters.getCreator()));
            }

            hasStartDate    = filters.getDateStartRange() != null;
            hasEndDate      = filters.getDateEndRange() != null;
            if(hasStartDate && hasEndDate){
                appliedFields.add(new SingleFilter("g.groupDate >= :dateStartRange", "dateStartRange", filters.getDateStartRange()));
                appliedFields.add(new SingleFilter("g.groupDate <= :dateEndRange", "dateEndRange", filters.getDateEndRange()));
            } else if (hasStartDate ^ hasEndDate){
                //solo uno delle due= cerchiamo la data esatta
                Date exactDate;
                exactDate = hasStartDate ? filters.getDateStartRange() : filters.getDateEndRange();
                appliedFields.add(new SingleFilter("g.groupDate = :date", "date", exactDate));
            } // else: no date filters setted

            // questo filtro deve rimanere per ultimo

            if (filters.getTags() != null) {
                /*
                    Si filtrano quei gruppi il cui insieme di tag associati forma una intersezione non vuota con
                    l'insieme di tag fornito come filtro di ricerca, ordinando poi tali gruppi in senso decrescente.
                    A tal proposito, si ottengono tutti i tag con qualche bel JOIN e poi si verifica se i tag del gruppo
                    sono effettivamente contenuti nell'insieme fornito con la parola chiave "IN" (non so se è corretta,
                    servono dei test), contando poi l'ammontare di tag del gruppo effettivamente contenuti nell'insieme
                    fornito.
                 * */
                orderTagMatchAmountClause = //
                        "0 < ( SELECT count(*) AS tagMatches FROM " + GroupTag.class.getName() + " gt JOIN "
                                + AppTag.class.getName() +
                                " tag ON (gt.tagId = tag.tagId) WHERE ((g.groupId = gt.groupId) AND (tag.name IN ANY (:tags)))";
                appliedFields.add(new SingleFilter(orderTagMatchAmountClause, "tags", //
                        filters.getTags().toArray(new String[filters.getTags().size()])));
            }

            if (appliedFields.isEmpty()) return null;

            sb = new StringBuilder(32);
            sb.append("SELECT g FROM ");
            sb.append(AppGroup.class.getName());
            sb.append(" g WHERE ");

            isNotFirstFilter = false;
            for (SingleFilter filter : appliedFields) {
                if (isNotFirstFilter) sb.append(andConnector);
                sb.append(filter.clause);
                isNotFirstFilter = true;
            }

            if (orderTagMatchAmountClause != null)
                sb.append(" ORDER BY tagMatches DESC ");

            query = entityManager.createQuery(sql = sb.toString(), AppGroup.class);
            sb = null;
            System.out.println("Huge advanced group query:\n\t");
            System.out.println(sql);
            sql = null;

            appliedFields.forEach(filter -> query.setParameter(filter.nameParameter, filter.parameter));

            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }



    static class SingleFilter {
        String nameParameter, clause;
        Object parameter;

        SingleFilter(String clause, String nameParameter, Object parameter) {
            this.clause = clause;
            this.nameParameter = nameParameter;
            this.parameter = parameter;
        }
    }
}
