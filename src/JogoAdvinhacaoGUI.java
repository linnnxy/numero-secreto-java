import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JogoAdvinhacaoGUI extends JFrame {

    private JLabel labelTitulo;
    private JLabel labelInstrucao;
    private JLabel labelTentativas;
    private JTextField campoPalpite;
    private JButton botaoAdivinhar;
    private JButton botaoJogarNovamente;

    private int numeroSecreto;
    private int tentativas;
    private final int MAX_TENTATIVAS = 10;
    private Random gerador;

    private final Color COR_FUNDO = new Color(248, 249, 250);
    private final Color COR_TEXTO_PRINCIPAL = new Color(33, 37, 41);
    private final Color COR_TEXTO_SECUNDARIO = new Color(108, 117, 125);
    private final Color COR_BOTAO_PRIMARIO = new Color(23, 162, 184);
    private final Color COR_BOTAO_SUCESSO = new Color(40, 167, 69);
    private final Color COR_MENSAGEM_ERRO = new Color(220, 53, 69);

    public JogoAdvinhacaoGUI() {
        setTitle("Jogo de Adivinhação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gerador = new Random();

        setLayout(new BorderLayout());
        JPanel painelPrincipal = (JPanel) getContentPane();
        painelPrincipal.setBackground(COR_FUNDO);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        labelTitulo = new JLabel("Qual é o número?");
        labelTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        labelTitulo.setForeground(COR_TEXTO_PRINCIPAL);
        labelTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));

        labelTentativas = new JLabel("Você tem " + MAX_TENTATIVAS + " tentativas.");
        labelTentativas.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTentativas.setHorizontalAlignment(SwingConstants.CENTER);
        labelTentativas.setForeground(COR_TEXTO_PRINCIPAL);

        labelInstrucao = new JLabel("Digite um número de 1 a 100.");
        labelInstrucao.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        labelInstrucao.setHorizontalAlignment(SwingConstants.CENTER);
        labelInstrucao.setForeground(COR_TEXTO_SECUNDARIO);
        labelInstrucao.setBorder(new EmptyBorder(10, 0, 10, 0));

        campoPalpite = new JTextField(5);
        campoPalpite.setFont(new Font("Segoe UI", Font.BOLD, 24));
        campoPalpite.setHorizontalAlignment(SwingConstants.CENTER);

        botaoAdivinhar = new JButton("Adivinhar");
        botaoAdivinhar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botaoAdivinhar.setBackground(COR_BOTAO_PRIMARIO);
        botaoAdivinhar.setForeground(Color.WHITE);
        botaoAdivinhar.setFocusPainted(false);

        botaoJogarNovamente = new JButton("Jogar Novamente");
        botaoJogarNovamente.setFont(new Font("Segoe UI", Font.BOLD, 16));
        botaoJogarNovamente.setBackground(COR_BOTAO_SUCESSO);
        botaoJogarNovamente.setForeground(Color.WHITE);
        botaoJogarNovamente.setFocusPainted(false);
        botaoJogarNovamente.setVisible(false);

        JPanel painelEntrada = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        painelEntrada.setBackground(COR_FUNDO);
        painelEntrada.add(campoPalpite);
        painelEntrada.add(botaoAdivinhar);

        JPanel painelFeedback = new JPanel(new BorderLayout());
        painelFeedback.setBackground(COR_FUNDO);
        painelFeedback.add(labelTentativas, BorderLayout.NORTH);
        painelFeedback.add(labelInstrucao, BorderLayout.CENTER);

        JPanel painelBotaoNovoJogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        painelBotaoNovoJogo.setBackground(COR_FUNDO);
        painelBotaoNovoJogo.add(botaoJogarNovamente);
        painelFeedback.add(painelBotaoNovoJogo, BorderLayout.SOUTH);

        add(labelTitulo, BorderLayout.NORTH);
        add(painelEntrada, BorderLayout.CENTER);
        add(painelFeedback, BorderLayout.SOUTH);

        botaoAdivinhar.addActionListener(e -> verificarPalpite());
        campoPalpite.addActionListener(e -> verificarPalpite());
        botaoJogarNovamente.addActionListener(e -> iniciarNovoJogo());

        iniciarNovoJogo();
        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
    }

    private void iniciarNovoJogo() {
        numeroSecreto = gerador.nextInt(100) + 1;
        tentativas = 0;
        labelTentativas.setText("Você tem " + MAX_TENTATIVAS + " chances!");
        labelTentativas.setForeground(COR_TEXTO_PRINCIPAL);
        labelInstrucao.setText("Pensei em um número de 1 a 100.");
        labelInstrucao.setForeground(COR_TEXTO_SECUNDARIO);
        campoPalpite.setText("");
        campoPalpite.setEditable(true);
        botaoAdivinhar.setEnabled(true);
        botaoJogarNovamente.setVisible(false);
        campoPalpite.requestFocus();
    }

    private void verificarPalpite() {
        String textoPalpite = campoPalpite.getText();
        if (textoPalpite.isEmpty()) {
            labelInstrucao.setText("Você precisa digitar um número!");
            labelInstrucao.setForeground(Color.ORANGE);
            return;
        }

        try {
            int palpite = Integer.parseInt(textoPalpite);
            tentativas++;
            int tentativasRestantes = MAX_TENTATIVAS - tentativas;

            if (palpite == numeroSecreto) {
                labelInstrucao.setText("Você acertou em " + tentativas + " tentativas!");
                labelInstrucao.setForeground(COR_BOTAO_SUCESSO);
                labelTentativas.setText("Parabéns!");
                finalizarJogo();
            } else if (tentativas >= MAX_TENTATIVAS) {
                labelInstrucao.setText("Você perdeu! O número secreto era " + numeroSecreto + ".");
                labelInstrucao.setForeground(COR_MENSAGEM_ERRO);
                labelTentativas.setText("Fim de Jogo!");
                finalizarJogo();
            } else {
                if (palpite < numeroSecreto) {
                    labelInstrucao.setText("Muito baixo! Tente um número maior.");
                } else {
                    labelInstrucao.setText("Muito alto! Tente um número menor.");
                }
                labelInstrucao.setForeground(COR_MENSAGEM_ERRO);
                labelTentativas.setText("Chances restantes: " + tentativasRestantes);
            }
        } catch (NumberFormatException ex) {
            labelInstrucao.setText("Entrada inválida. Digite apenas números.");
            labelInstrucao.setForeground(Color.ORANGE);
        } finally {
            campoPalpite.setText("");
            if (botaoAdivinhar.isEnabled()) {
                campoPalpite.requestFocus();
            }
        }
    }

    private void finalizarJogo() {
        campoPalpite.setEditable(false);
        botaoAdivinhar.setEnabled(false);
        botaoJogarNovamente.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JogoAdvinhacaoGUI jogo = new JogoAdvinhacaoGUI();
            jogo.setVisible(true);
        });
    }
}