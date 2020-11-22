package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import org.hibernate.Hibernate;

import javax.persistence.*;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setName("user1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.getReference(Member.class, member1.getId());
            System.out.println("m1 = " + m1.getClass());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m1));
            Hibernate.initialize(m1);
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(m1));


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
