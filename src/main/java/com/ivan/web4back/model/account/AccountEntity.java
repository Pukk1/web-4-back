package com.ivan.web4back.model.account;

import com.ivan.web4back.model.access.AccessEntity;
import com.ivan.web4back.model.areapoint.AreaPointEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<AreaPointEntity> areaPoints;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<AccessEntity> accesses;
}
