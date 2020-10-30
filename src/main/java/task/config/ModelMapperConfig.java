package task.config;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@AllArgsConstructor
@Profile({"dev", "prod"})
public class ModelMapperConfig {
    
    

    Account account = Account.builder().id("123456")
            .name("testAccount")
            .email("test@staffjoy.net")
            .memberSince(Instant.now())
            .confirmedAndActive(true)
            .photoUrl("https://staffjoy.xyz/photo/test.png")
            .phoneNumber("18001801266")
            .support(false)
            .build();

    AccountDto accountDto = modelMapper.map(account, AccountDto.class);
    validateAccount(accountDto, account);

    Account account1 = modelMapper.map(accountDto, Account.class);
    validateAccount(accountDto, account1);
    
    
      @Override
    protected void configure(){
        using(toUppercase)
            .map(source.getProperty())
            .setOther(null);
    }
    
    
    }
}
