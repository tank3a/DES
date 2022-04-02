import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CheckInput {

    BufferedReader br;
    CheckInput() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    public long check(String inputName) throws IOException {
        int i;
        long converted;
        String input;
        while(true) {
            System.out.print(inputName + "을/를 입력하세요 : ");
            input = br.readLine().trim();
            if(input.length() != 16) {
                System.out.println("64bits의 16진수화 된 " + inputName + "을 입력해주세요(길이 16)");
                continue;
            }

            try {
                converted = Long.parseLong(input, 16);
            } catch (NumberFormatException e) {
                System.out.println("16진수만 입력 가능합니다. 다시 입력해주세요.");
                continue;
            }

            break;
        }

        return converted;
    }
}
