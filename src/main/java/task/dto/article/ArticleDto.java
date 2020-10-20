package task.dto.article;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.entity.enums.Color;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+02")
    private Date lessonDate;

    private AudienceDTO audience;

    private SubjectDTO subject;

    private LessonTypeDTO lessonType;

    private Integer numberOfLessonInDay;

    private TeacherShortDTO teacher;

    private List<GroupShortDTO> groups;
    
    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Color color;
}
