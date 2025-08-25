package tobyspring.splearn.application;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tobyspring.splearn.application.provided.MemberFinder;
import tobyspring.splearn.application.provided.MemberRegister;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.application.required.MemberRepository;
import tobyspring.splearn.domain.DuplicateEmailException;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberRegisterRequest;
import tobyspring.splearn.domain.PasswordEncoder;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check
        checkDuplicateEmail(registerRequest);

        // domain model
        Member member = Member.register(registerRequest, passwordEncoder);

        // repository
        memberRepository.save(member);

        // post process
        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);

        member.activate();

        // 일반 jpa에는 .save()라는 개념이 없다. .persist()와 .merge() 두가지만 있음.
        // 우리는 spring-data-jpa를 사용하는 것! 공식문서에서도 .save()를 사용하라고 명시되어있음.
        // 이유1. spring은 jpa만을 위해 만들어진 기술이 아니다! Spring Data JPA + Spring Data 추상화의 일관성 을 지키자
        // 이유2. spring에서 domain event publication 일때는 save()를 호출해야 한다.
        // 이유3. Auditing

        // Spring Data 를 사용할 때는 스프링이 추구하는 방향성에 맞춰 개발하는 것이 사이드 이펙트를 줄이는데 유리하다.
        // 효과1: 세련된 코드가 된다.
        // 효과2: Spring Data가 제공하는 부가 기능(domain event)도 자연스럽게 사용 가능하다.
        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 ㄹ이크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다: " + registerRequest.email());
        }
    }
}
