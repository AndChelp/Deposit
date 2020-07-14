package deposit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        List<SomeDto> baseList = Arrays.asList(
                new SomeDto(1, "q1w1", "1234"),
                new SomeDto(2, "q0w2", "4125"),
                new SomeDto(3, "q1w1", "5627"),
                new SomeDto(4, "q3a1", "8738"),
                new SomeDto(5, "2ws4", "9657"),
                new SomeDto(6, "q12a", "1124"));

        List<SomeDto> list_q1 = baseList.stream().filter(someDto -> someDto.getName().contains("q1")).collect(Collectors.toList());

        List<SomeDto> list_2 = baseList.stream().filter(someDto -> someDto.getSomething().contains("3")).collect(Collectors.toList());


        //list_q1.retainAll(list_2);

        System.out.println(baseList);
        System.out.println(list_q1);
        System.out.println(list_2);
    }
}

@Getter
@Setter
@ToString
@AllArgsConstructor
class SomeDto {
    private int id;
    private String name;
    private String something;
}