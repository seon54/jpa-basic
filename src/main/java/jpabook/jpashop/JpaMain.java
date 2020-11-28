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
            Team team1 = new Team();
            team1.setName("teamA");
            em.persist(team1);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(70);
            member.changeTeam(team1);
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
            member3.setUsername("member3");
            member3.setAge(10);
            member3.changeTeam(team2);
            member3.setType(MemberType.USER);
            em.persist(member3);

            em.flush();
            em.clear();

            // entity 직접 사용
            String query = "select m from Member m where m = :member";
            Member findMember = em.createQuery(query, Member.class)
                    .setParameter("member", member)
                    .getSingleResult();

            System.out.println("findMember = " + findMember);

            // 식별자 직접 전달
            String query2 = "select m from Member m where m.id = :memberId";
            Member findMember2 = em.createQuery(query2, Member.class)
                    .setParameter("memberId", member.getId())
                    .getSingleResult();

            System.out.println("findMember2 = " + findMember2);

            // entity 직접 사용 - FK
            String query3 = "select m from Member m where m.team = :team";
            Member findMember3 = em.createQuery(query3, Member.class)
                    .setParameter("team", team1)
                    .getSingleResult();
            System.out.println("findMember3 = " + findMember3);

            String query4 = "select m from Member m where m.team.id = :teamId";
            Member findMember4 = em.createQuery(query4, Member.class)
                    .setParameter("teamId", team1.getId())
                    .getSingleResult();
            System.out.println("findMember4 = " + findMember4);

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
