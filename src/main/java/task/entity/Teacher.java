
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
import javax.persistence.UniqueConstraint;
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
@Table(name = TEACHER, uniqueConstraints = {
    @UniqueConstraint(columnNames = {"first_name", "last_name", "middle_name"})
})
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "politek_id", length = 60)
    private String politekId;


    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(name = "middle_name", length = 30, nullable = false)
    private String middleName;

    @Column(name = "full_name_abbreviation", length = 100)
    private String fullNameAbbreviation;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Rank rank;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "about", length = 300)
    private String about;

    @JsonIgnore
    @OneToMany(mappedBy = "curator", fetch = FetchType.EAGER)
    private List<Group> groups;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Timetable> timetables;

    @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName;
    }
}
