import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Classe utilitária com as cores, fontes e componentes padronizados do
 * sistema VetCare, seguindo o guia de estilo definido no projeto de
 * interfaces (Etapa 2 - Figma).
 *
 * @author VetCare
 */
public class EstiloUI {

    public static final Color PRIMARIA = new Color(0x2C, 0x5F, 0x8A);
    public static final Color ACENTO = new Color(0x5F, 0xA8, 0xD3);
    public static final Color FUNDO = new Color(0xF5, 0xF7, 0xFA);
    public static final Color BORDA = new Color(0xD1, 0xD9, 0xE0);
    public static final Color TEXTO = new Color(0x33, 0x33, 0x33);
    public static final Color TEXTO_MUTED = new Color(0x77, 0x77, 0x77);
    public static final Color SUCESSO = new Color(0x4C, 0xAF, 0x50);
    public static final Color ERRO = new Color(0xE5, 0x39, 0x35);
    public static final Color BRANCO = Color.WHITE;

    public static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 20);
    public static final Font FONTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_ROTULO = new Font("SansSerif", Font.BOLD, 11);
    public static final Font FONTE_CAMPO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_BOTAO = new Font("SansSerif", Font.BOLD, 13);

    public static JLabel criarTitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE_TITULO);
        l.setForeground(PRIMARIA);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public static JLabel criarSubtitulo(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(FONTE_SUBTITULO);
        l.setForeground(TEXTO_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    public static JTextField criarCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(FONTE_CAMPO);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDA, 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 34));
        return campo;
    }

    /**
     * Monta um bloco vertical com o rótulo em cima e o campo (JTextField,
     * JComboBox, etc.) embaixo, no padrão usado em todas as telas.
     */
    public static JPanel criarCampoComRotulo(String rotulo, JComponent campo) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setOpaque(false);
        painel.setAlignmentX(Component.LEFT_ALIGNMENT);
        painel.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));

        JLabel label = new JLabel(rotulo);
        label.setFont(FONTE_ROTULO);
        label.setForeground(TEXTO_MUTED);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(label);
        painel.add(javax.swing.Box.createVerticalStrut(4));
        painel.add(campo);
        return painel;
    }

    public static JButton criarBotaoPrimario(String texto) {
        JButton b = new JButton(texto);
        estilizarBotao(b, PRIMARIA, BRANCO, null);
        return b;
    }

    public static JButton criarBotaoSecundario(String texto) {
        JButton b = new JButton(texto);
        estilizarBotao(b, BRANCO, PRIMARIA, PRIMARIA);
        return b;
    }

    private static void estilizarBotao(JButton b, Color fundo, Color texto, Color borda) {
        b.setFont(FONTE_BOTAO);
        b.setBackground(fundo);
        b.setForeground(texto);
        b.setFocusPainted(false);
        b.setOpaque(true);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (borda != null) {
            b.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(borda, 1),
                    BorderFactory.createEmptyBorder(10, 22, 10, 22)));
        } else {
            b.setBorder(BorderFactory.createEmptyBorder(10, 22, 10, 22));
        }
    }
}
