package ir.pilqush.ewallet.dto;

import ch.qos.logback.core.status.Status;
import ir.pilqush.ewallet.base.BaseDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Data
public class AccountDto extends BaseDto {
    private String userId;
    private Double currentAmount;
    private Status status;
}
