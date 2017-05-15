package example.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.rest.core.annotation.Description;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created : vincent
 * Date : 2017/5/11 上午10:44
 * Email : wangxiao@wafersystems.com
 */
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Description("Name need string")
    @NonNull
    @Setter
    @Getter
    private String name;

    @OneToMany(mappedBy = "company")
    @LazyCollection(LazyCollectionOption.EXTRA)
    @Getter
    @Setter
    private Set<Employee> employees = new HashSet<Employee>();
}
