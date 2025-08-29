package tobyspring.splearn;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

class SplearnApplicationTests {
    @Test
    void run() {
        MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class);

        SplearnApplication.main(new String[0]);

        mocked.verify(() -> SpringApplication.run(SplearnApplication.class, new String[0]));
    }
}
