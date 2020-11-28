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

            Member member3 = new Member();
            member3.setUsername(null);
            member3.setAge(10);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("관리자");
            em.persist(member4);

            em.flush();
            em.clear();

            // 기본 case 문
            System.out.println("===============  기본 case  ===============");
            String query = "select " +
                    "case when m.age <= 10 then '학생요금' " +
                            "when m.age >= 60 then '경로요금' " +
                            "else '일반요금' " +
                    "end " +
                    "from Member m";
            List<String> resultList = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : resultList) {
                System.out.println("s = " + s);
            }

            // 조건 case식 (coalesce)
            System.out.println("===============  coalesce  ===============");
            String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m";
            List<String> result2 = em.createQuery(query2, String.class).getResultList();

            for (String result : result2) {
                System.out.println("result = " + result);
            }

            // 조건 case식 (nullif)
            System.out.println("===============  nullif  ===============");
            String query3 = "select nullif(m.username, '관리자') from Member m";
            List<String> result3 = em.createQuery(query3, String.class).getResultList();

            for (String result : result3) {
                System.out.println("result = " + result);
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
