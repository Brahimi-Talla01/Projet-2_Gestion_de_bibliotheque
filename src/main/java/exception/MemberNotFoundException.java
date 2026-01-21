package exception;

public class MemberNotFoundException extends Exception {
    public MemberNotFoundException(int id) {
        super("Le membre avec l'ID " + id + " n'est pas inscrit.");
    }

    public MemberNotFoundException(String name) {
        super("Aucun membre trouvé avec le nom " + name + ".");
    }
}