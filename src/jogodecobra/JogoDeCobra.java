package jogodecobra;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class JogoDeCobra extends JFrame {
        
    public JogoDeCobra() {
        setTitle("Desenhos");
        setSize(1260,1260);
        setVisible(true);
        setBackground(fundo_cor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public JFrame frame;
    public JPanel panel;
    public JTextField text;
    
    public String[] alternativa = new String[3];
    public String pergunta, boss_laser_tipo;
    public byte[] cobra_x = new byte[101];
    public byte[] cobra_y = new byte[101];
    public int sco, tempInt;
    public char cobra_tiro_direcao;
    public short boss_turbina_y, boss_turbina_x, boss_laser_x, boss_laser_y, boss_laser_offset_y, atraso_boss_movimento, atraso_boss_laser, boss_laser_beam_length, boss_movimento_timer, boss_vida_alpha, cobra_tiro_alpha;
    public short x, y, x2, y2, quadro_x, quadro_y, comp, comp2;
    public byte larg, width, height, biscoito_x, biscoito_y, boss_vida, boss_vida_limite, cobra_tiro_dano, biscoitos_comidos; 
    public short atraso, tamanho, num_tiros, timer_perguntas, timer_resposta, timer_efeito, timer_minas, alpha, boss_laser_count, boss_laser_timer, tempShrt;
    public byte numero, efeito, resposta, duplicador, boss_direcao, loop, j2, num_grupos, linha1, linha2, cobra_tiro_y, cobra_tiro_x;
    public boolean boss_movimento_iniciado, gerado_perguntas, feito, noturno, noturnado, boss, boss_derrotado, boss_laser, boss_impacto, destruido, lasado, obstaculado, interromper_boss_movimento, interromper_boss_laser, interromper_cobra_tiro, apagar, invalido, reagendar_boss_movimento, reagendar_boss_laser;
    public boolean cobra_tiro_disparado;
    public Color fundo_cor = Color.black;
    public Color boss_laser_simples_cor = Color.red;
    public Color boss_laser_perfurador_cor = new Color(255,255,127);
    public Color boss_laser_refletidor_cor = Color.orange;
    public Color boss_laser_beam_cor1 = Color.white;
    public Color boss_laser_beam_cor2 = new Color(255,255,127);
    public byte[][] obstaculo_grupo;
    
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
        
        switch (efeito) {//jump
            case 1: duplicador = 2;
            case 2: atraso *= 2 ; //lentidao
            case 3: {
                    if (y == 0)
                        cobra_y[0] = height;
                     else
                       if (y == height+1)
                           cobra_y[0] = 1;
                        else
                          if (x == 0)
                              cobra_x[0] = width;
                           else
                             if (x == width+1)
                                 cobra_x[0] = 1;
                    break;
            }
            case 4: boolean bomba = true;
            case 5: boolean fantasma = true;
            case 6: atraso /= 2 ; //rapidez
            case 7: sco -= 2000; 
            case 8: boolean minas = true;
            case 9: noturno = true; //fix snake head still appearing. Hint: move if efeito 9 to under snake movement
        }
    }
    public void paint(Graphics g) {
        String highscore, score;
        int high;
        highscore = "0";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"));
            highscore = reader.readLine();
            reader.close();
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        high = Integer.parseInt(highscore);
        boolean reiniciar;
    do {
        Timer t = new Timer();
        Scanner ler = new Scanner(System.in);
        short[] obstaculo_x = new short[61];
        short[] obstaculo_y = new short[61];       
        short[] escolha_x = new short[3];       
        short[] escolha_y = new short[3];
        short[] reescrever_x = new short[61];
        short[] reescrever_y = new short[61];
        short i, j, num_obstaculos;
        short resto;
        float tempFlt;
        byte nivel, pixel, campo, escolhido, num_perguntas, time, min, max, minas_y, minas_x, diametro;
        boolean lenda, perdeu, comido, gerado_obstaculos, mesmo_grupo, minado;
        char cobra_direcao, tempChr;
        String tempStr;
        score = "0";
        cobra_direcao = "d".charAt(0); tempChr = "d".charAt(0);
        atraso = 68; timer_perguntas = 0; timer_resposta = 0; efeito = 0; timer_efeito = 0; minas_y = 0; minas_x = 0;
        sco = 0; num_grupos = 0; num_obstaculos = 0; tamanho = 7; tempFlt = 0; biscoitos_comidos = 0;
        quadro_x = 440; quadro_y = 150; pixel = 11; comp = 420; comp2 = 508; larg = 6;
        boss_vida = 30; boss_vida_limite = 30; boss_direcao = 1; boss_movimento_timer = 0; boss_laser_count = 0; boss_laser_beam_length = 0; boss_laser_timer = 0; boss_laser_tipo = "simples"; atraso_boss_movimento = 15; boss_vida_alpha = 0; cobra_tiro_alpha = 0; atraso_boss_laser = 20;
        biscoito_x = 0; biscoito_y = 0; escolhido = -1; alpha = 5; diametro = 0; duplicador = 1; loop = 0; j2 = -1;
        perdeu = false; comido = true; destruido = false; gerado_obstaculos = false; gerado_perguntas = false; minado = false; feito = false; noturno = false; noturnado = false; apagar = false;
        boss = false; boss_derrotado = false; boss_laser = false; boss_impacto = false; boss_movimento_iniciado = false; lasado = false; obstaculado = false; interromper_boss_movimento = false; interromper_boss_laser = false; interromper_cobra_tiro = false; reagendar_boss_movimento = true; reagendar_boss_laser = true;
        
        g.setColor(Color.white);     
        //g.setFont(custom);
        resto = (short) ((comp-larg) % 11); comp -= resto;
        resto = (short) ((comp2-larg) % 11); comp2 -= resto;
        //vertical
        g.fillRect(quadro_x,quadro_y,larg,comp);
        g.fillRect(quadro_x+comp2,quadro_y,larg,comp);
        //horizontal
        g.fillRect(quadro_x,quadro_y,comp2,larg);
        g.fillRect(quadro_x,quadro_y+comp,comp2+larg,larg);
        g.fillRect(quadro_x,quadro_y+comp+100,comp2+larg,larg);
        //espaco da barra
        g.fillRect(quadro_x+larg+20,quadro_y+comp+25,comp2-larg-41,59);
        g.setColor(Color.black);
        g.fillRect(quadro_x+larg+26,quadro_y+comp+31,comp2-larg-52,48);              
        g.setColor(Color.white);
        g.drawString("Highscore:",quadro_x+comp2+larg+44,quadro_y);
        g.drawString(highscore,quadro_x+comp2+larg+115,quadro_y);
        g.drawString("Score:",quadro_x+comp2+larg+44,quadro_y+22);
        g.drawString(score,quadro_x+comp2+larg+85,quadro_y+22);

        quadro_x += larg; quadro_y += larg;
        
        Color cobra_cor = new Color(0,255,160);
        Color cobra_bomba_cor = new Color(76,44,18);
        Color cobra_tiro_cor = new Color(185,170,227);
        Color biscoito_cor = Color.yellow;
        Color obstaculo_cor = Color.red;
        Color minas_cor = Color.red;
        Color boss_turbina_cor = Color.cyan;
        Color boss_corpo_cor = Color.red;
        
        g.setColor(cobra_cor);
        width = (byte) (comp2/11);
        height = (byte) (comp/11);
        boolean[][] cobra = new boolean[height+2][width+2];
        boolean[][] cobra_caida = new boolean[height+2][width+2];
        boolean[][] livre = new boolean[height+2][width+2];
        boolean[][] obstaculo = new boolean[height+2][width+2];
        obstaculo_grupo = new byte[height+2][width+2]; //indica o grupo de um obstaculo
        byte[][] obstaculo_direcao = new byte[height+2][width+2];
        boolean[][] redesenhar = new boolean[height+2][width+2];
        boolean[][] escolha = new boolean[height+2][width+2];
        boolean[][] minas = new boolean[height+2][width+2];
        boolean[][] boss_laser = new boolean[height+2][width+2];
        int c = quadro_x+comp2;
        int boss_corpo_x[] = {c+67,c+82,c+112,c+127,c+112,c+82,c+67};
        c = quadro_y;
        int boss_corpo_y[] = {c+210,c+185,c+185,c+210,c+235,c+235,c+210};
        boss_turbina_x = (short)(boss_corpo_x[2]-15); boss_turbina_y = (short)(boss_corpo_y[2]);
        boss_laser_x = (short)((boss_corpo_x[0]-quadro_x)/11); boss_laser_y = (short)((boss_corpo_y[0]-quadro_y)/11); boss_laser_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
        
        for (i = 0; i <= height+1; i++) {
          for (j = 0; j <= width+1; j++) {
             if (i == 0 || i == height+1 || j == 0||j == width+1)
                livre[i][j] = false;
              else
                livre[i][j] = true;   
           }
        }
        tempShrt = 4;
        for (i = 7; i >= 1; i--) {
            cobra_x[i] = (byte)(tempShrt++);
            cobra_y[i] = 5;
            x = cobra_x[i];
            livre[5][x] = false;
            cobra[5][x] = true;
            g.fillRect(quadro_x+((x-1)*11),quadro_y+(4*11),10,10);
        }
        while(perdeu == false) {
            
            if (comido == true || destruido == true) {
               do { //gera biscoito
                   invalido = false;
                   biscoito_x = (byte) ((Math.random() * width) + 1);
                   biscoito_y = (byte) ((Math.random() * height) + 1);
                   y = biscoito_y;
                   x = biscoito_x;
                   if (livre[y][x] != true)
                      invalido = true;
               } while (invalido == true);
               livre[y][x] = false;
               g.setColor(biscoito_cor);
               g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
               comido = false;
               if (destruido == true)
                   destruido = false;
            }
            
            if ((sco % 600/*0*/ == 0)&&(sco != 0)) {
                
                if (gerado_obstaculos == false && num_obstaculos < 60) /*&& (nivel == 3)*/ {
                    num_obstaculos++;
                    do { //gera obstaculos;
                        invalido = false;
                        obstaculo_y[num_obstaculos] = (short) ((Math.random() * height) + 1);
                        obstaculo_x[num_obstaculos] = (short) ((Math.random() * width) + 1);                   
                        y = obstaculo_y[num_obstaculos];
                        x = obstaculo_x[num_obstaculos];
                        if (livre[y][x] == false)
                            invalido = true;
                    } while (invalido == true);
                    obstaculo[y][x] = true;
                    livre[y][x] = false;
                    num_grupos++;
                    obstaculo_direcao[y][x] = 1;
                    obstaculo_grupo[y][x] = (byte) (num_grupos);
                    g.setColor(obstaculo_cor);
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                    numero = (byte) ((Math.random() * 4) + 2);
                    campo = 1;
                    for (i = 1; i <= numero; i++) {
                       do {
                           invalido = true;
                           x2 = (short) ((x-campo)+(Math.random()*((campo*2)+1)));
                           y2 = (short) ((y-campo)+(Math.random()*((campo*2)+1)));
                           if ((x2 > 0 && x2 <= width)&&(y2 > 0 && y2 <= height) && num_obstaculos < 60) {
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
                                }
                           }
                       } while (invalido == true); 
                    }
                    formarGrupos();
                    gerado_obstaculos = true;
                }
                if (timer_perguntas >= 2000 && gerado_perguntas == false) {
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
                             escolha_y[i] = (short) ((Math.random()*height)+1);
                             escolha_x[i] = (short) ((Math.random()*width)+1);
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
                    g.fillRect(quadro_x+larg+20,quadro_y+comp+25,comp2-larg-52,48);
                    g.setColor(Color.white);
                    for (i = 0; i <= 14; i++) {
                         int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                         int py[] = {641,592,592,641};
                         g.fillPolygon(px,py,px.length);
                    }
                    g.setColor(cobra_cor);
                }
                if (timer_efeito == 147 && escolhido != -1) {
                    g.setColor(fundo_cor);
                    g.fillRect(quadro_x+comp2+40,quadro_y+25,300,95);
                }                
            }
            if (tamanho == 15) {
                reagendar_boss_movimento = true;
                atraso_boss_movimento = 1;
            }
            if (sco == 200/*0*/ && (reagendar_boss_movimento == true || reagendar_boss_laser == true)) {//movimento do boss
                System.out.println("Aquiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii: "+atraso_boss_movimento);
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
                                          if (boss_laser_count % 30 == 0 && boss_laser_count != 0 && boss_impacto == false) {
                                              if (boss_corpo_y[0] == (int)(quadro_y+(comp/2)) && boss_movimento_iniciado == false) {
                                                  boss_direcao = 3; //right
                                                  boss_movimento_iniciado = true;
                                                  atraso_boss_movimento = 30;
                                                  reagendar_boss_movimento = true;
                                              }
                                               else
                                                 if (boss_corpo_x[3] == quadro_x+comp2+(40*11)) {
                                                     boss_direcao = 4; //left
                                                     atraso_boss_movimento = 3000;
                                                     reagendar_boss_movimento = true;}
                                                 else 
                                                   if ((boss_corpo_x[0] == quadro_x+comp2+20 && boss_impacto == false && boss_direcao == 4) || (boss_corpo_x[0] == quadro_x+comp2+67 && boss_direcao == 3)) {
                                                       if (boss_corpo_x[0] == quadro_x+comp2+20) {
                                                           boss_impacto = true;
                                                           boss_direcao = 0; }//ate 15 segundos se passarem
                                                        else
                                                          if (boss_corpo_x[0] == quadro_x+comp2+67) {
                                                              boss_movimento_timer = 0;
                                                              boss_direcao = (byte)((Math.random()*2)+1);
                                                              interromper_boss_laser = false;
                                                              boss_movimento_iniciado = false;
                                                              atraso_boss_movimento = 1000;
                                                              reagendar_boss_movimento = true;
                                                              boss_vida_alpha = 0;
                                                              boss_laser_count++;
                                                              g.setColor(fundo_cor);
                                                              g.fillRect(boss_corpo_x[0]-8,boss_corpo_y[0]-4,10,8);
                                                              g.fillRect(boss_corpo_x[0]-20,boss_corpo_y[0]-15,15,30);
                                                              /*
                                                              g.setColor(new Color(69,222,227));
                                                              g.fillRoundRect(boss_corpo_x[3]+45,256,15,188,12,12);
                                                              g.setColor(new Color(61,68,179));
                                                              g.fillRoundRect(boss_corpo_x[3]+47,259,11,183,5,5);
                                                              g.setColor(new Color(255,124,30));
                                                              for (int i = (short)(boss_vida-1); i >= 0; i--)
                                                                   g.fillRoundRect(boss_corpo_x[3]+48,262+(i*6),9,4,8,8);*/
                                                       }
                                                   }   
                                          }
                                    if (boss_impacto == false) {
                                        g.setColor(fundo_cor);
                                        g.fillRect(boss_turbina_x,boss_turbina_y,40,10);
                                        g.fillRect(boss_turbina_x,boss_turbina_y+40,40,10);
                                        g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                                        if (boss_direcao == 4 || boss_movimento_timer == 300) {
                                            g.fillRect(boss_corpo_x[0]-8,boss_corpo_y[0]-4,10,8);
                                            g.fillRect(boss_corpo_x[0]-20,boss_corpo_y[0]-15,15,30);
                                        }
                                        if (boss_laser_tipo.equals("beam")) {
                                            if (boss_direcao == 1)
                                                g.drawLine(quadro_x+1,boss_corpo_y[0]+8,boss_corpo_x[0],boss_corpo_y[0]+8);
                                             else
                                               g.drawLine(quadro_x+1,boss_corpo_y[0]-8,boss_corpo_x[0],boss_corpo_y[0]-8);
                                        }   
                                        switch (boss_direcao) {
                                            case 1 -> boss_turbina_y--; 
                                            case 2 -> boss_turbina_y++;
                                            case 3 -> {boss_turbina_x++;/*
                                                       if (boss_vida_alpha < 255 && (boss_direcao == 3 || boss_direcao == 4)) {
                                                           boss_vida_alpha++;
                                                           g.setColor(new Color(0,0,0,boss_vida_alpha));
                                                           g.fillRoundRect(boss_corpo_x[3]+48,256,15,188,12,12);}*/
                                            }
                                            case 4 -> {boss_turbina_x--;
                                                       atraso_boss_movimento -= 6;
                                                       reagendar_boss_movimento = true;}
                                        }
                                        g.setColor(boss_turbina_cor);
                                        g.fillRect(boss_turbina_x,boss_turbina_y,40,10);
                                        g.fillRect(boss_turbina_x,boss_turbina_y+40,40,10);
                                        g.setColor(boss_corpo_cor);
                                        for (int i = 0; i <= 6; i++) {
                                             switch (boss_direcao) {
                                                case 1 -> boss_corpo_y[i]--;
                                                case 2 -> boss_corpo_y[i]++;
                                                case 3 -> boss_corpo_x[i]++;
                                                case 4 -> boss_corpo_x[i]--;
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
                                    if (boss_impacto == true) {//depois de 15 segundos boss_impacto == false;
                                        for (int i = 1; i <= height; i++) {
                                             for (int j = 1; j <= width; j++) {
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
                                                          if (escolha[i][j] == true) //torne isso num metodo
                                                              escolha[i][j] = false;
                                                           else
                                                             if (i == biscoito_y && j+j2 == biscoito_x)
                                                                 destruido = true;
                                                              else
                                                                if (cobra_caida[i][j+j2] == true)
                                                                    cobra_caida[i][j+j2] = false;
                                                                 else
                                                                   if (cobra[i][j+j2] == true && efeito != 5 && (efeito != 4 || (efeito == 4 && (i != cobra_y[1] || j+j2 != cobra_x[1])))) {//usado muitas vezes - metodo
                                                                       tempShrt = tamanho;
                                                                       for (int i2 = 1; i2 <= tempShrt; i2++) {
                                                                            if (cobra_y[i2] == i && cobra_x[i2] == j+j2) {
                                                                                tamanho = (short)(i2-1);
                                                                                for (i2 = (short)(tamanho+1); i2 <= tempShrt; i2++) {
                                                                                     y = cobra_y[i2]; x = cobra_x[i2];
                                                                                     cobra[y][x] = false;
                                                                                     cobra_caida[y][x] = true;
                                                                                }
                                                                                if (tamanho <= 4)
                                                                                    obstaculado = true;
                                                                                if (num_tiros > 0) {
                                                                                    num_tiros -= tempShrt-tamanho;
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
                                                          try {
                                                              Thread.sleep(20);
                                                          } catch (InterruptedException ex) {
                                                              java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                                          }
                                                      }
                                                      else
                                                        if (obstaculo[i][j+1] == true && obstaculo_direcao[i][j+1] == 2) {
                                                            for (j2 = (byte)(j+2); j2 <= width; j2++) {
                                                                if (obstaculo_direcao[i][j2] == 1 || j2 == width+1)
                                                                    break;
                                                                 else
                                                                   if (obstaculo[i][j2] == false) {
                                                                       obstaculo[i][j] = false;
                                                                       obstaculo_direcao[i][j] = 0;
                                                                       g.setColor(fundo_cor);
                                                                       g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                                                       obstaculo[i][j2] = true;
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
                                            boss_direcao = 3;
                                        }
                                    }
                                }    
                            }
                            else {
                              boss = false;
                              boss_derrotado = true;
                              sco += 3000; //quanto mais rapido maior o score
                              //t.cancel();
                            }
                        }
                    };
                    t.scheduleAtFixedRate(mov,atraso_boss_movimento,atraso_boss_movimento);
                }
                if (reagendar_boss_laser == true) {
                    reagendar_boss_laser = false;
                    TimerTask lsr = new TimerTask() {
                        public void run() {
                            if (interromper_boss_laser == false) {
                                y = boss_laser_y; x = boss_laser_x;
                                if (boss_laser_count % 30 == 0 && boss_laser_count != 0) {
                                    interromper_boss_laser = true;
                                    atraso_boss_movimento = 10;}
                                else
                                  if (boss_laser_count % 45 == 0 && boss_laser_count != 0) {
                                      if (loop < 6) {
                                          boss_laser_tipo = "beam";
                                          interromper_boss_movimento = true; //t.wait on mov?
                                          atraso_boss_laser = 1600;
                                          reagendar_boss_laser = true;
                                          if (loop % 2 == 0)
                                              g.setColor(boss_laser_simples_cor);
                                           else
                                             g.setColor(fundo_cor);
                                          g.drawLine(quadro_x+1,boss_corpo_y[0],boss_corpo_x[0],boss_corpo_y[0]);
                                          loop++;
                                          if (loop == 6) {
                                              atraso_boss_laser = 50;
                                              reagendar_boss_laser = true;
                                          }    
                                          //t.schedule(mov,5,1);?
                                      }
                                      if (loop >= 6) {
                                          if (boss_laser_beam_length < boss_corpo_x[0]-quadro_x)
                                              boss_laser_beam_length++;
                                          if (boss_laser_timer < 420) {
                                              int boss_laser_beam_x[] = {boss_corpo_x[0],boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_x[0]-boss_laser_beam_length-12,boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_x[0]};
                                              int boss_laser_beam_y[] = {boss_corpo_y[0]-8,boss_corpo_y[0]-8,boss_corpo_y[0],boss_corpo_y[0]+8,boss_corpo_y[0]+8};
                                              g.fillPolygon(boss_laser_beam_x,boss_laser_beam_y,boss_laser_beam_x.length);      
                                              g.setColor(boss_laser_beam_cor1);
                                              g.fillRect(boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_y[0]-6,boss_laser_beam_length,12);
                                          }
                                          else 
                                            if (apagar == true) {
                                                g.setColor(fundo_cor);
                                                g.fillRect(boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_y[0]-8,boss_laser_beam_length,17);  
                                            }//boss movimento tem a sua propria velocidade no schedule
                                          if (boss_laser_beam_length == boss_corpo_x[0]-quadro_x && interromper_boss_movimento == true && apagar == false) {//chegou a parede
                                              interromper_boss_movimento = false;
                                              atraso_boss_movimento = 2000;}
                                           else
                                             if (boss_laser_timer == 420 && interromper_boss_movimento == false) {
                                                 boss_laser_beam_length = 0;
                                                 interromper_boss_movimento = true;
                                                 atraso_boss_movimento = 300;
                                                 apagar = true;
                                                 y = (short)((boss_corpo_y[0]-quadro_y)/11);
                                                 for (int i = 0; i <= 1; i++) {//patch. faca ler somente blocos unicos
                                                     for (int j = 1; j <= width; j++) {
                                                         boss_laser[y+i][j] = true;
                                                         livre[y+i][j] = false;
                                                     }
                                                 }
                                             }
                                             else
                                               if (boss_laser_beam_length == boss_corpo_x[0]-quadro_x && interromper_boss_movimento == false)
                                                   boss_laser_timer++;
                                          //sai aqui
                                          if (boss_laser_beam_length == boss_corpo_x[0]-quadro_x && apagar == true) {
                                              boss_laser_tipo = "simples";
                                              atraso_boss_laser = 900;
                                              reagendar_boss_laser = true;
                                              loop = 0;
                                              boss_laser_timer = 0;
                                              boss_laser_count++;
                                              boss_laser_beam_length = 0;
                                              atraso_boss_movimento = 1000;
                                              interromper_boss_movimento = false;
                                              apagar = false;
                                          }
                                          /*g.setColor(fundo_cor);
                                          g.fillRect(quadro_x,quadro_y,width*11,height*11);
                                          for (int i = 1; i <= height; i++) {
                                              for (int j = 1; j <= width; j++) {
                                                  g.setColor(Color.white);
                                                  if (boss_laser[i][j] == true)
                                                      g.drawString("T",quadro_x+((j-1)*11),quadro_y+(i*11));
                                                   else
                                                     g.drawString("F",quadro_x+((j-1)*11),quadro_y+(i*11));
                                              }
                                          }*/
                                          if (((boss_corpo_x[0]-quadro_x)-boss_laser_beam_length)/11 <= width) {
                                              y = (short)((boss_corpo_y[0]-quadro_y)/11);
                                              if (interromper_boss_movimento == true) {
                                                  x = (short)(((boss_corpo_x[0]-quadro_x)-boss_laser_beam_length)/11);
                                                  linha1 = 0; linha2 = 1;}
                                               else
                                                 if (boss_laser_timer != 420){
                                                     if (boss_direcao == 1) {
                                                         linha1 = 0; linha2 = 2; }
                                                      else {
                                                        linha1 = -1; linha2 = 1; }
                                               }
                                              for (int j = 1; j <= width; j++) {
                                                   if (interromper_boss_movimento == false)
                                                       x = (short)(j);
                                                   for (int i = linha1; i <= linha2; i++) {
                                                        if (i == -1 || i == 2 || boss_laser_timer >= 420) {//torna false os lugares onde o laser ja nao esta
                                                            boss_laser[y+i][x] = false;
                                                            livre[y+i][x] = true;
                                                        }
                                                        else {
                                                          boss_laser[y+i][x] = true;
                                                          livre[y+i][x] = false;
                                                          if (escolha[y+i][x] == true)
                                                             escolha[y+i][x] = false;
                                                           else
                                                             if (obstaculo[y+i][x] == true)
                                                                 obstaculo[y+i][x] = false;
                                                              else
                                                                if (x == biscoito_y && y+i == biscoito_y)
                                                                    destruido = true;
                                                                 else
                                                                   if (cobra_caida[y+i][x] == true)
                                                                       cobra_caida[y+i][x] = false;
                                                                    else
                                                                      if (cobra[y+i][x] == true && efeito != 5) {//usado muitas vezes - metodo
                                                                          tempShrt = tamanho;
                                                                          for (int i2 = 1; i2 <= tempShrt; i2++) {
                                                                               if (cobra_y[i2] == y+i && cobra_x[i2] == x) {
                                                                                   tamanho = (short)(i2-1);
                                                                                   for (i2 = (short)(tamanho+1); i2 <= tempShrt; i2++) {
                                                                                        y = cobra_y[i2]; x = cobra_x[i2];
                                                                                        cobra[y][x] = false;
                                                                                        cobra_caida[y][x] = true;
                                                                                   }
                                                                                   if (tamanho <= 4)
                                                                                       lasado = true;
                                                                                   if (num_tiros > 0) {
                                                                                       num_tiros -= tempShrt-tamanho;
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
                                    if (boss_laser_count % 5 == 0 && boss_laser_count != 0)
                                        boss_laser_tipo = "perfurador";
                                     else
                                       boss_laser_tipo = "simples";
                                if (!(boss_laser_tipo.equals("beam"))) {
                                    if (x <= width) {
                                        boss_laser[y][x] = false;
                                        if (cobra[y][x] == false)
                                            livre[y][x] = true;
                                    }
                                    g.setColor(fundo_cor);
                                    if (boss_laser_tipo.equals("simples"))
                                        g.drawLine(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+boss_laser_offset_y,quadro_x+((x-1)*11)+8,quadro_y+((y-1)*11)+boss_laser_offset_y);
                                     else
                                       g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,6);
                                    boss_laser_x--;
                                    x = boss_laser_x;
                                    if (x <= width) {
                                        boss_laser[y][x] = true;
                                        livre[y][x] = false;
                                    }
                                    if (boss_laser_tipo.equals("simples")) {
                                        g.setColor(boss_laser_simples_cor);
                                        g.drawLine(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+boss_laser_offset_y,quadro_x+((x-1)*11)+8,quadro_y+((y-1)*11)+boss_laser_offset_y);}
                                     else { 
                                       g.setColor(boss_laser_perfurador_cor);
                                       g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,6); 
                                     }

                                    if (x <= width) {//usado no beam tambem. Torne num metodo
                                        if (cobra[y][x] == true && efeito != 5) {
                                            tempShrt = tamanho;
                                            for (int i = 1; i <= tempShrt; i++) {
                                                 if (cobra_y[i] == y && cobra_x[i] == x) {
                                                     tamanho = (short)(i-1);
                                                     for (i = (short)(tamanho+1); i <= tempShrt; i++) {
                                                          y = cobra_y[i]; x = cobra_x[i];
                                                          cobra[y][x] = false;
                                                          cobra_caida[y][x] = true;
                                                     }
                                                     if (tamanho <= 4)
                                                         lasado = true;
                                                     if (num_tiros > 0) {
                                                         num_tiros -= tempShrt-tamanho;
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
                                              destruido = true;
                                              g.setColor(fundo_cor);
                                              g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
                                          }
                                          else
                                            if (((obstaculo[y][x] == true || (y == cobra_tiro_y && x == cobra_tiro_x)) && boss_laser_tipo.equals("simples")) || x == 0) {
                                                /*if boss_laser_tipo == 1*/
                                                if (obstaculo[y][x] == true) {
                                                    g.setColor(obstaculo_cor);
                                                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                                }
                                                boss_laser[y][x] = false;
                                                boss_laser_y = (short) ((boss_corpo_y[0]-quadro_y+11)/11);
                                                boss_laser_x = (short) ((boss_corpo_x[0]-quadro_x)/11);
                                                boss_laser_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
                                                boss_laser_count++;
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
                    t.scheduleAtFixedRate(lsr,/*500*/0,atraso_boss_laser);
                }     
            }
            TimerTask enter = new TimerTask() {  
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
                    if (gerado_perguntas == true && efeito == 0) {
                        g.setColor(fundo_cor);
                        g.fillRect(915-(2*timer_resposta),quadro_y+comp+25,timer_resposta,48);
                        timer_resposta++;
                        if (timer_resposta == 222) { //os 15 segundos pra responder acabaram
                            timer_resposta = 0;
                            gerado_perguntas = false;
                            g.fillRect(quadro_x-320,quadro_y-11,300,95);
                            for (int i = 0; i <= 2; i++) {
                                 y = escolha_y[i];
                                 x = escolha_x[i];
                                 g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                 livre[y][x] = true;
                                 escolha[y][x] = false;
                            }
                            efeito = 40/*(byte)((Math.random()*5)+5)*/;
                            Efeitos(g);
                            //green good, red bad
                            g.setColor(Color.red);
                            g.fillRect(quadro_x+larg+20,quadro_y+comp+25,comp2-larg-52,48);
                            g.setColor(Color.white);
                            for (int i = 0; i <= 14; i++) {
                                int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                                int py[] = {641,592,592,641};
                                g.fillPolygon(px,py,px.length);
                             }
                        }    
                    }
                    else
                      if (efeito != 0) { //so ativa se de fato tiver um efeito ativo
                          g.setColor(fundo_cor);
                          g.fillRect(915-timer_efeito,quadro_y+comp+25,timer_efeito,48);
                          timer_efeito++;
                          if (timer_efeito == 444) { //30 segundos de duracao
                              timer_efeito = 0;
                              efeito = 0;
                          }
                          gerado_perguntas = false;
                      }  
                    if (gerado_perguntas == false)
                        timer_perguntas += atraso;
                    if (feito == false) {
                        if (alpha == 255) {
                            noturnado = noturnado == false;
                            alpha = 5;
                        }
                        /*if (gerado_minas[1] == true || noturno == true)
                            timer_minas += atraso;
                        if (timer_minas >= 700 && alpha < 255) {
                            alpha += 25;
                            timer_minas = 0;
                        }*/
                    }    
                };  
            };
            t.schedule(enter,400,20);//original: 400, altered because sharp turns were not triggering
            tempStr = (ler.nextLine() + "m").toLowerCase();
            tempChr = tempStr.charAt(0);
            switch (tempChr) {
                case 112 : interromper_boss_movimento = true; //p
                            interromper_boss_laser = true;
                            interromper_cobra_tiro = true;
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
                            interromper_boss_laser = false;
                            interromper_cobra_tiro = false;
                            if (efeito != 0)
                                timer_resposta = tempShrt;
                             else 
                               timer_efeito = tempShrt;
                            break;
                case 48 : if (num_tiros > 0 && cobra_tiro_disparado == false && boss == true) {//0
                              cobra_tiro_disparado = true;
                              num_tiros--;
                              cobra_tiro_direcao = cobra_direcao;
                              cobra_tiro_x = cobra_x[1];
                              cobra_tiro_y = cobra_y[1];
                              cobra_tiro_alpha = 255;
                              g.setColor(Color.white);
                              g.drawString("valido",100,300);
                              TimerTask tiro = new TimerTask() {
                                   @Override
                                   public void run() {
                                       g.setColor(Color.white);
                                       g.drawString("entrou",100,500);
                                       if (interromper_cobra_tiro == false) {
                                           g.setColor(fundo_cor);
                                           g.fillRect(quadro_x+((cobra_tiro_x-1)*11),quadro_y+((cobra_tiro_y-1)*11),11,11);
                                           switch (cobra_tiro_direcao) {
                                                case 119 -> cobra_tiro_y--;//w
                                                case 97 -> cobra_tiro_x--;//a
                                                case 115 -> cobra_tiro_y++;//s
                                                case 100 -> cobra_tiro_x++;//d
                                           }
                                           y2 = cobra_tiro_y; x2 = cobra_tiro_x;
                                           if (boss_corpo_x[0] <= cobra_tiro_x && cobra_tiro_x <= boss_corpo_x[3]
                                               && boss_corpo_y[1] <= cobra_tiro_y && cobra_tiro_y <= boss_corpo_y[5] && boss_movimento_iniciado == false) {
                                               cobra_tiro_dano = (byte)(tamanho/4);
                                               if (boss_vida-cobra_tiro_dano < 0)
                                                   cobra_tiro_dano += boss_vida - cobra_tiro_dano;
                                               g.setColor(new Color(61,68,179));
                                               byte c = (byte)(boss_vida_limite-boss_vida);
                                               for (int i = c; i <= c+cobra_tiro_dano; i++) {
                                                    g.fillRoundRect(boss_corpo_x[3]+48,262+(i*6),9,4,8,8);
                                               }
                                               boss_vida -= cobra_tiro_dano;
                                               cobra_tiro_disparado = false;
                                               interromper_cobra_tiro = true; }
                                            else
                                              if (x2 == 0 || cobra_tiro_alpha == 0 || y2 == 0 || y2 == height+1 || obstaculo[y2][x2] == true || (boss_laser[y2][x2] == true && !boss_laser_tipo.equals("simples"))) {
                                                  cobra_tiro_disparado = false;
                                                  interromper_cobra_tiro = true;
                                              }   
                                              else {
                                                g.setColor(cobra_tiro_cor);
                                                g.fillRect(quadro_x+((x2-1)*11),quadro_y+((y2-1)*11),11,11);
                                                if (cobra_tiro_x > boss_turbina_x) {
                                                    cobra_tiro_alpha++;
                                                    g.setColor(new Color(0,0,0,cobra_tiro_alpha));
                                                    g.fillRect(quadro_x+((x2-1)*11),quadro_y+((y2-1)*11),11,11);
                                                }    
                                              }
                                           if (escolha[y2][x2] == true)
                                               escolha[y2][x2] = false;
                                            else
                                              if (y2 == biscoito_y && x2 == biscoito_x)
                                                  destruido = true;
                                       }
                                   }
                               };
                               t.scheduleAtFixedRate(tiro,0,34);
                               break;
                          }
            }
            if (((cobra_direcao == 119 && tempChr != 115) || (cobra_direcao == 115 && tempChr != 119) || (cobra_direcao == 97 && tempChr != 100) || (cobra_direcao == 100 && tempChr != 97)) && (tempChr == 97 || tempChr == 100 || tempChr == 115 || tempChr == 119))
               cobra_direcao = tempChr;
            switch (cobra_direcao) {
                case 119 : {cobra_y[0] = (byte) (cobra_y[1] - 1);
                            cobra_x[0] = cobra_x[1]; 
                            break;}//w
                case 97 : {cobra_x[0] = (byte) (cobra_x[1] - 1);
                           cobra_y[0] = cobra_y[1];
                           break;}//a
                case 115 : {cobra_y[0] = (byte) (cobra_y[1] + 1);
                            cobra_x[0] = cobra_x[1];
                            break;}//s
                case 100 : {cobra_x[0] = (byte) (cobra_x[1] + 1);
                            cobra_y[0] = cobra_y[1];
                            break;}//d
            }
            x = cobra_x[0]; y = cobra_y[0];
            if (efeito == 3) {
                Efeitos(g); 
                x = cobra_x[0]; y = cobra_y[0];
            }
            if (boss_laser[y][x] == true && efeito != 5)
                lasado = true;
            if ((((cobra[y][x] == true || cobra_caida[y][x] == true || minado == true || lasado == true || obstaculado == true || (obstaculo[y][x] == true && efeito != 4))) && efeito != 5) || (y == 0 || y == height+1 || x == 0 || x == width+1)) {
                perdeu = true; //cancel schedule
                t.cancel(); }
             else {
               livre[y][x] = false;
               g.setColor(fundo_cor);
               if (efeito == 4 && obstaculo[y][x] == true) { //destroi os obstaculos que estao juntos
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
                   timer_efeito = 443;
                   System.out.println("timer_efeitosssssssssssssssssssssssssss: "+timer_efeito);
                   g.setColor(cobra_cor);
                   for (i = 1; i <= tamanho; i++) {
                       y = cobra_y[i]; x = cobra_x[i];
                       g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                   }
                   g.setColor(fundo_cor);
                       
               }
               x = cobra_x[tamanho]; y = cobra_y[tamanho];
               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
               livre[y][x] = true;
               cobra[y][x] = false;
               if (efeito == 5) {
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
                   x = cobra_x[tamanho];
                   y = cobra_y[tamanho];
               }         
               else 
                 if (efeito == 8) {
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
                      alpha += (short) (tempFlt);
                      if (alpha >= 255) {
                          tempShrt = tamanho;
                          if (efeito != 5) {
                              for (i = 1; i <= tempShrt; i++) {
                                   y = cobra_y[i]; x = cobra_x[i];
                                   if (minas[y][x] == true) {
                                       tamanho = (short)(i-1);
                                       for (i = (short)(tamanho+1); i <= tempShrt; i++) {
                                            y = cobra_y[i]; x = cobra_x[i];
                                            cobra[y][x] = false;
                                            cobra_caida[y][x] = true;
                                       }
                                       if (tamanho <= 4)
                                           minado = true;
                                       if (num_tiros > 0) {
                                           num_tiros -= tempShrt-tamanho;
                                           if (num_tiros < 0)
                                               num_tiros = 0;
                                           biscoitos_comidos = 0;
                                       }
                                       break;
                                   }    
                              }
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
                   if (efeito == 9) {
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
               for (i = (short) (tamanho-1); i >= 0; i--) {
                   cobra_x[i+1] = cobra_x[i];
                   cobra_y[i+1] = cobra_y[i];
               }  
               x = cobra_x[1]; y = cobra_y[1];
               livre[y][x] = false;
               cobra[y][x] = true;
               if (efeito != 4)
                   g.setColor(cobra_cor);
                else
                  g.setColor(cobra_bomba_cor); 
               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
               if (num_tiros > 0) {
                   g.setColor(cobra_tiro_cor);
                   for (i = tamanho; i > tamanho-num_tiros; i--) {
                       g.fillRect(quadro_x+((cobra_x[i]-1)*11),quadro_y+((cobra_y[i]-1)*11),11,11);
                   }
               }
                    
               if ((x == biscoito_x)&&(y == biscoito_y)) {
                  comido = true;
                  livre[y][x] = true;
                  tamanho++;
                  cobra_x[tamanho] = cobra_x[tamanho-1];
                  cobra_y[tamanho] = cobra_y[tamanho-1];
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
                      if (biscoitos_comidos == 5 && num_tiros <= tamanho-1) {
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
                      if (escolhido == resposta)
                         min = 1;
                       else
                         min = 5;
                      efeito = /*(byte)((Math.random()*4)+min)*/5;
                      Efeitos(g);
                      if (efeito == 4) {
                          g.setColor(cobra_bomba_cor);
                          for (i = 1; i <= tamanho; i++) {
                               y = cobra_y[i]; x = cobra_x[i];
                               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);                                  
                          }
                      }
                      else {//green good, red bad
                        if (min == 1)
                            g.setColor(new Color(31,204,198));
                         else
                            g.setColor(Color.red); 
                        g.fillRect(quadro_x+larg+20,quadro_y+comp+25,comp2-larg-52,48);
                        g.setColor(Color.white);
                        for (i = 0; i <= 14; i++) {
                             int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                             int py[] = {641,592,592,641};
                             g.fillPolygon(px,py,px.length);
                        }
                      }                      
                  }
             }
        }//while fecha aqui 
        g.setColor(obstaculo_cor);
        g.drawString("Game Over",quadro_x+comp2+larg+44,quadro_y+170);
        if (sco > high) {//store in file
            highscore = score;
            try {
              BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"));
              writer.write(highscore);
              writer.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            g.setColor(fundo_cor);
            g.fillRect(quadro_x+comp2+120,quadro_y-15,55,15);
            g.setColor(Color.white);
            g.drawString(highscore,quadro_x+comp2+120,quadro_y-larg);
        }
        g.setColor(Color.green);
        g.drawString("Reiniciar? (y/n)",quadro_x+comp2+larg+44,quadro_y+200);
        tempStr = "";
        tempStr = ler.nextLine();
        tempChr = tempStr.charAt(0);
        reiniciar = (tempChr == 121 || tempChr == 89);
        g.setColor(Color.black);
        g.fillRect(0,0,1260,1260);
        
    } while (reiniciar == true);    
    
    System.exit(0);
               
    }
    public void formarGrupos() {
        for (int i = 1; i <= height; i++) {
           for (int j = 1; j <= width; j++) {
               tempInt = 0;
               if (obstaculo_grupo[i-1][j] < obstaculo_grupo[i][j] && obstaculo_grupo[i-1][j] != 0) {
                   tempInt = obstaculo_grupo[i][j];
                   for (int i2 = i; i2 <= height; i2++) {
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
                     for (int i2 = i; i2 <= height; i2++) {
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
    
    public static void main(String[] args) {
        JogoDeCobra jdc = new JogoDeCobra();
    }
    
}
