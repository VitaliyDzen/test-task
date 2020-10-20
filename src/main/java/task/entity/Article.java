package task.entity;

import static task.constants.DBConstant.ARTICLE_TABLE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import task.entity.enums.Color;

@Builder
@Table(name = ARTICLE_TABLE)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 60, nullable = false, unique = true)
    private String name;

    
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Color color;
    
    @Column(name = "description", length = 300)
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "audience", fetch = FetchType.LAZY)
    private List<Timetable> timetables;

    @Override
    public String toString() {
        return name;
    }


}
