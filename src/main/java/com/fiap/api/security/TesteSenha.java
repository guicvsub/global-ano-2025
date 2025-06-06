import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteSenha {
    public static void main(String[] args) {
        String senhaRaw = "123456"; // senha em texto que o usuário digitou
        String senhaHash = "$2a$10$Yc2vw/Ve/qsLDhqxncFqquh0kwmuI9M2yObz9wb6uaFSqH26tlg9G"; // hash do banco

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        boolean senhaValida = encoder.matches(senhaRaw, senhaHash);

        System.out.println("Senha válida? " + senhaValida);
    }
}
