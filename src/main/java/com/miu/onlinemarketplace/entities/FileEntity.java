package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.FileType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;
    private String uri;
    @Enumerated
    private FileType fileType;

}
