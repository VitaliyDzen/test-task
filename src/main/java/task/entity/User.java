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

    @Column(nullable = false)
    private Integer age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @Column(unique = true)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private String refreshTokenKey;
}
