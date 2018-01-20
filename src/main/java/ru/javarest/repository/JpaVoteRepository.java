package ru.javarest.repository;

import org.hibernate.jpa.QueryHints;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javarest.AuthorizedUser;
import ru.javarest.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVoteRepository {

    @PersistenceContext
    private EntityManager em;

    public Vote get(int id) {
        return em.find(Vote.class, id);
    }

    public Vote get(int userId, Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        List<Vote> votes = em.createNamedQuery(Vote.VOTE_BY_DATE, Vote.class).
                setParameter("user_id", userId).
                setParameter("datelunch", sqlDate).
                setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false)
                .getResultList();
        return DataAccessUtils.singleResult(votes);
    }

    @Transactional
    public Vote save(Vote vote) {
        if (vote.isNew()) {
            em.persist(vote);
            return vote;
        } else {
            return em.merge(vote);
        }
    }

    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Vote.DELETE).
                setParameter("id", id).
                setParameter("user_id", AuthorizedUser.id()).
                executeUpdate() != 0;
    }

    public List<Vote> getAllByDate(Date date) {
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        return em.createNamedQuery(Vote.BY_DATE, Vote.class).
                setParameter("datelunch", sqlDate).
                getResultList();
    }

}
