package jogodecobra;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class JogoDeCobra extends JFrame implements KeyListener {
    
    public JogoDeCobra() {
    }
    
    public JogoDeCobra(String algo) {
        setTitle("Desenhos");
        setSize(1260,1260);
        setVisible(true);
        setBackground(fundo_cor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*addKeyListener(this);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }    
    
    public JButton b, proximoNivel, sair, reiniciarButton;
    public ImageIcon icon;
    public JLabel efeitoImg;
    public JFrame frame;

    public String[] alternativa = new String[3];
    public String pergunta, boss_ataque_tipo, tempStr, dificuldade;
    public byte[][] cobra_y, cobra_x;
    public boolean[] cobra_derrotada;
    public boolean[] rival_derrotado = new boolean[2];
    public short[] rival_tamanho;
    byte[] escolha_y;
    byte[] escolha_x;
    public boolean[][] cobra, rival, cobra_caida, escolha, livre, obstaculo_movido, minas;
    public short[] cobra_tamanho;
    boolean[][] boss_ataque;
    public byte[][] obstaculo_direcao;
    public int i, i2, j, j2, sco, tempInt, c, inicio, fim;
    public char cobra_tiro_direcao, tempChr;
    public short boss_turbina_y, boss_turbina_x, boss_ataque_x, boss_ataque_y, boss_ataque_offset_y, atraso_boss_movimento, atraso_boss_ataque, boss_ataque_beam_length, boss_movimento_timer, boss_vida_alpha, cobra_tiro_alpha, boss_x, boss_x2, boss_y, boss_y2, boss_ataque_refletor_ponto, num_obstaculos;
    public short x, y, x2, y2, quadro_x, quadro_y, comp, comp2;
    public byte larg, width, height, biscoito_x, biscoito_y, boss_vida, boss_vida_limite, cobra_tiro_dano, biscoitos_comidos, num_jogadores, num_rivais;  
    public short atraso, num_tiros, timer_perguntas, timer_resposta, timer_barra_efeito, timer_minas, alpha, boss_ataque_count, boss_ataque_timer, boss_beam_timer, boss_ataque_timer_alvo, tempShrt, efeito_timer, efeito_timer_alvo;
    public byte alvo_y, alvo_x, pos_y, pos_x, id, nivel, numero, efeito, resposta, boss_direcao, boss_loop, num_grupos, linha1, linha2, cobra_tiro_y, cobra_tiro_x, num_clicks, minas_y, minas_x, minas_diametro, minas_loop;
    public byte pixel, cobra_pixel, cobra_tiro_pixel, obstaculo_pixel, biscoito_pixel, rival_pixel, rival_biscoitos_comidos; 
    public boolean boss_defesa, rival_comeu, boss_movimento_iniciado, boss_ataque_refletor, gerado_perguntas, feito, noturno, noturnado, boss, boss_derrotado, boss_impacto, biscoito_destruido, lasado, obstaculado, interromper_efeito, interromper_boss_movimento, interromper_boss_ataque, apagar, invalido, reagendar_boss_movimento, reagendar_boss_ataque;
    public boolean cobra_tiro_disparado, cobra_redonda, reiniciar, envenenado, /*cobra_movimento_iniciado,*/ gerado_obstaculos, cobra_empurrada, cobra_presa, minado, baleado;
                       
    public float duplicador, boss_impacto_velocidade;
    public Color rival_cor1 = Color.red;
    public Color rival_cor2 = Color.orange;
    public Color fundo_cor = Color.black;
    public Color cobra_cor = new Color(0,255,160);
    public Color biscoito_cor = Color.yellow;
    public Color minas_cor = Color.red;
    public Color boss_ataque_laser_cor = Color.red;
    public Color boss_ataque_perfurador_cor = new Color(255,255,127);
    //public Color boss_ataque_refletidor_cor = Color.orange;
    public Color boss_ataque_refletor_cor1 = Color.orange;
    public Color boss_ataque_refletor_cor2 = Color.white;
    public Color boss_ataque_beam_cor1 = Color.white;
    public Color boss_ataque_beam_cor2 = new Color(255,255,127);
    public Color cobra_tiro_cor = new Color(185,170,227);
    public Color obstaculo_cor = Color.red;
    public boolean[][] obstaculo;
    public byte[][] obstaculo_grupo;
    
    public byte[][] rival_y,  rival_x;
    public byte[][] alvo_y2 = new byte[28][25];
    public byte[][] alvo_x2 = new byte[28][25];
    public byte[] alvo_c = new byte[28];
    public byte[] rival_direcao;
    public byte[] prioridade = new byte[5];
    public byte[] check_y = new byte[61];
    public byte[] check_x = new byte[61];
    public byte[] check_direcao = new byte[61];
    public boolean[][] check_invalido = new boolean[61][5];
    public boolean[][] checkpoint;
    public byte num_check, anterior, atual, inicio_y, inicio_x, fim_y, rival_anterior;
    public boolean rival_desistiu, permissao, iniciar_jogo, incompleto;
    
    BufferedImage image;
    
    public void telaInicio(Graphics g) {
        quadro_x = 400;
        quadro_y = 130;
        g.setColor(Color.white);
        //vertical
        g.fillRect(quadro_x,quadro_y,larg,500);
        g.fillRect(quadro_x+594,quadro_y,larg,500);
        //horizontal
        g.fillRect(quadro_x,quadro_y,600,larg);
        g.fillRect(quadro_x,quadro_y+500,600,larg);
        rival_pixel = 4;
        c = rival_pixel;
        pixel = 4;
        cobra_redonda = false;
        for (i = 0; i <= height; i++)
            for (j = 0; j <= width; j++)
                livre[i][j] = true;
        letraAlvo();
        iniciarRival(g);
        for (id = 1; id <= 27; id++)
            alvo_c[id] = 1;
        System.out.println("Meta: "+alvo_y2[1][1]+" "+alvo_x2[1][1]);
        do {incompleto = false;
            for (id = 1; id <= 11; id++) {
                System.out.println("Pos: "+rival_y[id][1]+" "+rival_x[id][1]);
                if (rival_y[id][1] == alvo_y2[id][alvo_c[id]] && rival_x[id][1] == alvo_x2[id][alvo_c[id]])
                    alvo_c[id]++;
                if (alvo_x2[id][alvo_c[id]] != 0) {
                    System.out.println("entrou");
                    g.setColor(Color.green);
                    moverRival(g, Color.green);
                    incompleto = true;
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (incompleto == true);
    }
    
    public void letraAlvo() {
        //S
        alvo_y2[1][1] = 20; alvo_y2[1][2] = alvo_y2[1][1]; alvo_y2[1][3] = (byte)(alvo_y2[1][2]-5); alvo_y2[1][4] = alvo_y2[1][3]; alvo_y2[1][5] = (byte) (alvo_y2[1][4]-4); alvo_y2[1][6] = alvo_y2[1][5]; //S
        alvo_x2[1][1] = 45; alvo_x2[1][2] = (byte)(alvo_x2[1][1]+5); alvo_x2[1][3] = alvo_x2[1][2]; alvo_x2[1][4] = alvo_x2[1][1]; alvo_x2[1][5] = alvo_x2[1][4]; alvo_x2[1][6] = alvo_x2[1][2];
        //N
        alvo_y2[2][1] = alvo_y2[1][1]; alvo_y2[2][2] = alvo_y2[1][5]; alvo_y2[2][3] = alvo_y2[2][2]; alvo_y2[2][4] = (byte)(alvo_y2[2][3]+2); alvo_y2[2][5] = alvo_y2[2][4]; alvo_y2[2][6] = (byte)(alvo_y2[2][5]+2); alvo_y2[2][7] = alvo_y2[2][6]; alvo_y2[2][8] = (byte)(alvo_y2[2][7]+2); alvo_y2[2][9] = alvo_y2[2][8]; alvo_y2[2][10] = (byte)(alvo_y2[2][9]+2); alvo_y2[2][11] = alvo_y2[2][10]; alvo_y2[2][12] = (byte)(alvo_y2[2][11]+1); alvo_y2[2][13] = alvo_y2[2][12]; alvo_y2[2][14] = alvo_y2[2][2]; //N
        alvo_x2[2][1] = (byte)(alvo_x2[1][2]+5); alvo_x2[2][2] = alvo_x2[2][1]; alvo_x2[2][3] = (byte)(alvo_x2[2][2]+2); alvo_x2[2][4] = alvo_x2[2][3]; alvo_x2[2][5] = (byte)(alvo_x2[2][4]+1); alvo_x2[2][6] = alvo_x2[2][5]; alvo_x2[2][7] = (byte)(alvo_x2[2][6]+1); alvo_x2[2][8] = alvo_x2[2][7]; alvo_x2[2][9] = (byte)(alvo_x2[2][8]+1); alvo_x2[2][10] = (byte)(alvo_x2[2][9]); alvo_x2[2][11] = (byte)(alvo_x2[2][10]+1); alvo_x2[2][12] = alvo_x2[2][11]; alvo_x2[2][13] = (byte)(alvo_x2[2][12]+1); alvo_x2[2][14] = alvo_x2[2][13];
        //A
        alvo_y2[3][1] = alvo_y2[1][1]; alvo_y2[3][2] = alvo_y2[1][5]; alvo_y2[3][3] = alvo_y2[3][2]; alvo_y2[3][4] = (byte)(alvo_y2[3][3]+3); //A1
        alvo_x2[3][1] = (byte)(alvo_x2[2][12]+5); alvo_x2[3][2] = alvo_x2[3][1]; alvo_x2[3][3] = (byte)(alvo_x2[3][2]+5); alvo_x2[3][4] = alvo_x2[3][3];
        alvo_y2[4][1] = alvo_y2[3][1]; alvo_y2[4][2] = (byte)(alvo_y2[3][4]+1); alvo_y2[4][3] = alvo_y2[4][2]; //A2
        alvo_x2[4][1] = alvo_x2[3][4]; alvo_x2[4][2] = alvo_x2[4][1]; alvo_x2[4][3] = (byte)(alvo_x2[3][1]+1);
        //K
        alvo_y2[5][1] = alvo_y2[1][5]; alvo_y2[5][2] = alvo_y2[1][1]; //K1
        alvo_x2[5][1] = (byte)(alvo_x2[4][1]+5); alvo_x2[5][2] = alvo_x2[5][1];
        alvo_y2[6][1] = alvo_y2[5][1]; alvo_y2[6][2] = (byte)(alvo_y2[6][1]+2); alvo_y2[6][3] = alvo_y2[6][2]; alvo_y2[6][4] = (byte)(alvo_y2[6][3]+1); alvo_y2[6][5] = alvo_y2[6][4]; alvo_y2[6][6] = (byte)(alvo_y2[6][5]+1); alvo_y2[6][7] = alvo_y2[6][6];//K2
        alvo_x2[6][1] = (byte)(alvo_x2[5][1]+5); alvo_x2[6][2] = alvo_x2[6][1]; alvo_x2[6][3] = (byte)(alvo_x2[6][2]-1); alvo_x2[6][4] = alvo_x2[6][3]; alvo_x2[6][5] = (byte)(alvo_x2[6][4]-1); alvo_x2[6][6] = alvo_x2[6][5]; alvo_x2[6][7] = (byte)(alvo_x2[6][6]-2);
        alvo_y2[7][1] = alvo_y2[5][2]; alvo_y2[7][2] = (byte)(alvo_y2[7][1]-2); alvo_y2[7][3] = alvo_y2[7][2]; alvo_y2[7][4] = (byte)(alvo_y2[7][3]-1); alvo_y2[7][5] = alvo_y2[7][4]; alvo_y2[7][6] = (byte)(alvo_y2[7][5]-1); //K3
        alvo_x2[7][1] = alvo_x2[6][1]; alvo_x2[7][2] = alvo_x2[6][2]; alvo_x2[7][3] = alvo_x2[6][3]; alvo_x2[7][4] = alvo_x2[6][4]; alvo_x2[7][5] = alvo_x2[6][5]; alvo_x2[7][6] = alvo_x2[6][6];
        //E
        alvo_y2[8][1] = alvo_y2[1][5]; alvo_y2[8][2] = alvo_y2[8][1]; alvo_y2[8][3] = (byte)(alvo_y2[1][3]-1);//E1
        alvo_x2[8][1] = (byte)(alvo_x2[6][1]+10); alvo_x2[8][2] = (byte)(alvo_x2[6][1]+5); alvo_x2[8][3] = alvo_x2[8][2];
        alvo_y2[9][1] = alvo_y2[1][1]; alvo_y2[9][2] = alvo_y2[9][1]; alvo_y2[9][3] = (byte)(alvo_y2[8][3]+1); alvo_y2[9][4] = alvo_y2[9][3]; //E2
        alvo_x2[9][1] = alvo_x2[8][1]; alvo_x2[9][2] = alvo_x2[8][2]; alvo_x2[9][3] = alvo_x2[8][3]; alvo_x2[9][4] = (byte)(alvo_x2[9][1]-2);
        //:
        alvo_y2[10][1] = (byte)(alvo_y2[1][1]-2); alvo_y2[10][2] = alvo_y2[10][1]; alvo_y2[10][3] = (byte)(alvo_y2[10][2]+2); alvo_y2[10][4] = alvo_y2[10][3]; alvo_y2[10][5] = (byte)(alvo_y2[10][1]+1); //:1
        alvo_x2[10][1] = (byte)(alvo_x2[9][1]+6); alvo_x2[10][2] = (byte)(alvo_x2[10][1]-2); alvo_x2[10][3] = alvo_x2[10][2]; alvo_x2[10][4] = alvo_x2[10][1]; alvo_x2[10][5] = alvo_x2[10][4];
        alvo_y2[11][1] = (byte)(alvo_y2[10][1]-3); alvo_y2[11][2] = alvo_y2[11][1]; alvo_y2[11][3] = (byte)(alvo_y2[11][2]-2); alvo_y2[11][4] = alvo_y2[11][3]; alvo_y2[11][5] = (byte)(alvo_y2[11][1]-1); //:1
        alvo_x2[11][1] = (byte)(alvo_x2[9][1]+6); alvo_x2[11][2] = (byte)(alvo_x2[11][1]-2); alvo_x2[11][3] = alvo_x2[11][2]; alvo_x2[11][4] = alvo_x2[11][1]; alvo_x2[11][5] = alvo_x2[11][4];
        
        /*
        alvo_y2[4][1] = alvo_y2[1][1]; alvo_y2[4][2] = (by2te)(alvo_y2[3][4]+1); alvo_y2[4][3] = alvo_y2[4][2]; //A2
        alvo_x2[4][1] = 10; alvo_x2[4][2] = 15; alvo_x2[4][3] = alvo_x2[3][1];
        
        alvo_y2[13][1] = alvo_y2[1][1]; alvo_y2[13][2] = (by2te)(alvo_y2[3][4]+1); alvo_y2[13][3] = alvo_y2[13][2]; 
        alvo_x2[13][1] = 10; alvo_x2[13][2] = 15; alvo_x2[13][3] = alvo_x2[3][1];
        
        alvo_y2[20][1] = alvo_y2[13][1]; alvo_y2[20][2] = alvo_y2[13][2]; alvo_y2[20][3] = alvo_y2[13][2]; 
        alvo_x2[20][1] = 10; alvo_x2[20][2] = 15; alvo_x2[20][3] = alvo_x2[3][1];
        
        alvo_y2[5][1] = alvo_y2[1][5]; alvo_y2[5][2] = alvo_y2[1][1]; //K1
        alvo_x2[5][1] = 10; alvo_x2[5][2] = 15; alvo_x2[5][3] = alvo_x2[3][1];
        
        //K2
        alvo_y2[6][1] = alvo_y2[1][5]; alvo_y2[6][2] = (by2te)(alvo_y2[6][1]+1);
        alvo_y2[6][3] = alvo_y2[6][2]; alvo_y2[6][4] = (by2te)(alvo_y2[6][3]+1);
        alvo_y2[6][5] = alvo_y2[6][4]; alvo_y2[6][6] = (by2te)(alvo_y2[6][5]+1);
        alvo_y2[6][7] = alvo_y2[6][6]; alvo_y2[6][8] = (by2te)(alvo_y2[6][7]+1);
        alvo_y2[6][9] = alvo_y2[6][8]; alvo_y2[6][10] = (by2te)(alvo_y2[6][9]+1);
        alvo_y2[6][11] = alvo_y2[6][10]; alvo_y2[6][12] = (by2te)(alvo_y2[6][11]+1);
        alvo_x2[6][1] = 10; alvo_x2[6][2] = (by2te)(alvo_x2[6][1]-1);
        alvo_x2[6][3] = alvo_x2[6][2]; alvo_x2[6][4] = (by2te)(alvo_x2[6][3]-1);
        alvo_x2[6][5] = alvo_x2[6][4]; alvo_x2[6][6] = (by2te)(alvo_x2[6][5]-1);
        alvo_x2[6][7] = alvo_x2[6][6]; alvo_x2[6][8] = (by2te)(alvo_x2[6][7]-1);
        alvo_x2[6][9] = alvo_x2[6][8]; alvo_x2[6][10] = (by2te)(alvo_x2[6][9]-1);
        alvo_x2[6][11] = alvo_x2[6][10]; alvo_x2[6][12] = (by2te)(alvo_x2[6][11]-1);
        
        
        alvo_y2[7][1] = alvo_y2[1][5]; alvo_y2[7][2] = alvo_y2[1][1]; //K3
        alvo_x2[7][1] = 10; alvo_x2[7][2] = 17; alvo_x2[7][3] = alvo_x2[3][1];*/
        
    }
    
    public void desenharQuadro(Graphics g) {
        c = 30;
        g.setColor(Color.white);
        try {
            for (i = 1; i <= height+1; i++) {
                g.fillRect(quadro_x,quadro_y+((i-1)*11),larg,11);
                g.fillRect(quadro_x+comp2,quadro_y+comp+(-i*11)+6,larg,11);
                Thread.sleep(c);
            }
            for (j = 1; j <= width+1; j++) {
                g.fillRect(quadro_x+((j-1)*11)+1,quadro_y+comp,11,larg);
                g.fillRect(quadro_x+comp2+(-j*11)+6,quadro_y,11,larg);
                Thread.sleep(c);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nivel == 0) {
            //vertical
            g.fillRect(quadro_x,quadro_y,larg,comp+100);
            g.fillRect(quadro_x+comp2,quadro_y,larg,comp+100);
            //horizontal
            g.fillRect(quadro_x,quadro_y+comp+100,comp2+larg,larg);
            //espaco da barra
            g.fillRect(quadro_x+larg+20,quadro_y+comp+23,comp2-130,59);
            g.fillRect(quadro_x+comp2-80,quadro_y+comp+23,59,59);
            g.setColor(Color.black);
            g.fillRect(quadro_x+larg+26,quadro_y+comp+29,comp2-142,47);
            g.fillRect(quadro_x+comp2-75,quadro_y+comp+29,48,47);
        }
    }
    
    public void salvarProgresso(String score) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./Jogador/progresso.txt"));
            writer.write(score);
            writer.newLine();
            writer.write(Short.toString(cobra_tamanho[0]));
            writer.newLine();
            writer.write(Short.toString(biscoito_y)+Short.toString(biscoito_x));
            writer.newLine();
            writer.write(Short.toString(timer_resposta));
            writer.newLine();
            for (i = 0; i < 2; i++) {
                writer.write(zero(escolha_y[i])+Short.toString(escolha_y[i]));
                writer.write(zero(escolha_x[i])+Short.toString(escolha_x[i]));
            }
            writer.newLine();
            writer.write(Short.toString(timer_barra_efeito));
            writer.newLine();
            writer.write(Short.toString(efeito));
            writer.newLine();
            for (i = 1; i <= height; i++)
                for (j = 1; j <= width; j++)
                    if (cobra[i][j] == true) {
                        writer.write(zero(i)+Integer.toString(i));
                        writer.write(zero(j)+Integer.toString(j));
                    }
            writer.newLine();
            for (id = 0; id < num_jogadores; i++) {
                for (i = 1; i <= cobra_tamanho[id]; i++) {
                    writer.write(zero(cobra_y[id][i])+Short.toString(cobra_y[id][i]));
                    writer.write(zero(cobra_x[id][i])+Short.toString(cobra_x[id][i]));
                }
                if (num_jogadores-id > 1)
                    writer.write(".");//continuacao
                writer.newLine();
            }
            for (id = 0; id < num_rivais; i++) {
                for (i = 1; i <= rival_tamanho[id]; i++) {
                    writer.write(zero(rival_y[id][i])+Short.toString(rival_y[id][i]));
                    writer.write(zero(rival_x[id][i])+Short.toString(rival_x[id][i]));
                }
                if (num_rivais-id > 1)
                    writer.write(",");//continuacao
                writer.newLine();
            }
            for (i = 1; i <= height; i++)
                for (j = 1; j <= width; j++)
                    if (obstaculo[i][j] == true) {
                        writer.write(zero(i)+Integer.toString(i));
                        writer.write(zero(j)+Integer.toString(j));
                    }
            writer.newLine();
            writer.write(Integer.toString(boss_y+25)+Short.toString(boss_x));
            writer.newLine();
            writer.write(boss_ataque_count);
            writer.newLine();
            writer.write(zero(boss_ataque_y)+Short.toString(boss_ataque_y));
            writer.write(zero(boss_ataque_x)+Short.toString(boss_ataque_x));
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carregarConfiguracoes() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./Jogador/configuracoes.txt"));
            dificuldade = reader.readLine();
            cobra_pixel = Byte.parseByte(reader.readLine());
            cobra_tiro_pixel = Byte.parseByte(reader.readLine());
            cobra_redonda = Boolean.parseBoolean(reader.readLine());
            obstaculo_pixel = Byte.parseByte(reader.readLine());
            biscoito_pixel = Byte.parseByte(reader.readLine());
            rival_pixel = Byte.parseByte(reader.readLine());
            /*Color rival_cor = new Color(reader.readLine());
            Color cobra_bomba_cor = new Color(reader.readLine());
            Color minas_cor = new Color(reader.readLine());
            Color boss_turbina_cor = new Color(reader.readLine());
            Color boss_corpo_cor = new Color(reader.readLine());*/
            reader.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public void replay(Graphics g) {
        int ciclos = 0, biscoito_c = 0, obs_num_c = 0, tamanho = 7, obs_add_c = 0;
        byte pos = 0;
        String highscore = "", tempStr = "", direcoes = "", biscoitos = "", obs_add = "", obs_num = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./Jogador/jogador.txt"));
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
            g.fillRect(quadro_x+((x-1)*11),quadro_y+(4*11),11,11);
            System.out.println("cobra_y: "+cobra_y[0][i]+", cobra_x: "+cobra_x[0][i]);
        }//jump
        System.out.println("highscore: "+highscore);
        System.out.println("direcoes: "+direcoes);
        System.out.println("biscoitos: "+biscoitos);
        System.out.println("obs_add: "+obs_add);
        System.out.println("obs_num: "+obs_num);
        biscoito_y = Byte.parseByte(biscoitos.substring(biscoito_c,biscoito_c+2));
        biscoito_x = Byte.parseByte(biscoitos.substring(biscoito_c+2,biscoito_c+4));
        
        //biscoito_c += 4;
        y = biscoito_y; x = biscoito_x;
        g.setColor(biscoito_cor);
        g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
        
        for (i = 0; i < ciclos; i++) {
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
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            for (j = (byte) (tamanho-1); j >= 0; j--) {
                cobra_x[0][j+1] = cobra_x[0][j];
                cobra_y[0][j+1] = cobra_y[0][j];
            }
            System.out.println("cobra_y[0]: "+cobra_y[0][1]+", cobra_x[0]: "+cobra_x[0][1]);
            //System.out.println("biscoito_y: "+biscoito_y+", biscoito_x: "+biscoito_x);
            g.setColor(cobra_cor);
            x = cobra_x[0][1]; y = cobra_y[0][1];
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            if (y == biscoito_y && x == biscoito_x) {
                //System.out.println("biscoito_y: "+biscoito_y+", biscoito_x: "+biscoito_x);
                //System.out.println(biscoito_y+""+biscoito_x);
                biscoito_y = Byte.parseByte(biscoitos.substring(biscoito_c,biscoito_c+2));
                biscoito_x = Byte.parseByte(biscoitos.substring(biscoito_c+2,biscoito_c+4));
                tamanho++;
                biscoito_c += 1/*4*/;
                y = biscoito_y; x = biscoito_x;
                g.setColor(biscoito_cor);
                g.fillOval(quadro_x+(x-1)*11,quadro_y+(y-1)*11,10,10);
                g.setColor(obstaculo_cor);
                tempInt = Integer.parseInt(obs_num.substring(obs_num_c,obs_num_c+1));
                for (j = 0; j <= tempInt; j++) {
                    y = Byte.parseByte(obs_add.substring(obs_add_c,obs_add_c+2));
                    x = Byte.parseByte(obs_add.substring(obs_add_c+2,obs_add_c+4));
                    g.fillRect(quadro_x+(x-1)*11,quadro_y+(y-1)*11,11,11);
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
            try {
                Thread.sleep(atraso);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void receberInformacoes(String dificuldade, byte nivel) {
        this.dificuldade = dificuldade;
        this.nivel = nivel;
    }
    
    public void receberInformacoes(Graphics g, byte nivel, short quadro_y, short quadro_x) {
        this.nivel = nivel;
        this.quadro_y = quadro_y;
        this.quadro_x = quadro_x;
        g.setColor(Color.green);
        g.fillRect(quadro_x,quadro_y,200,200);
    }
    
    public void pausar(Graphics g) {
        interromper_efeito = true;
        interromper_boss_movimento = true;
        interromper_boss_ataque = true;
        y = (short)(height/2); x = (short)(width/2);
        try {
            c = 15;
            for (i = 0; i >= -20; i--) {
                g.setColor(Color.white);
                g.fillOval(quadro_x+((x-1)*11),quadro_y+(29*11)+((i-(c/2))*4),c,c);
                Thread.sleep(12);//ease out
                g.setColor(fundo_cor);
                g.fillOval(quadro_x+((x-1)*11),quadro_y+(29*11)+((i-(c/2))*4),c,c);
            }
            c = (29*11)+((i-(c/2))*4);
            g.setColor(Color.white);
            for (i = 0; i <= 130; i++) {
                 g.fillRect(quadro_x+(x*11)+(i*-1),quadro_y+c,1,5);
                 g.fillRect(quadro_x+(x*11)+(i*1),quadro_y+c,1,5);
                 Thread.sleep(2);
            }
            g.setColor(Color.orange);
            for (i = 0; i <= 90; i++) {
                 g.fillRect(quadro_x+(x*11)-130,quadro_y+c+(i*-1),261,1);
                 g.fillRect(quadro_x+(x*11)-130,quadro_y+c+(i-1),261,1);
                 Thread.sleep(5);
            }
            //g.setColor(new Color(50,50,50));
            g.setColor(Color.red);//Horizontal
            g.fillRect(quadro_x+(x*11)-130,quadro_y+c+((i-1)*-1),261,8);
            g.fillRect(quadro_x+(x*11)-130,quadro_y+c+((i-1)*-1)+174,261,8);
            //Vertical
            g.fillRect(quadro_x+(x*11)-130,quadro_y+c+((i-1)*-1),8,180);
            g.fillRect(quadro_x+(x*11)+130,quadro_y+c+((i-1)*-1),8,180);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.setColor(Color.white);        
        g.drawString("Pausado",quadro_x+(x*11),quadro_y+(y*11));
        
        g.drawString("Q - Sair          P - Resumir",quadro_x+(x*11),quadro_y+((y+5)*11));
            
    }
    
    public void resumir(Graphics g) {
        g.setColor(Color.white);
        //g.setColor(fundo_cor);
        y = 12; x = 12;
        g.fillRect(quadro_x+(x*11),quadro_y+((y-1)*11),261,180);
    }
    
    //refactor com cobra retorna valor
    public byte analisarVencedor(short y, short x) {
        tempShrt = 0;
        cobra_derrotada[id] = true;
        for (id = 0; id < num_jogadores; id++)
            if (cobra_derrotada[id] == false) {
                tempShrt++;
                tempInt = id;
            }
        return tempShrt == 1? (byte) tempInt : 0;
    }
    
    public boolean removerElemento(Graphics g, int i, int j) {
        id = 0;
        if (biscoito_y == i && biscoito_x == j)
            biscoito_destruido = true;
         else
           if (escolha[i][j] == true)
               escolha[i][j] = false;
            else
              if (obstaculo[i][j] == true && obstaculo_movido[i][j] == false && minas[i][j] == false) {
                  obstaculo[i][j] = false;
                  if (cobra_tiro_y == i && cobra_tiro_x == j) {
                      cobra_tiro_disparado = false;
                      g.setColor(fundo_cor);
                      g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                  }
               }
               else
                 if (cobra_caida[i][j] == true)
                     cobra_caida[i][j] = false;
                  else
                    if (cobra[i][j] == true && efeito != 6) {
                        tempShrt = cobra_tamanho[id];
                        for (i2 = 1; i2 <= tempShrt; i2++) {
                             y = cobra_y[id][i2]; x = cobra_x[id][i2];
                             if (i == y && j == x) {
                                 cobra_tamanho[id] = (short)(i2-1);
                                 for (i2 = (short)(cobra_tamanho[id]+1); i2 <= tempShrt; i2++) {
                                      cobra[y][x] = false;
                                      cobra_caida[y][x] = true;
                                 }
                                 if (num_tiros > 0) {
                                     num_tiros -= tempShrt-cobra_tamanho[id];
                                     if (num_tiros < 0)
                                         num_tiros = 0;
                                     biscoitos_comidos = 0;
                                 }
                                 break; 
                             }
                        }
                    }
         return cobra_tamanho[id] <= 4;
    }    
    
    public void redesenharBloco(Graphics g, int i, int j) {
        if (livre[i][j] == false) {
            if (obstaculo[i][j] == true) {
                g.setColor(obstaculo_cor);
                c = obstaculo_pixel;
            }
            else
              if (cobra[i][j] == true || cobra_caida[i][j] == true) {   
                  g.setColor(cobra_cor);
                  c = cobra_pixel;
              }
              else
                if (biscoito_y == i && biscoito_x == j) {   
                    g.setColor(biscoito_cor);
                    c = biscoito_pixel;
                }
                else
                  if (rival[i][j] == true) {
                      boolean Break = false;
                      for (id = 0; id <= 1; id++) {
                        for (j2 = 0; j2 <= rival_tamanho[id]; j2++) {
                             if (rival_y[id][j2] == i && rival_x[id][j2] == j) {
                                 if (id == 0)
                                     g.setColor(rival_cor1);
                                  else
                                     g.setColor(rival_cor2);
                                 Break = true;
                                 break;
                             }
                             if (Break == true)
                                 break;
                        }  
                      }
                      c = rival_pixel;
                  }
            if (obstaculo[i][j] == true || cobra_redonda == false)
                g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),c,c);
             else
               g.fillOval(quadro_x+((j-1)*11),quadro_y+((i-1)*11),c,c);
        }
    }
    
    public void timerEfeito(Graphics g) {
        if (nivel == 0) {                
            if (gerado_perguntas == true && efeito == 0) {
                g.setColor(fundo_cor);
                g.fillRect(830-(2*timer_resposta),quadro_y+comp+23,2,48);
                timer_resposta++;
                if (timer_resposta == 180) { //os 15 segundos pra responder acabaram
                    timer_resposta = 0;
                    //gerado_perguntas = false;
                    g.fillRect(quadro_x-320,quadro_y-11,300,95);
                    for (i = 0; i <= 2; i++) {
                         y = escolha_y[i]; x = escolha_x[i];
                         g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                         livre[y][x] = true;
                         escolha[y][x] = false;
                    }
                    efeito = (byte)((Math.random()*6)+7);
                    if (efeito == 9)
                        Efeitos(g);
                    g.setColor(Color.red);
                    desenharEfeito(g);
                    desenharBarra(g);
                }    
            }
            else
              if (efeito != 0) { //so ativa se de fato tiver um efeito ativo
                  efeito_timer += atraso;
                  g.setColor(fundo_cor);
                  //g.fillRect(831-timer_barra_efeito,quadro_y+comp+23,timer_barra_efeito,48);
                  g.fillRect(830-timer_barra_efeito,quadro_y+comp+23,1,48);
                  timer_barra_efeito++;
                  if (timer_barra_efeito == 361) { //30 segundos de duracao
                      g.setColor(fundo_cor);
                      g.fillRect(quadro_x+26,quadro_y+comp+23,comp2-142,48);
                      removerEfeito(g);
                      interromper_efeito = true;
                      timer_barra_efeito = 0;
                      efeito = 0;
                      switch (dificuldade) {
                          case "easy": atraso = 80; break;
                          case "normal": atraso = 68; break;
                          case "hard": atraso = 45; break;
                      }
                      duplicador = 1;
                      gerado_perguntas = false;
                  }
              }
        }
        /*if (nivel == 0 || nivel == 8) {
            if (alpha >= 250) {
                noturnado = noturnado == false;
                alpha = 5;
            }
        } */              
    }
    
    public boolean direcaoValida(char[] cobra_direcao) {
        switch (tempChr) {
            case 97,100,115,119 : return id == 0 && abs(cobra_direcao[id]-tempChr) > 4;
            case 105,106,107,108 : return id == 1 && abs(cobra_direcao[id]-tempChr) != 2;
        }
        return false;
    }
    
    public void Perguntas(byte numero) {
        switch (numero) {
            case 1 : { 
                pergunta = "Um byte e composto por quantos bits?";
                alternativa[0] = "4 bits";
                alternativa[1] = "8 bits";
                alternativa[2] = "16 bits";
                resposta = 1;
                break;
            }
            case 2: {
                pergunta = "Quando e que o primeiro computador foi inventado?";
                alternativa[0] = "1943";
                alternativa[1] = "1971";
                alternativa[2] = "1973";
                resposta = 0;
                break;
            }
            case 3: {
                pergunta = "Qual foi o primeiro computadores com cores no ecra?";
                alternativa[0] = "IBM 650";
                alternativa[1] = "HP 4500";
                alternativa[2] = "Apple 1";
                resposta = 2;
                break;
            }
            case 4: {
                pergunta = "Quando e que o primeiro disco de 1GB foi lançado?";
                alternativa[0] = "1980";
                alternativa[1] = "1990";
                alternativa[2] = "2002";
                resposta = 0;
                break;
            }
            case 5: {
                pergunta = "Qual foi a primeira companhia a inventar o CPU?";
                alternativa[0] = "AMD Corporations";
                alternativa[1] = "Intel Corporations";
                alternativa[2] = "Nvidia Corporations";
                resposta = 1;
                break;
            }
            case 6: {
                pergunta = "Qual e o jogo mais vendido de todos os tempos?";
                alternativa[0] = "Tetris";
                alternativa[1] = "Bounce";
                alternativa[2] = "Minecraft";
                resposta = 2;
                break;
            }
        }

    }
    
    public void desenharBarra(Graphics g) {
        g.fillRect(quadro_x+26,quadro_y+comp+23,comp2-142,48);
        g.setColor(Color.white);
        for (i = 0; i <= 11; i++) {
             int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
             int py[] = {641,592,592,641};
             g.fillPolygon(px,py,px.length);
        }
    }
    
    public void Efeitos(Graphics g) {
        if (num_jogadores == 1) {
            switch (efeito) {
                case 1: sco += 200;
                        tempStr = Integer.toString(sco);
                        g.setColor(fundo_cor);
                        g.fillRect(quadro_x+comp2+85,quadro_y+5,55,11);
                        g.setColor(Color.white);
                        g.drawString(tempStr,quadro_x+comp2+90,quadro_y+16);
                        efeito_timer_alvo = 3000;
                        break;
                case 2: duplicador = 2; break;
                case 3: atraso *= 2; break; //lentidao
                case 4: y = cobra_y[id][0]; x = cobra_x[id][0];
                        if (y == 0)
                            cobra_y[id][0] = height;
                         else
                           if (y == height+1)
                               cobra_y[id][0] = 1;
                            else
                              if (x == 0)
                                  cobra_x[id][0] = width;
                               else
                                 if (x == width+1)
                                     cobra_x[id][0] = 1;
                        break;
                case 5: boolean bomba = true;
                        tempInt = obstaculo_grupo[y][x];
                        System.out.println("Aqui: "+tempInt);
                        g.setColor(fundo_cor);
                        for (i = 1; i <= height; i++) {
                           for (j = 1; j <= width; j++) {
                               System.out.println("Grupo: "+obstaculo_grupo[i][j]);
                               if (obstaculo_grupo[i][j] == tempInt) {
                                   System.out.println("Aqui2");
                                   obstaculo_grupo[i][j] = 0;
                                   livre[i][j] = true;
                                   obstaculo[i][j] = false;
                                   num_obstaculos--;
                                   g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                               }
                               else
                                 if (obstaculo_grupo[i][j] > tempInt)
                                     obstaculo_grupo[i][j]--;
                          }
                       }
                       num_grupos--;
                       timer_barra_efeito = 360;
                       System.out.println("timer_barra_efeitosssssssssssssssssssssssssss: "+timer_barra_efeito);
                       c = cobra_pixel;
                       g.setColor(cobra_cor);
                       for (i = 0; i <= cobra_tamanho[id]; i++) {
                           y = cobra_y[id][i]; x = cobra_x[id][i];
                           if (cobra_redonda == false)
                               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                            else
                              g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);    
                       }
                       break;
                case 6: boolean fantasma = true;
                        redesenharBloco(g,y,x);
                        break;
                case 7: sco -= 200;
                        tempStr = Integer.toString(sco);
                        g.setColor(fundo_cor);
                        g.fillRect(quadro_x+comp2+85,quadro_y+5,55,11);
                        g.setColor(Color.white);
                        g.drawString(tempStr,quadro_x+comp2+90,quadro_y+16);
                        efeito_timer_alvo = 3000;
                        break;
                case 8: duplicador = (float)(0.5); break;
                case 9: atraso /= 2; break; //rapidez
                case 10: if (minas_y == 0) {
                             efeito_timer_alvo = 800;
                             do {invalido = false;
                                 minas_diametro = (byte) ((Math.random()*10) + 6);
                                 minas_y = (byte) ((Math.random()*height) + 1);
                                 minas_x = (byte) ((Math.random()*width) + 1);
                                 if ((minas_x+minas_diametro) > width || (minas_y+minas_diametro) > height) 
                                     invalido = true;
                             } while (invalido == true);
                         }
                         else {
                           efeito_timer = 0;
                           efeito_timer_alvo *= 0.4; //velocidade aumenta
                           y = minas_y; x = minas_x;
                           c = minas_diametro*11;
                           g.setColor(fundo_cor);
                           if (minas_loop % 2 == 1 || (minas_loop == 20 && minas[y][x+1] == true))
                               g.setColor(minas_cor);
                           g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                           c = minas_diametro;
                           if (minas_loop % 2 == 0)
                               for (i = minas_y; i <= minas_y+c; i++)
                                    for (j = minas_x; j <= minas_x+c; j++) {
                                        if (!((i == minas_y || i == minas_y+c) && (j == minas_x || j == minas_x+c))) {
                                            if (minas_loop == 20) {
                                                minas[i][j] = !minas[i][j];
                                                if (minas[i][j] == true && removerElemento(g,i,j) == true)
                                                    minado = true;
                                             }    
                                        }
                                        if (minas_loop != 20 || minas[i][j] == false)
                                            redesenharBloco(g,i,j);
                                    } 
                               }
                           if (minas_loop == 20 && minas[i][j] == false) {
                               minas_y = 0;
                               minas_loop = 1;
                           }
                           if (minas_loop != 20)
                               minas_loop++;
                          boolean minas = true; 
                          break;

                case 11: noturno = true; 
                         alpha += 4;
                         if (noturnado == false) {
                              g.setColor(new Color(0,0,0,alpha));
                              g.fillRect(quadro_x,quadro_y,width*11,height*11);
                         }
                         else
                           for (i = 1; i <= height; i++) {
                               for (j = 1; j <= width; j++) {
                                   if (livre[i][j] == false) { 
                                        if (obstaculo[i][j] == true) {
                                            c = obstaculo_pixel;
                                            g.setColor(new Color(255,0,0,alpha));
                                        }
                                        else
                                          if (cobra[i][j] == true || cobra_caida[i][j] == true) {
                                              c = cobra_pixel;
                                              g.setColor(new Color(0,255,160,alpha));
                                          }
                                          else
                                            if (biscoito_x == j && biscoito_y == i) {
                                                c = biscoito_pixel;
                                                g.setColor(new Color(255,255,0,alpha));
                                            }
                                        if (obstaculo[i][j] == true || cobra_redonda == false)
                                            g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),c,c);
                                         else
                                           g.fillOval(quadro_x+((j-1)*11),quadro_y+((i-1)*11),c,c);

                                    }
                               }
                           }
                        if (alpha >= 250) {
                            noturnado = noturnado == false;
                            alpha = 5;
                        }
                        break;//fix snake head still appearing. Hint: move if efeito 10 to under snake movement
                case 12: cobra_tamanho[0]--;
                         num_tiros--;
                         if (num_tiros < 0) 
                             num_tiros = 0;
                         if (cobra_tamanho[0] == 4) 
                             envenenado = true;
                         efeito_timer_alvo = 3000;
                         break;
            }
        }
    }
    
    public void desenharEfeito(Graphics g) {
        /*try {
            switch (efeito) {
                case 1: image = ImageIO.read(new File("./Arte/Efeitos/+200.png")); break;
                case 2: image = ImageIO.read(new File("./Arte/Efeitos/Score x2.png")); break;
                case 3: image = ImageIO.read(new File("./Arte/Efeitos/Velocidade--.png")); break;
                case 4: image = ImageIO.read(new File("./Arte/Efeitos/Portal.png")); break;
                case 5: image = ImageIO.read(new File("./Arte/Efeitos/Cobra_bomba.png")); break;
                case 6: image = ImageIO.read(new File("./Arte/Efeitos/Fantasma.png")); break;
                case 7: image = ImageIO.read(new File("./Arte/Efeitos/-200.png")); break;
                case 8: image = ImageIO.read(new File("./Arte/Efeitos/Score -2.png")); break;
                case 9: image = ImageIO.read(new File("./Arte/Efeitos/Velocidade++.png")); break;
                case 10: image = ImageIO.read(new File("./Arte/Efeitos/Minas.png")); break;
                case 11: image = ImageIO.read(new File("./Arte/Efeitos/Noturno.png")); break; 
                case 12: image = ImageIO.read(new File("./Arte/Efeitos/Veneno.png")); break;
            }
            g.drawImage(image,866,592,48,48,b);
        } catch (IOException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    
    public void removerEfeito(Graphics g) {
        g.setColor(fundo_cor);
        g.fillRect(866,592,48,48);
        /*try {
        image = ImageIO.read(new File("./Arte/Efeitos/Blank.png"));
        g.drawImage(image,866,592,48,48, null);
        } catch (IOException ex) {
        Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    public void iniciarCobra(Graphics g) {
        g.setColor(cobra_cor);
        byte t = 5;
        c = cobra_pixel;
        for (id = 0; id < num_jogadores; id++) {
            tempShrt = 4;
            for (j = 7; j >= 1; j--) {
                cobra_x[id][j] = (byte)(tempShrt++);
                x = cobra_x[id][j];
                if (nivel != 5 && nivel != 6) {
                    cobra_y[id][j] = t;
                    livre[t][x] = false;
                    cobra[t][x] = true;
                    if (cobra_redonda == false)
                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((t-1)*11),c,c);
                     else
                       g.fillOval(quadro_x+((x-1)*11),quadro_y+((t-1)*11),c,c);
                }
                else {
                    cobra_y[0][j] = 20;
                    livre[20][x] = false;
                    cobra[20][x] = true;
                    if (cobra_redonda == false)
                        g.fillRect(quadro_x+((x-1)*11),quadro_y+(19*11),c,c);
                     else
                       g.fillOval(quadro_x+((x-1)*11),quadro_y+(19*11),c,c); 
                }
            }
            t = 32;
        }
    }
    
    public void iniciarRival(Graphics g) {
        if (iniciar_jogo == false) {
            c = cobra_pixel;
            do {
                x = (byte) ((Math.random() * width) + 1);
                y = (byte) ((Math.random() * height) + 1);
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
          for (id = 1; id <= 11; id++) {
              switch (id) {
                  case 1 -> rival_tamanho[id] = 25; //S
                  case 2,21 -> rival_tamanho[id] = 35; //N
                  case 3,12,19 -> rival_tamanho[id] = 18; //A1
                  case 4,13,20 -> rival_tamanho[id] = 10; //A2
                  case 5 -> rival_tamanho[id] = 12; //K1
                  case 6 -> rival_tamanho[id] = 9; //K2
                  case 7 -> rival_tamanho[id] = 7; //K3
                  case 8,16,26 -> rival_tamanho[id] = 9; //E1
                  case 9,17,27 -> rival_tamanho[id] = 14; //E2
                  case 10,11 -> rival_tamanho[id] = 8; //:
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
              g.setColor(Color.green);
              g.fillRect(quadro_x+(1*11),quadro_y+(1*11),11,11);
              g.fillRect(quadro_x+(52*11),quadro_y+(1*11),11,11);
              g.fillRect(quadro_x+(1*11),quadro_y+(44*11),11,11);
              g.fillRect(quadro_x+(52*11),quadro_y+(44*11),11,11);
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
        pos_x = (byte) rival_x[id][1];
        pos_y = (byte) rival_y[id][1];
        if (iniciar_jogo == true) {
            alvo_y = alvo_y2[id][alvo_c[id]];
            alvo_x = alvo_x2[id][alvo_c[id]];
        }
        else {
            if (id == 0) {
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
            else {
              alvo_y = biscoito_y;
              alvo_x = biscoito_x;  
              g.setColor(Color.orange);
            }
        }
        System.out.println("percebido: y: "+alvo_y+", x: "+alvo_x);
        /*System.out.println(cobra_x[0][0]);
        System.out.println(cobra_x[0][1]);
        if (livre[alvo_y][alvo_x] == true && checkpoint[alvo_y][alvo_x] == false)
          g.drawString("T",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));
       else
         g.drawString("F",quadro_x+((alvo_x-1)*11),quadro_y+(alvo_y*11));*/
    }
          
  public void moverRival(Graphics g, Color rival_cor) {
        System.out.println("called");
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
                  if (x+1 <= width+1 && (prioridade[j] == 1 && checkpoint[y][x+1] == false&& (livre[y][x+1] == true || (y == biscoito_y && x+1 == biscoito_x)) && anterior != 3 && (check_invalido[num_check][1] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check]))) {
                      pos_x++; atual = 1;
                      break;}
                  if (y-1 >= 0 && (prioridade[j] == 2 && checkpoint[y-1][x] == false && (livre[y-1][x] == true || (y-1 == biscoito_y && x == biscoito_x)) && anterior != 4 && (check_invalido[num_check][2] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check]))) {
                      pos_y--; atual = 2;
                      break;}
                  if (x-1 >= 0 && (prioridade[j] == 3 && checkpoint[y][x-1] == false  && (livre[y][x-1] == true || (y == biscoito_y && x-1 == biscoito_x)) && anterior != 1 && (check_invalido[num_check][3] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check]))) {
                      pos_x--; atual = 3;
                      break;}
                  if (y+1 <= height+1 && (prioridade[j] == 4 && checkpoint[y+1][x] == false && (livre[y+1][x] == true || (y+1 == biscoito_y && x == biscoito_x)) && anterior != 2 && (check_invalido[num_check][4] == false || pos_x != check_x[num_check] || pos_y != check_y[num_check]))) {
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
                if (atual != 0 && (y != rival_y[id][1] || x != rival_x[id][1]) && ((obstaculo[y-1][x-1] == true && obstaculo[y-1][x] == false && obstaculo[y-1][x+1] == true) || (obstaculo[y-1][x+1] == true && obstaculo[y][x+1] == false && obstaculo[y+1][x+1] == true) || (obstaculo[y+1][x+1] == true && obstaculo[y+1][x] == false && obstaculo[y+1][x-1] == true) || (obstaculo[y+1][x-1] == true && obstaculo[y][x-1] == false && obstaculo[y-1][x-1] == true))) {
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
        if (livre[y][x] == false && (y != biscoito_y || x != biscoito_x)) {   
            rival_derrotado[id] = true;
            if (rival_derrotado[0] == true && rival_derrotado[1] == true) {
                interromper_boss_ataque = false;
                interromper_boss_movimento = false;
            }
                
        }
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
                rival_biscoitos_comidos++;
                rival_comeu = true;
            }
        }    
            
    }
    
    public int abs(int diferenca) {
        if (diferenca < 0)
            diferenca = (byte)(-1*diferenca);
        return diferenca;
    }
    
    public String zero(int num) {
        return num <= 9? "0" : "";
    }
    
    public void agruparObstaculos() {
        for (i = 1; i <= height; i++) {
           for (j = 1; j <= width; j++) {
               tempInt = 0;
               if (obstaculo_grupo[i-1][j] < obstaculo_grupo[i][j] && obstaculo_grupo[i-1][j] != 0) {
                   tempInt = obstaculo_grupo[i][j];
                   for (i2 = i; i2 <= height; i2++) {
                      for (j2 = (byte)(j); j2 <= width; j2++) {
                          if (obstaculo_grupo[i2][j2] == tempInt)
                              obstaculo_grupo[i2][j2] = obstaculo_grupo[i-1][j];
                           else
                             if (obstaculo_grupo[i2][j2] > tempInt)
                                 obstaculo_grupo[i2][j2]--;
                      }
                   }
                   num_grupos--;
               }
               else
                 if (obstaculo_grupo[i][j-1] < obstaculo_grupo[i][j] && obstaculo_grupo[i][j-1] != 0) {
                     tempInt = obstaculo_grupo[i][j];
                     for (i2 = i; i2 <= height; i2++) {
                        for (j2 = (byte)(j); j2 <= width; j2++) {
                            if (obstaculo_grupo[i2][j2] == tempInt)
                                obstaculo_grupo[i2][j2] = obstaculo_grupo[i][j-1];
                             else
                               if (obstaculo_grupo[i2][j2] > tempInt)
                                   obstaculo_grupo[i2][j2]--;
                        }
                     }
                     num_grupos--;
                 }
           }
        }
        
    }
    public void cobraTiro(Graphics g) {
        //System.out.println("cobra_y[0]: "+cobra_y[0][1]+", cobra_x[0]: "+cobra_x[0][1]);
        System.out.println("tiro_y: "+cobra_tiro_y+", tiro_x: "+cobra_tiro_x);
        if (boss_defesa == true && width-10 <= cobra_tiro_x && (boss_y-quadro_y)/11 <= cobra_tiro_y && cobra_tiro_y <= (boss_y2-quadro_y)/11 && cobra_tiro_disparado == true) {
            boss_ataque_refletor = true;
            interromper_boss_movimento = true;
            g.setColor(boss_ataque_refletor_cor1);
            g.fillRect(boss_x-20,boss_y,5,60);
            if (cobra_tiro_x >= (boss_x-quadro_x-20)/11) {
                cobra_tiro_disparado = false;
                boss_defesa = true;
                boss_ataque_tipo = "refletor";
                boss_ataque_y = cobra_tiro_y;
                boss_ataque_x = cobra_tiro_x--;
            }
            boss_defesa = false;
        }
        y = cobra_tiro_y; x = cobra_tiro_x;
        g.setColor(fundo_cor);
        if (x != 62 && x != 63)//barra de vida
            g.fillRect(quadro_x+((cobra_tiro_x-1)*11),quadro_y+((cobra_tiro_y-1)*11),11,11);
                
        if (0 < y && y <= height && 0 < x && x <= width)
            livre[y][x] = true;        
        switch (cobra_tiro_direcao) {
             case 119 -> cobra_tiro_y--;//w
             case 97 -> cobra_tiro_x--;//a
             case 115 -> cobra_tiro_y++;//s
             case 100 -> cobra_tiro_x++;//d
        }
        y = cobra_tiro_y; x = cobra_tiro_x;
        if (boss_x <= quadro_x+(x*11) && quadro_x+(x*11) <= boss_x2 && boss_y <= quadro_y+(y*11) && quadro_y+(y*11) <= boss_y2 && boss_movimento_iniciado == false) {//hitbox do boss
            cobra_tiro_dano = (byte)(cobra_tamanho[0]/4);
            if (boss_vida-cobra_tiro_dano < 0)
               cobra_tiro_dano += boss_vida - cobra_tiro_dano;
            byte c = (byte)(boss_vida_limite-boss_vida);
            g.setColor(new Color(61,68,179));
            for (i = c; i <= c+cobra_tiro_dano; i++) {
                 g.fillRect(boss_x2+48,221+(i*6),14,4);
            }
            System.out.println("Danooooooooooooooooo: "+cobra_tiro_dano);
            boss_vida -= cobra_tiro_dano;
            cobra_tiro_disparado = false; }
         else/*
           if (0 < y && y <= height && 0 < x && x <= width)
               if (obstaculo[y][x] == true || (boss_ataque[y][x] == true && !boss_ataque_tipo.equals("laser"))) {
                   cobra_tiro_disparado = false;
               }
            else*/
              if (x == 0 || cobra_tiro_alpha == 250 || y == 0 || y == height+1) {
                  cobra_tiro_disparado = false;
              }   
              else {
                g.setColor(cobra_tiro_cor);
                c = cobra_pixel;
                if (x != 62 && x != 63)
                    if (cobra_redonda == false)
                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                     else
                       g.fillOval(quadro_x+((cobra_tiro_x-1)*11),quadro_y+((cobra_tiro_y-1)*11),c,c);
                if (0 < y && y <= height && 0 < x && x <= width)
                    livre[y][x] = false;
                if ((quadro_x+(x*11)) > boss_turbina_x+40) {
                    cobra_tiro_alpha += 25;
                    g.setColor(new Color(0,0,0,cobra_tiro_alpha));
                    if (x != 62 && x != 63)
                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                }
              }
        id = 0;
        if (0 < y && y <= height && 0 < x && x <= width) {
            if (removerElemento(g,y,x) == true)
                baleado = true;
        }
    }
    
    public void empurrarCobra(Graphics g) {
        if (cobra_presa == false) {
            c = boss_ataque_refletor_ponto;
            tempShrt = (byte) c;
            y = cobra_y[0][c];
            System.out.println("cobra_y: "+c);
            /*do {
            } while (cobra_y[0][++tempShrt] == cobra_y[0][c] && tempShrt <= cobra_tamanho[0]);
            System.out.println("cobra_y: "+tempShrt);
            g.setColor(fundo_cor);
            for (i = 1; i <= cobra_tamanho[0]; i++) {
                y = cobra_y[0][i]; x = cobra_x[0][i];
                cobra[y][x] = false;
                livre[y][x] = true;
                g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                if (i >= tempShrt-1) {
                    cobra_y[0][i] += +-1;
                    cobra_x[0][i] = (byte)(cobra_x[0][i-1]);
                }
                else
                  cobra_x[0][i]--;
            }
            g.setColor(cobra_cor);
            for (i = 1; i <= cobra_tamanho[0]; i++) {
                y = cobra_y[0][i]; x = cobra_x[0][i];
                cobra[y][x] = true;
                livre[y][x] = false;
                g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                if (x == 1) {
                    cobra_presa = true;
                    boss_ataque_timer_alvo = 0;
                }
            }*/
            //refactor com cobra[][] retorna valor
            if (cobra_x[0][c-1]+1 == cobra_x[0][c] || cobra_x[0][c+1]+1 == cobra_x[0][c]) {
                i2 = cobra_y[0][c-1]+1 > cobra_y[0][c]? -1 : 1;
                do {tempShrt += i2;
                } while (cobra_y[0][tempShrt] == cobra_y[0][c] && tempShrt <= cobra_tamanho[0] && tempShrt > 0);
                tempShrt -= i2;
            }
            g.setColor(fundo_cor);
            x2 = cobra_x[0][c];
            for (j = 1; j <= 2; j++) {
                fim = j == 1? c : c-1;
                i = j == 1? cobra_tamanho[0]+1 : 0;
                i2 = j == 1? -1 : 1;
                do {
                    i += i2;
                    if (i > 300 || i < 0)
                        break;
                    y = cobra_y[0][i]; x = cobra_x[0][i];
                    cobra[y][x] = false;
                    livre[y][x] = true;
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                    //refactor range
                    if (((i > c && cobra_x[0][c+1]+1 == x2) || (i < c && cobra_x[0][c-1]+1 == x2)) || abs(c-i) <= 1) {
                        if ((i < tempShrt && i >= c-1) || (i > tempShrt && i <= c-1) || (abs(c-i) <= 1 && x == x2)) 
                            cobra_x[0][i]--;
                        else
                          cobra_y[0][i] += i > c? (byte) 1 : -1;
                    }
                    else {
                        cobra_y[0][i] = i > c? cobra_y[0][i-1] : cobra_y[0][i+1];
                        cobra_x[0][i] = i > c? cobra_x[0][i-1] : cobra_x[0][i+1];
                    }
                    if (x == 1) {
                        cobra_presa = true;
                        boss_ataque_timer_alvo = 0;
                        g.setColor(fundo_cor);
                        g.fillRect(boss_x-20,boss_y,5,60);
                    }
                } while (i != fim);
            }
            y = cobra_y[0][c]; x = cobra_x[0][c];
            cobra[y][x+1] = false;
            y = cobra_y[0][1]; x = cobra_x[0][1];
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            g.setColor(cobra_cor);
            for (i = 1; i <= cobra_tamanho[0]; i++) {
                y = cobra_y[0][i]; x = cobra_x[0][i];
                cobra[y][x] = true;
                livre[y][x] = false;
                g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            }
        }
        else
          if (num_clicks >= 15) {
              cobra_empurrada = false;
              cobra_presa = false;
              num_clicks = 0;
          }
    }
    
    public void moverObstaculos(Graphics g) {
        if (boss_impacto == false) {
            for (i = 1; i <= height; i++) {
                for (j = 1; j <= width; j++) {
                    if (obstaculo[i][j] == true && obstaculo_movido[i][j] == false) {
                        obstaculo[i][j] = false;
                        livre[i][j] = true;
                        g.setColor(fundo_cor);
                        g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                        g.setColor(obstaculo_cor);
                        switch (obstaculo_direcao[i][j]) {
                            case 1 -> {
                                obstaculo[i][j-1] = true;
                                livre[i][j-1] = false;
                                obstaculo_movido[i][j-1] = true;
                                g.fillRect(quadro_x+((j-2)*11),quadro_y+((i-1)*11),11,11);
                                obstaculo_direcao[i][j-1] = obstaculo_direcao[i][j];
                                obstaculo_direcao[i][j-1] = j == 8? 2 : (byte)(1);
                            }
                            case 2 -> {
                                obstaculo[i][j+1] = true;
                                livre[i][j+1] = false;
                                obstaculo_movido[i][j+1] = true;
                                g.fillRect(quadro_x+((j)*11),quadro_y+((i-1)*11),11,11);
                                obstaculo_direcao[i][j+1] = obstaculo_direcao[i][j];
                                obstaculo_direcao[i][j+1] = j == 37? 1 : (byte)(2);
                            }
                            case 3 -> {
                                obstaculo[i-1][j] = true;
                                livre[i-1][j] = false;
                                obstaculo_movido[i-1][j] = true;
                                g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-2)*11),11,11);
                                obstaculo_direcao[i-1][j] = obstaculo_direcao[i][j];
                                obstaculo_direcao[i-1][j] = i == 3? 4 : (byte)(3);
                            }
                            case 4 -> {
                                obstaculo[i+1][j] = true;
                                livre[i+1][j] = false;
                                obstaculo_movido[i+1][j] = true;
                                g.fillRect(quadro_x+((j-1)*11),quadro_y+((i)*11),11,11);
                                obstaculo_direcao[i+1][j] = obstaculo_direcao[i][j];
                                obstaculo_direcao[i+1][j] = i == 34? 3 : (byte)(4);
                            }
                        }
                        obstaculo_direcao[i][j] = 0;
                    }
                }
            }
            invalido = true;
            for (i = 1; i <= height; i++)
                for (j = 1; j <= width; j++) {
                    if (obstaculo[i][j] == true) 
                        if (removerElemento(g,i,j) == true && efeito != 6)
                            obstaculado = true;
                    obstaculo_movido[i][j] = false;
                }    
            invalido = false;
        }
        else {
          for (i = 1; i <= height; i++) {
             for (j = 1; j <= width; j++) {
                  if (obstaculo[i][j] == true) {
                      if (obstaculo_direcao[i][j] == 1)
                          j2 = -1;
                       else 
                         j2 = 1;
                      if (obstaculo[i][j+j2] == true) {
                          if (obstaculo_direcao[i][j] != obstaculo_direcao[i][j+j2]) { //colisao
                              tempInt = obstaculo_direcao[i][j];
                              obstaculo_direcao[i][j] = obstaculo_direcao[i][j+j2];
                              obstaculo_direcao[i][j+j2] = (byte)(tempInt);
                          }
                      }
                      else
                        if (j == 1) {
                            obstaculo_direcao[i][j] = 2;
                            j2 = 1; }
                         else
                           if (j == width) {
                               obstaculo_direcao[i][j] = 1;
                               j2 = -1;
                           }
                      if ((obstaculo[i][j+j2] == false && j+j2 != 0 && j+j2 != width+1)) {
                          obstaculo[i][j] = false;
                          obstaculo[i][j+j2] = true;
                          obstaculo_direcao[i][j+j2] = obstaculo_direcao[i][j];
                          obstaculo_direcao[i][j] = 0;
                          livre[i][j] = true;
                          livre[i][j+j2] = false;
                          if (removerElemento(g,i,j2) == true && efeito != 6)
                              obstaculado = true;
                          //if (invalido == true) {
                          g.setColor(fundo_cor);
                          g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                          //}    
                          g.setColor(obstaculo_cor);
                          g.fillRect(quadro_x+((j+j2-1)*11),quadro_y+((i-1)*11),11,11);
                          if (obstaculo_direcao[i][j+1] == 2 /*|| (invalido == false && obstaculo_direcao[i][j+2] != 2)*/) //para nao pegar o mesmo obstaculo varias vezes
                              j++;
                      }
                      else
                        if (obstaculo[i][j+1] == true && obstaculo_direcao[i][j+1] == 2) {
                            for (j2 = (byte)(j+2); j2 <= width; j2++) {
                                if (obstaculo_direcao[i][j2] == 1 || j2 == width+1)
                                    break;
                                 else
                                   if (obstaculo[i][j2] == false) {
                                       obstaculo[i][j] = false;
                                       livre[i][j] = true;
                                       obstaculo_direcao[i][j] = 0;
                                       g.setColor(fundo_cor);
                                       g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                       obstaculo[i][j2] = true;
                                       livre[i][j2] = false;
                                       obstaculo_direcao[i][j2] = 2;
                                       g.setColor(obstaculo_cor);
                                       g.fillRect(quadro_x+((j2-1)*11),quadro_y+((i-1)*11),11,11);
                                       j = j2;
                                       break;
                                   }
                            }
                        }
                  }
             }
        }
        boss_movimento_timer++;
        if (boss_movimento_timer == 300) {
            agruparObstaculos();
            boss_impacto = false;
            interromper_boss_movimento = false;
            boss_direcao = 3;
            g.setColor(fundo_cor);
        }
            
        }
        /*if (dificuldade.equals("normal") || dificuldade.equals("hard")) {
            obstaculo[10][(width/2)+1] = true;
            obstaculo[12][(width/2)+1] = true;
            obstaculo[25][(width/2)+1] = true;
            obstaculo[27][(width/2)+1] = true;
        }
        if (dificuldade.equals("hard")) {
            obstaculo[17][(width/2)+1] = true;
            obstaculo[20][(width/2)+1] = true;
        }*/
    }
        
    public void paint(Graphics g) {
        System.out.println("yes");
        iniciar_jogo = false;
        String highscore, score, scores = "";
        int high, num_jogos = 0;
        highscore = "0"; tempStr = "0";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./Jogador/jogador.txt"));
            highscore = reader.readLine();
            reader.close();
            /*reader = new BufferedReader(new FileReader("/home/timana/Documents/3º Semestre/FP/Trabalho Semestral/Jogo de Cobra/Jogador/estatisticas.txt"));
            String tempStr = reader.readLine();
            num_jogos = Integer.parseInt(tempStr);
            scores = reader.readLine();
            reader.close();*/
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        high = Integer.parseInt(highscore);
        nivel = 0; //jump
        reiniciar = false;
        carregarConfiguracoes();
        
        do {
            Timer t = new Timer();
            Scanner ler = new Scanner(System.in);
            int meta;
            short[] obstaculo_x = new short[201];
            short[] obstaculo_y = new short[201];
            escolha_x = new byte[3];
            escolha_y = new byte[3];
            short[] reescrever_x = new short[61];
            short[] reescrever_y = new short[61];
            cobra_tamanho = new short[4];
            char[] cobra_direcao = new char[4];
            String replay_cobra, replay_biscoito, replay_obs_add, replay_obs_num;
            short resto;
            float tempFlt;
            byte campo, escolhido, num_perguntas, time, min, max, vencedor;
            boolean lenda, perdeu, comido, /*gerado_obstaculos,*/ mesmo_grupo;
            score = "0";
            cobra_direcao[0] = "d".charAt(0); cobra_direcao[1] = "d".charAt(0); tempChr = "d".charAt(0); replay_cobra = ""; replay_biscoito = ""; replay_obs_add = ""; replay_obs_num = "";
            quadro_x = 440; quadro_y = 150; pixel = 11; comp = 420; comp2 = 508; larg = 6; meta = 0;
            timer_perguntas = 0; timer_resposta = 0; efeito = 0; timer_barra_efeito = 0; efeito_timer = 0; efeito_timer_alvo = 0; minas_y = 0; minas_x = 0; minas_loop = 1;
            boss_vida = 50; boss_vida_limite = 50; boss_direcao = 1; boss_movimento_timer = 0; boss_ataque_count = 0; boss_ataque_beam_length = 0; boss_ataque_timer = 0; boss_beam_timer = 0; boss_ataque_timer_alvo = 0; boss_ataque_tipo = "laser"; atraso_boss_movimento = 15; boss_vida_alpha = 0; cobra_tiro_alpha = 0; atraso_boss_ataque = 20;
            sco = 0; num_grupos = 0; num_obstaculos = 0; cobra_tamanho[0] = 7; cobra_tamanho[1] = 7; tempFlt = 0; biscoitos_comidos = 0; efeito_timer = 0; num_tiros = 0; num_jogadores = 1; num_rivais = 2; num_clicks = 0; inicio = 0; fim = 0;
            biscoito_x = 0; biscoito_y = 0; escolhido = -1; alpha = 5; minas_diametro = 0; duplicador = 1; boss_loop = 0; j2 = -1; inicio_x = 0; inicio_y = 0; fim_y = 0; atual = 1; rival_biscoitos_comidos = 0; vencedor = 0;
            perdeu = false; comido = true; biscoito_destruido = false; gerado_obstaculos = false; gerado_perguntas = false; minado = false; baleado = false; feito = false; noturno = false; noturnado = false; apagar = false; envenenado = false; ////cobra_movimento_iniciado = false;
            boss = false; boss_defesa = false; boss_derrotado = false; rival_derrotado[0] = true; rival_derrotado[1] = true; boss_impacto = false; boss_movimento_iniciado = false; lasado = false; obstaculado = false; interromper_efeito = true; interromper_boss_movimento = false; interromper_boss_ataque = false; reagendar_boss_movimento = true; reagendar_boss_ataque = true; boss_ataque_refletor = false;
            
            //Font custom = new Font("Roboto",1,15);
            //g.setFont(custom);
            resto = (short) ((comp-larg) % 11); comp -= resto;
            resto = (short) ((comp2-larg) % 11); comp2 -= resto;
            width = (byte) (comp2/11);
            height = (byte) (comp/11);
            if (iniciar_jogo == false) {
                desenharQuadro(g);
                g.setColor(Color.white);

                if (nivel == 0) {
                    g.drawString("Highscore:",quadro_x+comp2+larg+44,quadro_y);
                    g.drawString(highscore,quadro_x+comp2+larg+115,quadro_y);
                }
                else
                  g.drawString("Meta:",quadro_x+comp2+larg+44,quadro_y);
                g.drawString("Score:   0",quadro_x+comp2+larg+44,quadro_y+22);
                //g.drawString(score,quadro_x+comp2+larg+85,quadro_y+22);
            }
            quadro_x += larg; quadro_y += larg;

            Color rival_cor = new Color(255,25,160);
            Color cobra_bomba_cor = new Color(76,44,18);
            Color cobra_fantasma_cor = new Color(255,255,255,150);
            Color boss_turbina_cor = Color.cyan;
            Color boss_corpo_cor = Color.red;
            g.setColor(cobra_cor);
            
            cobra_y = new byte[4][301];
            cobra_x = new byte[4][301];
            cobra_derrotada = new boolean[4];
            cobra = new boolean[height+2][width+2];
            rival = new boolean[height+2][width+2];
            rival_y = new byte[28][301];
            rival_x = new byte[28][301];
            rival_tamanho = new short[28];
            rival_direcao = new byte[28];
            cobra_caida = new boolean[height+2][width+2];
            livre = new boolean[height+2][width+2];
            obstaculo = new boolean[height+2][width+2];
            obstaculo_grupo = new byte[height+2][width+2]; //indica o grupo de um obstaculo
            obstaculo_direcao = new byte[height+2][width+2];
            obstaculo_movido = new boolean[height+2][width+2];
            minas = new boolean[height+2][width+2];
            escolha = new boolean[height+2][width+2];
            boss_ataque = new boolean[height+2][width+2];
            checkpoint = new boolean[height+2][width+2];
            if (iniciar_jogo == true) {
                telaInicio(g);
                iniciar_jogo = false;
            }
            c = quadro_x+comp2;
            int[] boss_corpo_x = {c+67,c+82,c+112,c+127,c+112,c+82,c+67};
            c = quadro_y;
            int[] boss_corpo_y = {c+210,c+185,c+185,c+210,c+235,c+235,c+210};
            boss_turbina_x = (short)(boss_corpo_x[2]-15); boss_turbina_y = (short)(boss_corpo_y[2]);
            boss_ataque_x = (short)((boss_corpo_x[0]-quadro_x)/11); boss_ataque_y = (short)((boss_corpo_y[0]-quadro_y)/11); boss_ataque_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);

            switch (dificuldade) {
                case "easy": atraso = 80; break;
                case "normal": atraso = 68; break;
                case "hard": atraso = 45; break;
            }
            id = 0;
            g.setColor(obstaculo_cor);
            
            switch (nivel) {
                case 1: if (dificuldade.equals("easy"))
                            meta = 2000;
                        if (dificuldade.equals("normal"))
                            meta = 3000;
                        if (dificuldade.equals("hard"))
                            meta = 5000;
                        break;
                case 2: {
                    obstaculo[height/2][(width/2)+1] = true;
                    meta = 2500;
                    if (dificuldade.equals("normal") || dificuldade.equals("hard")) {
                        obstaculo[30][11] = true;
                        obstaculo[30][10] = true;
                        obstaculo[30][9] = true;
                        obstaculo[30][8] = true;
                        obstaculo[29][8] = true;
                        obstaculo[28][8] = true;
                        obstaculo[27][8] = true;
                        meta = 3000;
                    }
                    if (dificuldade.equals("hard")) {
                        obstaculo[8][35] = true;
                        obstaculo[8][36] = true;
                        obstaculo[8][37] = true;
                        obstaculo[8][38] = true;
                        obstaculo[9][38] = true;
                        obstaculo[10][38] = true;
                        obstaculo[11][38] = true;
                        meta = 5000;
                    }
                    break;
                }
                case 3: {
                    obstaculo[height/2][(width/2)+1] = true;
                    obstaculo[30][11] = true;
                    obstaculo[30][10] = true;
                    obstaculo[30][9] = true;
                    obstaculo[30][8] = true;
                    obstaculo[29][8] = true;
                    obstaculo[28][8] = true;
                    obstaculo[27][8] = true;
                    meta = 3000;
                    if (dificuldade.equals("normal") || dificuldade.equals("hard")) {
                        obstaculo[30][35] = true;
                        obstaculo[30][36] = true;
                        obstaculo[30][37] = true;
                        obstaculo[30][38] = true;
                        obstaculo[29][38] = true;
                        obstaculo[28][38] = true;
                        obstaculo[27][38] = true;

                        obstaculo[8][11] = true;
                        obstaculo[8][10] = true;
                        obstaculo[8][9] = true;
                        obstaculo[8][8] = true;
                        obstaculo[9][8] = true;
                        obstaculo[10][8] = true;
                        obstaculo[11][8] = true;
                        meta = 4000;
                    }
                    if (dificuldade.equals("hard")) {
                        obstaculo[height/2][width/2] = true;
                        obstaculo[(height/2)+1][width/2] = true;
                        obstaculo[(height/2)+1][(width/2)+1] = true;                            
                        obstaculo[height/2][(width/2)+2] = true;
                        obstaculo[(height/2)+1][(width/2)+2] = true;                            
                        obstaculo[8][35] = true;
                        obstaculo[8][36] = true;
                        obstaculo[8][37] = true;
                        obstaculo[8][38] = true;
                        obstaculo[9][38] = true;
                        obstaculo[10][38] = true;
                        obstaculo[11][38] = true;
                        meta = 6000;
                    }
                    break;
                }
                case 4: {
                    obstaculo[3][(width/2)+1] = true; obstaculo_direcao[3][(width/2)+1] = 1;
                    obstaculo[5][(width/2)+1] = true; obstaculo_direcao[5][(width/2)+1] = 2;
                    obstaculo[32][(width/2)+1] = true; obstaculo_direcao[32][(width/2)+1] = 1;
                    obstaculo[34][(width/2)+1] = true; obstaculo_direcao[34][(width/2)+1] = 2;
                    meta = 3000;
                    if (dificuldade.equals("normal") || dificuldade.equals("hard")) {
                        obstaculo[10][(width/2)+1] = true; obstaculo_direcao[10][(width/2)+1] = 1;
                        obstaculo[12][(width/2)+1] = true; obstaculo_direcao[12][(width/2)+1] = 2;
                        obstaculo[25][(width/2)+1] = true; obstaculo_direcao[25][(width/2)+1] = 1;
                        obstaculo[27][(width/2)+1] = true; obstaculo_direcao[27][(width/2)+1] = 2;
                        meta = 4000;
                    }
                    if (dificuldade.equals("hard")) {
                        obstaculo[17][(width/2)+1] = true; obstaculo_direcao[17][(width/2)+1] = 1;
                        obstaculo[20][(width/2)+1] = true; obstaculo_direcao[20][(width/2)+1] = 2;
                        meta = 6000;
                    }
                    break;
                }
                case 5: {
                    obstaculo[3][(width/2)+1] = true; obstaculo_direcao[3][(width/2)+1] = 1;
                    obstaculo[5][(width/2)+1] = true; obstaculo_direcao[5][(width/2)+1] = 2;
                    obstaculo[32][(width/2)+1] = true; obstaculo_direcao[32][(width/2)+1] = 1;
                    obstaculo[34][(width/2)+1] = true; obstaculo_direcao[34][(width/2)+1] = 2;
                    obstaculo[10][(width/2)+1] = true; obstaculo_direcao[10][(width/2)+1] = 1;
                    obstaculo[12][(width/2)+1] = true; obstaculo_direcao[12][(width/2)+1] = 2;
                    obstaculo[25][(width/2)+1] = true; obstaculo_direcao[25][(width/2)+1] = 1;
                    obstaculo[27][(width/2)+1] = true; obstaculo_direcao[27][(width/2)+1] = 2;
                    obstaculo[17][(width/2)+1] = true; obstaculo_direcao[17][(width/2)+1] = 1;
                    obstaculo[20][(width/2)+1] = true; obstaculo_direcao[20][(width/2)+1] = 2;
                    meta = 2000;
                    if (dificuldade.equals("normal") || dificuldade.equals("hard")) {
                        obstaculo[3][3] = true; obstaculo_direcao[3][3] = 4;
                        obstaculo[3][42] = true; obstaculo_direcao[3][42] = 4;
                        meta = 4000;
                    }
                    if (dificuldade.equals("hard")) {
                        obstaculo[3][7] = true; obstaculo_direcao[3][7] = 4;
                        obstaculo[3][38] = true; obstaculo_direcao[3][38] = 4;
                        obstaculo[34][5] = true; obstaculo_direcao[34][5] = 3;
                        obstaculo[34][40] = true; obstaculo_direcao[34][40] = 3;
                        meta = 6000;
                    }
                    break;
                }
                case 6: {
                    for (i = 1; i <= height; i++) {
                        for (j = 1; j <= width; j++) {
                            if (dificuldade.equals("hard") || (dificuldade.equals("easy") && i % 3 != 0 && j % 3 != 0) || (dificuldade.equals("normal") && i % 3 != 0)) {
                                if (i == 1 || i == height || j == 1 || j == width) {
                                    if (((j-1) % 3 == 0 && (i == 1 || i == height)) || ((i-1) % 3 == 0 && (j == 1 || j == width))) 
                                         obstaculo[i][j] = true;
                                }
                                else
                                  if (i % 2 == 1 && j % 2 == 1)
                                      obstaculo[i][j] = true;
                            }    
                        }
                    }
                    if (dificuldade.equals("easy"))
                        meta = 3000;
                    if (dificuldade.equals("normal"))
                        meta = 3000;
                    if (dificuldade.equals("hard")) {
                        meta = 2000;
                        atraso = 60;
                    }
                    break;
                }
                case 7: {
                    boss = true;
                    break;
                }
                case 8: {
                    cobra_tamanho[0] = 190;
                    efeito = 11;
                    meta = 5000;
                    break;
                }
                case 9: {
                    efeito = 10;
                    meta = 5000;
                    break;
                }
                case 10: {
                    obstaculo[10][10] = true;
                    obstaculo[10][35] = true;
                    obstaculo[27][10] = true;
                    obstaculo[27][35] = true;
                    atraso = (short)(atraso/2.5);
                    meta = 2600;
                    break;
                }
            }
            for (i = 0; i <= height+1; i++) {
              for (j = 0; j <= width+1; j++) {
                 if (i == 0 || i == height+1 || j == 0||j == width+1 || obstaculo[i][j] == true) {
                    livre[i][j] = false;
                    if (obstaculo[i][j] == true)
                        g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                 }
                 else
                   livre[i][j] = true;   
               }
            }
            g.setColor(cobra_cor);
            if (nivel == 7)
                g.drawString("derrote o boss",quadro_x+comp2+larg+78,quadro_y-7);
             else
               if (nivel != 0)
                   g.drawString(Integer.toString(meta),quadro_x+comp2+larg+78,quadro_y-7);
            
            iniciarCobra(g);
            ler.nextLine();
            System.out.println("width: "+width);
            System.out.println("height: "+height);
            g.setColor(cobra_cor);
            perdeu = true;
            replay(g);//jump
            while(vencedor == 0 && (perdeu == false && nivel == 0) || (perdeu == false && (sco < meta || (nivel == 7 && boss_derrotado == false)))) {
                id = 0;
                if (comido == true || biscoito_destruido == true || rival_comeu == true) {
                   do { //gera biscoito
                       biscoito_x = (byte) ((Math.random() * width) + 1);
                       biscoito_y = (byte) ((Math.random() * height) + 1);
                       y = biscoito_y;
                       x = biscoito_x;
                   } while (livre[y][x] == false);
                   livre[y][x] = false;
                   g.setColor(biscoito_cor);
                   g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
                   replay_biscoito += y < 10? "0"+y : y; 
                   replay_biscoito += x < 10? "0"+x : x;
                   comido = false;
                   if (biscoito_destruido == true)
                       biscoito_destruido = false;
                }
                if (nivel == 0) {       

                    if ((sco % 200/*600/*0*/ == 0)&&(sco != 0)) {

                        if ((gerado_obstaculos == false && num_obstaculos < 200) || rival_comeu == true) /*&& (nivel == 3)*/ {
                            num_obstaculos++;
                            do { //gera obstaculos;
                                obstaculo_y[num_obstaculos] = (short) ((Math.random() * height) + 1);
                                obstaculo_x[num_obstaculos] = (short) ((Math.random() * width) + 1);                   
                                y = obstaculo_y[num_obstaculos];
                                x = obstaculo_x[num_obstaculos];
                            } while (livre[y][x] == false);
                            obstaculo[y][x] = true;
                            livre[y][x] = false;
                            rival_comeu = false;
                            num_grupos++;
                            obstaculo_direcao[y][x] = 1;
                            obstaculo_grupo[y][x] = (byte) (num_grupos);
                            g.setColor(obstaculo_cor);
                            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                            numero = (byte) ((Math.random() * 4) + 2);
                            campo = 1;
                            replay_obs_add += y < 10? "0"+y : y; 
                            replay_obs_add += x < 10? "0"+x : x;
                            replay_obs_num += numero+"";
                            for (i = 1; i <= numero; i++) {
                               do {
                                   invalido = true;
                                   x2 = (short) ((x-campo)+(Math.random()*((campo*2)+1)));
                                   y2 = (short) ((y-campo)+(Math.random()*((campo*2)+1)));
                                   if ((x2 > 0 && x2 <= width)&&(y2 > 0 && y2 <= height) && num_obstaculos < 200) {
                                        if ((livre[y2][x2] == true)&&(obstaculo[y2][x2-1] == true || obstaculo[y2][x2+1] == true || obstaculo[y2-1][x2] == true || obstaculo[y2+1][x2] == true)) {
                                             num_obstaculos++;
                                             campo++;
                                             obstaculo_x[num_obstaculos] = x2;                         
                                             obstaculo_y[num_obstaculos] = y2;
                                             obstaculo_direcao[y2][x2] = 1;
                                             obstaculo[y2][x2] = true;
                                             obstaculo_grupo[y2][x2] = (byte) (num_grupos);
                                             livre[y2][x2] = false;
                                             invalido = false;
                                             g.fillRect(quadro_x+((x2-1)*11),quadro_y+((y2-1)*11),11,11);
                                             replay_obs_add += y2 < 10? "0"+y2 : y2; 
                                             replay_obs_add += x2 < 10? "0"+x2 : x2;
                                        }
                                   }
                               } while (invalido == true); 
                            }
                            agruparObstaculos();
                            gerado_obstaculos = true;
                        }
                        //if (timer_perguntas >= 20000 && gerado_perguntas == false && num_jogadores == 1) {
                        if (timer_perguntas >= 2000 && gerado_perguntas == false && num_jogadores == 1) {
                            removerEfeito(g);
                            interromper_efeito = false;
                            numero = (byte)((Math.random()*6)+1);
                            Perguntas(numero);
                            g.setColor(Color.white);
                            g.drawString("Q. "+pergunta,quadro_x-310,quadro_y);
                            g.drawString("1. "+alternativa[0],quadro_x-260,quadro_y+22);
                            g.drawString("2. "+alternativa[1],quadro_x-260,quadro_y+44);
                            g.drawString("3. "+alternativa[2],quadro_x-260,quadro_y+66);
                            short red, green, blue;
                            for (i = 0; i <= 2; i++) {
                                do { //lança perguntas no mapa
                                     escolha_y[i] = (byte) ((Math.random()*height)+1);
                                     escolha_x[i] = (byte) ((Math.random()*width)+1);
                                     y = escolha_y[i]; x = escolha_x[i];
                                } while (livre[y][x] == false);
                                red = (short) (Math.random()*256); green = (short) (Math.random()*256); blue = (short) (Math.random()*256);
                                g.setColor(new Color(red,green,blue));
                                tempStr = Integer.toString(i+1);
                                g.drawString(tempStr,2+quadro_x+((x-1)*11),quadro_y+(y*11));
                                livre[y][x] = false;
                                escolha[y][x] = true;
                            }
                            gerado_perguntas = true;
                            timer_perguntas = 0;
                            //barra de carregar
                            g.setColor(new Color(107,29,90));
                            desenharBarra(g);
                            g.setColor(cobra_cor);
                        }
                        if (timer_barra_efeito == 147 && escolhido != -1) {
                            g.setColor(fundo_cor);
                            g.fillRect(quadro_x+comp2+40,quadro_y+25,300,95);
                        }
                    }
                    if (boss_derrotado == false && sco == 600/*00*/ && (reagendar_boss_movimento == true || reagendar_boss_ataque == true)) {//movimento do boss
                        System.out.println("Aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii: "+atraso_boss_movimento);
                        boss = true;
                        g.setColor(new Color(69,222,227));
                        g.fillRoundRect(boss_corpo_x[3]+45,215,20,308,12,12);
                        g.setColor(new Color(61,68,179));
                        g.fillRoundRect(boss_corpo_x[3]+47,218,16,303,5,5);
                        g.setColor(new Color(255,124,30));
                        for (i = (short)(boss_vida-1); i >= 0; i--)
                             g.fillRect(boss_corpo_x[3]+48,221+(i*6),14,4);
                        if (reagendar_boss_movimento == true) {
                            reagendar_boss_movimento = false;
                            TimerTask mov = new TimerTask() {
                                public void run() {
                                    boss = boss_vida > 0;
                                    if (boss == true) {
                                        if (interromper_boss_movimento == false) {
                                            //boss_corpo_y[]++;
                                            if (boss_corpo_y[2] == quadro_y+(2*11))//por causa dos scores
                                                boss_direcao = 2; //down}
                                             else
                                               if (boss_corpo_y[5] == quadro_y+comp)
                                                   boss_direcao = 1; //up
                                                else
                                                  if (boss_ataque_count % 45 == 0 && boss_ataque_count != 0) {
                                                      interromper_boss_ataque = true;
                                                      interromper_boss_movimento = true;
                                                      id = 0;
                                                      iniciarRival(g);
                                                      id = 1;
                                                      iniciarRival(g);
                                                      boss_ataque_count++;
                                                      rival_derrotado[0] = false;
                                                      rival_derrotado[1] = false;
                                                      g.setColor(fundo_cor);
                                                      g.fillRect(boss_corpo_x[0],boss_corpo_y[1],60,50);
                                                  }
                                                  else
                                                  if (boss_ataque_count % 30 == 0 && boss_ataque_count != 0) {
                                                      if (boss_corpo_y[0] == (int)(quadro_y+(comp/2)) && boss_movimento_iniciado == false) {
                                                          boss_direcao = 3; //right
                                                          boss_movimento_iniciado = true;
                                                          atraso_boss_movimento = 30;
                                                          reagendar_boss_movimento = true;
                                                          g.setColor(fundo_cor);
                                                          g.fillRect(boss_corpo_x[3]+45,215,20,308);
                                                      }
                                                       else
                                                         if (boss_corpo_x[3] == quadro_x+comp2+(40*11)) {
                                                             boss_direcao = 4; //left
                                                             boss_impacto_velocidade = 0;}
                                                         else 
                                                           if ((boss_corpo_x[0] == quadro_x+comp2+20 && boss_direcao == 4) || (boss_corpo_x[0] == quadro_x+comp2+67 && boss_direcao == 3)) {
                                                               if (boss_corpo_x[0] == quadro_x+comp2+20) {
                                                                   boss_impacto = true;
                                                                   interromper_boss_movimento = true;
                                                                   boss_direcao = 0; }//ate 15 segundos se passarem
                                                                else
                                                                 if (boss_corpo_x[0] == quadro_x+comp2+67) {
                                                                      boss_movimento_timer = 0;
                                                                      boss_direcao = (byte)((Math.random()*2)+1);
                                                                      interromper_boss_ataque = false;
                                                                      boss_movimento_iniciado = false;
                                                                      atraso_boss_movimento = 1000;
                                                                      reagendar_boss_movimento = true;
                                                                      boss_vida_alpha = 0;
                                                                      boss_ataque_count++;
                                                                      g.setColor(fundo_cor);
                                                                      g.fillRect(boss_corpo_x[0]-8,boss_corpo_y[0]-4,10,8);
                                                                      g.fillRect(boss_corpo_x[0]-20,boss_corpo_y[0]-15,15,30);
                                                                     
                                                                      g.setColor(new Color(69,222,227));
                                                                      g.fillRoundRect(boss_corpo_x[3]+45,215,20,308,12,12);
                                                                      g.setColor(new Color(61,68,179));
                                                                      g.fillRoundRect(boss_corpo_x[3]+47,218,16,303,5,5);
                                                                      g.setColor(new Color(255,124,30));
                                                                      for (i = (short)(boss_vida-1); i >= 0; i--)
                                                                           g.fillRect(boss_corpo_x[3]+48,221+(i*6),14,4);
                                                               }
                                                           }   
                                                  }
                                            if (boss_ataque_tipo.equals("beam"))
                                                boss_movimento_timer++;
                                            if ((!boss_ataque_tipo.equals("beam")) || (boss_ataque_tipo.equals("beam") && boss_movimento_timer == 2)) {
                                                boss_movimento_timer = 0;
                                                g.setColor(fundo_cor);
                                                g.fillRect(boss_turbina_x,boss_turbina_y,40,10);
                                                g.fillRect(boss_turbina_x,boss_turbina_y+40,40,10);
                                                g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                                                if (boss_direcao == 4 || boss_movimento_timer == 300) {
                                                    g.fillRect(boss_corpo_x[0]-8,boss_corpo_y[0]-4,10,8);
                                                    g.fillRect(boss_corpo_x[0]-20,boss_corpo_y[0]-15,15,30);
                                                }
                                                if (boss_ataque_tipo.equals("beam")) {
                                                    if (boss_direcao == 1)
                                                        g.drawLine(quadro_x+1,boss_corpo_y[0]+8,boss_corpo_x[0],boss_corpo_y[0]+8);
                                                     else
                                                       g.drawLine(quadro_x+1,boss_corpo_y[0]-8,boss_corpo_x[0],boss_corpo_y[0]-8);
                                                }
                                                switch (boss_direcao) {
                                                    case 1 -> boss_turbina_y--; 
                                                    case 2 -> boss_turbina_y++;
                                                    case 3 -> boss_turbina_x++;
                                                    case 4 -> {boss_impacto_velocidade += 0.5;
                                                               if (boss_turbina_x-boss_impacto_velocidade < (quadro_x+comp2+50)) 
                                                                   boss_impacto_velocidade = boss_turbina_x-(quadro_x+comp2+50);
                                                               boss_turbina_x -= boss_impacto_velocidade;
                                                    }
                                                               
                                                }
                                                g.setColor(boss_turbina_cor);
                                                g.fillRect(boss_turbina_x,boss_turbina_y,40,10);
                                                g.fillRect(boss_turbina_x,boss_turbina_y+40,40,10);
                                                g.setColor(boss_corpo_cor);
                                                for (i = 0; i <= 6; i++) {
                                                     switch (boss_direcao) {
                                                        case 1 -> boss_corpo_y[i]--;
                                                        case 2 -> boss_corpo_y[i]++;
                                                        case 3 -> boss_corpo_x[i]++;
                                                        case 4 -> boss_corpo_x[i] -= boss_impacto_velocidade;
                                                     }
                                                }
                                                g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                                                if (boss_direcao == 4 || boss_movimento_timer == 300) {
                                                    g.setColor(Color.yellow);
                                                    g.fillRect(boss_corpo_x[0]-8,boss_corpo_y[0]-4,10,8);
                                                    g.setColor(boss_corpo_cor);
                                                    g.fillRect(boss_corpo_x[0]-20,boss_corpo_y[0]-15,15,30);
                                                }
                                            }
                                        }    
                                    }
                                    else {
                                      boss = false;
                                      boss_derrotado = true;
                                      sco += 3000; //quanto mais rapido maior o score
                                      g.setColor(fundo_cor);
                                      g.fillRoundRect(boss_corpo_x[3]+45,215,20,308,12,12);
                                      g.fillRect(boss_corpo_x[0],boss_corpo_y[1],100,100);
                                      //t.cancel();
                                    }
                                }
                            };
                            t.scheduleAtFixedRate(mov,atraso_boss_movimento,atraso_boss_movimento);
                        }
                        if (reagendar_boss_ataque == true) {
                            reagendar_boss_ataque = false;
                            TimerTask lsr = new TimerTask() {
                                public void run() {
                                    boss_ataque_timer += 20;
                                    if (interromper_boss_ataque == false && boss_ataque_timer >= boss_ataque_timer_alvo) {
                                        if (!boss_ataque_tipo.equals("beam"))
                                            boss_ataque_timer = 0;
                                        y = boss_ataque_y; x = boss_ataque_x;
                                        if (boss_ataque_refletor == false) {
                                            if (boss_ataque_count % 30 == 0 && boss_ataque_count != 0) {
                                                interromper_boss_ataque = true;
                                                atraso_boss_movimento = 10;}
                                            else
                                              if ((boss_ataque_count % 55 == 0 && boss_ataque_count != 0) || cobra_presa == true) {
                                                  if (boss_loop < 4) {
                                                      boss_ataque_tipo = "beam";
                                                      interromper_boss_movimento = true; //t.wait on mov?
                                                      reagendar_boss_ataque = true;
                                                      if (boss_ataque_timer >= 500) {
                                                          boss_ataque_timer = 0;
                                                          if (boss_loop % 2 == 0)
                                                              g.setColor(boss_ataque_laser_cor);
                                                           else
                                                             g.setColor(fundo_cor);
                                                          g.drawLine(quadro_x+1,boss_corpo_y[0],boss_corpo_x[0],boss_corpo_y[0]);
                                                          if (++boss_loop == 4) {
                                                              atraso_boss_ataque = 50;
                                                              boss_ataque_timer_alvo = 0;
                                                              reagendar_boss_ataque = true;
                                                          }    
                                                      }
                                                  }
                                                  if (boss_loop >= 4) {
                                                      System.out.println(boss_ataque_beam_length+" = "+(boss_corpo_x[0]-quadro_x));
                                                      if (boss_ataque_beam_length < boss_corpo_x[0]-quadro_x) {
                                                          boss_ataque_beam_length += 9;
                                                      }
                                                      if (boss_ataque_beam_length > boss_corpo_x[0]-quadro_x)
                                                          boss_ataque_beam_length = (short)(boss_corpo_x[0]-quadro_x);
                                                      if (boss_ataque_timer < 400) {
                                                          int boss_ataque_beam_x[] = {boss_corpo_x[0],boss_corpo_x[0]-boss_ataque_beam_length,boss_corpo_x[0]-boss_ataque_beam_length-12,boss_corpo_x[0]-boss_ataque_beam_length,boss_corpo_x[0]};
                                                          int boss_ataque_beam_y[] = {boss_corpo_y[0]-8,boss_corpo_y[0]-8,boss_corpo_y[0],boss_corpo_y[0]+8,boss_corpo_y[0]+8};
                                                          g.fillPolygon(boss_ataque_beam_x,boss_ataque_beam_y,boss_ataque_beam_x.length);      
                                                          g.setColor(boss_ataque_beam_cor1);
                                                          g.fillRect(boss_corpo_x[0]-boss_ataque_beam_length,boss_corpo_y[0]-6,boss_ataque_beam_length,12);
                                                      }
                                                      else 
                                                        if (apagar == true) {
                                                            g.setColor(fundo_cor);
                                                            g.fillRect(boss_corpo_x[0]-boss_ataque_beam_length,boss_corpo_y[0]-8,9,17);  
                                                        }
                                                      if (boss_ataque_beam_length == boss_corpo_x[0]-quadro_x && interromper_boss_movimento == true && apagar == false) {//chegou a parede
                                                          interromper_boss_movimento = false;
                                                          boss_direcao = 0;
                                                          for (i = 1; i <= cobra_tamanho[0]; i++) {
                                                              if ((quadro_y+(cobra_y[0][i]*11)) <= boss_corpo_y[0]) {
                                                                   boss_direcao = 1;
                                                                   break;
                                                              }     
                                                          }
                                                          if (boss_direcao == 0)
                                                              boss_direcao = 2;}
                                                       else
                                                         if (boss_ataque_timer == 400 && interromper_boss_movimento == false) {
                                                             boss_ataque_beam_length = 0;
                                                             interromper_boss_movimento = true;
                                                             atraso_boss_movimento = 300;
                                                             apagar = true;
                                                             y = (short)((boss_corpo_y[0]-quadro_y)/11);
                                                             for (i = 1; i <= 2; i++) {//patch. faca ler somente blocos unicos
                                                                 for (j = 1; j <= width; j++) {
                                                                     boss_ataque[y+i][j] = true;
                                                                     livre[y+i][j] = false;
                                                                 }
                                                             }
                                                         }
                                                         else
                                                           if (boss_ataque_beam_length == boss_corpo_x[0]-quadro_x && interromper_boss_movimento == false)
                                                               boss_ataque_timer++;
                                                      //sai aqui
                                                      if (boss_ataque_beam_length == boss_corpo_x[0]-quadro_x && apagar == true) {
                                                          boss_ataque_tipo = "laser";
                                                          atraso_boss_ataque = 900;
                                                          reagendar_boss_ataque = true;
                                                          boss_loop = 0;
                                                          boss_ataque_timer = 0;
                                                          boss_ataque_count++;
                                                          boss_ataque_beam_length = 0;
                                                          atraso_boss_movimento = 1000;
                                                          interromper_boss_movimento = false;
                                                          apagar = false;
                                                      }
                                                      g.setColor(fundo_cor);
                                                      g.fillRect(quadro_x,quadro_y,width*11,height*11);
                                                      for (i = 1; i <= height; i++) {
                                                           for (j = 1; j <= width; j++) {
                                                                g.setColor(Color.white);
                                                                if (boss_ataque[i][j] == true)
                                                                    g.drawString("T",quadro_x+((j-1)*11),quadro_y+(i*11));
                                                                 else
                                                                   g.drawString("F",quadro_x+((j-1)*11),quadro_y+(i*11));
                                                            }
                                                        }
                                                      if (((boss_corpo_x[0]-quadro_x)-boss_ataque_beam_length)/11 <= width) {
                                                          y = (short)((boss_corpo_y[0]-quadro_y)/11);
                                                          if (interromper_boss_movimento == true) {
                                                              x = (short)(((boss_corpo_x[0]-quadro_x)-boss_ataque_beam_length)/11);
                                                              linha1 = 1; linha2 = 2;}
                                                           else
                                                             if (boss_ataque_timer != 420){
                                                                 if (boss_direcao == 1) {
                                                                     linha1 = 0; linha2 = 2; }
                                                                  else {
                                                                    linha1 = -1; linha2 = 1; }
                                                           }
                                                          for (j = 1; j <= width; j++) {
                                                               if (interromper_boss_movimento == false)
                                                                   x = (short)(j);
                                                               for (i = linha1; i <= linha2; i++) {
                                                                    if ( i == -1 || i == 2 || boss_ataque_timer >= 400) {//torna false os lugares onde o laser ja nao esta
                                                                        boss_ataque[y+i][x] = false;
                                                                        livre[y+i][x] = true;
                                                                    }
                                                                    else {
                                                                      boss_ataque[y+i][x] = true;
                                                                      livre[y+i][x] = false;
                                                                      if (removerElemento(g,(y+1),x) == true && efeito != 6)
                                                                          lasado = true;
                                                                      g.setColor(fundo_cor);
                                                                      if (i == -1) //elimina o excesso na parte de cima e de baixo do beam
                                                                          g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,3);
                                                                       else
                                                                         g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+8,11,3);
                                                                    }
                                                               }
                                                               if (interromper_boss_movimento == true)
                                                                   break;
                                                          }
                                                      }
                                                      
                                                  }
                                              }   
                                              else   
                                                if (boss_ataque_count % 5 == 0 && boss_ataque_count != 0)
                                                    boss_ataque_tipo = "perfurador";
                                                 else
                                                   boss_ataque_tipo = "laser";
                                        }
                                        if (!(boss_ataque_tipo.equals("beam"))) {
                                            if (x <= width) {
                                                boss_ataque[y][x] = false;
                                                if (cobra[y][x] == false)
                                                    livre[y][x] = true;
                                            }
                                            g.setColor(fundo_cor);
                                            switch (boss_ataque_tipo) {
                                                case "laser" -> g.drawLine(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+boss_ataque_offset_y,quadro_x+((x-1)*11)+8,quadro_y+((y-1)*11)+boss_ataque_offset_y);
                                                case "perfurador" -> g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,6);
                                                default -> g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                            }
                                            System.out.println("boss_ataque_y: "+boss_ataque_y);
                                            System.out.println("boss_ataque_x: "+boss_ataque_x);
                                            if (cobra_empurrada == true && x <= width+1 && cobra[y][x-1] == true) {
                                                empurrarCobra(g);
                                            }
                                            boss_ataque_x--;
                                            y = boss_ataque_y; x = boss_ataque_x;
                                            if (x <= width) {
                                                boss_ataque[y][x] = true;
                                                livre[y][x] = false;
                                            }
                                            switch (boss_ataque_tipo) {
                                                case "laser" -> {
                                                    g.setColor(boss_ataque_laser_cor);
                                                    g.drawLine(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+boss_ataque_offset_y,quadro_x+((x-1)*11)+8,quadro_y+((y-1)*11)+boss_ataque_offset_y);
                                                }
                                                case "perfurador" -> {
                                                    g.setColor(boss_ataque_perfurador_cor);
                                                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,6);
                                                }
                                                default -> {
                                                    g.setColor(boss_ataque_refletor_cor2);
                                                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                                }
                                            }
                                            if (x <= width) {//usado no beam tambem. Torne num metodo
                                                if (cobra[y][x] == true && efeito != 6 && cobra_empurrada == false) {
                                                    tempShrt = cobra_tamanho[0];
                                                    if (boss_ataque_tipo.equals("perfurador")) {
                                                        cobra[y][x] = false;
                                                        livre[y][x] = true;
                                                        g.setColor(fundo_cor);
                                                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,6); 
                                                    }
                                                    for (i = 1; i <= tempShrt; i++) {
                                                         if (cobra_y[0][i] == boss_ataque_y && cobra_x[0][i] == boss_ataque_x) {
                                                             if (boss_ataque_refletor == true) {
                                                                 boss_ataque_refletor_ponto = (short) i;
                                                                 cobra_empurrada = true;
                                                                 boss_ataque_x++;
                                                                 boss_ataque_timer_alvo = 30;
                                                                 break;
                                                             }
                                                             cobra_tamanho[0] = (short)(i-1);
                                                             for (i = (short)(cobra_tamanho[0]+1); i <= tempShrt; i++) {
                                                                  y = cobra_y[0][i]; x = cobra_x[0][i];
                                                                  cobra[y][x] = false;
                                                                  cobra_caida[y][x] = true;
                                                             }
                                                             if (cobra_tamanho[0] <= 4)
                                                                 lasado = true;
                                                             if (num_tiros > 0) {
                                                                 num_tiros -= tempShrt-cobra_tamanho[0];
                                                                 if (num_tiros < 0)
                                                                     num_tiros = 0;
                                                                 biscoitos_comidos = 0;
                                                             }
                                                             break;
                                                         }
                                                    }
                                                }
                                                else
                                                  if (x == biscoito_x && y == biscoito_y) {
                                                      biscoito_destruido = true;
                                                      g.setColor(fundo_cor);
                                                      g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
                                                  }
                                                  else
                                                    if (((obstaculo[y][x] == true || (y == cobra_tiro_y && x == cobra_tiro_x)) && boss_ataque_tipo.equals("laser")) || x == 0) {
                                                        /*if boss_ataque_tipo == 1*/
                                                        if (obstaculo[y][x] == true) {
                                                            g.setColor(obstaculo_cor);
                                                            c = obstaculo_pixel;
                                                            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                                                        }
                                                        boss_ataque[y][x] = false;
                                                        boss_ataque_y = (short) ((boss_corpo_y[0]-quadro_y+11)/11);
                                                        boss_ataque_x = (short) ((boss_corpo_x[0]-quadro_x)/11);
                                                        boss_ataque_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
                                                        boss_ataque_count++;
                                                        if (boss_ataque_refletor == true) {
                                                            boss_ataque_refletor = false;
                                                            interromper_boss_movimento = false;
                                                            g.setColor(fundo_cor);
                                                            g.fillRect(boss_x-20,boss_y,5,60);
                                                        }
                                                        /*else
                                                        //redesenhar
                                                        */   
                                                    }
                                                    else
                                                      if (escolha[y][x] == true) {
                                                          escolha[y][x] = false;
                                                          g.setColor(fundo_cor);
                                                          g.fillRect(quadro_x+((x-1)*11),quadro_y+(y*11),11,11);
                                                      }    
                                            }    
                                        }
                                    }
                                }
                            };
                            t.scheduleAtFixedRate(lsr,/*500*/0,atraso_boss_ataque);
                        }
                    }
                    TimerTask efeitoT = new TimerTask() {  
                        @Override
                        public void run() {
                            if (interromper_efeito == false)
                                timerEfeito(g);
                        }
                    }; t.schedule(efeitoT,83,83);
                
                }//escolher tipos apropriados para as variaveis: height, width
                if (cobra_tiro_disparado == true) {
                    boss_x = (short)(boss_corpo_x[0]); boss_x2 = (short)(boss_corpo_x[3]);
                    boss_y = (short)(boss_corpo_y[1]); boss_y2 = (short)(boss_corpo_y[5]);
                    cobraTiro(g);
                }
                if (gerado_perguntas == false)
                    timer_perguntas += atraso;
                if (/*cobra_movimento_iniciado == true &&*/ (nivel == 4 || nivel == 5 || boss_impacto == true))
                    moverObstaculos(g);
                TimerTask enter = new TimerTask() {//pause depende disso
                    @Override
                    public void run() {
                        try {
                            Robot rob = null;
                            rob = new Robot();
                            rob.keyPress(KeyEvent.VK_ENTER);
                            rob.keyRelease(KeyEvent.VK_ENTER);
                        } catch (AWTException ex) {
                            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                        try {
                            Thread.sleep(atraso); //original: 80
                        } catch (InterruptedException ex) {
                            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                        }
                    }; 
                };
                t.schedule(enter,400,20);//original: 400, altered because sharp turns were not triggering

                tempStr = ("m"+ler.nextLine());
                tempChr = tempStr.charAt(tempStr.length()-1);
                switch (tempChr) {
                    case 112 -> {
                        pausar(g);
                        tempChr = 0;
                        /*if (efeito != 0)
                            tempShrt = timer_resposta;
                        else
                            tempShrt = efeito_timer;*/
                        while (tempChr != 112 && tempChr != 113) {
                            
                            tempStr = "m"+ler.nextLine().toLowerCase();
                            tempChr = tempStr.charAt(tempStr.length()-1);
                            System.out.println("loop");
                        }
                        System.out.println("saiu: "+tempChr);
                        if (tempChr == 113) {
                            salvarProgresso(score);
                            Menu menu = new Menu();
                        }
                        else {
                            resumir(g);
                            interromper_efeito = false;
                            interromper_boss_movimento = false;
                            interromper_boss_ataque = false;
                        }
                        /*if (efeito != 0)
                            timer_resposta = tempShrt;
                        else
                            efeito_timer = tempShrt;*/
                    }
                    case 48 -> {
                        if (num_tiros > 0 && cobra_tiro_disparado == false && boss == true) {//0
                            cobra_tiro_disparado = true;
                            num_tiros--;
                            y = cobra_y[0][cobra_tamanho[0]]; x = cobra_x[0][cobra_tamanho[0]];
                            cobra[y][x] = false;
                            livre[y][x] = true;
                            cobra_tamanho[0]--;
                            g.setColor(fundo_cor);
                            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                            cobra_tiro_direcao = cobra_direcao[0];
                            cobra_tiro_x = cobra_x[0][1];
                            cobra_tiro_y = cobra_y[0][1];
                            switch (cobra_tiro_direcao) {
                                case 119 -> cobra_tiro_y -= 3;//w
                                case 97 -> cobra_tiro_x -= 3;//a
                                case 115 -> cobra_tiro_y += 3;//s
                                case 100 -> cobra_tiro_x += 3;//d
                            }
                            cobra_tiro_alpha = 0;
                            if (Math.random()*10 >= 8 && interromper_boss_movimento == false && interromper_boss_ataque == false)
                                boss_defesa = true;
                            cobraTiro(g);
                        }
                    }
                    case 32 -> {
                        if (cobra_presa == true)
                            System.out.println("clicks: "+(++num_clicks));
                    }
                }
                for (id = 0; id < num_jogadores; id++) { 
                    if (cobra_derrotada[id] == false && cobra_empurrada == false) {
                        for (j = 0; j < tempStr.length(); j++) {
                            tempChr = tempStr.charAt(j);
                            if (direcaoValida(cobra_direcao) == true) {
                                cobra_direcao[id] = tempChr;
                                break;
                            }    
                        }
                        switch (cobra_direcao[id]) {
                            case 119,105 : {cobra_y[id][0] = (byte) (cobra_y[id][1] - 1);//W
                                            cobra_x[id][0] = cobra_x[id][1]; //cobra_movimento_iniciado = true;
                                            break;}
                            case 97, 106 : {cobra_x[id][0] = (byte) (cobra_x[id][1] - 1);//A
                                           cobra_y[id][0] = cobra_y[id][1]; //cobra_movimento_iniciado = true;
                                           break;}
                            case 115,107 : {cobra_y[id][0] = (byte) (cobra_y[id][1] + 1);//S
                                            cobra_x[id][0] = cobra_x[id][1]; //cobra_movimento_iniciado = true;
                                            break;}
                            case 100,108 : {cobra_x[id][0] = (byte) (cobra_x[id][1] + 1);//D
                                            cobra_y[id][0] = cobra_y[id][1]; //cobra_movimento_iniciado = true;
                                            break;}
                        }
                    }
                    if (efeito == 4) {
                        Efeitos(g); 
                        x = cobra_x[0][0]; y = cobra_y[0][0];
                    }
                    else
                      if (efeito == 1 || efeito == 7 || efeito == 12) {
                          efeito_timer += 68; //constante
                          if (efeito_timer >= efeito_timer_alvo) {
                              efeito_timer = 0;
                              Efeitos(g);
                          }
                      }
                    y = cobra_y[id][0]; x = cobra_x[id][0];
                    if (obstaculo[y][x] == true && efeito != 5 && efeito != 6)
                        obstaculado = true;
                    //if (boss_ataque[y][x] == true && efeito != 6 && boss_ataque_refletor == false)
                    //    lasado = true;
                    if ((efeito != 6 && (cobra[y][x] == true || rival[y][x] == true || cobra_caida[y][x] == true || baleado == true || minado == true || lasado == true || obstaculado == true || envenenado == true)) || (y == 0 || y == height+1 || x == 0 || x == width+1)) {
                        if (num_jogadores > 1)
                            vencedor = analisarVencedor(y,x);
                         else
                           perdeu = true; //cancel schedule
                    }
                    else {
                       g.setColor(fundo_cor);
                       if (efeito != 6)
                           livre[y][x] = false;
                       cobra[y][x] = true;
                       g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                       if (efeito == 5 && obstaculo[y][x] == true) //destroi os obstaculos que estao juntos
                           Efeitos(g);
                       y = cobra_y[id][cobra_tamanho[id]]; x = cobra_x[id][cobra_tamanho[id]];
                       cobra[y][x] = false;
                       if (efeito == 6 && livre[y][x] == false)
                           Efeitos(g);
                        else {
                           livre[y][x] = true;
                           g.setColor(fundo_cor);
                           g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                        }
                       g.setColor(Color.white);
                       if (livre[y][x] == true) {
                           g.drawString("T",quadro_x+((x-1)*11),quadro_y+(y*11));
                           System.out.println("alrigthy");
                       }
                       else {
                          g.drawString("F",quadro_x+((x-1)*11),quadro_y+(y*11));
                          System.out.println("nopy");
                       }
                       for (j = (short) (cobra_tamanho[id]-1); j >= 0; j--) {
                           cobra_x[id][j+1] = cobra_x[id][j];
                           cobra_y[id][j+1] = cobra_y[id][j];
                       }
                       switch (efeito) {
                           case 5 -> g.setColor(cobra_bomba_cor);
                           case 6 -> g.setColor(cobra_fantasma_cor);
                           default -> g.setColor(cobra_cor);
                       }
                       c = cobra_pixel;
                       x = cobra_x[id][1]; y = cobra_y[id][1];
                       if (cobra_redonda == false)
                           g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                        else
                          g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                       if ((efeito == 10 && efeito_timer >= efeito_timer_alvo) || efeito == 11)
                            Efeitos(g);

                       if (num_tiros > 0) {
                           g.setColor(cobra_tiro_cor);
                           c = cobra_pixel;
                           for (i = cobra_tamanho[0]; i > cobra_tamanho[0]-num_tiros; i--) {
                               if (cobra_redonda == false)
                                   g.fillRect(quadro_x+((cobra_x[0][i]-1)*11),quadro_y+((cobra_y[0][i]-1)*11),c,c);
                                else
                                  g.fillOval(quadro_x+((cobra_x[0][i]-1)*11),quadro_y+((cobra_y[0][i]-1)*11),c,c); 
                           }
                       }
                       if (x == biscoito_x && y == biscoito_y) {
                           comido = true;
                           cobra_tamanho[0]++;
                           cobra_x[id][cobra_tamanho[id]] = cobra_x[id][cobra_tamanho[id]-1];
                           cobra_y[id][cobra_tamanho[id]] = cobra_y[id][cobra_tamanho[id]-1];
                           sco += 200*duplicador;
                           
                           System.out.println("duplicador: "+duplicador);
                           score = Integer.toString(sco);
                           g.setColor(fundo_cor);
                           g.fillRect(quadro_x+comp2+85,quadro_y+5,55,11);
                           g.setColor(Color.white);
                           g.drawString(score,quadro_x+comp2+90,quadro_y+16);
                           g.setColor(cobra_cor);
                           gerado_obstaculos = false;

                           if (boss == true) {
                               biscoitos_comidos++;
                               if (biscoitos_comidos == 5 && num_tiros <= cobra_tamanho[0]-4) {
                                   biscoitos_comidos = 0;
                                   num_tiros++;
                               }
                           }
                       }
                       else 
                         if (escolha[y][x] == true) {                      
                             g.setColor(fundo_cor);
                             g.fillRect(quadro_x-320,quadro_y-11,300,95);
                             timer_resposta = 0;
                             for (i = 0; i <= 2; i++) {
                                  if (x == escolha_x[i] && y == escolha_y[i]) {
                                      escolhido = (byte)(i);
                                      for (j = 0; j <= 2; j++) {
                                          escolha[y][x] = false;
                                          livre[y][x] = true;
                                          if (i != j) {
                                             y = escolha_y[j]; x = escolha_x[j];
                                             g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                          }
                                      }
                                      break;
                                   }
                             }
                             if (escolhido == resposta) {
                                 efeito = (byte)((Math.random()*6)+1);
                                 g.setColor(new Color(31,204,198)); }
                              else {
                                efeito = (byte)((Math.random()*6)+7);
                                g.setColor(Color.red);
                             }
                             efeito = 6;
                             desenharEfeito(g);
                             desenharBarra(g);
                             if (efeito == 2 || efeito == 3 || efeito == 8 || efeito == 9)
                                 Efeitos(g);
                             if (efeito == 5 || efeito == 6) {
                                 if (efeito == 5)
                                     g.setColor(cobra_bomba_cor);
                                  else
                                    g.setColor(cobra_fantasma_cor);
                                 c = cobra_pixel;
                                 for (i = 1; i <= cobra_tamanho[id]; i++) {
                                      y = cobra_y[id][i]; x = cobra_x[id][i];
                                      if (cobra_redonda == false)
                                          g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                                       else
                                         g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),c,c);
                                 }
                             }
                         }
                   }
                }
                replay_cobra += cobra_direcao[0];
                for (id = 0; id < num_rivais; id++) {   
                    if (rival_derrotado[id] == false)
                        moverRival(g,rival_cor);
                    if (rival_biscoitos_comidos == 3 && id-- == 0)
                        rival_biscoitos_comidos = 0;
                }
            }//while fecha aqui
            t.cancel();
            if (perdeu == true) {
                g.setColor(Color.red);
                g.drawString("Game Over",quadro_x+comp2+larg+44,quadro_y+170);
            }
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("./Jogador/estatisticas.txt"));
                writer.write(++num_jogos);
                writer.newLine();
                writer.write(scores+sco);
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (sco > high && nivel == 0) {//store in file
                highscore = score;
                g.setColor(fundo_cor);
                g.fillRect(quadro_x+comp2+120,quadro_y-15,55,15);
                g.setColor(Color.white);
                g.drawString(highscore,quadro_x+comp2+120,quadro_y-larg);
                try {
                    writer = new BufferedWriter(new FileWriter("./Jogador/jogador.txt"));
                    writer.write(highscore);
                    writer.newLine();
                    writer.write(replay_cobra);
                    writer.newLine();
                    writer.write(replay_biscoito);
                    writer.newLine();
                    writer.write(replay_obs_add);
                    writer.newLine();
                    writer.write(replay_obs_num);
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            g.setColor(Color.yellow);
            g.drawString("Reiniciar? (r/n)",quadro_x+comp2+larg+44,quadro_y+200);
            g.setColor(Color.green);
            if (perdeu == false && (nivel < 7 || (nivel < 10 && dificuldade.equals("hard"))))
                g.drawString("Prosseguir? (p/n)",quadro_x+comp2+larg+44,quadro_y+230);
            if (nivel == 7 && boss_derrotado == true) {
                g.setColor(Color.green);
                g.drawString("Parabens",quadro_x+comp2+larg+44,quadro_y+180);
            }
            tempStr = "m"+ler.nextLine().toLowerCase();
            tempChr = tempStr.charAt(tempStr.length()-1);
            reiniciar = (tempChr == 114 || tempChr == 112);
            if (tempChr == 112 && (nivel < 7 || (nivel < 10 && dificuldade.equals("hard"))))
                nivel++;
            //}
            g.setColor(Color.black);
            g.fillRect(0,0,1260,1260);
            removerEfeito(g);
            try {
                Thread.sleep(30);
            } catch (InterruptedException ex) {
                Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while(reiniciar == true);
        System.exit(0);
    }

    @Override
    public void keyTyped(KeyEvent k) {
        System.out.println("entrou");
        System.out.println(k.getKeyCode());
        System.out.println("Aqui: "+(k.getKeyChar() == 32));
        System.out.println(k.getKeyCode() == 32);
    }

    @Override
    public void keyPressed(KeyEvent k) {
        System.out.println("entrou");
        System.out.println(k.getKeyCode());
        System.out.println("Aqui: "+(k.getKeyChar() == 32));
        System.out.println(k.getKeyCode() == 32);
    }

    @Override
    public void keyReleased(KeyEvent k) {
        System.out.println("entrou");
        System.out.println(k.getKeyCode());
        System.out.println("Aqui: "+(k.getKeyChar() == 32));
        System.out.println(k.getKeyCode() == 32);
    }
    
    public void bin() {
        /*
        case 6: boolean fantasma = true; 
                    for (i = 1; i <= height; i++) {
                        for (j = 1; j <= width; j++) {
                            if (redesenhar[i][j] == true/* && redesenhar[i][j] == true) {*/
                                //if (obstaculo[i][j] == true) {
                                    //g.setColor(obstaculo_cor);
                                   // c = obstaculo_pixel;
                                /*}
                                else {
                                  g.setColor(cobra_cor);
                                  c = cobra_pijel;  
                                } 
                                g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),c,c);
                                redesenhar[i][j] = false;
                            } 
                        }
                    }*/
        //quadro
        //vertical
        /*if (nivel == 0) {
            g.fillRect(quadro_x,quadro_y,larg,comp+100);
            g.fillRect(quadro_x+comp2,quadro_y,larg,comp+100);
        }
        else {
            g.fillRect(quadro_x,quadro_y,larg,comp);
            g.fillRect(quadro_x+comp2,quadro_y,larg,comp);
        }
        //horizontal
        g.fillRect(quadro_x,quadro_y,comp2,larg);
        g.fillRect(quadro_x,quadro_y+comp,comp2+larg,larg);*/
        /*
        if (escolha[y+i][x] == true)
                                                                         escolha[y+i][x] = false;
                                                                       else
                                                                         if (obstaculo[y+i][x] == true)
                                                                             obstaculo[y+i][x] = false;
                                                                          else
                                                                            if (x == biscoito_y && y+i == biscoito_y)
                                                                                biscoito_destruido = true;
                                                                             else
                                                                               if (cobra_caida[y+i][x] == true)
                                                                                   cobra_caida[y+i][x] = false;
                                                                                else
                                                                                  if (cobra[y+i][x] == true && efeito != 6) {//usado muitas vezes - metodo
                                                                                      tempShrt = cobra_tamanho[0];
                                                                                      for (i2 = 1; i2 <= tempShrt; i2++) {
                                                                                           if (cobra_y[0][i2] == y+i && cobra_x[0][i2] == x) {
                                                                                               cobra_tamanho[0] = (short)(i2-1);
                                                                                               for (i2 = (short)(cobra_tamanho[0]+1); i2 <= tempShrt; i2++) {
                                                                                                    y = cobra_y[0][i2]; x = cobra_x[0][i2];
                                                                                                    cobra[y][x] = false;
                                                                                                    cobra_caida[y][x] = true;
                                                                                               }
                                                                                               if (cobra_tamanho[0] <= 4)
                                                                                                   lasado = true;
                                                                                               if (num_tiros > 0) {
                                                                                                   num_tiros -= tempShrt-cobra_tamanho[0];
                                                                                                   if (num_tiros < 0)
                                                                                                       num_tiros = 0;
                                                                                                   biscoitos_comidos = 0;
                                                                                               }
                                                                                               break;
                                                                                           }
                                                                                      }
                                                                                  }*/
    }
    
    public static void iniciarFicheiros() {
        BufferedReader reader;
        BufferedWriter writer;
        String[] ficheiros = {"jogador","configuracoes","movimento","biscoito"};
        String[][] conteudos = {{"0"},{"normal","11","11","false","11","10","11"},{""},{""}};
        for (int i = 0; i < ficheiros.length; i++) {
            try {
                reader = new BufferedReader(new FileReader("./Jogador/"+ficheiros[i]+".txt"));
            } catch (FileNotFoundException ex) {
                try {
                    writer = new BufferedWriter(new FileWriter("./Jogador/"+ficheiros[i]+".txt"));
                    for (int j = 0; j < conteudos[i].length; j++) {
                        writer.write(conteudos[i][j]);
                        writer.newLine();
                    }
                    
                    writer.close();
                } catch (IOException ex1) {
                    Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, "too bad", ex1);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        String algo = "";
        iniciarFicheiros();
        JogoDeCobra jdc = new JogoDeCobra(algo);
    }

}
