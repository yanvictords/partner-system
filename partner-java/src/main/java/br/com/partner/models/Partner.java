package br.com.partner.models;

import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Partner {

    public static final String SCHEME_NAME = "partnerdatabase";
    public static final String TABLE_NAME = "PARTNER";

    private BigInteger id;
    private String name;
    private String email;
    private LocalDate birthDate;
    private BigInteger teamId;
    
}
