package helloJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

            //이렇게 JPQL에서 조회할 때 member라는 테이블을 참조하는 것이 아닌
            //객체 자체를 참조한다
            //List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();

            //객체를 가져오는것이 굉장한 이점들이 있다 ex> 페이징처리 첫번째 페이지 마지막페이지. 처리 가능
            List<Member> result = em.createQuery("select m from Member as m",Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();


            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }


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
