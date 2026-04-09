import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double pa1 = scanner.nextDouble();
        double pa2 = scanner.nextDouble();
        double pa3 = scanner.nextDouble();
        double pa4 = scanner.nextDouble();
        double pa5 = scanner.nextDouble();
        double pa6 = scanner.nextDouble();
        double pa7 = scanner.nextDouble();
        double pa8 = scanner.nextDouble();
        double paPercent = (pa1 + pa2 + pa3 + pa4 + pa5 + pa6 + pa7 + pa8) / 8.0;

        double quiz1 = scanner.nextDouble();
        double quiz2 = scanner.nextDouble();
        double quiz3 = scanner.nextDouble();
        double quizPercent = (quiz1 + quiz2 + quiz3) / 3.0;

        double participation = scanner.nextDouble();
        double participationPercent = (participation / 10.0) * 100.0;

        double midterm = scanner.nextDouble();

        double finalExam = scanner.nextDouble();

        scanner.close();

        double overallScore = (paPercent * 0.40) + (participationPercent * 0.10) + (quizPercent * 0.10)
                + (midterm * 0.15) + (finalExam * 0.25);

        System.out.printf("%.2f%n", overallScore);
    }
}