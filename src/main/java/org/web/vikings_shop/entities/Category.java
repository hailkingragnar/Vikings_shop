package org.web.vikings_shop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    private String cid;
    private String name;
    private String description;


    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<Product> products;
}
