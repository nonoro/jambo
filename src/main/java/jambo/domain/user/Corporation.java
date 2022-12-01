package jambo.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Corporation {
    @Id
    @Column(name = "corporation_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "corporation_seq")
    @SequenceGenerator(name = "corporation_seq", sequenceName = "corporation_seq", allocationSize = 1)
    private Long id;

    private String password;

    private String phone;

    private String name;

    @CreatedDate
    private LocalDateTime joinDate;

    private String mainIcon;
}
