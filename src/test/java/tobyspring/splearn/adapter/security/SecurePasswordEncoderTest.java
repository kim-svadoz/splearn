package tobyspring.splearn.adapter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoder() {
        SecurePasswordEncoder encoder = new SecurePasswordEncoder();

        String passwordHash = encoder.encode("secret");
        
        assertThat(encoder.matches("secret", passwordHash)).isTrue();
        assertThat(encoder.matches("wrong", passwordHash)).isFalse();
    }
}