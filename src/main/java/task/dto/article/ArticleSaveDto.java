package task.dto.article;

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

    @NotNull
    private String text;

    @Enumerated(value = EnumType.STRING)
    private Color color;

}
