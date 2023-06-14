package helloJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin(); //트랜잭션 시작
        try {
            //등록
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("denver2");
            //em.persist(member);

            Member findMember = em.find(Member.class,1L);

            findMember.setName("denver93");

            System.out.println("findMember.ID = " + findMember.getId());
            System.out.println("findMember.NAME = " + findMember.getName());

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
