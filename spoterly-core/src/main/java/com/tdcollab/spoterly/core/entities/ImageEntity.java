package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class ImageEntity {
    @NotNull(message = "id cannot be null")
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull(message = "image_data cannot be null")
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private byte[] imageData;

    @NotNull(message = "file_type cannot be null")
    private String fileType;
}
