package tobyspring.splearn.adapter.integration;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;
import tobyspring.splearn.domain.Email;

class DummyEmailSenderTest {
    @Test
    @StdIo
    void dummyEmailSender(StdOut stdOut) {
        DummyEmailSender sender = new DummyEmailSender();

        sender.send(new Email("toby@splearn.app"), "subject", "body");

        assertThat(stdOut.capturedLines()[0]).isEqualTo("[DummyEmailSender] Sending email to: Email[address=toby@splearn.app]");

    }
}