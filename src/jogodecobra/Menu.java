package jogodecobra;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
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

public class Menu implements ActionListener {
   
    JFrame frame;
    JPanel telaPrincipal, telaEstatisticas, telaReplay, telaMenor, telaModo;
    JButton newGame, loadGame, confirmarCadastro, easyButton, normalButton, hardButton, jogador1Button, jogador2Button, jogador3Button, niveisButton, continuoButton, multiplayerButton, estatisticasButton;
    JButton nivel1, nivel2, nivel3, nivel4, nivel5, nivel6, nivel7, nivel8, nivel9, nivel10, avancarButton, recuarButton, voltarContas, voltarNiveis, voltarModo, voltarEstatisticas, avancarEstatisticas;
    JButton replay1, replay2, replay3;
    JButton nivelTest;
    JTextField usuarioField;
    JPasswordField senhaField;
    Icon icon;
    JLabel aviso, mainBorder, miniBorder, usuarioText, senhaText, dificuldade, niveisText;
    Color cobra_cor = new Color(0,255,160);
    Color biscoito_cor = Color.yellow;
    Color obstaculo_cor = Color.red;
    
    String[] jogadorNome = new String[4];
    String[] jogadorSenha = new String[4];
    String[] jogadorID = new String[4];
    String[] jogadorDificuldade = new String[4];
    byte[] jogadorNivel = new byte[4];
    
    int inicio, fim, num_pontos = 0;
    short quadro_x = 440, quadro_y = 120, pixel = 11, comp = 420, comp2 = 508, larg = 6, x = 20;
    byte nivel, jogador, num_jogadores = 0;
    short tempShrt = 0;
    boolean incompleto, colapso = false, expansao = false;
    
    float[] ponto_x = new float[2001];
    float[] ponto_y = new float[2001];
    float[] antigo_x = new float[2001];
    float[] antigo_y = new float[2001];
    short[] expansao_grupos = new short[101];
    String[] expansao_scores = new String[101];
    
    
    public String[] alternativa = new String[3];
    public byte[][] cobra_x = new byte[4][301];
    public byte[][] cobra_y = new byte[4][301];
    public boolean[][] cobra, rival, cobra_caida, escolha, livre, obstaculo_movido;
    public short[] cobra_tamanho;
    public byte alvo_y, alvo_x;
    byte width = 30;
    byte height = 30;
    public byte[][] obstaculo_direcao;
    public int i, i2, j, j2, sco, tempInt, y;
    public Color fundo_cor = Color.black;
    public boolean[][] obstaculo;
    public byte[][] obstaculo_grupo;
    
    
    public byte[] rival_y = new byte[61];
    public byte[] rival_x = new byte[61];
    public byte[] prioridade = new byte[5];
    public byte[] check_y = new byte[61];
    public byte[] check_x = new byte[61];
    public byte[] check_direcao = new byte[61];
    public boolean[][] check_invalido = new boolean[61][5];
    public boolean[][] checkpoint;
    public byte biscoito_y, biscoito_x, num_check, anterior, atual, pos_y, pos_x, inicio_y, inicio_x, fim_y, rival_direcao, rival_anterior, rival_tamanho;
    public boolean rival_desistiu, rival_derrotado, permissao;
        
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
        
        telaEstatisticas = new JPanel();
        telaEstatisticas.setBounds(0,0,600,500);
        telaEstatisticas.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaEstatisticas.setBackground(new Color(188,158,130));
        telaEstatisticas.setLayout(null);  
        
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
             icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Estatisticas.png");
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
            JLabel estatisticasText = new JLabel("Estatisticas");
            estatisticasText.setBounds(100,50,100,100);
            estatisticasText.setForeground(Color.white);
            telaEstatisticas.add(estatisticasText);
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
            telaEstatisticas.add(avancarEstatisticas);
            telaPrincipal.add(telaEstatisticas);
            frame.repaint();
        }
        
        if (e.getSource() == avancarEstatisticas) {
            telaPrincipal.remove(telaEstatisticas);
            quadro_x = 80; quadro_y = 100;
            replay1 = new JButton() {
                @Override    
                protected void paintComponent(Graphics g) {
                    replay(g);
                }
            };
            replay1.setBounds(quadro_x,quadro_y,140,140);
            telaReplay.add(replay1);
            telaPrincipal.add(telaReplay);
            frame.repaint();
        }
          
    }
    
    public void replay(Graphics g) {
        int ciclos = 0, biscoito_c = 0, obs_num_c = 0, tamanho = 7, obs_add_c = 0;
        byte pos = 0;
        String highscore = "", tempStr = "", direcoes = "", biscoitos = "", obs_add = "", obs_num = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/jogador.txt"));
            highscore = reader.readLine();
            direcoes = reader.readLine();
            ciclos = direcoes.length();
            biscoitos = reader.readLine();
            obs_add = reader.readLine();
            obs_num = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        g.setColor(cobra_cor);
        pos = 4;
        for (i = 7; i >= 1; i--) {
            cobra_x[0][i] = pos++;
            cobra_y[0][i] = 5;
            x = cobra_x[0][i];
            g.fillRect((x-1)*11,4*11,11,11);
            System.out.println("cobra_y: "+cobra_y[0][i]+", cobra_x: "+cobra_x[0][i]);
        }
        System.out.println("highscore: "+highscore);
        System.out.println("direcoes: "+direcoes);
        System.out.println("biscoitos: "+biscoitos);
        System.out.println("obs_add: "+obs_add);
        System.out.println("obs_num: "+obs_num);
        biscoito_y = Byte.parseByte(biscoitos.substring(biscoito_c,biscoito_c+2));
        biscoito_x = Byte.parseByte(biscoitos.substring(biscoito_c+2,biscoito_c+4));
        
        biscoito_c += 4;
        y = biscoito_y; x = biscoito_x;
        g.setColor(biscoito_cor);
        g.fillOval((x-1)*11,(y-1)*11,10,10);
        
        for (i = 0; i < 10; i++) {
            System.out.println("i: "+i);
            cobra_y[0] = cobra_y[1];
            cobra_x[0] = cobra_x[1];
            switch (direcoes.charAt(i)) {
                case 119: {cobra_y[0][0] = (byte) (cobra_y[0][1] - 1);//W
                           cobra_x[0][0] = cobra_x[0][1];
                           break;}
                case 97: {cobra_x[0][0] = (byte) (cobra_x[0][1] - 1);//A
                          cobra_y[0][0] = cobra_y[0][1];
                          break;}
                case 115: {cobra_y[0][0] = (byte) (cobra_y[0][1] + 1);//S
                           cobra_x[0][0] = cobra_x[0][1];
                           break;}
                case 100: {cobra_x[0][0] = (byte) (cobra_x[0][1] + 1);//D
                           cobra_y[0][0] = cobra_y[0][1];
                           break;}
            }
            g.setColor(fundo_cor);
            x = cobra_x[0][tamanho]; y = cobra_y[0][tamanho];
            g.fillRect((x-1)*11,(y-1)*11,11,11);
            for (j = (byte) (tamanho-1); j >= 0; j--) {
                cobra_x[0][j+1] = cobra_x[0][j];
                cobra_y[0][j+1] = cobra_y[0][j];
            }
            System.out.println("cobra_y[0]: "+cobra_y[0][1]+", cobra_x[0]: "+cobra_x[0][1]);
            //System.out.println("biscoito_y: "+biscoito_y+", biscoito_x: "+biscoito_x);
            g.setColor(cobra_cor);
            x = cobra_x[0][1]; y = cobra_y[0][1];
            g.fillRect((x-1)*11,(y-1)*11,11,11);
            if (y == biscoito_y && x == biscoito_x) {
                //System.out.println("biscoito_y: "+biscoito_y+", biscoito_x: "+biscoito_x);
                //System.out.println(biscoito_y+""+biscoito_x);
                biscoito_y = Byte.parseByte(biscoitos.substring(biscoito_c,biscoito_c+2));
                biscoito_x = Byte.parseByte(biscoitos.substring(biscoito_c+2,biscoito_c+4));
                tamanho++;
                biscoito_c += 4;
                y = biscoito_y; x = biscoito_x;
                g.setColor(biscoito_cor);
                g.fillOval((x-1)*11,(y-1)*11,10,10);
                g.setColor(obstaculo_cor);
                tempInt = Integer.parseInt(obs_num.substring(obs_num_c,obs_num_c+1));
                for (j = 0; j <= tempInt; j++) {
                    y = Byte.parseByte(obs_add.substring(obs_add_c,obs_add_c+2));
                    x = Byte.parseByte(obs_add.substring(obs_add_c+2,obs_add_c+4));
                    g.fillRect((x-1)*11,(y-1)*11,11,11);
                    //System.out.println(y+""+x);
                    obs_add_c += 4;
                }
                tempStr = "";
                for (j = biscoito_c; j < biscoitos.length(); j++)
                    tempStr += biscoitos.charAt(j);
                //for (j = obs_add_c; j < obs_add.length(); j++)
                //    tempStr += obs_add.charAt(j);
                System.out.println("\n"+tempStr+"\n");
                obs_num_c++;
            }
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
        String tempStr;
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
        //Menu menu = new Menu();
        Menu menu = new Menu();
    }
    
}/*
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
        for (int i = 0; i <= num_check; i++)
            for (int j = 0; j <= 4; j++)
               check_invalido[i][j] = false;
        for (i = 1; i <= height; i++)
            for (j = 1; j <= width; j++) 
                if (checkpoint[i][j] == true)
                    checkpoint[i][j] = false;
        num_check = 0;
        check_y[0] = rival_y[1];
        check_x[0] = rival_x[1];
        //check_invalido[0][atual < 3? atual+2 : atual-2] = true;
        permissao = true;
        rival_desistiu = false;
        check_direcao[0] = rival_direcao;
        atual = rival_direcao;
        pos_x = (byte) rival_x[1];
        pos_y = (byte) rival_y[1];
        alvo_y = cobra_y[0][0];
        alvo_x = cobra_x[0][0];
        g.setColor(Color.orange);
        //System.out.println(cobra_x[0][0]);
        //System.out.println(cobra_x[0][1]);
        //if (livre[alvo_y][alvo_x] == true && checkpoint[alvo_y][alvo_x] == false)
        //  g.drawString("T",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));
       //else
        // g.drawString("F",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));
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
            if (i2 == 0 || rival_desistiu == true)
                break;
            else {
              if ((anterior % 2 == 0 && i2 == 2) || (i2 == 1 && prioridade[1] % 2 == 1))
                  prioridade[4] = alvo_x > rival_x[1]? (byte) 3 : 1;
               else
                 prioridade[4] = alvo_y < rival_y[1]? (byte) 4 : 2;
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
                  if (prioridade[j] == 1 && checkpoint[y][x+1] == false && (livre[y][x+1] == true || (y == biscoito_y && x+1 == biscoito_x)) && anterior != 3 && (check_invalido[num_check][1] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_x++; atual = 1;
                      break;}
                  if (prioridade[j] == 2 && checkpoint[y-1][x] == false && (livre[y-1][x] == true || (y-1 == biscoito_y && x == biscoito_x)) && anterior != 4 && (check_invalido[num_check][2] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_y--; atual = 2;
                      break;}
                  if (prioridade[j] == 3 && checkpoint[y][x-1] == false && (livre[y][x-1] == true || (y == biscoito_y && x-1 == biscoito_x)) && anterior != 1 && (check_invalido[num_check][3] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_x--; atual = 3;
                      break;}
                  if (prioridade[j] == 4 && checkpoint[y+1][x] == false && (livre[y+1][x] == true || (y+1 == biscoito_y && x == biscoito_x)) && anterior != 2 && (check_invalido[num_check][4] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check])) {
                      pos_y++; atual = 4;
                      break;}
              }
              checkpoint[y][x] = true;
              if (permissao == true && atual != 0) {
                  rival_direcao = atual;
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
                  if (abs(rival_x[1]-pos_x) <= 1 && atual % 2 == 0) {
                      inicio_x = pos_x;
                      inicio_y = rival_y[1]; fim_y = pos_y;
                  }
              }
              else {
                y = pos_y; x = pos_x;
                if (atual != 0 && (y != rival_y[1] || x != rival_x[1]) && ((obstaculo[y-1][x-1] == true && obstaculo[y-1][x] == false && obstaculo[y-1][x+1] == true) || (obstaculo[y-1][x+1] == true && obstaculo[y][x+1] == false && obstaculo[y+1][x+1] == true) || (obstaculo[y+1][x+1] == true && obstaculo[y+1][x] == false && obstaculo[y+1][x-1] == true) || (obstaculo[y+1][x-1] == true && obstaculo[y][x-1] == false && obstaculo[y-1][x-1] == true))) {
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
        rival_y[0] = rival_y[1];
        rival_x[0] = rival_x[1];
        switch (rival_direcao) {
            case 1 -> rival_x[0]++;
            case 2 -> rival_y[0]--;
            case 3 -> rival_x[0]--;
            case 4 -> rival_y[0]++;
        }
        x = rival_x[0]; y = rival_y[0];
        if (livre[y][x] == false && (y != biscoito_y || x != biscoito_x))
            rival_derrotado = true;
        else {
            x = rival_x[rival_tamanho]; y = rival_y[rival_tamanho];
            livre[y][x] = true;
            rival[y][x] = false;
            g.setColor(fundo_cor);
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            for (i = (byte) (rival_tamanho-1); i >= 0; i--) {
                rival_x[i+1] = rival_x[i];
                rival_y[i+1] = rival_y[i];
            }
            x = rival_x[1]; y = rival_y[1];
            livre[y][x] = false;
            rival[y][x] = true;
            g.setColor(rival_cor);
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
        }    
            
    }    
    public void algo(Graphics g) {
        g.setColor(Color.blue);
        g.fillRect(quadro_x,quadro_y,200,200);
    }
    
    public void enviarInformacoes() {
        JogoDeCobra jdc = new JogoDeCobra();
        jdc.receberInformacoes(jogadorDificuldade[jogador], nivel);
        frame.dispose();
    }
/*Niveis
public Menu() {
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TestPane());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public class TestPane extends JPanel implements ActionListener {

        JButton b, c;
        JPanel d;
        Timer t;
        boolean iniciado;
        MiniTelas mini = new MiniTelas();
        
        public TestPane() {
            setBackground(Color.white);
            setLayout(null);  
            b = new JButton("Enter");
            add(b);
            c = new JButton("Buenas");
            c.setBounds(50,50,150,150);
            c.addActionListener(this);
            c.setForeground(Color.black);
            d = new JPanel();
            d.setBounds(50,50,150,150);
            d.setBackground(Color.black);
            add(c);
            add(d);
            t = new Timer();
            iniciado = false;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800,800);
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            super.paintComponent(g);
            short quadro_x, quadro_y;
            quadro_x = 0; quadro_y = 0;
            for (byte id = 1; id <= 6; id++) {
                quadro_y = id <= 3? (short) 150 : 350;
                switch (id) {   
                    case 1,4 -> quadro_x = 200;
                    case 2,5 -> quadro_x = 400;
                    case 3,6 -> quadro_x = 600;
                }
                if (iniciado == false)
                    mini.iniciarCobra(g, id, quadro_x, quadro_y);
                mini.moverRival(g,id,quadro_x,quadro_y);
            }
            iniciado = true;
            repaint();
            paint(g);
        }
        
        @Override
        public void paint(Graphics g) {
           paintComponent(g);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == c) {
                System.out.println("Hey baby");
            }
        }
*/