package tobyspring.splearn.application.required;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember() {
        var member = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
    }

    @Test
    void duplicateEmailFail() {
        var member1 = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());

        memberRepository.save(member1);

        var member2 = Member.register(MemberFixture.createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}