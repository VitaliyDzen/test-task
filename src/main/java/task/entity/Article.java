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

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String factorySerialNumber;

    @ManyToOne
    private AirCompany airCompany;

    @Column(nullable = false)
    private Integer numberOfFlights;

    @Column(nullable = false)
    private Float flightDistance;
    
    @Column(nullable = false)
    private Float fuelCapacity;

    @ManyToOne
    private AirplaneType airplaneType;

    @Column(nullable = false)
    private Date createdAt;
}
