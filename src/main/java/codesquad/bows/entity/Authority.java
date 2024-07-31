package codesquad.bows.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
