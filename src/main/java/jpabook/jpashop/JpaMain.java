package jpabook.jpashop;

import jpabook.jpashop.domain.Member;

import javax.persistence.*;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("userA");
            em.persist(member);

            Member result = em.createQuery("select m from Member as m where m.username = :username", Member.class)
                    .setParameter("username", "userA")
                    .getSingleResult();
            System.out.println("result = " + result.getUsername());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.clear();
        }
        emf.close();
    }
}
