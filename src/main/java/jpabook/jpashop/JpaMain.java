package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


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
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            Team team2 = new Team();
            team2.setName("teamB");
            em.persist(team2);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(10);
            member2.changeTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select m from Member m left join Team t on t.name = m.username";
            List<Member> members = em.createQuery(query, Member.class)
                    .getResultList();

            System.out.println("members.size() = " + members.size());
            for (Member mem : members) {
                System.out.println("mem = " + mem);
                System.out.println("mem.getTeam().getName() = " + mem.getTeam().getName());
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
