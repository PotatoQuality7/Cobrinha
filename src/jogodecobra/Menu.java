package jogodecobra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Menu implements ActionListener, KeyListener, MouseListener {
   
    JFrame frame;
    JPanel telaPrincipal, telaEstatisticas, telaReplay, telaMenor, telaModo;
    JButton newGame, loadGame, confirmarCadastro, easyButton, normalButton, hardButton, jogador1Button, jogador2Button, jogador3Button, niveisButton, continuoButton, multiplayerButton, estatisticasButton;
    JButton nivel1, nivel2, nivel3, nivel4, nivel5, nivel6, nivel7, nivel8, nivel9, nivel10, avancarButton, recuarButton, voltarContas, voltarNiveis, voltarModo, voltarEstatisticas, avancarEstatisticas;
    JTextField scrollEstatisticas;
    JButton replay1, replay2, replay3;
    JButton nivelTest;
    JTextField usuarioField;
    JPasswordField senhaField;
    Icon icon;
    JLabel aviso, mainBorder, miniBorder, usuarioText, senhaText, dificuldade, niveisText;
    Color fundo_cor = Color.black;
    Color cobra_cor = new Color(0,255,160);
    Color biscoito_cor = Color.yellow;
    Color obstaculo_cor = Color.red;
    
    String tempStr;
    String[] jogadorNome = new String[4];
    String[] jogadorSenha = new String[4];
    String[] jogadorID = new String[4];
    String[] jogadorDificuldade = new String[4];
    byte[] jogadorNivel = new byte[4];
    
    int y, x, i, j, i2, j2, id, inicio, fim, num_pontos = 0;
    short quadro_x = 440, quadro_y = 120, pixel = 11, comp = 420, comp2 = 508, larg = 6;
    byte nivel, jogador, num_jogadores = 0, width, height;
    short tempShrt = 0;
    boolean iniciar_jogo = false, incompleto, colapso = false, expansao = false;
    
    float[] ponto_x = new float[2001];
    float[] ponto_y = new float[2001];
    float[] antigo_x = new float[2001];
    float[] antigo_y = new float[2001];
    short[] expansao_grupos = new short[101];
    String[] expansao_scores = new String[101];
    
    public boolean[][] rival, livre, obstaculo, checkpoint, check_invalido;
    public boolean[] rival_derrotado;
    public byte[][] rival_y, rival_x;
    public byte[] rival_direcao, prioridade, check_y, check_x, check_direcao;
    public short[] rival_tamanho;
    public boolean permissao, rival_desistiu, cobra_redonda;
    public byte num_check, rival_anterior, atual, anterior, biscoito_y, biscoito_x, inicio_x, inicio_y, fim_y;
    public byte pos_y, pos_x, alvo_y, alvo_x, num_rivais, cobra_pixel;
    int c;
        
    public Menu() {
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1260,1260);
        frame.setBackground(Color.black);
        frame.setVisible(true);
        frame.setLayout(null);  
        
        telaPrincipal = new JPanel();
        telaPrincipal.setBounds(400,100,600,500);
        telaPrincipal.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaPrincipal.setBackground(new Color(188,158,130));
        telaPrincipal.setLayout(null);
                
        telaReplay = new JPanel();
        telaReplay.setBounds(0,0,600,500);
        telaReplay.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaReplay.setBackground(new Color(188,158,130));
        telaReplay.setLayout(null);  
        
        
        newGame = new JButton("New Game");
        loadGame = new JButton("Load Game");
        newGame.addActionListener(this);
        loadGame.addActionListener(this);
        newGame.setForeground(Color.yellow);
        loadGame.setForeground(Color.yellow);
        newGame.setBackground(Color.black);
        loadGame.setBackground(Color.black);
        newGame.setBounds(175,150,250,60);
        loadGame.setBounds(175,270,250,60);
        newGame.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.yellow));
        loadGame.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.yellow));
        
        telaPrincipal.add(newGame);
        telaPrincipal.add(loadGame);
        frame.add(telaPrincipal);
        
        telaMenor = new JPanel();
        telaMenor.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaMenor.setBackground(new Color(168,138,110));
        telaMenor.setBounds(100,100,400,300);
        telaMenor.setLayout(null);
        
        jogadorNome[1] = "Bob";
        jogadorNivel[1] = 1;
        
        
        telaEstatisticas = new JPanel();
        telaEstatisticas.setBounds(0,0,600,800);
        telaEstatisticas.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaEstatisticas.setBackground(new Color(0, 51, 51));
        telaEstatisticas.setLayout(null);
        telaEstatisticas.addKeyListener(this);
        
        JLabel estatisticasText = new JLabel("Estatisticas");
        estatisticasText.setBounds(100,50,100,100);
        estatisticasText.setForeground(Color.white);
        telaEstatisticas.add(estatisticasText);
        JLabel estatisticasText2 = new JLabel("Ela tinha corpo de pera maca");
        estatisticasText2.setBounds(100,600,100,100);
        estatisticasText2.setForeground(Color.white);
        telaEstatisticas.add(estatisticasText2);
        icon = new ImageIcon("recuar.png");
        voltarEstatisticas = new JButton(icon);
        voltarEstatisticas.setBounds(15,245,52,40);
        voltarEstatisticas.setBackground(null);
        voltarEstatisticas.addActionListener(this);
        telaEstatisticas.add(voltarEstatisticas);
        icon = new ImageIcon("avancar.png");
        avancarEstatisticas = new JButton(icon);
        avancarEstatisticas.setBounds(550,245,52,40);
        avancarEstatisticas.setBackground(null);
        avancarEstatisticas.addActionListener(this);
        scrollEstatisticas = new JTextField();
        scrollEstatisticas.setBounds(200,205,52,40);
        scrollEstatisticas.addKeyListener(this);
        scrollEstatisticas.addMouseListener(this);
        
        telaEstatisticas.add(scrollEstatisticas);
        i = 0;
        width = 17;
        height = 17;
        rival = new boolean[height+2][width+2];
        rival_y = new byte[28][301];
        rival_x = new byte[28][301];
        rival_tamanho = new short[28];
        rival_direcao = new byte[28];
        rival_derrotado = new boolean[2];
        check_invalido = new boolean[61][5];
        check_direcao = new byte[61];
        check_y = new byte[61];
        check_x = new byte[61];
        checkpoint = new boolean[height+2][width+2];
        prioridade = new byte[5];
        livre = new boolean[height+2][width+2];
        obstaculo = new boolean[height+2][width+2];
        frame.repaint();
    }
    
    public void enviarInformacoes() {
        JogoDeCobra jdc = new JogoDeCobra();
        jdc.receberInformacoes(jogadorDificuldade[jogador], nivel);
        frame.dispose();
    }

    public int abs(int diferenca) {
        if (diferenca < 0)
            diferenca = (byte)(-1*diferenca);
        return diferenca;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == scrollEstatisticas) {
            /*i += 10;
            telaEstatisticas.setBounds(0,0-i,600,700);
            telaPrincipal.add(telaEstatisticas);
            frame.repaint();*/
            
        }
        if (e.getSource() == voltarContas) {
            frame.dispose();
            new Menu();
        }
        if (e.getSource() == newGame) {
            if (num_jogadores < 3) {
                telaPrincipal.removeAll();
                frame.repaint();
                usuarioField = new JTextField(15);
                senhaField = new JPasswordField(15);
                usuarioText = new JLabel("Usuario");
                senhaText = new JLabel("Senha");
                confirmarCadastro = new JButton("Confirmar");

                usuarioText.setForeground(Color.yellow);
                senhaText.setForeground(Color.yellow);
                usuarioText.setBounds(175,30,80,60);
                senhaText.setBounds(180,130,80,60);
                usuarioField.setBounds(80,80,250,40);
                senhaField.setBounds(80,180,250,40);

                confirmarCadastro.addActionListener(this);
                confirmarCadastro.setBackground(Color.orange);
                confirmarCadastro.setBounds(150,240,120,40);

                telaMenor.add(usuarioField);
                telaMenor.add(senhaField);
                telaMenor.add(usuarioText);
                telaMenor.add(senhaText);
                telaMenor.add(confirmarCadastro);
                telaPrincipal.add(telaMenor);
            }
            else {
                aviso = new JLabel("Atingiu o limite maximo de 3 contas");
                aviso.setBounds(175,170,250,60);
                aviso.setForeground(Color.white);
                aviso.setBounds(200,330,100,30);
            }              
        }    
        if (e.getSource() == confirmarCadastro) {
            telaMenor.removeAll();
            frame.repaint();
            num_jogadores++;
            jogadorNome[num_jogadores] = usuarioField.getText();
            jogadorSenha[num_jogadores] = new String(senhaField.getPassword());
            jogadorID[num_jogadores] = "ID";
            for (int i = 1; i <= 4; i++) {
                 jogadorID[num_jogadores] += (int)(Math.random()*10);
            }
            jogadorNivel[num_jogadores] = 1;
            System.out.println(jogadorNome[num_jogadores]);
            System.out.println(jogadorSenha[num_jogadores]);
            System.out.println(jogadorID[num_jogadores]);
            dificuldade = new JLabel("Dificuldade");
            dificuldade.setForeground(Color.black);
            dificuldade.setBounds(150,5,250,60);
            easyButton = new JButton("EASY");
            normalButton = new JButton("NORMAL");
            hardButton = new JButton("HARD");
            easyButton.addActionListener(this);
            normalButton.addActionListener(this);
            hardButton.addActionListener(this);
            easyButton.setBackground(Color.green);
            normalButton.setBackground(Color.yellow);
            hardButton.setBackground(Color.red);
            easyButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
            normalButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
            hardButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
            easyButton.setBounds(80,50,250,60);
            normalButton.setBounds(80,120,250,60);
            hardButton.setBounds(80,190,250,60);

            telaMenor.add(dificuldade);
            telaMenor.add(easyButton);
            telaMenor.add(normalButton);
            telaMenor.add(hardButton);
            //telaPrincipal.add(telaMenor);
        }
        if (e.getSource() == easyButton)
            jogadorDificuldade[num_jogadores] = "easy";
         else
           if (e.getSource() == normalButton)
               jogadorDificuldade[num_jogadores] = "normal";
            else
              if (e.getSource() == hardButton)
             jogadorDificuldade[num_jogadores] = "hard";
        jogador = num_jogadores;
        if (e.getSource() == loadGame || e.getSource() == voltarModo) {
            System.out.println("entrou");
            if (num_jogadores > 0) {
                if (e.getSource() == voltarModo)
                    telaMenor.removeAll();    
                telaPrincipal.removeAll();
                frame.repaint();
                JLabel jogador1Nivel = new JLabel("Nivel: "+jogadorNivel[1]);
                jogador1Button = new JButton(jogadorNome[1]);
                jogador1Button.setBounds(80,100,250,60);
                jogador1Button.setBackground(Color.yellow);
                jogador1Button.addActionListener(this);
                jogador1Nivel.setForeground(Color.black);
                jogador1Nivel.setBounds(200,140,50,10);
                telaMenor.add(jogador1Nivel);
                telaMenor.add(jogador1Button);
                if (num_jogadores >= 2) {
                    JLabel jogador2Nivel = new JLabel("Nivel: "+jogadorNivel[2]);
                    jogador2Button = new JButton(jogadorNome[2]);
                    jogador2Button.setBounds(80,150,250,60);
                    jogador2Button.setBackground(Color.yellow);
                    jogador2Button.addActionListener(this);
                    jogador2Nivel.setForeground(Color.black);
                    jogador2Nivel.setBounds(200,190,50,10);
                    telaMenor.add(jogador2Nivel);
                    telaMenor.add(jogador2Button);
                }
                if (num_jogadores == 3) {
                    JLabel jogador3Nivel = new JLabel("Nivel: "+jogadorNivel[3]);
                    jogador3Button = new JButton(jogadorNome[3]);
                    jogador3Button.setBounds(80,200,250,60);
                    jogador3Button.setBackground(Color.yellow);
                    jogador3Button.addActionListener(this);
                    jogador3Nivel.setForeground(Color.black);
                    jogador3Nivel.setBounds(200,240,50,10);
                    telaMenor.add(jogador3Nivel);
                    telaMenor.add(jogador3Button);
                }
                telaPrincipal.add(telaMenor);
            }
            else {
              aviso = new JLabel("Nenhum jogador cadastrado");
              aviso.setForeground(Color.white);
              aviso.setBounds(200,330,100,30);
            }
              
        }
        if (e.getSource() == jogador1Button)
            jogador = 1;
         else
           if (e.getSource() == jogador2Button)
               jogador = 2;
            else
              if (e.getSource() == jogador3Button)
                  jogador = 3;
        if (e.getSource() == voltarEstatisticas || e.getSource() == voltarNiveis || e.getSource() == jogador1Button || e.getSource() == jogador2Button || e.getSource() == jogador3Button || e.getSource() == easyButton || e.getSource() == normalButton || e.getSource() == hardButton) {
             if (e.getSource() == voltarNiveis || e.getSource() == voltarEstatisticas)
                 telaPrincipal.removeAll();
              else
                telaMenor.removeAll();
             frame.repaint();
             niveisButton = new JButton("Niveis");
             continuoButton = new JButton("Modo Continuo");
             icon = new ImageIcon("./Arte/Estatisticas.png");
             estatisticasButton = new JButton(icon);
             //multiplayerButton = new JButton("Multiplayer");
             niveisButton.setBounds(80,50,250,60);
             continuoButton.setBounds(80,120,250,60);
             estatisticasButton.setBounds(330,230,50,50);
             niveisButton.setForeground(Color.yellow);
             continuoButton.setForeground(Color.yellow);
             niveisButton.setBackground(Color.black);
             continuoButton.setBackground(Color.black);
             estatisticasButton.setBackground(null);
             niveisButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.yellow));
             continuoButton.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.yellow));
             //multiplayerButton.setBounds(80,190,250,60);
             niveisButton.addActionListener(this);
             continuoButton.addActionListener(this);
             estatisticasButton.addActionListener(this);
             //multiplayerButton.addActionListener(this);
             telaMenor.add(niveisButton);
             telaMenor.add(continuoButton);
             telaMenor.add(estatisticasButton);
             icon = new ImageIcon("voltar.png");
             voltarModo = new JButton(icon);
             voltarModo.setBounds(15,245,52,40);
             voltarModo.setBackground(null);
             voltarModo.addActionListener(this);
             telaMenor.add(voltarModo);
             telaPrincipal.add(telaMenor);
        }
        if (e.getSource() == niveisButton || e.getSource() == recuarButton) {
            telaPrincipal.removeAll();
            frame.repaint();
            niveisText = new JLabel("Niveis");
            niveisText.setForeground(Color.white);
            niveisText.setBounds(270,10,250,60);
            telaPrincipal.add(niveisText);
            icon = new ImageIcon("Avancar.png");
            avancarButton = new JButton(icon);
            avancarButton.setBounds(555,245,32,40);
            avancarButton.setBackground(null);
            avancarButton.addActionListener(this);
            
            icon = new ImageIcon("voltar.png");
            voltarNiveis = new JButton(icon);
            voltarNiveis.setBounds(15,445,52,40);
            voltarNiveis.setBackground(null);
            voltarNiveis.addActionListener(this);
            
            nivel1 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,50,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(90,80,6,6);
                }
            };
            //nivel1.setBorder(BorderFactory.createMatteBorder(3,3,3,3,Color.white));
            nivel2 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,50,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(100,90,6,6);
                    g.setColor(obstaculo_cor);
                    g.fillRect(70,70,8,8);
                    if (jogadorDificuldade[jogador].equals("normal") || jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(80,20,24,8);
                        g.fillRect(96,28,8,16);
                    }
                    if (jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(36,102,24,8);
                        g.fillRect(36,86,8,16);
                    }
                    if (jogadorNivel[jogador] < 2) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel3 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,25,8);
                    g.fillRect(28,38,8,25);
                    g.setColor(biscoito_cor);
                    g.fillRect(120,90,6,6);
                    g.setColor(obstaculo_cor);
                    g.fillRect(70,70,8,8);
                    g.fillRect(36,102,24,8);
                    g.fillRect(36,86,8,16);
                    if (jogadorDificuldade[jogador].equals("normal") || jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(80,20,24,8);
                        g.fillRect(96,28,8,16);
                        g.fillRect(80,102,24,8);
                        g.fillRect(96,86,8,16);
                    }
                    if (jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(62,62,16,16);
                        g.fillRect(36,20,24,8);
                        g.fillRect(36,28,8,16);
                    }
                    if (jogadorNivel[jogador] < 3) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            //nivelTest = new JButton("Nivel 3");
            //nivel4.setBounds(160,200,140,140);
            //telaPrincipal.add(nivelTest);
            nivel4 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,42,8);
                    g.fillRect(45,38,8,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(120,90,6,6);
                    g.setColor(obstaculo_cor);
                    g.fillRect(80,20,8,8);
                    g.fillRect(60,36,8,8);
                    if (jogadorDificuldade[jogador].equals("normal") || jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(100,64,8,8);
                        g.fillRect(40,80,8,8);
                    }
                    if (jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(80,104,8,8);
                        g.fillRect(60,112,8,8);
                    }
                    if (jogadorNivel[jogador] < 4) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel5 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,42,8);
                    g.fillRect(45,38,8,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(120,90,6,6);
                    g.setColor(obstaculo_cor);
                    g.fillRect(80,20,8,8);
                    g.fillRect(60,36,8,8);
                    g.fillRect(100,64,8,8);
                    g.fillRect(40,80,8,8);
                    g.fillRect(80,104,8,8);
                    g.fillRect(60,112,8,8);
                    if (jogadorDificuldade[jogador].equals("normal") || jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(20,20,8,8);
                        g.fillRect(112,112,8,8);
                    }
                    if (jogadorDificuldade[jogador].equals("hard")) {
                        g.fillRect(20,112,8,8);
                        g.fillRect(112,20,8,8);
                    }
                    if (jogadorNivel[jogador] < 5) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel6 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(10,32,8,50);
                    g.setColor(biscoito_cor);
                    g.fillRect(122,32,6,6);
                    g.setColor(obstaculo_cor);
                    for (int i = 1; i <= 17; i++) {
                        for (int j = 1; j <= 17; j++) {
                            if (jogadorDificuldade[jogador].equals("hard") || (jogadorDificuldade[jogador].equals("easy") && i % 3 != 0 && j % 3 != 0) || (jogadorDificuldade[jogador].equals("normal") && i % 3 != 0)) {
                                if (i == 1 || i == 17 || j == 1 || j == 17) {
                                    if (((j-1) % 3 == 0 && (i == 1 || i == 17)) || ((i-1) % 3 == 0 && (j == 1 || j == 17))) 
                                         g.fillRect(((j-1)*8)+2,((i-1)*8)+2,8,8);
                                }
                                else
                                  if (i % 2 == 1 && j % 2 == 1)
                                      g.fillRect(((j-1)*8)+2,((i-1)*8)+2,8,8);
                            }    
                        }
                    }
                    if (jogadorNivel[jogador] < 6) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel1.addMouseListener(this);
            nivel1.addActionListener(this);
            nivel2.addActionListener(this);
            nivel3.addActionListener(this);
            nivel4.addActionListener(this);
            nivel5.addActionListener(this);
            nivel6.addActionListener(this);
            nivel1.setBounds(40,100,140,140);
            nivel2.setBounds(230,100,140,140);
            nivel3.setBounds(420,100,140,140);
            nivel4.setBounds(40,300,140,140);
            nivel5.setBounds(230,300,140,140);
            nivel6.setBounds(420,300,140,140);
            telaPrincipal.add(nivel1);
            telaPrincipal.add(nivel2);
            telaPrincipal.add(nivel3);
            telaPrincipal.add(nivel4);
            telaPrincipal.add(nivel5);
            telaPrincipal.add(nivel6);
            telaPrincipal.add(avancarButton);
            telaPrincipal.add(voltarNiveis);
                
        }
        if (e.getSource() == avancarButton) {
            telaPrincipal.removeAll();
            frame.repaint();
            telaPrincipal.add(niveisText);
            icon = new ImageIcon("Recuar.png");
            recuarButton = new JButton(icon);
            recuarButton.setBounds(15,245,32,40);
            recuarButton.addActionListener(this);
            recuarButton.setBackground(null);
            nivel7 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,50,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(90,80,6,6);//mudar se conseguir dano a tempo
                    g.setColor(Color.cyan);
                    g.fillRect(123,53,17,7);
                    g.fillRect(123,77,17,7);
                    int boss_corpo_x[] = {100,108,124,132,124,108,100};
                    int boss_corpo_y[] = {70,53,53,70,84,84,70};
                    g.setColor(Color.red);
                    g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                    g.drawLine(80,70,86,70);
                    if (jogadorNivel[jogador] < 7) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel8 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,50,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(90,80,6,6);
                    g.setColor(new Color(0,0,0,200));
                    g.fillRect(11,30,50,8);
                    g.fillRect(90,80,6,6);
                    if (jogadorNivel[jogador] < 8) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel9 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,50,8);
                    g.setColor(biscoito_cor);
                    g.fillRect(90,30,6,6);
                    g.setColor(Color.red);
                    g.fillOval(60,70,40,40);
                    if (jogadorNivel[jogador] < 9) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel10 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    g.setColor(Color.black);
                    g.fillRect(0,0,140,140);
                    g.setColor(cobra_cor);
                    g.fillRect(11,30,8,50);
                    g.setColor(biscoito_cor);
                    g.fillRect(90,30,6,6);
                    g.setColor(Color.red);
                    g.fillRect(20,20,8,8);
                    g.fillRect(112,20,8,8);
                    g.fillRect(20,112,8,8);
                    g.fillRect(112,112,8,8);
                    if (jogadorNivel[jogador] < 10) {
                        g.setColor(new Color(0,0,0,200));
                        g.fillRect(0,0,140,140);
                    }
                }
            };
            nivel7.setBounds(40,100,140,140);
            if (jogadorDificuldade[jogador].equals("hard")) {
                nivel8.setBounds(230,100,140,140);
                nivel9.setBounds(420,100,140,140);
                nivel10.setBounds(40,300,140,140);
                telaPrincipal.add(nivel8);
                telaPrincipal.add(nivel9);
                telaPrincipal.add(nivel10);
            }
            nivel8.addActionListener(this);
            nivel9.addActionListener(this);
            nivel10.addActionListener(this);
            nivel7.addActionListener(this);
            telaPrincipal.add(nivel7);
            telaPrincipal.add(recuarButton);
        }
        if (e.getSource() == nivel1) {
            nivel = 1;
            enviarInformacoes();
        }    
        if (e.getSource() == nivel2) {
            if (jogadorNivel[jogador] >= 2) {
                nivel = 2;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel3) {
            if (jogadorNivel[jogador] >= 3) {
                nivel = 3;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel4) {
            if (jogadorNivel[jogador] >= 4) {
                nivel = 4;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel5) {
            if (jogadorNivel[jogador] >= 5) {
                nivel = 5;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel6) {
            if (jogadorNivel[jogador] >= 6) {
                nivel = 6;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel7) {
            if (jogadorNivel[jogador] >= 7) {
                nivel = 7;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel8) {
            if (jogadorNivel[jogador] >= 8) {
                nivel = 8;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel9) {
            if (jogadorNivel[jogador] >= 9) {
                nivel = 9;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == nivel10) {
            if (jogadorNivel[jogador] >= 10) {
                nivel = 10;
                enviarInformacoes();
            }
        }    
        if (e.getSource() == continuoButton) {
            if (jogadorNivel[jogador] <= 7) {
                aviso = new JLabel("Para desbloquear esse modo, primeiro venca o nivel 7");
                aviso.setBounds(80,190,250,60);
                aviso.setBackground(Color.white);
            }
            else {
              nivel = 0;
              enviarInformacoes();
            }
              
        }
        if (e.getSource() == estatisticasButton) {
            telaPrincipal.removeAll();
            telaPrincipal.add(telaEstatisticas);
            frame.repaint();
        }
        
        if (e.getSource() == avancarEstatisticas) {
            telaPrincipal.remove(telaEstatisticas);
            quadro_x = 80; quadro_y = 100;
            JogoDeCobra jdc = new JogoDeCobra();
            replay1 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    jdc.replay(g);
                }
            };
            replay1.setBounds(quadro_x,quadro_y,140,140);
            telaReplay.add(replay1);
            telaPrincipal.add(telaReplay);
            frame.repaint();
        }
          
    }
    
    public void comecarScores(Graphics g) {
        g.setColor(Color.green);
        quadro_x = 200;
        quadro_y = 100;
        String scores = "", tempStr = "";
        /*try {
            BufferedReader reader = new BufferedReader(new FileReader("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/estatisticas.txt"));
            tempStr = reader.readLine();
            num_pontos = Integer.parseInt(tempStr);
            scores = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //num_pontos = 50;
        num_pontos = 5;
        tempShrt = (short) (800/num_pontos);
        for (i = 1; i <= num_pontos; i++) {
            //ponto_y[i] = (short)((scores.charAt(i-1)*200)/500);
            //ponto_y[i] = (short)((Math.random()*100000)/400);
            ponto_y[i] = (i*100);
            ponto_x[i] = (short)(i*tempShrt);
            antigo_y[i] = ponto_y[i];
            antigo_x[i] = ponto_x[i];
            y = (short) ponto_y[i]; x = (short) ponto_x[i];
            expansao_scores[i] = "";
        }
        desenharScores(g);
        colapsarPonto(g);
        //colapsarPonto(g);
        expandirPonto(g);
        //expandirPonto(g);
    }
    
    public void desenharScores(Graphics g) {
        float tempFlt;
        int tempInt = 0, inicio, fim;
        g.setColor(Color.cyan);
        tempFlt = 0;
        for (i = 0; i < num_pontos; i++) {
            tempShrt = (short) (ponto_x[i+1]-ponto_x[i]);
            for (j = (int)ponto_x[i]; j < ponto_x[i+1]; j++) {
                tempFlt -= (ponto_y[i]-ponto_y[i+1])/tempShrt;
                /*System.out.println("Flt: "+tempFlt);
                if (tempInt == (int) tempFlt)
                    j--;*/
                tempInt = (int) tempFlt;
                g.fillOval(quadro_x+(j-1),quadro_y+(400-tempInt),4,4);
                try {
                    Thread.sleep(0);
                    //Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
                //System.out.println(j);
            }
        }
        g.setColor(Color.yellow);
        if (colapso == false && expansao == false)
            for (i = 1; i <= num_pontos; i++)  {
                g.fillOval(quadro_x+(short)(ponto_x[i]-5),quadro_y+(short)(400-ponto_y[i]),8,8);
                System.out.println("ponto_y["+i+"]: "+ponto_y[i]);
            }
         else {
            do {
                incompleto = false;
                i2 = 0;
                for (i = 1; i <= num_pontos; i++) {
                    if (i % 2 == 1)
                        i2++;
                    if (antigo_y[i] != ponto_y[i2]) {
                        incompleto = true;
                        y = (short) antigo_y[i]; x = (short) antigo_x[i];
                        g.setColor(Color.black);
                        g.fillOval(quadro_x+(x-5),quadro_y+(400-y),8,8);
                        if (antigo_x[i] != ponto_x[i2])
                            antigo_x[i] += x > ponto_x[i2]? -1 : 1;
                         else
                           antigo_y[i] += y > ponto_y[i2]? -1 : 1;
                        y = (short) antigo_y[i]; x = (short) antigo_x[i];
                        g.setColor(Color.yellow);
                        g.fillOval(quadro_x+(x-5),quadro_y+(400-y),8,8);
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                }
            } while (incompleto == true);
            colapso = false;
            expansao = false;
            num_pontos = i2;
         }
        System.out.println("Prompt");
        Scanner ler = new Scanner(System.in);
        ler.nextLine();
    }
    
    public void colapsarPonto(Graphics g) {
        colapso = true;
        i2 = num_pontos/2;
        i2 += num_pontos % 2 == 1? 1 : 0;
        tempShrt = (short) (800/i2);
        g.setColor(Color.white);
        inicio = 1;
        for (i = 1; i <= i2; i++) {
            ponto_x[i] = i*tempShrt;
            //ponto_y[i] = (ponto_y[i]+ponto_y[i+1])/2;
            expansao_grupos[i] = num_pontos % 2 == 1 && i == i2? (short) 1 : 2;
            fim = inicio + expansao_grupos[i];
            for (j = inicio; j < fim; j++) {
                ponto_y[i] += ponto_y[j];
                expansao_scores[i] += (char) ((antigo_y[j]/200)+32);
                if (antigo_y[j]/200 > (short) antigo_y[j]/200)
                    expansao_scores[i] += ",";
            }
            ponto_y[i] /= expansao_grupos[i];
            inicio = fim;
        }
        inicio = 1;
        for (i = 1; i <= i2; i++) {
            fim = inicio + expansao_grupos[i];
            for (j = inicio; j < fim; j++) {   
                antigo_y[j] = ponto_y[i];
                antigo_x[j] = ponto_x[i];
            }
            inicio = fim;
        }
        System.out.println("exp[1]: '"+expansao_scores[1]+"'");
        System.out.println("exp[2]: '"+expansao_scores[2]+"'");
        System.out.println("exp[3]: '"+expansao_scores[3]+"'");
        if (num_pontos % 2 == 1) {
            ponto_y[i2] = ponto_y[num_pontos];
            expansao_grupos[i2] = 1;
        }
        g.setColor(Color.black);
        g.fillRect(0,0,1260,728);
        g.setColor(Color.yellow);
        g.fillOval(quadro_x+(short)(ponto_x[i2]-5),quadro_y+(short)(400-ponto_y[i2]),8,8);
        desenharScores(g);
    }
  
    public void expandirPonto(Graphics g) {
        num_pontos = 0;
        inicio = 1;
        for (i = 1; i <= i2; i++)
            num_pontos += expansao_grupos[i];
        tempShrt = (short) (800/num_pontos);
        int tempFlt;
        for (i = 1; i <= i2; i++) {
            fim = inicio + expansao_grupos[i];
            tempStr = expansao_scores[i];
            int k = 0;
            for (j = inicio; j < fim; j++) {
                System.out.println("j: "+j);
                ponto_x[j] = (short) (j*tempShrt);
                //tempStr = expansao_scores[i].substring(expansao_c,expansao_c+2);
                ponto_y[j] = (short)((expansao_scores[i].charAt(k++)-32)*200);
                if (expansao_scores[i].length() > k && expansao_scores[i].charAt(k) == 44) {
                    ponto_y[j] += 100;
                    k++;
                }
            }
            inicio = fim;   
        }
        expansao = true;
        g.setColor(Color.black);
        g.fillRect(0,0,1260,728);
        g.setColor(Color.yellow);
        g.fillOval(quadro_x+(short)(ponto_x[i2]-5),quadro_y+(short)(400-ponto_y[i2]),8,8);
        desenharScores(g);
    }
    
    public static void main(String[] args) {
        new Menu();
    }

    @Override
    public void keyTyped(KeyEvent k) {
    }

    @Override
    public void keyPressed(KeyEvent k) {
        if (k.getKeyCode() == 40) {
            System.out.println("Hello");
        }
        if (k.getKeyCode() == 40 && i != -200) {
            i -= 10;
            telaEstatisticas.setBounds(0,0+i,600,700);
            scrollEstatisticas.setBounds(200,205-i,52,40);
            frame.repaint();
            System.out.println(i);
        }
        if (k.getKeyCode() == 38 && i != 0) {
            i += 10;
            telaEstatisticas.setBounds(0,0+i,600,700);
            scrollEstatisticas.setBounds(200,205-i,52,40);
            frame.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent k) {
    }

    @Override
    public void mouseClicked(MouseEvent m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent m) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void algo(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(0,0,200,200);
    }
    
    TimerTask tt;
    boolean iniciado = false;
    @Override
    public void mouseEntered(MouseEvent m) {
        if (m.getSource() == nivel1) {
            telaPrincipal.remove(nivel1);
            Timer t = new Timer();
            width = 17;
            height = 17;
            num_rivais = 1;
            quadro_y = 0;
            quadro_x = 0;
            cobra_pixel = 8;
            pixel = 8;
            rival_direcao[1] = 1;
            String algo = "";
            for (i = 0; i <= height+1; i++)
              for (j = 0; j <= width+1; j++) {
                 if (i == 0 || i == height+1 || j == 0||j == width+1 || obstaculo[i][j] == true)
                    livre[i][j] = false;
                  else
                    livre[i][j] = true;
              }      
            System.out.println("'"+rival_tamanho[0]+"'");
            System.out.println("'"+rival_tamanho[0]+"'");
            biscoito_y = 16;
            biscoito_x = 15;
            id = 1;
            tt = new TimerTask() {
                @Override 
                public void run() {
                    nivel1 = new JButton() {
                        @Override    
                        protected void paintComponent(Graphics g) {
                            if (iniciado == false) {
                                iniciarRival(g);
                                iniciado = true;
                            }
                            id = 1;
                            g.setColor(fundo_cor);
                            g.fillRect(quadro_x,quadro_y,140,140);
                            moverRival(g, cobra_cor);
                            g.setColor(cobra_cor);
                            for (i = 1; i <= rival_tamanho[1]; i++)
                                g.fillRect(quadro_x+((rival_x[1][i]-1)*pixel),quadro_y+((rival_y[1][i]-1)*pixel),cobra_pixel, cobra_pixel);
                            if (rival[biscoito_y][biscoito_x] == true)
                                do {
                                    biscoito_y = (byte) ((Math.random() * (height-2)) + 2);
                                    biscoito_x = (byte) ((Math.random() * (width-2)) + 2);
                                } while (livre[biscoito_y][biscoito_x] == false);
                            if (rival_derrotado[1] == true) 
                                tt.cancel();
                            g.setColor(biscoito_cor);
                            g.fillRect(quadro_x+((biscoito_x-1)*pixel),quadro_y+((biscoito_y-1)*pixel),cobra_pixel-3, cobra_pixel-3);
                            System.out.println("rival_y: "+rival_y[1][1]+", rival_x: "+rival_x[1][1]);
                            System.out.println("biscoito_y: "+biscoito_y+", biscoito_x: "+biscoito_x);
                            System.out.println("tamanho: "+rival_tamanho[1]);
                            System.out.println("derrotado: "+rival_derrotado[1]);
                        }
                    };
                    nivel1.setBounds(40,100,140,140);
                    telaPrincipal.add(nivel1);
                    frame.repaint();
                }
            };t.schedule(tt,0,1000);
        }
    }

    @Override
    public void mouseExited(MouseEvent m) {
        if (m.getSource() == scrollEstatisticas) {
            System.out.println("Saiu");
            tt.cancel();
            
        }
    }
    //Deletar depois
    public String pergunta, boss_ataque_tipo;
    boolean[][] boss_ataque;
                           
    public float duplicador, boss_impacto_velocidade;
    public Color rival_cor1 = Color.red;
    public Color rival_cor2 = Color.orange;
    public Color minas_cor = Color.red;
    public Color boss_ataque_laser_cor = Color.red;
    public Color boss_ataque_perfurador_cor = new Color(255,255,127);
    //public Color boss_ataque_refletidor_cor = Color.orange;
    public Color boss_ataque_refletor_cor1 = Color.orange;
    public Color boss_ataque_refletor_cor2 = Color.white;
    public Color boss_ataque_beam_cor1 = Color.white;
    public Color boss_ataque_beam_cor2 = new Color(255,255,127);
    public Color cobra_tiro_cor = new Color(185,170,227);
    
    public void iniciarRival(Graphics g) {
        if (iniciar_jogo == false) {
            c = cobra_pixel;
            if (iniciado == false) {    
                y = 5; 
                x = 12;
            }
            else
              do {
                  y = (byte) ((Math.random() * height) + 1);
                  x = (byte) ((Math.random() * width) + 1);
              } while (livre[y][x] == false);
            if (id == 0)
                g.setColor(rival_cor1);
             else
               g.setColor(rival_cor2);
            livre[y][x] = false;
            rival[y][x] = true;
            rival_y[id][1] = (byte) y;
            rival_x[id][1] = (byte) x;
            g.fillRect(quadro_x+((x-1)*pixel),quadro_y+((y-1)*pixel),c,c);
            rival_tamanho[id] = 7;
        }
        else {
          for (id = 1; id <= 2; id++) {
              switch (id) {
                  case 1 -> rival_tamanho[id] = 26; //S
                  case 2,21 -> rival_tamanho[id] = 20; //N
                  case 3,12,19 -> rival_tamanho[id] = 15; //A1
                  case 4,13,20 -> rival_tamanho[id] = 15; //A2
                  case 5 -> rival_tamanho[id] = 15; //K1
                  case 6 -> rival_tamanho[id] = 15; //K2
                  case 7 -> rival_tamanho[id] = 15; //K3
                  case 8,16,26 -> rival_tamanho[id] = 15; //E1
                  case 9,17,27 -> rival_tamanho[id] = 15; //E2
                  case 10,11 -> rival_tamanho[id] = 15; //:
                  case 14 -> rival_tamanho[id] = 15; //R1
                  case 15 -> rival_tamanho[id] = 15; //R2
                  case 18 -> rival_tamanho[id] = 15; //V
                  case 22 -> rival_tamanho[id] = 15; //C
                  case 23 -> rival_tamanho[id] = 15; //H1
                  case 24 -> rival_tamanho[id] = 15; //H2
                  case 25 -> rival_tamanho[id] = 15; //H3
              }
              tempShrt = (short)((Math.random()*80)+1);
              if (tempShrt >= 5) {//x extremo
                  rival_y[id][1] = (byte) tempShrt;
                  rival_x[id][1] = (byte)(Math.random()*10) >= 5? (byte) 420 : 0; 
              }
              else {
                  rival_x[id][1] = (byte) tempShrt;
                  rival_y[id][1] = (byte)(Math.random()*10) >= 5? (byte) 280 : 0; 
              }
              rival_direcao[id] = rival_x[id][1] == width? (byte) 1 : 3;
              System.out.println(rival_y[id][1]+"  "+rival_x[id][1]);   
          }
        }
            
    }
    
    public void metaHorizontal() {
        if (alvo_x > pos_x) {
            i2++;
            prioridade[i2] = 1;}
         else
           if (alvo_x < pos_x) {
               i2++;
               prioridade[i2] = 3;
           }
    }
    public void metaVertical() {
        if (alvo_y < pos_y) {
            i2++;
            prioridade[i2] = 2;}
         else
           if (alvo_y > pos_y) {
               i2++;
               prioridade[i2] = 4;
           }
    }
    
    public void checkpointReiniciar(Graphics g,boolean[][] rival) {
        for (i = 0; i <= num_check; i++)
            for (j = 0; j <= 4; j++)
               check_invalido[i][j] = false;
        for (i = 1; i <= height; i++)
            for (j = 1; j <= width; j++) 
                if (checkpoint[i][j] == true)
                    checkpoint[i][j] = false;
        num_check = 0;
        check_y[0] = rival_y[id][1];
        check_x[0] = rival_x[id][1];
        //check_invalido[0][atual < 3? atual+2 : atual-2] = true;
        permissao = true;
        rival_desistiu = false;
        rival_anterior = rival_direcao[id];
        check_direcao[0] = rival_direcao[id];
        atual = rival_direcao[id];
        pos_y = (byte) rival_y[id][1];
        pos_x = (byte) rival_x[id][1];
        /*if (iniciar_jogo == true) {
            alvo_y = alvo_y2[id][alvo_c[id]];
            alvo_x = alvo_x2[id][alvo_c[id]];
        }
        else {*/
            /*if (id == 0) {
                alvo_y = cobra_y[0][0];
                alvo_x = cobra_x[0][0];
                if ((abs(pos_y-cobra_y[0][1]) > 1))
                    alvo_y += cobra_y[0][0]-cobra_y[0][1];
                 else
                   if (abs(pos_x-cobra_x[0][1]) > 1) {
                       alvo_x += cobra_x[0][0]-cobra_x[0][1];
                }
                g.setColor(Color.red);
            }
            else {*/
              alvo_y = biscoito_y;
              alvo_x = biscoito_x;  
              g.setColor(Color.orange);
            /*}
        }*/
        System.out.println("percebido: y: "+alvo_y+", x: "+alvo_x);
        /*System.out.println(cobra_x[0][0]);
        System.out.println(cobra_x[0][1]);
        if (livre[alvo_y][alvo_x] == true && checkpoint[alvo_y][alvo_x] == false)
          g.drawString("T",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));
       else
         g.drawString("F",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));*/
    }
          
  public void moverRival(Graphics g, Color rival_cor) {
        checkpointReiniciar(g,rival);
        while (num_check < 60) {
            i2 = 0;
            anterior = atual;
            if (pos_x == inicio_x && ((inicio_y <= pos_y && pos_y < fim_y)||(fim_y < pos_y && pos_y <= inicio_y))) {
                metaVertical();
                metaHorizontal(); }
            else {
                metaHorizontal();
                metaVertical();
            }
            if (iniciar_jogo == true)
                break;
            if (i2 == 0 || rival_desistiu == true)
                break;
            else {
              if ((anterior % 2 == 0 && i2 == 2) || (i2 == 1 && prioridade[1] % 2 == 1))
                  prioridade[4] = alvo_x > rival_x[id][1]? (byte) 3 : 1;
               else
                 prioridade[4] = alvo_y < rival_y[id][1]? (byte) 4 : 2;
              for (j = 1; j <= 4; j++) {
                  if (j != prioridade[1] && (j != prioridade[2] || i2 < 2) && j != prioridade[4]) {
                      i2++;
                      prioridade[i2] = (byte)(j);//i2
                  }
                  if (i2 == 3) 
                      break;
              }
              y = pos_y; x = pos_x;
              atual = 0;
              for (j = 1; j <= 4; j++) {
                  //refazer a parte do check_invalido
                  if (prioridade[j] == 1 && checkpoint[y][x+1] == false&& (livre[y][x+1] == true || (y == biscoito_y && x+1 == biscoito_x)) && anterior != 3 && (check_invalido[num_check][1] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_x++; atual = 1;
                      break;}
                  if (prioridade[j] == 2 && checkpoint[y-1][x] == false && (livre[y-1][x] == true || (y-1 == biscoito_y && x == biscoito_x)) && anterior != 4 && (check_invalido[num_check][2] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_y--; atual = 2;
                      break;}
                  if (prioridade[j] == 3 && checkpoint[y][x-1] == false  && (livre[y][x-1] == true || (y == biscoito_y && x-1 == biscoito_x)) && anterior != 1 && (check_invalido[num_check][3] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_x--; atual = 3;
                      break;}
                  if (prioridade[j] == 4 && checkpoint[y+1][x] == false && (livre[y+1][x] == true || (y+1 == biscoito_y && x == biscoito_x)) && anterior != 2 && (check_invalido[num_check][4] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_y++; atual = 4;
                      break;}
              }
              checkpoint[y][x] = true;
              if (permissao == true && atual != 0) {
                  rival_direcao[id] = atual;
                  permissao = false;
              }
              if (anterior != atual && atual != 0) {
                  check_y[num_check+1] = pos_y;
                  check_x[num_check+1] = pos_x;
                  switch (atual) {
                      case 1 -> check_x[num_check+1]--;
                      case 2 -> check_y[num_check+1]++;
                      case 3 -> check_x[num_check+1]++;
                      case 4 -> check_y[num_check+1]--;
                  }
                  if (check_y[1] != check_y[0] || check_x[1] != check_x[0])
                      num_check++;
                  check_direcao[num_check] = atual;
                  if (abs(rival_x[id][1]-pos_x) <= 1 && atual % 2 == 0) {
                      inicio_x = pos_x;
                      inicio_y = rival_y[id][1]; fim_y = pos_y;
                  }
              }
              else {
                y = pos_y; x = pos_x;
                System.out.println("id: "+id);
                System.out.println("y: "+pos_y+", x: "+pos_x);
                if (atual != 0 && (y != rival_y[id][1] || x != 
                        rival_x[id][1]) && 
                        ((obstaculo[y-1][x-1] == true && 
                        obstaculo[y-1][x] == false && 
                        obstaculo[y-1][x+1] == true) || 
                        (obstaculo[y-1][x+1] == true && 
                        obstaculo[y][x+1] == false && 
                        obstaculo[y+1][x+1] == true) || 
                        (obstaculo[y+1][x+1] == true && 
                        obstaculo[y+1][x] == false && 
                        obstaculo[y+1][x-1] == true) || 
                        (obstaculo[y+1][x-1] == true && 
                        obstaculo[y][x-1] == false && 
                        obstaculo[y-1][x-1] == true))) {
                    num_check++;
                    check_y[num_check] = pos_y;
                    check_x[num_check] = pos_x;
                    check_direcao[num_check] = atual;
                }
              }
              while (atual == 0 && num_check >= 0 && rival_desistiu == false) {
                  check_invalido[num_check][check_direcao[num_check]] = true;
                  for (j = 1; j <= 4; j++) {
                      if (check_invalido[num_check][prioridade[j]] == false) {
                          atual = num_check > 0? check_direcao[num_check-1] : rival_anterior;
                          pos_y = check_y[num_check];
                          pos_x = check_x[num_check];
                          check_direcao[num_check] = prioridade[j];
                          permissao = num_check == 0;
                          break;
                      }    
                  }
                  if (j == 5) {
                    if (num_check == 0)
                        rival_desistiu = true;
                    else {
                      for (j = 1; j <= 4; j++)
                         check_invalido[num_check][j] = false;
                      num_check--;
                      pos_y = check_y[num_check];
                      pos_x = check_x[num_check];
                    }
                  }
              }
            }
        }
        if (iniciar_jogo == true)
            rival_direcao[id] = prioridade[1];
        System.out.println("prior: "+prioridade[1]);
        rival_y[id][0] = rival_y[id][1];
        rival_x[id][0] = rival_x[id][1];
        switch (rival_direcao[id]) {
            case 1 -> rival_x[id][0]++;
            case 2 -> rival_y[id][0]--;
            case 3 -> rival_x[id][0]--;
            case 4 -> rival_y[id][0]++;
        }
        System.out.println("dir: "+rival_direcao[id]);
        System.out.println("Pos: "+rival_y[id][0]+" "+rival_x[id][0]);
        x = rival_x[id][0]; y = rival_y[id][0];
        if (iniciar_jogo == true) {
            y = 0; x = 0;
        }
        if (livre[y][x] == false && (y != biscoito_y || x != biscoito_x))
            rival_derrotado[id] = true;                
         else {
            x =  rival_x[id][rival_tamanho[id]]; y = rival_y[id][rival_tamanho[id]];
            if (iniciar_jogo == false) {
                livre[y][x] = true;
                rival[y][x] = false;
            }
            g.setColor(fundo_cor);
            g.fillRect(quadro_x+((x-1)*pixel),quadro_y+((y-1)*pixel),c,c);
            for (i = (byte) (rival_tamanho[id]-1); i >= 0; i--) {
                rival_x[id][i+1] = rival_x[id][i];
                rival_y[id][i+1] = rival_y[id][i];
            }
            x = rival_x[id][1]; y = rival_y[id][1];
            if (iniciar_jogo == false) {
                livre[y][x] = false;
                rival[y][x] = true;
            }
            if (id == 0)
                g.setColor(rival_cor1);
             else
               g.setColor(rival_cor2);
            if (cobra_redonda == false)
                g.fillRect(quadro_x+((x-1)*pixel),quadro_y+((y-1)*pixel),c,c);
             else
               g.fillOval(quadro_x+((x-1)*pixel),quadro_y+((y-1)*pixel),c,c); 
            if (y == biscoito_y && x == biscoito_x && iniciar_jogo == false) {
                rival_y[0][++rival_tamanho[0]] = rival_y[0][rival_tamanho[0]-1];
                rival_x[0][rival_tamanho[0]] = rival_x[0][rival_tamanho[0]-1];
                rival_y[1][++rival_tamanho[1]] = rival_y[1][rival_tamanho[1]-1];
                rival_x[1][rival_tamanho[1]] = rival_x[1][rival_tamanho[1]-1];
                //rival_biscoitos_comidos++;
                //rival_comeu = true;
            }
        }    
            
    }
    
}