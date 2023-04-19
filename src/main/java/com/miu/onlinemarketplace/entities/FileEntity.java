package com.miu.onlinemarketplace.entities;

import com.miu.onlinemarketplace.common.enums.FileType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    private String uri;
    @Enumerated(value = EnumType.STRING)
    private FileType fileType;

}
