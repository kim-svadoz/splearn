package tobyspring.splearn.application.provided;

import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 *
 * entity를 return하는 것은 전혀 문제가 없다. adapter로 전송되어도 상관없다.
 * dto를 활용하는 presentation의 책임이다!
 * 코멘트 써도 된다! (나쁜 코드에 의해진 코멘트를 쓰지말라는 것이다)
 * port에는 코멘트 쓰는 습관이 좋다(api이기 때문)
 */
public interface MemberRegister {
    Member register(MemberRegisterRequest registerRequest);
}
