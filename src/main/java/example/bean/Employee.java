package example.bean;

import lombok.*;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:24
 * Email : wangxiao@wafersystems.com
 */
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Description("First Name max 12 characters.")
    @NonNull
    @Setter
    @Getter
    private String firstName;

    @Description("Last Name mx 12 characters.")
    @NonNull
    @Setter
    @Getter
    private String lastName;

    @Description("Age max 2 digits.")
    @NonNull
    @Setter
    @Getter
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companyId")
    @Setter
    @Getter
    private Company company;

    public Employee(String firstName, String lastName, int age, Company company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.company = company;
    }

}
