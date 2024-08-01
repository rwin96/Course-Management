package course.management.enums.Converter;

import course.management.enums.Credit;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

public class CreditConverter implements AttributeConverter<Credit, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Credit credit) {
        return credit.getId();
    }

    @Override
    public Credit convertToEntityAttribute(Integer integer) {
        return Arrays.stream(Credit.values()).filter(x -> x.getId() == integer).findFirst().orElse(null);
    }

}
