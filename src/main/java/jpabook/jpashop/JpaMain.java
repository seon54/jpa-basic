package jpabook.jpashop;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberDTO;
import jpabook.jpashop.domain.Team;

import javax.persistence.*;
import java.util.List;


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

            em.flush();
            em.clear();

            List resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            Object o = resultList.get(0);
            Object[] result = (Object[]) o;
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);

            List<Object[]> resultList2 = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();

            for (Object[] objects : resultList2) {
                System.out.println("objects[0] = " + objects[0]);
                System.out.println("objects[1] = " + objects[1]);
            }

            List<MemberDTO> dtoList = em.createQuery("select new jpabook.jpashop.domain.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            for (MemberDTO memberDTO : dtoList) {
                System.out.println("memberDTO = " + memberDTO.getUsername());
                System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
            }

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
