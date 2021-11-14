package hellojpa;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
    EntityManagerFactory는 하나만 생성해서 애플리케이션 전체에서 공유
    EntityManager는 쓰레드간 공유하면 안되고 사용시 반드시 버려야 한다.
    JPA의 모든 데이터 변경은 트렌젝션 안에서 해야한다[관계형 데이터 베이스는 트렌젝션안에서 실행되도록 설계 되어있기 때문]
 */
public class JpaMain {
    public static void main(String[] args)
    {
        // h2 DB연동에 연동[DB가 켜있지 않으면 연동이 안된다]
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //code
        try
        {
/*
            entityManager로 DB를 create 하는 코드

            Member member = new Member();
            member.setId(2L);
            member.setName("HelloB");

            entityManager.persist(member);
*/
/*
            entityManager로 DB를 update 하는 코드

            Member findMember = em.find(Member.class,1L);  // DB에 primaryKey로 조회 후
            findMember.setName("HelloJPA");                // name을 수정
*/          
            // hibernate에 각 관계형 DB로 형태 바꾼다음 실행해도 선택한 관계형 DB에 방언에 맞춰서 JPA가 관계형 DB에 알맞는 방언으로 형변환 해준다 
            List<Member> result = em.createQuery("select m from Member as m")
                    .setFirstResult(1)
                    .setMaxResults(2)
                    .getResultList();

            for (Member member : result)
            {
                System.out.println("member.name = " + member.getName());
            }
            tx.commit();

        }
        catch (Exception e)
        {
            tx.rollback();
        }
        finally
        {
            em.close();
        }

        emf.close();

    }
}
