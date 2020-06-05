package com.qiding.test.scenes.subpub;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublishBean implements Serializable {
    private String name;
    private String age;
}
