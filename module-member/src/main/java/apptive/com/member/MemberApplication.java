package apptive.com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "apptive.com.member",
        "apptive.com.common",
        "apptive.com.store"
})
@EnableJpaAuditing
@ComponentScan(basePackages = {"apptive.com.member", "apptive.com.common",
        "apptive.com.store.cake", "apptive.com.store.store"})
@EntityScan(basePackages = {"apptive.com.member", "apptive.com.store.cake.model", "apptive.com.store.store.model"})
@EnableJpaRepositories(basePackages = {"apptive.com.member",
        "apptive.com.store.cake.repository", "apptive.com.store.store.repository"})
public class MemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(MemberApplication.class, args);
    }
}
