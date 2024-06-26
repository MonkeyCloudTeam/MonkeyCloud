package com.monkeyteam.monkeycloud.dtos.folderDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
@AllArgsConstructor
public class FolderUploadRequest {
    private List<MultipartFile> files;
    private String fullPath;
    private String username;
}
