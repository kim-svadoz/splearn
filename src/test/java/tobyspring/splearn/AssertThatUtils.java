package tobyspring.splearn;

import java.util.function.Consumer;

import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;

import static org.assertj.core.api.Assertions.assertThat;
import tobyspring.splearn.domain.member.MemberRegisterRequest;

public class AssertThatUtils {

    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> assertThat(value).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return value -> assertThat(value).isEqualTo(request.email());
    }
}
