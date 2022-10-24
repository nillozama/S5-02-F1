package cat.itacademy.barcelonactiva.Leal.Victor.s05.t02.n01.S05T02N01LealVictor.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

   @Bean
   public ModelMapper modelMapper() {
      ModelMapper modelMapper = new ModelMapper();
      return modelMapper;
   }
}
