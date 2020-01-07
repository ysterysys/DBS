package contact;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class DummyLoad {

  @Bean
  CommandLineRunner initDatabase(ContactRepository repository) {
    return args -> {
        System.out.println("Preload :" + repository.save(new Contact("Bilbo Baggins", "burglar")));
  
        repository.save(new Contact("bb", "cc"));
      //log.info("Preloading " + repository.save(new Contact("Bilbo Baggins", "burglar")));
     // log.info("Preloading " + repository.save(new Contact("Frodo Baggins", "thief")));
    };
  }
}