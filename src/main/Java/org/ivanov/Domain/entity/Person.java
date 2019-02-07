package org.ivanov.Domain.entity;



import lombok.Data;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@Entity
@ToString(exclude = "cars")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String passport;
    private String identificationNumber;
    private String phone;
    private String email;
    private String password;
    private String status;
    @Column(columnDefinition = "timestamp")
    private LocalDateTime time;
    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
    private Collection<Car> cars;

//    @OneToMany(mappedBy = "person",fetch = FetchType.EAGER)
//    private Collection<Payment> payments;
}
