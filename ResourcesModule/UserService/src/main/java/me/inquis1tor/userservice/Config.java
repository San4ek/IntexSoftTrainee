package me.inquis1tor.userservice;

import lombok.RequiredArgsConstructor;
import me.inquis1tor.userservice.repositories.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class Config {

    private final AccountRepository accountRepository;

    @Bean
    CommandLineRunner runner() {
        return args -> {
            long exists=0;
            long find=0;

            for (int i = 0; i < 100; i++) {

                System.out.println(i);

                Temporal start;
                Temporal end;

                start=LocalDateTime.now();

                if (accountRepository.existsById(UUID.fromString("32544bd6-0f4c-49df-816c-b7f1d89789e9"))) {

                }

                end=LocalDateTime.now();

                long duration=Duration.between(start, end).toNanos();

                exists+=duration;

                System.out.println("Exists: "+duration);

                start=LocalDateTime.now();

                if (accountRepository.findById(UUID.fromString("32544bd6-0f4c-49df-816c-b7f1d89789e9")).isPresent()) {

                }
                end=LocalDateTime.now();

                duration=Duration.between(start, end).toNanos();

                find+=duration;

                System.out.println("Find: "+duration);
                System.out.println("-----------------------------------------------------------------------------");
            }

            System.out.println("Avg: exists "+exists/100+" vs find "+find/100);
        };
    }
}
