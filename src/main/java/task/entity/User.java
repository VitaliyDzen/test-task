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
    @Enumerated(value = EnumType.STRING)
    private FlightStatus flightStatus;

    @JsonIgnore
    @ManyToOne
    private AirCompany airCompany;

    @JsonIgnore
    @ManyToOne
    private Airplane airplane;

    @OneToOne
    private Country departureCountry;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Article> articles;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private Float distance;

    @Column(nullable = false)
    private Time estimatedFlightTime;

    @Column
    private Date endedAt;

    @Column
    private Date createdAt;

    @JsonIgnore
    @Column(nullable = false)
    private String refreshTokenKey;
}
