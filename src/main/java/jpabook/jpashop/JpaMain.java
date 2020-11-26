package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.AddressEntity;
import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("A");
            member.setAddress(new Address("Seoul", "Gangnam", "123"));

            member.getFavoriteFoods().add("pasta");
            member.getFavoriteFoods().add("pizza");
            member.getFavoriteFoods().add("bread");

            member.getAddressHistory().add(new AddressEntity("city1", "street1", "123"));
            member.getAddressHistory().add(new AddressEntity("city1", "street1", "123"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("===============================================");
            Member findMember = em.find(Member.class, member.getId());

            Address a = findMember.getAddress();
            findMember.setAddress(new Address("Incheon", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("pizza");
            findMember.getFavoriteFoods().add("korean food");


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
