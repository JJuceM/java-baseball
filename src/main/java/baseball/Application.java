package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Application {
    static StrikeAndBall strikeAndBall = new StrikeAndBall();

    public static void main(String[] args) {
        System.out.println("숫자 야구 게임을 시작합니다.");
        int regame = 1;
        do {
            // 컴퓨터 수 생성
            List<Integer> computer = setComputerNum();
            System.out.println(computer);

            // 반복 맞출때까지
            while (strikeAndBall.getStrike() != 3) {
                strikeAndBall.reset();
                List<Integer> userNum;
                // 입력
                try {
                    userNum = setUserNum();
                }catch (IllegalArgumentException e){
                    continue;
                }

                // 비교
                compareNum(computer, userNum);
                // 출력
                resultPrint();
            }
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            do {
                System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
                regame = Integer.parseInt(Console.readLine());
            } while (regame != 1 && regame != 2);
            strikeAndBall.reset();
        } while (regame != 2);
        System.out.println("게임 종료");
    }

    public static List<Integer> setComputerNum() {
        // 컴퓨터 수 생성
        List<Integer> computer = new ArrayList<>();
        while (computer.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);
            if (!computer.contains(randomNumber)) {
                computer.add(randomNumber);
            }
        }
        return computer;
    }

    public static List<Integer> setUserNum() {
        // 입력
        List<Integer> userNum;
        System.out.print("숫자를 입력해주세요 : ");
        String input = Console.readLine();
        isNumException(input);
        userNum = Arrays.stream(input.split(""))
                .map(Integer::parseInt)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(input);
        getUserNumException(userNum);
        return userNum;
    }

    public static void isNumException(String input) {
        String str = "^[0-9]*$";
        try {
            if (!input.matches(str))
                throw new IllegalArgumentException("숫자가 아닙니다. 바르게 입력해주세요."); // 숫자가 아님
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void getUserNumException(List<Integer> userNum) {
        // 예외
        try {
            if (userNum.size() < 3)
                throw new IllegalArgumentException("3자리 미만입니다. 바르게 입력해주세요."); // 중복제거로 인해 3자리 미만임
            else if (userNum.size() > 3)
                throw new IllegalArgumentException("3자리 초과입니다. 바르게 입력해주세요."); // 중복제거해도 3자리 초과함
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            setUserNum();
        }
    }

    public static void compareNum(List<Integer> computer, List<Integer> userNum) {
        for (int i = 0; i < 3; i++)
            if (Objects.equals(computer.get(i), userNum.get(i)))
                strikeAndBall.addStrike();
            else {
                for (int j = 0; j < 3; j++)
                    if (Objects.equals(computer.get(j), userNum.get(i)))
                        strikeAndBall.addBall();
            }
    }

    public static void resultPrint() {
        if (strikeAndBall.getBall() > 0) {
            if (strikeAndBall.getStrike() > 0)
                System.out.printf("%d볼 %d스트라이크\n", strikeAndBall.getBall(), strikeAndBall.getStrike());
            else
                System.out.printf("%d볼\n", strikeAndBall.getBall());
        } else if (strikeAndBall.getStrike() > 0)
            System.out.printf("%d스트라이크\n", strikeAndBall.getStrike());
        else
            System.out.println("낫싱");
    }
}

class StrikeAndBall {
    private int strike = 0;
    private int ball = 0;

    public void addStrike() {
        strike++;
    }

    public void addBall() {
        ball++;
    }

    public int getStrike() {
        return strike;
    }

    public int getBall() {
        return ball;
    }

    public void reset() {
        strike = 0;
        ball = 0;
    }
}
