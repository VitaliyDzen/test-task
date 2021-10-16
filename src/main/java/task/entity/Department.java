import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = DEPARTMENT)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 150, nullable = false, unique = true)
    private String name;

    @ManyToOne
    private Institute institute;

    @JsonIgnore
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Group> groups;

    @JsonIgnore
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private List<Teacher> teachers;

    @Override
    public String toString() {
        return name;
    }
}
