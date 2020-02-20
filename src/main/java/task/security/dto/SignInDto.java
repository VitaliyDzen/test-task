package task.security.dto;

import static task.constants.ErrorMessage.THIS_FIELD_CAN_T_BE_NULL;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDto {

    @NotBlank(message = THIS_FIELD_CAN_T_BE_NULL)
    private String email;

    @NotBlank(message = THIS_FIELD_CAN_T_BE_NULL)
    private String password;
}
