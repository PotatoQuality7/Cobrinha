package jogodecobra;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class JogoDeCobra extends JFrame {
        
    public JogoDeCobra() {
        setTitle("Desenhos");
        setSize(1260,1260);
        setVisible(true);
        setBackground(fundo_cor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public String[] alternativa = new String[3];
    public String pergunta, boss_laser_tipo;
    public short[] cobra_x = new short[101];
    public short[] cobra_y = new short[101];
    public boolean[] gerado_minas = new boolean[10];
    public int sco;
    public short boss_vida, boss_turbina_y, boss_turbina_x, boss_laser_x, boss_laser_y, boss_laser_offset_y, atraso_boss_movimento, atraso_boss_laser, boss_laser_beam_length;
    public short x, y, quadro_x, quadro_y, comp, comp2, larg, width, height, biscoito_x, biscoito_y;
    public short atraso, tamanho, timer_perguntas, timer_resposta, timer_efeito, timer_minas, alpha;
    public byte numero, efeito, resposta, duplicador, boss_direcao, boss_movimento_timer, boss_laser_count, boss_laser_timer, loop, tempNum, j2, num_grupos;
    public boolean gerado_perguntas, feito, noturno, noturnado, boss, boss_derrotado, boss_laser, boss_impacto, destruido, lasado, interromper_boss_movimento, interromper_boss_laser;
    public Color fundo_cor = Color.black;
    public Color boss_laser_simples_cor = Color.red;
    public Color boss_laser_perfurador_cor = new Color(255,255,127);
    public Color boss_laser_refletidor_cor = Color.orange;
    public Color boss_laser_beam_cor1 = Color.white;
    public Color boss_laser_beam_cor2 = new Color(255,255,127);
    
    public void Perguntas(byte numero) {
        switch (numero) {
            case 1 : { 
                     pergunta = "Um byte e composto por quantos bits?";
                     alternativa[0] = "4 bits";
                     alternativa[1] = "8 bits";
                     alternativa[2] = "16 bits";
                     resposta = 2;
            }
        }
    
    }
    public void Efeitos(byte efeito) {
        
        switch (efeito) {
            case 1: atraso /= 2 ; //rapidez
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
            case 4: boolean bomba = true; //bomba deleta tudo
            case 5: boolean fantasma = true;
            case 6: duplicador = 2;
            case 7: sco -= 2000; 
            case 8: boolean minas = true; //fix snake not dying
            case 9: noturno = true; //fix snake head still appearing. Hint: move if efeito 9 to under snake movement
        }
    }
    public void paint(Graphics g) {
        Timer t = new Timer();
        Scanner ler = new Scanner(System.in);
        short[] obstaculo_x = new short[61];
        short[] obstaculo_y = new short[61];       
        short[] escolha_x = new short[3];       
        short[] escolha_y = new short[3];
        short[] reescrever_x = new short[61];
        short[] reescrever_y = new short[61];
        short[] minas_x = new short[11];
        short[] minas_y = new short[11];
        boolean[][] toca = new boolean[21][21]; //ve se grupos se tocam
        int high;
        short x2, y2, i, j, pos, num_obstaculos;
        short resto;
        byte nivel, pixel, campo, escolhido, num_perguntas, time, min, max, diametro;
        boolean lenda, perdeu, comido, invalido, gerado_obstaculos, mesmo_grupo, minado;
        char direcao, tempChr, tecla;
        String tempStr, highscore, score;
        direcao = "d".charAt(0); tempChr = "d".charAt(0); tecla = "d".charAt(0);
        highscore = "0"; score = "0";
        atraso = 68; timer_perguntas = 0; timer_resposta = 0; efeito = 0; timer_efeito = 0; timer_minas = 0; 
        num_grupos = 1; num_obstaculos = 1; sco = 0; high = 0; tamanho = 7; 
        quadro_x = 440; quadro_y = 150; pixel = 11; comp = 420; comp2 = 508; larg = 6;
        boss_vida = 6000; boss_direcao = 1; boss_turbina_x = 97; boss_movimento_timer = 0; boss_laser_count = 0; boss_laser_timer = 0; boss_laser_tipo = "simples"; atraso_boss_movimento = 1000; atraso_boss_laser = 900;
        biscoito_x = 0; biscoito_y = 0; escolhido = -1; alpha = 5; diametro = 0; duplicador = 1; loop = 0; j2 = -1;
        perdeu = false; comido = true; destruido = false; gerado_obstaculos = false; gerado_perguntas = false; minado = false; feito = false; noturno = false; noturnado = false;
        boss_derrotado = false; boss_laser = false; boss_impacto = true; lasado = false; interromper_boss_movimento = false; interromper_boss_laser = false;
        
        g.setColor(Color.white);     
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
        g.drawString(highscore,quadro_x+comp2+larg+120,quadro_y);
        g.drawString("Score:",quadro_x+comp2+larg+44,quadro_y+22);
        g.drawString(score,quadro_x+comp2+larg+90,quadro_y+22);

        quadro_x += larg; quadro_y += larg;
        
        Color cobra_cor = new Color(0,255,160);
        Color biscoito_cor = Color.yellow;
        Color obstaculo_cor = Color.red;
        Color minas_cor = Color.red;
        Color boss_turbina_cor = Color.cyan;
        Color boss_corpo_cor = Color.red;
        
        width = (short) (comp2/11);
        height = (short) (comp/11);
        boolean[][] cobra = new boolean[height+2][width+2];
        boolean[][] livre = new boolean[height+2][width+2];
        boolean[][] obstaculo = new boolean[height+2][width+2];
        byte[][] obstaculo_grupo = new byte[height+2][width+2]; //indica o grupo de um obstaculo
        byte[][] obstaculo_direcao = new byte[height+2][width+2];
        boolean[][] redesenhar = new boolean[height+2][width+2];
        boolean[][] escolha = new boolean[height+2][width+2];
        boolean[][] minas = new boolean[height+2][width+2];
        boolean[][] boss_laser = new boolean[height+2][width+2];
        int c = quadro_x+comp2;
        int boss_corpo_x[] = {c+67,c+82,c+112,c+127,c+112,c+82,c+67};
        boss_laser_beam_length = (short)(comp2+67);
        c = quadro_y;
        int boss_corpo_y[] = {c+210,c+185,c+185,c+210,c+235,c+235,c+210};
        boss_turbina_y = (short)(boss_corpo_y[1]-quadro_y); boss_laser_x = (short)((boss_corpo_x[0]-quadro_x)/11); boss_laser_y = (short)((boss_corpo_y[0]-quadro_y)/11); boss_laser_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
        
        for (i = 0; i <= height+1; i++) {
          for (j = 0; j <= width+1; j++) {
             if (i == 0 || i == height+1 || j == 0||j == width+1)
                livre[i][j] = false;
              else {
                livre[i][j] = true;
                obstaculo_direcao[i][j] = 1;
              }   
           }
        }
        
        g.setColor(cobra_cor);
        pos = 4;
        for (i = 7; i >= 1; i--) {
            cobra_x[i] = pos++;
            cobra_y[i] = 5;
            x = cobra_x[i];
            livre[5][x] = false;
            cobra[5][x] = true;
            g.fillRect(quadro_x+((x-1)*11),quadro_y+(4*11),11,11);
        }
        
        while(perdeu == false) {
            
            if (comido == true || destruido == true) {
               do { //gera biscoito
                   invalido = false;
                   biscoito_x = (short) ((Math.random() * width) + 1);
                   biscoito_y = (short) ((Math.random() * height) + 1);
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
            
            if ((sco % 2000 == 0)&&(sco != 0)) {
                
                if (gerado_obstaculos == false) /*&& (nivel == 3)*/ {
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
                    obstaculo_grupo[y][x] = (byte) (num_grupos);
                    livre[y][x] = false;
                    num_obstaculos++;
                    mesmo_grupo = true;
                    if (num_grupos >= 2) {
                        if (obstaculo_grupo[y][x] != obstaculo_grupo[y][x-1])
                            obstaculo_grupo[y][x] = obstaculo_grupo[y][x-1];
                         else
                           if (obstaculo_grupo[y][x] != obstaculo_grupo[y][x+1])
                               obstaculo_grupo[y][x] = obstaculo_grupo[y][x+1];
                            else
                              if (obstaculo_grupo[y][x] != obstaculo_grupo[y-1][x])
                                  obstaculo_grupo[y][x] = obstaculo_grupo[y-1][x];
                               else
                                 if (obstaculo_grupo[y][x] != obstaculo_grupo[y+1][x])
                                     obstaculo_grupo[y][x] = obstaculo_grupo[y+1][x];
                                  else
                                    mesmo_grupo = false; 
                    }
                    g.setColor(obstaculo_cor);
                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                    numero = (byte) ((Math.random() * 4) + 2);
                    campo = 1;
                    for (i = 1; i <= numero; i++) {
                       do {
                           invalido = true;
                           x2 = (short) ((x-campo)+(Math.random()*((campo*2)+1)));
                           y2 = (short) ((y-campo)+(Math.random()*((campo*2)+1)));
                           if ((x2 > 0 && x2 <= width)&&(y2 > 0 && y2 <= height)) {
                                if ((livre[y2][x2] == true)&&(obstaculo[y2][x2-1] == true || obstaculo[y2][x2+1] == true || obstaculo[y2-1][x2] == true || obstaculo[y2+1][x2] == true)) {
                                     num_obstaculos++;
                                     campo++;
                                     obstaculo_x[num_obstaculos] = x2;                         
                                     obstaculo_y[num_obstaculos] = y2;
                                     obstaculo[y2][x2] = true;
                                     obstaculo_grupo[y2][x2] = (byte) (num_grupos);
                                     livre[y2][x2] = false;
                                     invalido = false;
                                     for (j = 1; j <= 20; j++) {
                                         if (/*(j != num_grupos)&&*/(obstaculo_grupo[y][x-1] == j || obstaculo_grupo[y][x+1] == j || obstaculo_grupo[y-1][x] == j || obstaculo_grupo[y+1][x] == j))
                                            toca[num_grupos][j] = true;
                                     }
                                     g.fillRect(quadro_x+((x2-1)*11),quadro_y+((y2-1)*11),11,11);
                                }
                           }
                       } while (invalido == true);
                       if (num_grupos >= 2) {
                           if (obstaculo_grupo[y2][x2] != obstaculo_grupo[y2][x2-1])
                               obstaculo_grupo[y2][x2] = obstaculo_grupo[y2][x2-1];
                            else
                              if (obstaculo_grupo[y2][x2] != obstaculo_grupo[y2][x2+1])
                                  obstaculo_grupo[y2][x2] = obstaculo_grupo[y2][x2+1];
                               else
                                 if (obstaculo_grupo[y2][x2] != obstaculo_grupo[y2-1][x2])
                                     obstaculo_grupo[y2][x2] = obstaculo_grupo[y2-1][x2];
                                  else
                                    if (obstaculo_grupo[y2][x2] != obstaculo_grupo[y2+1][x2])
                                        obstaculo_grupo[y2][x2] = obstaculo_grupo[y2+1][x2];
                                     else
                                       mesmo_grupo = false;
                       }       
                    }
                    if (mesmo_grupo == false)
                        num_grupos++;
                    gerado_obstaculos = true;
                }
                if (timer_perguntas >= 20000) {
                    numero = 1;
                    Perguntas(numero);
                    g.setColor(Color.white);
                    g.drawString("Q. "+pergunta,quadro_x+comp2+44,quadro_y+38);
                    g.drawString("1. "+alternativa[0],quadro_x+comp2+70,quadro_y+60);
                    g.drawString("2. "+alternativa[1],quadro_x+comp2+70,quadro_y+82);
                    g.drawString("3. "+alternativa[2],quadro_x+comp2+70,quadro_y+104);
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
            if (sco >= 600/*0*/ && boss_derrotado == false) {//movimento do boss
                TimerTask mov = new TimerTask() {
                    public void run() {
                        boss = boss_vida > 0;
                        if (boss == true && interromper_boss_movimento == false) {
                            //boss_corpo_y[]++;
                            if (boss_corpo_y[1] == quadro_y+(4*11)) //por causa dos scores
                                boss_direcao = 2; //down
                             else
                               if (boss_corpo_y[4] == quadro_y+comp)
                                   boss_direcao = 1; //up
                                else
                                  if (boss_laser_count % 30 == 0 && boss_laser_count != 0 && boss_impacto == false) {
                                      if (boss_corpo_y[0] == (int)(height/2) && boss_corpo_x[0] == quadro_x+comp2+67) {
                                          boss_direcao = 3; //right
                                          atraso_boss_movimento = 1000;}
                                       else
                                         if (boss_corpo_x[3] == quadro_x+comp2+(20*11)) {
                                             boss_direcao = 4; //left
                                             atraso_boss_movimento = 3000;}
                                         else 
                                           if (boss_corpo_x[0] == quadro_x+comp2+14 || loop == 53) {
                                               boss_direcao = 3; //right
                                               if (loop == 0)
                                                   boss_impacto = true;
                                                else
                                                  atraso_boss_movimento = 1000; 
                                               if (loop == 53) {
                                                   loop = 0;
                                                   boss_movimento_timer = 0;
                                                   boss_direcao = 1;
                                                   interromper_boss_laser = false;
                                                   g.setColor(fundo_cor);
                                                   g.fillRect(boss_corpo_x[0]-14,boss_corpo_y[0]-15,10,30);
                                                   g.fillRect(boss_corpo_x[0]-4,boss_corpo_y[0]-2,4,4);
                                               }
                                               else
                                                 loop++;
                                           }   
                                  }
                            if (boss_impacto == false || (boss_impacto == true && boss_movimento_timer > 3000)) {
                                g.setColor(fundo_cor);
                                g.fillRect(boss_turbina_x+quadro_x+comp2,boss_turbina_y+quadro_y,40,10);
                                g.fillRect(boss_turbina_x+quadro_x+comp2,boss_turbina_y+quadro_y+40,40,10);
                                g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                                if (boss_direcao == 4) {
                                    g.fillRect(boss_corpo_x[0]-14,boss_corpo_y[0]-15,10,30);
                                    g.fillRect(boss_corpo_x[0]-4,boss_corpo_y[0]-2,4,4);
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
                                    case 3 -> boss_turbina_x++;
                                    case 4 -> {boss_turbina_x--;
                                               atraso_boss_movimento -= 12;}
                                }          
                                g.setColor(boss_turbina_cor);
                                g.fillRect(boss_turbina_x+quadro_x+comp2,boss_turbina_y+quadro_y,40,10);
                                g.fillRect(boss_turbina_x+quadro_x+comp2,boss_turbina_y+quadro_y+40,40,10);
                                g.setColor(boss_corpo_cor);
                                if (boss_direcao == 4) {
                                    g.fillRect(boss_corpo_x[0]-14,boss_corpo_y[0]-15,10,30);
                                    g.fillRect(boss_corpo_x[0]-4,boss_corpo_y[0]-2,4,4);
                                }    
                                for (int i = 0; i <= 6; i++) {
                                     switch (boss_direcao) {
                                        case 1 -> boss_corpo_y[i]--;
                                        case 2 -> boss_corpo_y[i]++;
                                        case 3 -> boss_corpo_x[i]++;
                                        case 4 -> boss_corpo_x[i]--;
                                     }
                                }
                                g.fillPolygon(boss_corpo_x,boss_corpo_y,boss_corpo_x.length);
                            }    
                            if (boss_impacto == true) {//after 15 seconds boss_impacto == false;
                                if (efeito == 4)
                                    num_grupos = 1;
                                for (int i = 1; i <= height; i++) {
                                     for (int j = 1; j <= width; j++) {
                                          if (obstaculo[i][j] == true) {
                                              if (obstaculo_direcao[i][j] == 1)
                                                  j2 = -1;
                                               else 
                                                 j2 = 1;
                                              if (obstaculo[i][j+j2] == true) {
                                                  if (obstaculo_direcao[i][j] != obstaculo_direcao[i][j+j2]) { //colisao
                                                      tempNum = obstaculo_direcao[i][j];
                                                      obstaculo_direcao[i][j] = obstaculo_direcao[i][j+j2];
                                                      obstaculo_direcao[i][j+j2] = tempNum;
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
                                              if (obstaculo[i][j+j2] == false && j+j2 != 0 && j+j2 != width+1) {
                                                  obstaculo[i][j] = false;
                                                  obstaculo[i][j+j2] = true;
                                                  obstaculo_direcao[i][j+j2] = obstaculo_direcao[i][j];
                                                  livre[i][j] = true;
                                                  livre[i][j+j2] = false;
                                                  obstaculo_direcao[i][j] = 0;
                                                  if (escolha[i][j] == true)
                                                      escolha[i][j] = false;
                                                  g.setColor(fundo_cor);
                                                  g.fillRect(quadro_x+((j-1)*11),quadro_y+((i-1)*11),11,11);
                                                  g.setColor(obstaculo_cor);
                                                  g.fillRect(quadro_x+((j-1)*11),quadro_y+(((i+j2)-1)*11),11,11);
                                              }
                                              if (efeito == 4) {//bomba
                                                  if (obstaculo[i-1][j] == true || obstaculo[i][j-1] == true) {//do second pass outside
                                                      obstaculo_grupo[i][j] = 0;
                                                      if (obstaculo_grupo[i-1][j] <= obstaculo_grupo[i][j-1] || obstaculo[i][j-1] == false) {
                                                          obstaculo_grupo[i][j] = obstaculo_grupo[i-1][j];
                                                          if (obstaculo[i][j-1] == true && obstaculo_grupo[i-1][j] != obstaculo_grupo[i][j-1]) {
                                                              for (int i2 = 1; i2 <= height; i2++) {//junta os grupos
                                                                   for (j2 = 1; j2 <= width; j2++) {
                                                                        if (obstaculo_grupo[i2][j2] == obstaculo_grupo[i][j-1])
                                                                            obstaculo_grupo[i2][j2] = obstaculo_grupo[i-1][j]; 
                                                                         else
                                                                           if (obstaculo_grupo[i2][j2] > obstaculo_grupo[i][j-1])//todos grupos caem uma posicao
                                                                               obstaculo_grupo[i2][j2]--;
                                                                   }
                                                              }
                                                              num_grupos--;
                                                          }
                                                      }
                                                      else
                                                        if (obstaculo_grupo[i][j-1] < obstaculo_grupo[i-1][j] || obstaculo[i-1][j] == false) {
                                                            if (obstaculo[i-1][j] == true) {
                                                                for (int i2 = 1; i2 <= height; i2++) {//junta os grupos
                                                                     for (j2 = 1; j2 <= width; j2++) {
                                                                          if (obstaculo_grupo[i2][j2] == obstaculo_grupo[i-1][j])
                                                                              obstaculo_grupo[i2][j2] = obstaculo_grupo[i][j-1];
                                                                          else
                                                                            if (obstaculo_grupo[i2][j2] > obstaculo_grupo[i-1][j])//todos grupos caem uma posicao
                                                                                obstaculo_grupo[i2][j2]--;
                                                                     }
                                                                }
                                                                num_grupos--;
                                                            }
                                                        }                                                          
                                                  }
                                                  else
                                                    obstaculo_grupo[i][j] = num_grupos++;
                                              }
                                                  
                                          }
                                     }
                                }
                                boss_movimento_timer += atraso_boss_movimento;
                                if (boss_movimento_timer >= 15000)
                                    boss_impacto = false;
                            }
                        }
                        else {
                          boss = false;
                          boss_derrotado = true;
                          sco += 3000; //quanto mais rapido maior o score
                          t.cancel();
                        }
                    }
                };
                t.scheduleAtFixedRate(mov,0,atraso_boss_movimento);
                TimerTask lsr = new TimerTask() {
                    public void run() {
                        if (interromper_boss_laser == false) {
                            y = boss_laser_y; x = boss_laser_x;
                            if (boss_laser_count % 30 == 0 && boss_laser_count != 0) {
                                interromper_boss_laser = true;
                                atraso_boss_movimento = 600;}
                            else
                              if (boss_laser_count % 15 == 0 && boss_laser_count != 0) {
                                  if (loop < 3) {
                                      boss_laser_tipo = "beam";
                                      interromper_boss_movimento = true; //t.wait on mov?
                                      atraso_boss_laser = 900;
                                      if (loop % 2 == 0) {
                                          g.setColor(boss_laser_simples_cor);
                                          g.drawLine(quadro_x+1,boss_corpo_y[0],quadro_x+comp2+x,boss_corpo_y[0]); }
                                       else {
                                         g.setColor(fundo_cor);
                                         g.drawLine(quadro_x+1,boss_corpo_y[0],quadro_x+comp2+x,boss_corpo_y[0]);
                                         loop++;
                                       }
                                  }
                                  if (loop >= 3) {
                                      g.setColor(boss_laser_beam_cor2);
                                      int boss_laser_beam_x[] = {boss_corpo_x[0],quadro_x+boss_laser_beam_length,quadro_x+boss_laser_beam_length-12,quadro_x+boss_laser_beam_length,boss_corpo_x[0]};
                                      int boss_laser_beam_y[] = {boss_corpo_y[0]-8,boss_corpo_y[0]-8,boss_corpo_y[0],boss_corpo_y[0]+8,boss_corpo_y[0]+8};
                                      g.fillPolygon(boss_laser_beam_x,boss_laser_beam_y,boss_laser_beam_x.length);
                                      g.setColor(boss_laser_beam_cor1);
                                      g.fillRect(boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_y[0]-6,(comp2+67)-boss_laser_beam_length,12);
                                      loop++;
                                      if (boss_laser_beam_length != 0 && interromper_boss_movimento == true) {
                                          if (boss_laser_beam_length/11 <= width && loop == 14) {
                                              loop = 3;
                                              y = (short)((boss_corpo_y[0]-quadro_y)/11); x = (short)(boss_laser_beam_length/11);
                                              for (int i = 0; i <= 1; i++) {
                                                   boss_laser[y+i][x] = true;
                                                   if (escolha[y+i][x] == true)
                                                      escolha[y+i][x] = false;
                                                   livre[y+i][x] = false;
                                                   if (obstaculo[y+i][x] == true || (x == biscoito_y && y+i == biscoito_y)) {
                                                       if (obstaculo[y+i][x] == true)
                                                           obstaculo[y+i][x] = false;
                                                        else
                                                          destruido = true;
                                                       g.setColor(fundo_cor);
                                                       if (i == 0)
                                                           g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,3);
                                                        else
                                                          g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11)+8,11,3);
                                                   }
                                              }
                                          }
                                          boss_laser_beam_length--;
                                      }
                                      else {
                                        if (interromper_boss_movimento == true) {
                                            interromper_boss_movimento = false;
                                            boss_laser_beam_length = (short)(comp2+67);
                                            atraso_boss_laser = atraso_boss_movimento;
                                        }    
                                        boss_laser_timer += atraso_boss_laser;
                                        if (boss_laser_timer >= 3500) {
                                            atraso_boss_laser = 100;
                                            interromper_boss_movimento = true;
                                            g.setColor(fundo_cor);
                                            g.fillRect(boss_corpo_x[0]-boss_laser_beam_length,boss_corpo_y[0]-8,(comp2+67)-boss_laser_beam_length,16);
                                            boss_laser_beam_length--;
                                            if (boss_laser_beam_length/11 <= width && loop == 14) {
                                                loop = 3;
                                                y = (short)((boss_corpo_y[0]-quadro_y)/11); x = (short)(boss_laser_beam_length/11);
                                                for (int i = 0; i <= 1; i++) {
                                                     boss_laser[y+i][x] = true;
                                                     livre[y+i][x] = false;
                                                }
                                            }
                                            if (boss_laser_beam_length == 0) {
                                                boss_laser_tipo = "simples";
                                                loop = 0;
                                                boss_laser_count++;
                                                interromper_boss_movimento = true;
                                                atraso_boss_laser = 900;
                                            }
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
                            }
                            if (x <= width) {
                                if (cobra[y][x] == true) {
                                    y = cobra_y[1]; x = cobra_x[1];
                                    if (boss_laser[y][x] == true) //tocou a cabeca?
                                        lasado = true;
                                     else {//Nao? Entao veja qual segmento do corpo tocou
                                       for (int i = 2; i <= tamanho; i++) {
                                            y = cobra_y[i];
                                            x = cobra_x[i];
                                            if (boss_laser[y][x] == true) {
                                                for (int j = i; j <= tamanho; j++) {
                                                     y = cobra_y[j]; x = cobra_x[j];
                                                     cobra[y][x] = false;
                                                     tamanho--;
                                                }
                                                break;
                                            }
                                        }    
                                     }
                                }
                                else
                                  if (x == biscoito_x && y == biscoito_y && !(boss_laser_tipo.equals("beam"))) {
                                      destruido = true;
                                      g.setColor(fundo_cor);
                                      g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
                                  }
                                  else
                                    if (obstaculo[y][x] == true && boss_laser_tipo.equals("simples")) {
                                        /*if boss_laser_tipo == 1*/
                                        g.setColor(obstaculo_cor);
                                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                        boss_laser[y][x] = false;
                                        boss_laser_y = (short) ((boss_corpo_y[0]-quadro_y)/11);
                                        boss_laser_x = (short) ((boss_corpo_x[0]-quadro_x)/11);
                                        boss_laser_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
                                        boss_laser_count++;
                                        /*else
                                        //redesenhar
                                        */   
                                    }
                                    else
                                      if (x == 0 && !(boss_laser_tipo.equals("beam"))) {
                                          boss_laser[y][x] = false;
                                          boss_laser_y = (short) ((boss_corpo_y[0]-quadro_y)/11);
                                          boss_laser_x = (short) ((boss_corpo_x[0]-quadro_x)/11);
                                          boss_laser_offset_y = (short)((boss_corpo_y[0]-quadro_y) % 11);
                                          boss_laser_count++;
                                      }
                                      else
                                        if (escolha[y][x] == true)
                                            escolha[y][x] = false;
                            }
                        }
                    }
                };
                t.scheduleAtFixedRate(lsr,500,atraso_boss_laser);
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
                    if (gerado_perguntas == true) {
                        g.setColor(fundo_cor);
                        g.fillRect(915-(2*timer_resposta),quadro_y+comp+25,timer_resposta,48);
                        timer_resposta++;
                        if (timer_resposta == 222) { //os 15 segundos pra responder acabaram
                            timer_resposta = 0;
                            gerado_perguntas = false;
                            g.fillRect(quadro_x+comp2+40,quadro_y+25,300,95);
                            for (int i = 0; i <= 2; i++) {
                                 int y = escolha_y[i];
                                 int x = escolha_x[i];
                                 g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                 livre[y][x] = true;
                                 escolha[y][x] = false;
                            }
                            efeito = 6; //(byte)(min + ((Math.random()*diferenca)));
                            Efeitos(efeito);
                            //green good, red bad
                            g.setColor(new Color(31,204,198));
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
                      }  
                    if (gerado_perguntas == false)
                        timer_perguntas += atraso;
                    if (feito == false) {
                        if (alpha == 255) {
                            noturnado = noturnado == false;
                            alpha = 5;
                        }
                        if (gerado_minas[1] == true || noturno == true)
                            timer_minas += atraso;
                        if (timer_minas >= 700 && alpha < 255) {
                            alpha += 25;
                            timer_minas = 0;
                        }
                    }    
                };  
            };
            t.schedule(enter,400,20);//original: 400, altered because sharp turns were not triggering
            tempStr = (ler.nextLine() + "m").toLowerCase();
            tempChr = tempStr.charAt(0); 
            if (((direcao == 119 && tempChr != 115) || (direcao == 115 && tempChr != 119) || (direcao == 97 && tempChr != 100) || (direcao == 100 && tempChr != 97)) && (tempChr == 97 || tempChr == 100 || tempChr == 115 || tempChr == 119) || tempChr == 112)
               direcao = tempChr;
            switch (direcao) {
                case 119 : {cobra_y[0] = (short) (cobra_y[1] - 1);
                            cobra_x[0] = cobra_x[1]; 
                            tecla = direcao;
                            break;}//w
                case 97 : {cobra_x[0] = (short) (cobra_x[1] - 1);
                           cobra_y[0] = cobra_y[1];
                           tecla = direcao;
                           break;}//a
                case 115 : {cobra_y[0] = (short) (cobra_y[1] + 1);
                            cobra_x[0] = cobra_x[1];
                            tecla = direcao;
                            break;}//s
                case 100 : {cobra_x[0] = (short) (cobra_x[1] + 1);
                            cobra_y[0] = cobra_y[1];
                            tecla = direcao;
                            break;}//d
                case 112 : {tempChr = 0;
                            while (tempChr != 112) {
                                TimerTask enter2 = new TimerTask() {
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
                                    }
                                };
                                t.schedule(enter2,200,200);
                                tempStr = (ler.nextLine() + "m").toLowerCase();
                                tempChr = tempStr.charAt(0);
                                try {
                                    Thread.sleep(210);
                                } catch (InterruptedException ex) {
                                    java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                                }
                            }
                            direcao = tecla;
                            break;}//p
            }
            x = cobra_x[0]; y = cobra_y[0];
            if (efeito == 3) {
                Efeitos(efeito); 
                x = cobra_x[0]; y = cobra_y[0];
            }
            if (cobra[y][x] == true || minado == true || lasado == true || tamanho <= 4 || (y == 0 || y == height+1 || x == 0 || x == width+1) || (obstaculo[y][x] == true && efeito != 4 && efeito != 5)) {
                perdeu = true; //cancel schedule
                t.cancel(); }
             else {
               livre[y][x] = false;
               g.setColor(fundo_cor);
               if (efeito == 4 && obstaculo[y][x] == true) { //destroi os obstaculos que estao juntos
                   i = obstaculo_grupo[y][x];
                   for (y = 1; y <= height; y++) {
                      for (x = 1; x <= width; x++) {
                           if (obstaculo_grupo[y][x] == i) {
                               obstaculo_grupo[y][x] = 0;
                               livre[y][x] = true;
                               obstaculo[y][x] = false;
                               num_obstaculos--;
                               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                           }
                           else
                             if (obstaculo_grupo[y][x] > i)
                                 obstaculo_grupo[y][x]--;
                      }
                   }
                   num_grupos--;
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
                        if (gerado_minas[1] == false && feito == false) {
                            diametro = (byte) ((Math.random()*11) + 5);
                            minas_y[1] = (short) (((Math.random()*height) + 1) - (diametro/2));
                            minas_x[1] = (short) (((Math.random()*width) + 1) - (diametro/2));
                            for (i = 0; i <= diametro-1; i++) {
                                for (j = 0; j <= diametro-1; j++) {
                                    y2 = (short) (minas_y[1] + i);
                                    x2 = (short) (minas_x[1] + j);
                                    minas[y2][x2] = true;
                                }
                            }
                            gerado_minas[1] = true;
                        }  g.setColor(new Color(255,0,0,alpha));
                        //redraw shapes
                        y = minas_y[1];
                        x = minas_x[1];
                        g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),diametro*11,diametro*11);
                        if (alpha >= 250) {
                            for (i = 1; i <= tamanho; i++) {
                                y = cobra_y[i];
                                x = cobra_x[i];
                                if (minas[y][x] == true) {
                                    for (j = i; j <= tamanho; j++) {
                                        y = cobra_y[j]; x = cobra_x[j];
                                        cobra[y][x] = false;
                                        obstaculo[y][x] = true;
                                        tamanho--;
                                        g.setColor(minas_cor);
                                        g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                    }
                                    break;
                                }
                            }
                            y = cobra_y[1]; x = cobra_x[1];
                            if (minas[y][x] == true)
                                minado = true;
                            for (i = 0; i <= diametro; i++) {
                                for (j = 0; j <= diametro; j++) {
                                    y2 = (short) (minas_y[1] + i);
                                    x2 = (short) (minas_x[1] + j);
                                    if (obstaculo[y2][x2] == true)
                                        g.setColor(obstaculo_cor);
                                    else
                                        if (cobra[y2][x2] == true)
                                            g.setColor(cobra_cor);
                                        else
                                            g.setColor(fundo_cor);
                                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                    minas[y2][x2] = false;
                                }
                            }
                            timer_minas = 0;
                            feito = true;
                            //alpha = 5;
                            //gerado_minas[1] = false;
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
                                        if (cobra[i][j] == true) {
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
               g.setColor(cobra_cor);
               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                    
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
                  gerado_obstaculos = false;   }
                else 
                  if (escolha[y][x] == true) {                      
                      g.setColor(fundo_cor);
                      timer_resposta = 0;
                      gerado_perguntas = false;
                      for (i = 0; i <= 2; i++) {
                          if ((x == escolha_x[i])&&(y == escolha_y[i])) {
                             escolhido = (byte)(i+1);
                             escolha[y][x] = false;
                             livre[y][x] = true;
                             for (j = 0; j <= 2; j++) {
                                 if (i != j) {
                                    y = escolha_y[j]; 
                                    x = escolha_x[j];
                                    escolha[y][x] = false;
                                    livre[y][x] = true;
                                    g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                                 }
                             }
                             break;
                          }
                      }
                      g.setColor(cobra_cor);
                      if (escolhido == resposta) {
                         min = 1;
                         max = 4; }
                       else {
                         min = 5;
                         max = 8;
                       }
                      byte diferenca = (byte)((max-min)+1);
                      efeito = 3; //(byte)(min + ((Math.random()*diferenca)));
                      Efeitos(efeito);
                      if (efeito == 4) {
                         cobra_cor = new Color(76,44,18);
                         g.setColor(cobra_cor);
                         for (i = 1; i <= tamanho; i++) {
                              x = cobra_x[i]; y = cobra_y[i];
                              g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                         }   
                      }
                      //green good, red bad
                      g.setColor(new Color(31,204,198));
                      g.fillRect(quadro_x+larg+20,quadro_y+comp+25,comp2-larg-52,48);
                      g.setColor(Color.white);
                      for (i = 0; i <= 14; i++) {
                           int px[] = {466+(i*30),490+(i*30),494+(i*30),470+(i*30)};
                           int py[] = {641,592,592,641};
                           g.fillPolygon(px,py,px.length);
                      }
                      
                  }
             }
        }//while fecha aqui 
        g.setColor(obstaculo_cor);
        g.drawString("Game Over",quadro_x+comp2+larg+44,quadro_y+66);
        if (sco > high) {//store in file 
           highscore = score;
           g.setColor(fundo_cor);
           g.fillRect(quadro_x+comp2+120,quadro_y-15,55,15);
           g.setColor(Color.white);
           g.drawString(highscore,quadro_x+comp2+120,quadro_y-larg);
        }
               
    }
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        JogoDeCobra jdc = new JogoDeCobra();
    }
    
}
