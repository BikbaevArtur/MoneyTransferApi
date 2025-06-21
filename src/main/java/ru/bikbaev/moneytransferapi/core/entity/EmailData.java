package ru.bikbaev.moneytransferapi.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "email_data")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Getter
@Setter
public class EmailData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(unique = true,nullable = false)
    private String email;

}
