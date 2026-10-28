
package com.oes.online_examination_system.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oes.online_examination_system.auth.Role;
import com.oes.online_examination_system.auth.User;
import com.oes.online_examination_system.auth.repository.UserRepository;

@Component
@ConditionalOnProperty(
    name = "app.bootstrap.super-admin.enabled",
    havingValue = "true"
)
public class SuperAdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${SUPER_ADMIN_EMAIL:}")
    private String email;

    @Value("${SUPER_ADMIN_FULL_NAME:}")
    private String fullName;

    @Value("${SUPER_ADMIN_PASSWORD:}")
    private String password;

    public SuperAdminBootstrap(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.existsByRole(Role.SUPER_ADMIN)) {
            System.out.println(
                "A Super Admin already exists. Bootstrap skipped."
            );
            return;
        }

        String normalizedEmail =
            email == null ? "" : email.trim().toLowerCase(Locale.ROOT);

        String normalizedName =
            fullName == null ? "" : fullName.trim();

        if (normalizedEmail.isBlank()
                || normalizedName.isBlank()
                || password == null
                || password.isBlank()) {
            throw new IllegalStateException(
                "Super Admin bootstrap is enabled, but required "
                + "environment variables are missing."
            );
        }

        if (password.length() < 12) {
            throw new IllegalStateException(
                "The initial Super Admin password must contain "
                + "at least 12 characters."
            );
        }

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalStateException(
                "The configured Super Admin email already belongs "
                + "to an existing account. Bootstrap stopped."
            );
        }

        User superAdmin = new User();
        superAdmin.setFullName(normalizedName);
        superAdmin.setEmail(normalizedEmail);
        superAdmin.setPasswordHash(passwordEncoder.encode(password));
        superAdmin.setRole(Role.SUPER_ADMIN);
        superAdmin.setEnabled(true);

        userRepository.save(superAdmin);

        password = null;

        System.out.println(
            "Initial Super Admin created successfully."
        );
    }
}
