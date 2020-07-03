package br.com.vfs.integrationdynamodb.model;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
public class Host implements Serializable {
    private String name;
    private String ip;
    private int port;
}
