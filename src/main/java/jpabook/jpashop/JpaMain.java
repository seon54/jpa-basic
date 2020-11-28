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

            // 묵시적 join
            System.out.println("================================================================");
            String query1 = "select m from Member m";
            List<Member> results1 = em.createQuery(query1, Member.class).getResultList();

            for (Member member1 : results1) {
                System.out.println("member = " + member1 + " / team1: " + member1.getTeam().getName());
            }

            // join fetch
            System.out.println("============================ join fetch ====================================");
            String query2 = "select m from Member m join fetch m.team";
            List<Member> results2 = em.createQuery(query2, Member.class).getResultList();

            for (Member r : results2) {
                System.out.println("member = " + r.getUsername() + " / team1 = " + r.getTeam());
            }

            // join fetch(collection)
            System.out.println("============================== join fetch (collection) ==================================");
            String query3 = "select t From Team t join fetch t.members";
            List<Team> results3 = em.createQuery(query3, Team.class).getResultList();

            for (Team team : results3) {
                System.out.println("team = " + team.getName());
                for (Member teamMember : team.getMembers()) {
                    System.out.println("--> teamMember = " + teamMember.getUsername());
                }
            }

            // join fetch & distinct
            System.out.println("============================== join fetch & distinct ==================================");
            String query4 = "select distinct t From Team t join fetch t.members";
            List<Team> results4 = em.createQuery(query4, Team.class).getResultList();

            for (Team team : results4) {
                System.out.println("team = " + team.getName());
                for (Member teamMember : team.getMembers()) {
                    System.out.println("--> teamMember = " + teamMember.getUsername());
                }
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
