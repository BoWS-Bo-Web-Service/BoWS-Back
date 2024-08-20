package codesquad.bows.member.entity;

import codesquad.bows.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AUTHORITY")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authority extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
