package task.entity;

import static task.constants.DBConstant.USER_TABLE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Table(name = USER_TABLE)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(name = "first_name", length = 30, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, nullable = false)
    private String lastName;

    @Column(name = "middle_name", length = 30, nullable = false)
    private String middleName;

    @ManyToOne
    private Department department;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles;
   @ManyToOne
    private Rank rank;

    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    @Column(name = "about", length = 300)
    private String about;

    @JsonIgnore
    @Column(unique = true)
    private String password;

        @Override
    public String toString() {
        return lastName + " " + firstName + " " + middleName;
    
  @JsonIgnore
    @OneToMany(mappedBy = "curator", fetch = FetchType.EAGER)
    private List<Group> groups;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Timetable> timetables;
}
