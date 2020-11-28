package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberType;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static jpabook.jpashop.domain.MemberType.ADMIN;


public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(70);
            member.changeTeam(team);
            member.setType(ADMIN);
            em.persist(member);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.changeTeam(team2);
            member2.setType(MemberType.USER);
            em.persist(member2);

            em.flush();
            em.clear();

            // concat
            System.out.println("===============  concat  ===============");
            String query = "select 'a' || 'b' from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            String query2 = "select concat('a', 'b') from Member m";
            List<String> result2 = em.createQuery(query2, String.class)
                    .getResultList();

            for (String s : result2) {
                System.out.println("s = " + s);
            }

            // substring
            System.out.println("===============  substring  ===============");
            String query3 = "select substring(m.username, 2, 3) from Member m";
            List<String> result3 = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : result3) {
                System.out.println("s = " + s);
            }

            // trim
            System.out.println("===============  trim  ===============");
            String query4 = "select trim(m.username) from Member m";
            List<String> result4 = em.createQuery(query4, String.class)
                    .getResultList();

            for (String s : result4) {
                System.out.println("s = " + s);
            }

            // size
            System.out.println("===============  size  ===============");
            String query5 = "select size(t.members) from Team t";
            List<Integer> result5 = em.createQuery(query5, Integer.class)
                    .getResultList();

            for (Integer s : result5) {
                System.out.println("s = " + s);
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
