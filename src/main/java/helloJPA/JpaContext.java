package helloJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaContext {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {

            //아직은 비영속 상태
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            //아래코드부터 영속 상태
            System.out.println("=== BEFORE ===");
            em.persist(member); //현재 1차 캐쉬에 저장된 상태임
            System.out.println("=== AFTER === ");

            Member findMember =  em.find(Member.class,101L);
            System.out.println("findMember.getId = "+findMember.getId());
            System.out.println("findMember.getName = "+findMember.getName());

            /*
             === BEFORE ===
             === AFTER ===
             findMember.getId = 101
             findMember.getName = HelloJPA
             Hibernate:
              //insert helloJPA.Member//
             insert
             into
             Member
             (name, id)
             values
             (?, ?)
             */

            /**
             * 현재 select 쿼리가 실행되지 않는 이유는
             * em.persist(member);에서 1차 캐쉬에 저장된 상태이기 때문에
             * select 쿼리가 실행이 안된다
             * */

            //보면 persist에서 쿼리 수행이 되는것이 아니다
            //실제 쿼리 수행은 commit 시점이다.
            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
