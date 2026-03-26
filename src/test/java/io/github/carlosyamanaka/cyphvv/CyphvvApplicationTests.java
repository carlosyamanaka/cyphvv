package io.github.carlosyamanaka.cyphvv;

import io.github.carlosyamanaka.cyphvv.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev")
@Import(TestSecurityConfig.class)
class CyphvvApplicationTests {

	@Test
	void contextLoads() {
	}

}
