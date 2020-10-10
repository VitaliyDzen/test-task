package task.dto.article;

import static task.constants.ErrorMessage.THIS_FIELD_CAN_T_BE_NULL;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.entity.enums.Color;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveDto {

     private Long id;

    private String text;
    
    @NotNull(message = THIS_FIELD_CAN_T_BE_NULL)
    private String text;

    @Enumerated(value = EnumType.STRING)
    private Color color;
    
       @Enumerated(value = EnumType.STRING)
    private Color color;

}
