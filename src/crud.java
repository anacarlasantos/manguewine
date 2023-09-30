import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class crud {
    private static final String USER = "adega";
    private static final String PASSWORD = "1234";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        crud sistema = new crud();
        sistema.login();
    }

    public void login() {
        boolean validLogin = false;

        while (!validLogin) {
            System.out.print("Insira o nome de usuário: ");
            String usuario = scanner.nextLine();
            System.out.print("Insira a senha: ");
            String senha = scanner.nextLine();

            // Verifica se as credenciais correspondem
            if (usuario.equals(USER) && senha.equals(PASSWORD)) {
                System.out.println("\nLogin sucedido!");
                exibirMenu();
                validLogin = true;
            } else {
                System.out.println("\nFalha no login. Usuário ou senha inválidos.\n");
            }
        }
    }

    private void exibirMenu() {
        while (true) {
            System.out.println("\n--------< Menu >--------\n");
            System.out.println("1 - Inserir Produto ->");
            System.out.println("2 - Consultar Produto ->");
            System.out.println("3 - Alterar Produto ->");
            System.out.println("4 - Remover Produto ->");
            System.out.println("5 - Exibir todos Produtos ->");
            System.out.println("6 - Sair ->");

            System.out.print("\nEscolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer de entrada

            switch (opcao) {
                case 1:
                    inserirProduto();
                    break;

                case 2:
                    consultarProduto();
                    break;

                case 3:
                    alterarProduto();
                    break;

                case 4:
                    removerProduto();
                    break;
                case 5: // Adicione este caso para ver todos os produtos
                    exibirTodosProdutos();
                    break;

                case 6:
                    System.out.println("Saindo do sistema.");
                    System.exit(0);

                default:
                    System.out.println("Opção inválida, favor inserir uma opção válida.");
            }
        }
    }

     {
    }

    private void inserirProduto() {
        System.out.println("\n--------< Inserir Produto >--------\n");
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o preço do produto: ");
        float preco = scanner.nextFloat();
        scanner.nextLine(); // Limpar o buffer de entrada

        try (Connection connection =conexao.abrir();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Produto (nome, preco) VALUES (?, ?)")) {

            preparedStatement.setString(1, nome);
            preparedStatement.setFloat(2, preco);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\nProduto adicionado com sucesso.");
            } else {
                System.out.println("\nErro ao adicionar o produto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void consultarProduto() {
        System.out.println("\n--------< Consulta por ID >--------\n");
        System.out.print("Digite o ID do produto a ser consultado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        try (Connection connection = conexao.abrir();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Produto WHERE id = " + id);

            if (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") + ", Nome: " + resultSet.getString("nome") + ", Preço: " + resultSet.getFloat("preco"));
            } else {
                System.out.println("Nenhum produto encontrado com o ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void alterarProduto() {
        System.out.println("\n--------< Alterar Produto >--------\n");
        System.out.print("Digite o ID do produto a ser alterado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        System.out.print("Digite o novo nome: ");
        String novoNome = scanner.nextLine();

        System.out.print("Digite o novo preço: ");
        float novoPreco = scanner.nextFloat();
        scanner.nextLine(); // Limpar o buffer de entrada

        try (Connection connection =conexao.abrir();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Produto SET nome = ?, preco = ? WHERE id = ?")) {

            preparedStatement.setString(1, novoNome);
            preparedStatement.setFloat(2, novoPreco);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto alterado com sucesso.");
            } else {
                System.out.println("Nenhum produto encontrado com o ID " + id + " para alteração.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void removerProduto() {
        System.out.println("\n--------< Remover Produto >--------\n");
        System.out.print("Digite o ID do produto a ser removido: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer de entrada

        try (Connection connection = conexao.abrir();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Produto WHERE id = ?")) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto removido com sucesso.");
            } else {
                System.out.println("Nenhum produto encontrado com o ID " + id + " para remoção.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void exibirTodosProdutos() {
        System.out.println("\n--------< Todos os Produtos >--------\n");
    
        try (Connection connection = conexao.abrir();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Produto")) {
    
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                float preco = resultSet.getFloat("preco");
    
                System.out.println("ID: " + id + ", Nome: " + nome + ", Preço: " + preco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
