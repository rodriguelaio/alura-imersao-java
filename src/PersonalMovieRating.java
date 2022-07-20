import java.util.Scanner;

public class PersonalMovieRating {

    private Double personalRating = 0.0;

    private boolean isVoting;

    public Double getPersonalRating() {
        return personalRating;
    }

    public boolean isVoting() {
        return isVoting;
    }

    private void setPersonalRating(Double personalRating) {
        this.personalRating = personalRating;
    }

    private void setVoting(boolean voting) {
        isVoting = voting;
    }

    public PersonalMovieRating() {
        askingRatingWill("Gostaria de dar nota para uma lista de filmes?");
    }

    public void rateMovie(String movieName) {
        askingMovieRate(movieName);
    }

    public void askingKeepRating() {
        askingRatingWill("Gostaria de continuar dando notas para os filmes?");
    }

    private void askingRatingWill(String firtsQuestion) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(firtsQuestion);
        System.out.print("(S/N): ");
        String answer = scanner.next();
        while (!answer.equalsIgnoreCase("s") && !answer.equalsIgnoreCase("n")) {
            System.out.println("Opção Inválida!");
            System.out.print("Digite 'S' para SIM ou 'N' para NÃO): ");
            answer = scanner.next();
        }
        setVoting(answer.equalsIgnoreCase("s"));
    }

    private void askingMovieRate(String movieName) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual a sua nota para '".concat(movieName).concat("'?"));
        System.out.print("De 0 a 10: ");
        double answer = scanner.nextDouble();
        while (answer < 0.0 || answer > 10.0) {
            System.out.println("Nota Inválida!");
            System.out.print("Digite uma nota entre 0 e 10: ");
            answer = scanner.nextDouble();
        }
        setPersonalRating(answer);
    }
}
