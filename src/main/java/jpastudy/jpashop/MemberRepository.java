package jpastudy.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 커맨드와 쿼리를 분리하라는 명제에 의해 보통 리턴을 안넣지만 id 리턴이 요긴하게 쓰는 경우가 있음.
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
