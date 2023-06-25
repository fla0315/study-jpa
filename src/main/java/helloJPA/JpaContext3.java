package helloJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaContext3 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            Member member = em.find(Member.class,150L);
            System.out.println("member.getID = "+ member.getId());
            System.out.println("member.getName = "+ member.getName());

            member.setName("changeName");

            System.out.println("================================");
            System.out.println("member.getID = "+ member.getId());
            System.out.println("member.getName = "+ member.getName());

            em.remove(member);

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
