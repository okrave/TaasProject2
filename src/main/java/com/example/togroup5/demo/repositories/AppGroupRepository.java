package com.example.togroup5.demo.repositories;

import com.example.togroup5.demo.entities.*;
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

    public List<AppGroup> listGroupsByUserId(Long userId) {
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
        boolean groupByNeeded;
        boolean isNotFirstFilter, hasStartDate, hasEndDate;
        String orderTagMatchAmountClause, tagMatchJoin//
                , locationJoin;
        List<SingleFilter> appliedFields;
        StringBuilder sb;
        String andConnector;
        TypedQuery<AppGroup> query;

        if (filters == null) return null;
        try {
            groupByNeeded = false;
            orderTagMatchAmountClause = tagMatchJoin = locationJoin = null;
            andConnector = " AND ";
            appliedFields = new LinkedList<>();

            if (filters.getLocation() != null) {
                boolean hasDist;
                double lat, lng;
                String calc;
                hasDist = false;
                lat = Math.toRadians(filters.getLocation().getLat());
                lng = Math.toRadians(filters.getLocation().getLng());
                groupByNeeded = true;
                locationJoin = " JOIN " + GoogleLocation.class.getName() + " loc ON g.locationId = loc.locationId ";
                //exact location
                calc = " radians(loc.lat) = :lat AND radians(loc.lng) = :lng ";
                if (filters.getMaxDistance() != null && filters.getMaxDistance() > 0.0) {
                    hasDist = true;
                    /*
                     * (Math.toDegrees(Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2)))
                     * 60 * 1.1515 * 1.609344)
                     * */
                    calc = " ((" + calc + ") OR ( :dist >= abs(degrees( acos(sin( :lat) * sin(radians(loc.lat)) + cos( :lat) * cos(radians(loc.lat)) * cos( :lng - radians(loc.lng)) ) ) * 60 * 1.1515 * 1.609344)) ) ";
                }
                appliedFields.add(new SingleFilter(calc, "lat", lat));
                appliedFields.add(new SingleFilter("", "lng", lng));
                if (hasDist)
                    appliedFields.add(new SingleFilter("", "dist", filters.getMaxDistance()));

//                radians
            }

            if (filters.getGroupName() != null && (!filters.getGroupName().equals(""))) {
                appliedFields.add(new SingleFilter("g.groupName = :groupName", "groupName", filters.getGroupName()));
            }

            if (filters.getCreatorMember() != null && (!filters.getCreatorMember().equals(""))) {
                String creatorMember;
                creatorMember = filters.getCreatorMember();
                if (filters.isSearchingByCreator())
                    appliedFields.add(new SingleFilter("g.creator = :creator", "creator", creatorMember));
                else
                    appliedFields.add(new SingleFilter( // test se creatorMember e' membro del gruppo dato
                            " 0 < (SELECT count(*) FROM " + AppUser.class.getName() //
                                    + " u JOIN " + GroupUser.class.getName() + //
                                    " gu ON u.userId = gu.userId WHERE u.userName = :member )", //
                            "member", creatorMember));
            }

            hasStartDate = filters.getDateStartRange() != null;
            hasEndDate = filters.getDateEndRange() != null;
            if (hasStartDate && hasEndDate) {
                appliedFields.add(new SingleFilter("g.groupDate >= :dateStartRange", "dateStartRange", filters.getDateStartRange()));
                appliedFields.add(new SingleFilter("g.groupDate <= :dateEndRange", "dateEndRange", filters.getDateEndRange()));
            } else if (hasStartDate ^ hasEndDate) {
                //solo uno delle due= cerchiamo la data esatta
                Date exactDate;
                exactDate = hasStartDate ? filters.getDateStartRange() : filters.getDateEndRange();
                appliedFields.add(new SingleFilter("g.groupDate >= :date", "date", exactDate));
            } // else: no date filters setted

            if (filters.getTags() != null && filters.getTags().size() > 0) {
                groupByNeeded = true;
                /*
                    Si filtrano quei gruppi il cui insieme di tag associati forma una intersezione non vuota con
                    l'insieme di tag fornito come filtro di ricerca, ordinando poi tali gruppi in senso decrescente.
                    A tal proposito, si ottengono tutti i tag con qualche bel JOIN e poi si verifica se i tag del gruppo
                    sono effettivamente contenuti nell'insieme fornito con la parola chiave "IN" (non so se è corretta,
                    servono dei test), contando poi l'ammontare di tag del gruppo effettivamente contenuti nell'insieme
                    fornito.
                 * */
                /*tagMatchJoin = " JOIN " + GroupTag.class.getName() + " gt ON g.groupId = gt.groupId JOIN " + //
                        AppTag.class.getName() + " tag ON gt.tagId = tag.tagId ";
                orderTagMatchAmountClause = " (SELECT count(*) from tag WHERE tag.name IN :tags ) ";
                */
                tagMatchJoin = " JOIN " + GroupTag.class.getName() + " gt ON g.groupId = gt.groupId JOIN " + //
                        AppTag.class.getName() + " tag ON gt.tagId = tag.tagId ";
                orderTagMatchAmountClause = "(SELECT count(*) from tag WHERE gt.tagId = tag.tagId AND tag.name IN :tags )";

                appliedFields.add(new SingleFilter(" 0 < " + orderTagMatchAmountClause, "tags", // "", //
                        filters.getTags()));
            }

            if (appliedFields.isEmpty()) return null;

            sb = new StringBuilder(127);
            sb.append("SELECT g FROM ");
            sb.append(AppGroup.class.getName());
            sb.append(" g ");
            if (tagMatchJoin != null)
                sb.append(tagMatchJoin);
            if (locationJoin != null)
                sb.append(locationJoin);
            sb.append(" WHERE ");

            isNotFirstFilter = false;
            for (SingleFilter filter : appliedFields) {
                if (!"".equals(filter.clause.trim())) {
                    if (isNotFirstFilter) sb.append(andConnector);
                    sb.append(filter.clause);
                }
                isNotFirstFilter = true;
            }

            if (groupByNeeded)
                sb.append(" GROUP BY g.groupId");

            if (orderTagMatchAmountClause != null) {
                // dovrebbe essere DESC ma stranamente li ordina in senso contrario
                //sb.append(" ORDER BY count(tag.name) ASC");
                sb.append(" ORDER BY count(g) DESC");
                //sb.append(" ORDER BY ").append(orderTagMatchAmountClause).append(" ASC");

            }
            String sql;
            query = entityManager.createQuery(sql = sb.toString(), AppGroup.class);
            System.out.println("QUERY ADV:\n\t" + sql);
            sb = null;

            appliedFields.forEach(filter -> {
                if (!"".equals(filter.nameParameter)) query.setParameter(filter.nameParameter, filter.parameter);
            });

            return query.getResultList();
        } catch (
                NoResultException e) {
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
