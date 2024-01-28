package jogodecobra;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class JogoDeCobra extends JFrame implements KeyListener {

    public JogoDeCobra() {
        setTitle("Desenhos");
        setSize(1260,1260);
        setVisible(true);
        setBackground(fundo_cor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JogoDeCobra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public JButton b, proximoNivel, sair, reiniciarButton;
    public ImageIcon icon;
    public JLabel efeitoImg;
    public JFrame frame;

    public String[] alternativa = new String[3];
    public String pergunta, boss_ataque_tipo, tempStr, dificuldade;
    public byte[][] cobra_x = new byte[4][301];
    public byte[][] cobra_y = new byte[4][301];
    public boolean[][] cobra, rival, cobra_caida, escolha, livre, obstaculo_movido;
    public short[] cobra_tamanho;
    boolean[][] boss_ataque;
    public byte[][] obstaculo_direcao;
    public int i, i2, j, j2, sco, tempInt, c, inicio, fim;
    public char cobra_tiro_direcao, tempChr;
    public short boss_turbina_y, boss_turbina_x, boss_ataque_x, boss_ataque_y, boss_ataque_offset_y, atraso_boss_movimento, atraso_boss_ataque, boss_ataque_beam_length, boss_movimento_timer, boss_vida_alpha, cobra_tiro_alpha, boss_x, boss_x2, boss_y, boss_y2, boss_ataque_refletor_ponto;
    public short x, y, x2, y2, quadro_x, quadro_y, comp, comp2;
    public byte larg, width, height, biscoito_x, biscoito_y, boss_vida, boss_vida_limite, cobra_tiro_dano, biscoitos_comidos, num_jogadores;  
    public short atraso, rival_tamanho, num_tiros, timer_perguntas, timer_resposta, timer_barra_efeito, timer_minas, alpha, boss_ataque_count, boss_ataque_timer, boss_ataque_timer_alvo, tempShrt;
    public byte nivel, numero, efeito, resposta, boss_direcao, loop, num_grupos, linha1, linha2, cobra_tiro_y, cobra_tiro_x, alvo_y, alvo_x, num_clicks;
    public boolean boss_movimento_iniciado, boss_ataque_refletor, gerado_perguntas, feito, noturno, noturnado, boss, boss_derrotado, boss_impacto, biscoito_destruido, lasado, obstaculado, interromper_boss_movimento, interromper_boss_ataque, apagar, invalido, reagendar_boss_movimento, reagendar_boss_ataque;
    public boolean cobra_tiro_disparado, reiniciar, envenenado, cobra_movimento_iniciado, gerado_obstaculos, cobra_empurrada, cobra_presa;
    public float duplicador, boss_impacto_velocidade;
    public Color fundo_cor = Color.black;
    public Color cobra_cor = new Color(0,255,160);
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
    
    public byte[] rival_y = new byte[61];
    public byte[] rival_x = new byte[61];
    public byte[] prioridade = new byte[5];
    public byte[] check_y = new byte[61];
    public byte[] check_x = new byte[61];
    public byte[] check_direcao = new byte[61];
    public boolean[][] check_invalido = new boolean[61][5];
    public boolean[][] checkpoint;
    public byte num_check, anterior, atual, pos_y, pos_x, inicio_y, inicio_x, fim_y, rival_direcao, rival_anterior;
    public boolean rival_desistiu, rival_derrotado, permissao;
    
        
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
    public void Efeitos(Graphics g) {

        g.fillRect(0,0,0,0);

        switch (efeito) {
            case 1: sco += 200; break;
            case 2: duplicador = 2; break;//jump
            case 3: atraso *= 2; break; //lentidao
            case 4: {
                    if (y == 0)
                        cobra_y[0][0] = height;
                     else
                       if (y == height+1)
                           cobra_y[0][0] = 1;
                        else
                          if (x == 0)
                              cobra_x[0][0] = width;
                           else
                             if (x == width+1)
                                 cobra_x[0][0] = 1;
                    break;
            }
            case 5: boolean bomba = true; break;
            case 6: boolean fantasma = true; break;

            case 7: sco -= 200; break;
            case 8: duplicador = (float)(0.5);
            case 9: atraso /= 2; break; //rapidez
            case 10: boolean minas = true; break;
            case 11: noturno = true; break;//fix snake head still appearing. Hint: move if efeito 10 to under snake movement
            case 12: cobra_tamanho[0]--;
                     num_tiros--;
                     if (num_tiros < 0) 
                         num_tiros = 0;
                     if (cobra_tamanho[0] == 4) 
                         envenenado = true;
                     break;
        }
    }
    public boolean direcaoValida(char[] cobra_direcao) {
        switch (tempChr) {
            case 97,100,115,119 : return i == 0 && abs(cobra_direcao[i]-tempChr) > 4;
            case 105,106,107,108 : return i == 1 && abs(cobra_direcao[i]-tempChr) != 2;
        }
        return false;
    }
    /*
    public void desenharEfeito() {
        switch (efeito) {
            case 1: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/+200.png");break;
            case 2: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Score x2.png");break;
            case 3: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Velocidade--.png");break;
            case 4: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Portal.png");break;
            case 5: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Cobra_bomba.png");break;
            case 6: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Fantasma.png");break;
            case 7: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/-200.png");break;
            case 8: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Score /2.png");break;
            case 9: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Velocidade++.png");break;
            case 10: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Minas.png");break;
            case 11: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Noturno.png");break;
            case 12: icon = new ImageIcon("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/Efeitos/Veneno.png");break;
        }
        efeitoImg = new JLabel(icon);
        efeitoImg.setBounds(866,592,48,48);
        //efeitoImg.setBackground(null);
        add(efeitoImg);
    }
    public void removerEfeito() {
        remove(efeitoImg);
        frame.repaint();
    }*/

    public void iniciarCobra(Graphics g) {
        byte t = 5;       
        for (i = 0; i < num_jogadores; i++) {
            tempShrt = 4;
            for (j = 7; j >= 1; j--) {
                cobra_x[i][j] = (byte)(tempShrt++);
                x = cobra_x[i][j];
                if (nivel != 5 && nivel != 6) {
                    cobra_y[i][j] = t;
                    livre[t][x] = false;
                    cobra[t][x] = true;
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((t-1)*11),11,11);
                }
                else {
                    cobra_y[0][j] = 20;
                    livre[20][x] = false;
                    cobra[20][x] = true;
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+(19*11),11,11);
                }
            }
            t = 32;
        }
        /*cobra_tamanho[0] = 7;
        c = cobra_tamanho[0];
        x2 = 41;
        for (i = 1; i <= 5; i++) {
            cobra_y[0][i] = (byte) (5+i);
            cobra_x[0][i] = (byte)(x2);
        }
        for (j = 1; j <= 4; j++) {
            cobra_y[0][5+j] = 10;
            cobra_x[0][5+j] = (byte)(x2+j);
        }
        for (i = 1; i <= 26; i++) {
            cobra_y[0][9+i] = (byte)(10+i);
            cobra_x[0][9+i] = (byte)(x2+4);
        }
        for (i = 1; i <= c; i++) {
            System.out.println(i+": cobra_y: "+cobra_y[0][i]+", cobra_x: "+cobra_x[0][i]);
            y = cobra_y[0][i]; x = cobra_x[0][i];
            livre[y][x] = false;
            cobra[y][x] = true;
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
        }
        
        cobra_empurrada = true;
        sco = 600;
        boss_ataque_tipo = "refletor";
        boss_ataque_refletor = true;
        boss_ataque_refletor_ponto = (short) c;
        c = boss_ataque_refletor_ponto;
        boss_ataque_y = cobra_y[0][c];
        y = cobra_y[0][c]; x = cobra_x[0][c];
        g.setColor(Color.pink);
        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
        */
    }
    
    public void iniciarRival(Graphics g, Color rival_cor) {
        g.setColor(rival_cor);
        tempShrt = (short)(width-4);
        for (i = 7; i >= 1; i--) {
            rival_x[i] = (byte)(tempShrt--);
            x = rival_x[i];
            rival_y[i] = (byte)(height-4);
            livre[height-4][x] = false;
            rival[height-4][x] = true;
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((height-5)*11),11,11);
        }
    }
    
    public int abs(int diferenca) {
        if (diferenca < 0)
            diferenca = (byte)(-1*diferenca);
        return diferenca;
    }
    
    public void formarGrupos() {
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
        if (width-10 <= cobra_tiro_x && (boss_y-quadro_y)/11 <= cobra_tiro_y && cobra_tiro_y <= (boss_y2-quadro_y)/11 && cobra_tiro_disparado == true) {
            boss_ataque_refletor = true;
            interromper_boss_movimento = true;
            g.setColor(boss_ataque_refletor_cor1);
            g.fillRect(boss_x-20,boss_y,5,60);
            if (cobra_tiro_x == (boss_x-quadro_x-20)/11) {
                cobra_tiro_disparado = false;
                boss_ataque_tipo = "refletor";
                boss_ataque_y = cobra_tiro_y;
                boss_ataque_x = cobra_tiro_x--;
            }
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
                if (x != 62 && x != 63)
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                if (0 < y && y <= height && 0 < x && x <= width)
                    livre[y][x] = false;
                if ((quadro_x+(x*11)) > boss_turbina_x+40) {
                    cobra_tiro_alpha += 25;
                    g.setColor(new Color(0,0,0,cobra_tiro_alpha));
                    if (x != 62 && x != 63)
                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                }
              }
        if (0 < y && y <= height && 0 < x && x <= width) {
            if (escolha[y][x] == true)                
                escolha[y][x] = false;
             else
               if (y == biscoito_y && x == biscoito_x)
                   biscoito_destruido = true;
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
                } while (i != fim);
            }
            y = cobra_y[0][c]; x = cobra_x[0][c];
            cobra[y][x+1] = false;
            y = cobra_y[0][1]; x = cobra_x[0][1];
            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
            if (x == 1) {
                cobra_presa = true;
                boss_ataque_timer_alvo = 0;
            }
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
            for (i = 1; i <= height; i++)
                for (j = 1; j <= width; j++) {
                    if (obstaculo[i][j] == true) {
                       if (cobra_caida[i][j] == true)
                           cobra_caida[i][j] = false;
                        else
                          if (cobra[i][j] == true) {
                              tempShrt = cobra_tamanho[0];
                              for (int i2 = 1; i2 <= tempShrt; i2++) {
                                   if (cobra_y[0][i2] == i && cobra_x[0][i2] == j) {
                                       cobra_tamanho[0] = (short)(i2-1);
                                       for (i2 = (short)(cobra_tamanho[0]+1); i2 <= tempShrt; i2++) {
                                            y = cobra_y[0][i2]; x = cobra_x[0][i2];
                                            cobra[y][x] = false;
                                            cobra_caida[y][x] = true;
                                       }
                                       if (cobra_tamanho[0] <= 4)
                                           obstaculado = true;
                                       break;
                                    }
                               }
                          }
                          else
                            if (i == biscoito_y && j == biscoito_x)
                                biscoito_destruido = true;
                    }
                    obstaculo_movido[i][j] = false;
                }    
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
                          if (escolha[i][j+j2] == true) //torne isso num metodo
                              escolha[i][j+j2] = false;
                           else
                             if (i == biscoito_y && j+j2 == biscoito_x)
                                 biscoito_destruido = true;
                              else
                                if (cobra_caida[i][j+j2] == true)
                                    cobra_caida[i][j+j2] = false;
                                 else
                                   if (cobra[i][j+j2] == true && efeito != 6 && (efeito != 5 || (efeito == 5 && (i != cobra_y[0][1] || j+j2 != cobra_x[0][1])))) {//usado muitas vezes - metodo
                                       tempShrt = cobra_tamanho[0];
                                       for (int i2 = 1; i2 <= tempShrt; i2++) {
                                            if (cobra_y[0][i2] == i && cobra_x[0][i2] == j+j2) {
                                                cobra_tamanho[0] = (short)(i2-1);
                                                for (i2 = (short)(cobra_tamanho[0]+1); i2 <= tempShrt; i2++) {
                                                     y = cobra_y[0][i2]; x = cobra_x[0][i2];
                                                     cobra[y][x] = false;
                                                     cobra_caida[y][x] = true;
                                                }
                                                if (cobra_tamanho[0] <= 4)
                                                    obstaculado = true;
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
            formarGrupos();
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
        check_y[0] = rival_y[1];
        check_x[0] = rival_x[1];
        //check_invalido[0][atual < 3? atual+2 : atual-2] = true;
        permissao = true;
        rival_desistiu = false;
        rival_anterior = rival_direcao;
        check_direcao[0] = rival_direcao;
        atual = rival_direcao;
        pos_x = (byte) rival_x[1];
        pos_y = (byte) rival_y[1];
        //alvo_y = cobra_y[0][0];
        //alvo_x = cobra_x[0][0];
        alvo_y = biscoito_y;
        alvo_x = biscoito_x;
        g.setColor(Color.orange);
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
            if (y == biscoito_y && x == biscoito_x) {
                rival_y[++rival_tamanho] = rival_y[rival_tamanho-1];
                rival_x[rival_tamanho] = rival_x[rival_tamanho-1];
                biscoito_destruido = true;
                gerado_obstaculos = false;
            }
        }    
            
    }
        
    public void paint(Graphics g) {
        String highscore, score, scores = "";
        int high, num_jogos = 0;
        highscore = "0"; tempStr = "0";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("jogador.txt"));
            highscore = reader.readLine();
            reader.close();
            reader = new BufferedReader(new FileReader("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/estatisticas.txt"));
            String tempStr = reader.readLine();
            num_jogos = Integer.parseInt(tempStr);
            scores = reader.readLine();
            reader.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        high = Integer.parseInt(highscore);
        nivel = 0;
        reiniciar = false;
        do {
            Timer t = new Timer();
            Scanner ler = new Scanner(System.in);
            int meta;
            short[] obstaculo_x = new short[201];
            short[] obstaculo_y = new short[201];
            byte[] escolha_x = new byte[3];       
            byte[] escolha_y = new byte[3];
            short[] reescrever_x = new short[61];
            short[] reescrever_y = new short[61];
            cobra_tamanho = new short[4];
            char[] cobra_direcao = new char[4];
            String replay_cobra, replay_biscoito, replay_obs_add, replay_obs_num;
            short num_obstaculos, resto, timer_efeito;
            float tempFlt;
            byte pixel, campo, escolhido, num_perguntas, time, min, max, minas_y, minas_x, diametro;
            boolean lenda, perdeu, comido, /*gerado_obstaculos,*/ mesmo_grupo, minado;
            score = "0";
            cobra_direcao[0] = "d".charAt(0); cobra_direcao[1] = "d".charAt(0); tempChr = "d".charAt(0); replay_cobra = ""; replay_biscoito = ""; replay_obs_add = ""; replay_obs_num = "";
            quadro_x = 440; quadro_y = 150; pixel = 11; comp = 420; comp2 = 508; larg = 6; meta = 0;
            atraso = 68; timer_perguntas = 0; timer_resposta = 0; efeito = 0; timer_barra_efeito = 0; minas_y = 0; minas_x = 0;
            boss_vida = 50; boss_vida_limite = 50; boss_direcao = 1; boss_movimento_timer = 0; boss_ataque_count = 0; boss_ataque_beam_length = 0; boss_ataque_timer = 0; boss_ataque_timer_alvo = 0; boss_ataque_tipo = "laser"; atraso_boss_movimento = 15; boss_vida_alpha = 0; cobra_tiro_alpha = 0; atraso_boss_ataque = 5;
            sco = 0; num_grupos = 0; num_obstaculos = 0; cobra_tamanho[0] = 7; cobra_tamanho[1] = 7; rival_tamanho = 7; tempFlt = 0; biscoitos_comidos = 0; timer_efeito = 0; num_tiros = 0; num_jogadores = 1; num_clicks = 0; inicio = 0; fim = 0;
            biscoito_x = 0; biscoito_y = 0; escolhido = -1; alpha = 5; diametro = 0; duplicador = 1; loop = 0; j2 = -1; inicio_x = 0; inicio_y = 0; fim_y = 0; atual = 1; rival_direcao = 3;
            perdeu = false; comido = true; biscoito_destruido = false; gerado_obstaculos = false; gerado_perguntas = false; minado = false; feito = false; noturno = false; noturnado = false; apagar = false; envenenado = false; cobra_movimento_iniciado = false;
            boss = false; boss_derrotado = false; rival_derrotado = true; boss_impacto = false; boss_movimento_iniciado = false; lasado = false; obstaculado = false; interromper_boss_movimento = false; interromper_boss_ataque = false; reagendar_boss_movimento = true; reagendar_boss_ataque = true; boss_ataque_refletor = false;
            
            g.setColor(Color.white);     
            //Font custom = new Font("Roboto",1,15);
            //g.setFont(custom);
            resto = (short) ((comp-larg) % 11); comp -= resto;
            resto = (short) ((comp2-larg) % 11); comp2 -= resto;
            //vertical
            if (nivel == 0) {
                g.fillRect(quadro_x,quadro_y,larg,comp+100);
                g.fillRect(quadro_x+comp2,quadro_y,larg,comp+100);
            }
            else {
                g.fillRect(quadro_x,quadro_y,larg,comp);
                g.fillRect(quadro_x+comp2,quadro_y,larg,comp);
            }
            //horizontal
            g.fillRect(quadro_x,quadro_y,comp2,larg);
            g.fillRect(quadro_x,quadro_y+comp,comp2+larg,larg);
            if (nivel == 0) {
                g.fillRect(quadro_x,quadro_y+comp+100,comp2+larg,larg);
                //espaco da barra
                g.fillRect(quadro_x+larg+20,quadro_y+comp+23,comp2-130,59);
                g.fillRect(quadro_x+comp2-80,quadro_y+comp+23,59,59);
                g.setColor(Color.black);
                g.fillRect(quadro_x+larg+26,quadro_y+comp+29,comp2-142,47);
                g.fillRect(quadro_x+comp2-75,quadro_y+comp+29,48,47);
            }

            g.setColor(Color.white);
            if (nivel == 0) {
                g.drawString("Highscore:",quadro_x+comp2+larg+44,quadro_y);
                g.drawString(highscore,quadro_x+comp2+larg+115,quadro_y);
            }
            else
              g.drawString("Meta:",quadro_x+comp2+larg+44,quadro_y);
            g.drawString("Score:   0",quadro_x+comp2+larg+44,quadro_y+22);
            //g.drawString(score,quadro_x+comp2+larg+85,quadro_y+22);
            quadro_x += larg; quadro_y += larg;

            Color rival_cor = new Color(255,25,160);
            Color cobra_bomba_cor = new Color(76,44,18);
            Color biscoito_cor = Color.yellow;
            Color minas_cor = Color.red;
            Color boss_turbina_cor = Color.cyan;
            Color boss_corpo_cor = Color.red;

            g.setColor(cobra_cor);
            width = (byte) (comp2/11);
            height = (byte) (comp/11);
            cobra = new boolean[height+2][width+2];
            rival = new boolean[height+2][width+2];
            cobra_caida = new boolean[height+2][width+2];
            livre = new boolean[height+2][width+2];
            obstaculo = new boolean[height+2][width+2];
            obstaculo_grupo = new byte[height+2][width+2]; //indica o grupo de um obstaculo
            obstaculo_direcao = new byte[height+2][width+2];
            obstaculo_movido = new boolean[height+2][width+2];
            boolean[][] redesenhar = new boolean[height+2][width+2];
            escolha = new boolean[height+2][width+2];
            boolean[][] minas = new boolean[height+2][width+2];
            boss_ataque = new boolean[height+2][width+2];
            checkpoint = new boolean[height+2][width+2];
            c = quadro_x+comp2;
            int[] boss_corpo_x = {c+67,c+82,c+112,c+127,c+112,c+82,c+67};
            c = quadro_y;
            int[] boss_corpo_y = {c+210,c+185,c+185,c+210,c+235,c+235,c+210};
            boss_turbina_x = (short)(boss_corpo_x[2]-15); boss_turbina_y = (short)(boss_corpo_y[2]);
            boss_ataque_x = (short)((boss_corpo_x[0]-quadro_x)/11); boss_ataque_y = (short)((boss_corpo_y[0]-quadro_y)/11); boss_ataque_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);

            dificuldade = "easy";
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
                    meta = 2000;
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
                    if (dificuldade.equals("hard"))
                        meta = 2000;
                    break;
                }
                case 7: {
                    boss = true;
                    break;
                }
                case 8: {
                    cobra_tamanho[0] = 90;
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
            if (nivel != 7)
                g.drawString(Integer.toString(meta),quadro_x+comp2+larg+78,quadro_y-7);
             else 
               g.drawString("derrote o boss",quadro_x+comp2+larg+78,quadro_y-7);
               
            iniciarCobra(g);
            //iniciarRival(g, rival_cor);
            System.out.println("width: "+width);
            System.out.println("height: "+height);
            g.setColor(cobra_cor);
            while((perdeu == false && nivel == 0) || (perdeu == false && (sco < meta || (nivel == 7 && boss_derrotado == false)))) {

                if (comido == true || biscoito_destruido == true) {
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

                        if (gerado_obstaculos == false && num_obstaculos < 200) /*&& (nivel == 3)*/ {
                            num_obstaculos++;
                            do { //gera obstaculos;
                                obstaculo_y[num_obstaculos] = (short) ((Math.random() * height) + 1);
                                obstaculo_x[num_obstaculos] = (short) ((Math.random() * width) + 1);                   
                                y = obstaculo_y[num_obstaculos];
                                x = obstaculo_x[num_obstaculos];
                            } while (livre[y][x] == false);
                            obstaculo[y][x] = true;
                            livre[y][x] = false;
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
                            formarGrupos();
                            gerado_obstaculos = true;
                        }
                        if (timer_perguntas >= 20000 && gerado_perguntas == false) {
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
                                     invalido = false;
                                     escolha_y[i] = (byte) ((Math.random()*height)+1);
                                     escolha_x[i] = (byte) ((Math.random()*width)+1);
                                     y = escolha_y[i];
                                     x = escolha_x[i];
                                     if (livre[y][x] == false)
                                         invalido = true;
                                } while (invalido == true);
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
                            g.fillRect(quadro_x+26,quadro_y+comp+23,comp2-142,47);
                            g.setColor(Color.white);
                            for (i = 0; i <= 11; i++) {
                                 int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                                 int py[] = {641,592,592,641};
                                 g.fillPolygon(px,py,px.length);
                            }
                            g.setColor(cobra_cor);
                        }
                        if (timer_barra_efeito == 147 && escolhido != -1) {
                            g.setColor(fundo_cor);
                            g.fillRect(quadro_x+comp2+40,quadro_y+25,300,95);
                        }
                    }
                    if (boss_derrotado == false && sco == 600/*0*/ && (reagendar_boss_movimento == true || reagendar_boss_ataque == true)) {//movimento do boss
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
                                                  if (boss_ataque_count % 3/*0*/ == 0 && boss_ataque_count != 0) {
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
                                    boss_ataque_timer_alvo = 100;
                                    if (interromper_boss_ataque == false && boss_ataque_timer >= boss_ataque_timer_alvo) {
                                        boss_ataque_timer = 0;
                                        y = boss_ataque_y; x = boss_ataque_x;
                                        if (boss_ataque_refletor == false) {
                                            if (boss_ataque_count % 30 == 0 && boss_ataque_count != 0) {
                                                interromper_boss_ataque = true;
                                                atraso_boss_movimento = 10;}
                                            else
                                              if (boss_ataque_count % 15 == 0 && boss_ataque_count != 0) {
                                                  if (loop < 6) {
                                                      boss_ataque_tipo = "beam";
                                                      interromper_boss_movimento = true; //t.wait on mov?
                                                      reagendar_boss_ataque = true;
                                                      //boss_ataque_timer += 20;
                                                      if (boss_ataque_timer >= 500) {
                                                          boss_ataque_timer = 0;
                                                          if (loop % 2 == 0)
                                                              g.setColor(boss_ataque_laser_cor);
                                                           else
                                                             g.setColor(fundo_cor);
                                                          g.drawLine(quadro_x+1,boss_corpo_y[0],boss_corpo_x[0],boss_corpo_y[0]);
                                                          loop++;
                                                          if (loop == 6) {
                                                              atraso_boss_ataque = 50;
                                                              boss_ataque_timer_alvo = 0;
                                                              reagendar_boss_ataque = true;
                                                          }    
                                                      }
                                                  }
                                                  if (loop >= 6) {
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
                                                          loop = 0;
                                                          boss_ataque_timer = 0;
                                                          boss_ataque_count++;
                                                          boss_ataque_beam_length = 0;
                                                          atraso_boss_movimento = 1000;
                                                          interromper_boss_movimento = false;
                                                          apagar = false;
                                                      }
                                                      /*g.setColor(fundo_cor);
                                                      g.fillRect(quadro_x,quadro_y,width*11,height*11);
                                                      for (i = 1; i <= height; i++) {
                                                          for (j = 1; j <= width; j++) {
                                                              g.setColor(Color.white);
                                                              if (boss_ataque[i][j] == true)
                                                                  g.drawString("T",quadro_x+((j-1)*11),quadro_y+(i*11));
                                                               else
                                                                 g.drawString("F",quadro_x+((j-1)*11),quadro_y+(i*11));
                                                          }
                                                      }*/
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
                                                                                  }
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
                                                                 //boss_ataque_timer_alvo = 60;
                                                                 boss_ataque_timer_alvo = 1000;
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
                                                            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                                        }
                                                        boss_ataque[y][x] = false;
                                                        boss_ataque_y = (short) ((boss_corpo_y[0]-quadro_y+11)/11);
                                                        boss_ataque_x = (short) ((boss_corpo_x[0]-quadro_x)/11);
                                                        boss_ataque_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
                                                        boss_ataque_count++;
                                                        if (boss_ataque_refletor == true) {
                                                            boss_ataque_refletor = false;
                                                            interromper_boss_movimento = false;
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
                }//escolher tipos apropriados para as variaveis: height, width
                TimerTask enter = new TimerTask() {  
                    @Override
                    public void run() {
                        if (cobra_tiro_disparado == true) {
                            boss_x = (short)(boss_corpo_x[0]); boss_x2 = (short)(boss_corpo_x[3]);
                            boss_y = (short)(boss_corpo_y[1]); boss_y2 = (short)(boss_corpo_y[5]);
                            cobraTiro(g);
                        }
                        if (cobra_movimento_iniciado == true && (nivel == 4 || nivel == 5 || boss_impacto == true))
                            moverObstaculos(g);
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
                        if (nivel == 0) {                
                            if (gerado_perguntas == true && efeito == 0) {
                                g.setColor(fundo_cor);
                                g.fillRect(831-(2*timer_resposta),quadro_y+comp+23,timer_resposta,48);
                                timer_resposta++;
                                if (timer_resposta == 200) { //os 15 segundos pra responder acabaram
                                    timer_resposta = 0;
                                    gerado_perguntas = false;
                                    g.fillRect(quadro_x-320,quadro_y-11,300,95);
                                    for (i = 0; i <= 2; i++) {
                                         y = escolha_y[i];
                                         x = escolha_x[i];
                                         g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                         livre[y][x] = true;
                                         escolha[y][x] = false;
                                    }
                                    efeito = (byte)((Math.random()*6)+7);
                                    if (efeito == 9)
                                        Efeitos(g);
                                    g.setColor(Color.red);
                                    g.fillRect(quadro_x+26,quadro_y+comp+23,comp2-142,47);
                                    g.setColor(Color.white);
                                    for (i = 0; i <= 11; i++) {
                                        int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                                        int py[] = {641,592,592,641};
                                        g.fillPolygon(px,py,px.length);
                                    }
                                }    
                            }
                            else
                              if (efeito != 0) { //so ativa se de fato tiver um efeito ativo
                                  g.setColor(fundo_cor);
                                  g.fillRect(831-timer_barra_efeito,quadro_y+comp+23,timer_barra_efeito,48);
                                  timer_barra_efeito++;
                                  if (timer_barra_efeito == 444) { //30 segundos de duracao
                                      timer_barra_efeito = 0;
                                      efeito = 0;
                                      atraso = 68;
                                      duplicador = 1;
                                  }
                                  gerado_perguntas = false;
                              }  
                            if (gerado_perguntas == false)
                                timer_perguntas += atraso;
                        }
                        if (nivel == 0 || nivel == 8) {
                            if (alpha >= 250) {
                                noturnado = noturnado == false;
                                alpha = 5;
                            }
                        }               
                    }; 
                };
                t.schedule(enter,400,20);//original: 400, altered because sharp turns were not triggering

                tempStr = ("m"+ler.nextLine());
                tempChr = tempStr.charAt(tempStr.length()-1);
                switch (tempChr) {
                    case 112 -> {
                        interromper_boss_movimento = true; //p
                        interromper_boss_ataque = true;
                        tempChr = 0;
                        if (efeito != 0)
                            tempShrt = timer_resposta;
                        else
                            tempShrt = timer_efeito;
                        while (tempChr != 112) {
                            TimerTask enter2 = new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException ex) {
                                        java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                    try {
                                        Robot rob = null;
                                        rob = new Robot();
                                        rob.keyPress(KeyEvent.VK_ENTER);
                                        rob.keyRelease(KeyEvent.VK_ENTER);
                                    } catch (AWTException ex) {
                                        java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                    }
                                }
                            };
                            t.scheduleAtFixedRate(enter2,1000,1000);
                            tempStr = (ler.nextLine() + "m").toLowerCase();
                            tempChr = tempStr.charAt(0);
                            enter2.cancel();
                        }
                        interromper_boss_movimento = false;
                        interromper_boss_ataque = false;
                        if (efeito != 0)
                            timer_resposta = tempShrt;
                        else
                            timer_efeito = tempShrt;
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
                            cobraTiro(g);
                        }
                    }
                    case 32 -> {
                        if (cobra_presa == true)
                            System.out.println("clicks: "+(++num_clicks));
                    }
                }
                if (cobra_empurrada == false) {
                    for (i = 0; i < num_jogadores; i++) { 
                        for (j = 0; j < tempStr.length(); j++) {
                            tempChr = tempStr.charAt(j);
                            if (direcaoValida(cobra_direcao) == true) {
                                cobra_direcao[i] = tempChr;
                                break;
                            }    
                        }
                        switch (cobra_direcao[i]) {
                            case 119,105 : {cobra_y[i][0] = (byte) (cobra_y[i][1] - 1);//W
                                            cobra_x[i][0] = cobra_x[i][1]; cobra_movimento_iniciado = true;
                                            break;}
                            case 97, 106 : {cobra_x[i][0] = (byte) (cobra_x[i][1] - 1);//A
                                           cobra_y[i][0] = cobra_y[i][1]; cobra_movimento_iniciado = true;
                                           break;}
                            case 115,107 : {cobra_y[i][0] = (byte) (cobra_y[i][1] + 1);//S
                                            cobra_x[i][0] = cobra_x[i][1]; cobra_movimento_iniciado = true;
                                            break;}
                            case 100,108 : {cobra_x[i][0] = (byte) (cobra_x[i][1] + 1);//D
                                            cobra_y[i][0] = cobra_y[i][1]; cobra_movimento_iniciado = true;
                                            break;}
                        }
                    }
                    replay_cobra += cobra_direcao[0];
                }
                if (rival_derrotado == false)
                    moverRival(g,rival_cor);
                if (efeito == 4) {
                    Efeitos(g); 
                    x = cobra_x[0][0]; y = cobra_y[0][0];
                }
                else
                  if (efeito == 1 || efeito == 7 || efeito == 12) {
                      timer_efeito += 68;
                      if (timer_efeito >= 3000) {
                          timer_efeito = 0;
                          Efeitos(g);
                      }
                  }
                x = cobra_x[0][0]; y = cobra_y[0][0];
                g.setColor(cobra_cor);
                if (boss_ataque[y][x] == true && efeito != 6 && boss_ataque_refletor == false)
                    lasado = true;
                if (cobra_empurrada == false && (((cobra[y][x] == true || rival[y][x] == true || cobra_caida[y][x] == true || minado == true || lasado == true || obstaculado == true || envenenado == true || (obstaculo[y][x] == true && efeito != 5))) && efeito != 6) || (y == 0 || y == height+1 || x == 0 || x == width+1)) {
                    perdeu = true; //cancel schedule
                }
                 else {
                   livre[y][x] = false;
                   g.setColor(fundo_cor);
                   if (efeito == 5 && obstaculo[y][x] == true) { //destroi os obstaculos que estao juntos
                       tempInt = obstaculo_grupo[y][x];
                       for (i = 1; i <= height; i++) {
                          for (j = 1; j <= width; j++) {
                               if (obstaculo_grupo[i][j] == tempInt) {
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
                       timer_barra_efeito = 443;
                       System.out.println("timer_barra_efeitosssssssssssssssssssssssssss: "+timer_barra_efeito);
                       g.setColor(cobra_cor);
                       for (i = 1; i <= cobra_tamanho[0]; i++) {
                           y = cobra_y[0][i]; x = cobra_x[0][i];
                           g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                       }

                   }
                   if (cobra_empurrada == false) {
                       g.setColor(fundo_cor);
                       for (i = 0; i < num_jogadores; i++) {
                           x = cobra_x[i][cobra_tamanho[i]]; y = cobra_y[i][cobra_tamanho[i]];
                           g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                           livre[y][x] = true;
                           cobra[y][x] = false;
                   }
                   }
                   if (efeito == 6) {
                       if (obstaculo[y][x] == true)
                           redesenhar[y][x] = true;
                       g.setColor(obstaculo_cor);
                       for (y = 1; y <= height; y++) {
                           for (x = 1; x <= width; x++) {
                               if (redesenhar[y][x] == true && cobra[y][x] == false) {
                                   g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                   redesenhar[y][x] = false;
                               } 
                           }
                       }  g.setColor(fundo_cor);
                       x = cobra_x[0][cobra_tamanho[0]];
                       y = cobra_y[0][cobra_tamanho[0]];
                   }         
                   else 
                     if (efeito == 10) {
                        if (minas_y == 0) {
                            do {invalido = false;
                                diametro = (byte) ((Math.random()*11) + 5);
                                minas_y = (byte) ((Math.random()*height) + 1);
                                minas_x = (byte) ((Math.random()*width) + 1);
                                if ((minas_x+diametro) > width || (minas_y+diametro) > height) 
                                    invalido = true;
                            } while (invalido == true);
                            for (i = minas_y; i <= (minas_y+diametro); i++) {
                                for (j = minas_x; j <= (minas_x+diametro); j++) {
                                    if (i <= height && j <= width)
                                        minas[i][j] = true;
                                }
                            }
                        }
                        else {
                          g.setColor(new Color(255,0,0,alpha));
                          g.fillOval(quadro_x+((minas_x-1)*11),quadro_y+((minas_y-1)*11),diametro*11,diametro*11);
                          tempFlt += 255/(12000/atraso);
                          alpha = (short) (tempFlt);
                          if (alpha >= 255) {
                              tempShrt = cobra_tamanho[0];
                              if (efeito != 6) {
                                  for (i = 1; i <= tempShrt; i++) {
                                       y = cobra_y[0][i]; x = cobra_x[0][i];
                                       if (minas[y][x] == true) {
                                           cobra_tamanho[0] = (short)(i-1);
                                           for (i = (short)(cobra_tamanho[0]+1); i <= tempShrt; i++) {
                                                y = cobra_y[0][i]; x = cobra_x[0][i];
                                                cobra[y][x] = false;
                                                cobra_caida[y][x] = true;
                                           }
                                           if (cobra_tamanho[0] <= 4)
                                               minado = true;
                                           if (num_tiros > 0) {
                                               num_tiros -= tempShrt-cobra_tamanho[0];
                                               if (num_tiros < 0)
                                                   num_tiros = 0;
                                               biscoitos_comidos = 0;
                                           }
                                           break;
                                       }    
                                  }
                                  if (minas[biscoito_y][biscoito_x] == true)
                                      biscoito_destruido = true;
                              }
                              for (i = minas_y; i <= (minas_y+diametro); i++) {
                                  for (j = minas_x; j <= (minas_x+diametro); j++) {
                                      if (i <= height && j <= width) {
                                          if (obstaculo[i][j] == true)
                                              g.setColor(obstaculo_cor);
                                           else
                                             if (cobra[i][j] == true || cobra_caida[i][j] == true)
                                                 g.setColor(cobra_cor);
                                              else
                                                g.setColor(fundo_cor);
                                          g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                          minas[i][j] = false;
                                      }         
                                  }
                              }
                              tempFlt = 0;
                              alpha = 0;
                              minas_y = 0; minas_x = 0;
                          }
                        }    
                     }
                     else
                       if (efeito == 11) {
                           alpha += 4;
                           if (noturnado == false) {
                                g.setColor(new Color(0,0,0,alpha));
                                g.fillRect(quadro_x,quadro_y,width*11,height*11);
                           }
                           else
                             for (i = 1; i <= height; i++) {
                                 for (j = 1; j <= width; j++) {
                                     if (obstaculo[i][j] == true) {
                                         g.setColor(new Color(255,0,0,alpha));
                                         g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                     }
                                     else
                                       if (cobra[i][j] == true || cobra_caida[i][j] == true) {
                                           g.setColor(new Color(0,255,160,alpha));
                                           g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                       }
                                       else
                                         if (biscoito_x == j && biscoito_y == i) {
                                             g.setColor(new Color(255,255,0,alpha));
                                             g.fillOval(quadro_x+((j-1)*11),quadro_y+((i-1)*11),10,10);
                                         }
                                 }
                             }
                       }
                   if (cobra_empurrada == false) {
                       for (i = 0; i < num_jogadores; i++) {
                            for (j = (short) (cobra_tamanho[i]-1); j >= 0; j--) {
                                cobra_x[i][j+1] = cobra_x[i][j];
                                cobra_y[i][j+1] = cobra_y[i][j];
                            }
                            x = cobra_x[i][1]; y = cobra_y[i][1];
                            
                            //System.out.println("cobra_y: "+cobra_y[0][1]+", cobra_x: "+cobra_x[0][1]);
                            livre[y][x] = false;
                            cobra[y][x] = true;
                            if (efeito != 5)
                                g.setColor(cobra_cor);
                             else
                               g.setColor(cobra_bomba_cor); 
                            g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                       }
                   }
                   if (num_tiros > 0) {
                       g.setColor(cobra_tiro_cor);
                       for (i = cobra_tamanho[0]; i > cobra_tamanho[0]-num_tiros; i--) {
                           g.fillRect(quadro_x+((cobra_x[0][i]-1)*11),quadro_y+((cobra_y[0][i]-1)*11),11,11);
                       }
                   }

                   if ((x == biscoito_x)&&(y == biscoito_y)) {
                      comido = true;
                      cobra_tamanho[0]++;
                      cobra_x[0][cobra_tamanho[0]] = cobra_x[0][cobra_tamanho[0]-1];
                      cobra_y[0][cobra_tamanho[0]] = cobra_y[0][cobra_tamanho[0]-1];
                      sco += 200*duplicador;
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
                                        y = escolha_y[j]; 
                                        x = escolha_x[j];
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
                          efeito = 10;//jump
                          //desenharEfeito();
                          g.fillRect(quadro_x+26,quadro_y+comp+23,comp2-142,47);
                          g.setColor(Color.white);
                          for (i = 0; i <= 11; i++) {
                               int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                               int py[] = {641,592,592,641};
                               g.fillPolygon(px,py,px.length);
                          }
                          if (efeito == 3 || efeito == 9)
                              Efeitos(g);
                          if (efeito == 5) {
                              g.setColor(cobra_bomba_cor);
                              for (i = 1; i <= cobra_tamanho[0]; i++) {
                                   y = cobra_y[0][i]; x = cobra_x[0][i];
                                   g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);                                  
                              }
                          }        
                      }
                }
            }//while fecha aqui
            t.cancel();
            if (perdeu == true) {
                g.setColor(Color.red);
                g.drawString("Game Over",quadro_x+comp2+larg+44,quadro_y+170);
            }
            BufferedWriter writer;
            try {
                writer = new BufferedWriter(new FileWriter("/home/timana/Documents/3 Semestre/FP/Trabalho Semestral/estatisticas.txt"));
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
                    writer = new BufferedWriter(new FileWriter("jogador.txt"));
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
            g.setColor(Color.green);
            //if (nivel == 0) {
            g.setColor(Color.yellow);
            g.drawString("Reiniciar? (r/n)",quadro_x+comp2+larg+44,quadro_y+200);
            g.setColor(Color.green);
            if (perdeu == false && (nivel < 7 || (nivel < 10 && dificuldade.equals("hard"))))
                g.drawString("Prosseguir? (p/n)",quadro_x+comp2+larg+44,quadro_y+230);
            tempStr = "m"+ler.nextLine().toLowerCase();
            tempChr = tempStr.charAt(tempStr.length()-1);
            reiniciar = (tempChr == 114 || tempChr == 112);
            if (tempChr == 112 && (nivel < 7 || (nivel < 10 && dificuldade.equals("hard"))))
                nivel++;
            //}
            g.setColor(Color.black);
            g.fillRect(0,0,1260,1260);
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
    
    public static void main(String[] args) {
        //Menu menu = new Menu();
        JogoDeCobra jdc = new JogoDeCobra();
    }

}
