package com.miu.onlinemarketplace;

import com.miu.onlinemarketplace.entities.Role;
import com.miu.onlinemarketplace.repository.RoleRepository;
import com.miu.onlinemarketplace.security.models.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeedGenerator {

    @Value("${app.init.data-seed:false}")
    private boolean dataSeed;

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (dataSeed) {
                List<Role> roles = List.of(new Role(null, EnumRole.ROLE_USER), new Role(null, EnumRole.ROLE_ADMIN), new Role(null, EnumRole.ROLE_VENDOR));
                roleRepository.saveAll(roles);
            }
        };
    }

}
