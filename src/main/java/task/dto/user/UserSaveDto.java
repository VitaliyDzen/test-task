package task.dto.user;

import static task.constants.ErrorMessage.MAXIMUM_NUMBER_OF_YEARS;
import static task.constants.ErrorMessage.MINIMUM_NUMBER_OF_YEARS;
import static task.constants.ErrorMessage.THIS_FIELD_CAN_T_BE_NULL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {

    @NotNull(message = THIS_FIELD_CAN_T_BE_NULL)
    private String name;

    @NotNull(message = THIS_FIELD_CAN_T_BE_NULL)
    @Max(value = 200, message = MAXIMUM_NUMBER_OF_YEARS)
    @Min(value = 0, message = MINIMUM_NUMBER_OF_YEARS)
    private Integer age;

    @NotNull(message = THIS_FIELD_CAN_T_BE_NULL)
    private String email;

    @NotNull(message = THIS_FIELD_CAN_T_BE_NULL)
    private String password;
}
