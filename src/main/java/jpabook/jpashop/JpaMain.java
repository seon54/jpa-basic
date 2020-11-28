package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.MemberType;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
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

            // 단일 값 연관 경로: 묵시적 내부 조인 발생, 탐색 O
            String query = "select m.team.name from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            // 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색 X
            String query2 = "select t.members from Team t";
            Collection results2 = em.createQuery(query2, Collection.class)
                    .getResultList();

            for (Object s : results2) {
                System.out.println("s = " + s);
            }

            // 명시적 조인
            String query3 = "select m.username from Team t join t.members m";
            List<String> results3 = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : results3) {
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
