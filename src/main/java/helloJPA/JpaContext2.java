package helloJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaContext2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        try {
            /** context */
            //Member findMember1 =  em.find(Member.class,101L);
            //Member findMember2 =  em.find(Member.class,101L);

            /*
            * Hibernate:
            select
                member0_.id as id1_0_0_,
                member0_.name as name2_0_0_
            from
                Member member0_
            where
                member0_.id=?
            * */

            //Member findMember1 =  em.find(Member.class,101L); -> select 문이 실행되고
            //Member findMember2 =  em.find(Member.class,101L); -> 조회된 1차 캐쉬를 찾아서 가져오기 때문에 select가 한번만 실행됨.

            //보면 persist에서 쿼리 수행이 되는것이 아니다
            //실제 쿼리 수행은 commit 시점이다.
            //tx.commit();


            /** 쓰기지연 */
            Member member1 = new Member(150L,"A");
            Member member2 = new Member(160L,"B");

            em.persist(member1);
            em.persist(member2);
            System.out.println("======================");
            
            //->  ====================== 가 실행되고 쿼리 수행

           /* ======================
            Hibernate:
            insert helloJPA.Member
            insert
                into
            Member
                (name, id)
            values
                (?, ?)
            Hibernate:
            insert helloJPA.Member
            insert
                into
            Member
                (name, id)
            values
                (?, ?)
            */                

            tx.commit();

        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }
        emf.close();
    }
}
