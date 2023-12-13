/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
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

/**
 *
 * @author timana
 */
public class JogoDeCobra extends JFrame {
        
    public JogoDeCobra() {
        setTitle("Desenhos");
        setSize(1260,1260);
        setVisible(true);
        setBackground(fundo_cor);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public String[] alternativa = new String[3];
    public String pergunta;
    public byte numero, efeito, resposta;
    public short atraso, timer;
    public boolean gerado_perguntas;    
    public int sco;    
    public Color fundo_cor = Color.black;
    
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
        
        switch (efeito) {//jump
            case 1: atraso /= 2 ; //rapidez
            case 2: atraso *= 2 ; //lentidao
            case 3: boolean portal = true;
            case 4: boolean bomba = true;
            case 5: boolean invecibilidade = true;
            case 6: boolean duplicador = true;
            case 7: sco -= 2000;
            case 8: boolean minas = true;
            /*case "reduzirpontos": score = (int) score - (Math.random()*2000);
            case "escuridao": escuridao;*/
        }
    }
    public void paint(Graphics g) {
        Timer t = new Timer();
        Scanner ler = new Scanner(System.in);
        short[] cobra_x = new short[101];
        short[] cobra_y = new short[101];
        short[] obstaculo_x = new short[61];
        short[] obstaculo_y = new short[61];       
        short[] escolha_x = new short[3];       
        short[] escolha_y = new short[3];
        short[] reescrever_x = new short[61];
        short[] reescrever_y = new short[61];
        byte[][] grupo = new byte[61][61]; //indica o grupo de um obstaculo
        boolean[][] toca = new boolean[21][21]; //ve se grupos se tocam
        int high;
        short x, x2, y, y2, i, j, pos, tamanho, biscoito_x, biscoito_y, num_grupos, num_obstaculos;
        short quadro_x, quadro_y, comp, comp2, larg, width, height, resto;
        byte nivel, pixel, campo, escolhido, num_perguntas, time, min, max;
        boolean lenda, perdeu, comido, invalido, gerado_obstaculos, mesmo_grupo;
        char direcao, temp;
        String temp2, highscore, score;
        direcao = "d".charAt(0); temp = "d".charAt(0); highscore = "0"; score = "0";
        atraso = 70; timer = 0; num_grupos = 1; num_obstaculos = 1; sco = 0; high = 0; tamanho = 7; biscoito_x = 0; biscoito_y = 0; escolhido = 0;
        quadro_x = 440; quadro_y = 200; pixel = 11; comp = 420; comp2 = 508; larg = 6; x = 400; y = 200;
        perdeu = false; comido = true; gerado_obstaculos = false; gerado_perguntas = false;
        
        g.setColor(Color.white);     
        //Font custom = new Font("Roboto",1,15);
        //g.setFont(custom);
        resto = (short) ((comp-larg) % 11); comp -= resto;
        resto = (short) ((comp2-larg) % 11); comp2 -= resto;
        //vertical
        g.fillRect(quadro_x,quadro_y,larg,comp);
        g.fillRect(quadro_x+comp2,quadro_y,larg,comp);
        //horizontal
        g.fillRect(quadro_x,quadro_y,comp2,larg);
        g.fillRect(quadro_x,quadro_y+comp,comp2+larg,larg);
        
        g.drawString("Highscore:",quadro_x+comp2+larg+44,quadro_y);
        g.drawString(highscore,quadro_x+comp2+larg+120,quadro_y);
        g.drawString("Score:",quadro_x+comp2+larg+44,quadro_y+22);
        g.drawString(score,quadro_x+comp2+larg+90,quadro_y+22);

        quadro_x += larg; quadro_y += larg;
        
        Color cobra_cor = new Color(0,255,160);
        Color biscoito_cor = Color.yellow;
        Color obstaculo_cor = Color.red;
        
        g.setColor(cobra_cor);
        /*System.out.println("Escolha o modo.\n\n");
            System.out.println("Carreira"); //faça um bitao
            System.out.println("Lenda");
            if (carreira) {
            nivel = 1;
            else
            (lenda = true;)
            }*/
        width = (short) (comp2/11);
        height = (short) (comp/11);
        boolean[][] cobra = new boolean[height+2][width+2];
        boolean[][] livre = new boolean[height+2][width+2];
        boolean[][] obstaculo = new boolean[height+2][width+2];
        boolean[][] redesenhar = new boolean[height+2][width+2];
        boolean[][] escolha = new boolean[height+2][width+2];
        for (i = 0; i <= height+1; i++) {
          for (j = 0; j <= width+1; j++) {
             if (i == 0 || i == height+1 || j == 0||j == width+1) {
                livre[i][j] = false; }
              else 
                livre[i][j] = true;
           }
        }
        pos = 4;
        for (i = 7; i >= 1; i--) {
            cobra_x[i] = pos++;
            cobra_y[i] = 5;
            x = cobra_x[i];
            livre[5][x] = false;
            cobra[5][x] = true;
            g.fillRect(quadro_x+((x-1)*11), quadro_y+(4*11), 11, 11);
        }
        while(perdeu == false) {
            
            if (comido == true) {
               do { //gera biscoito
                   invalido = false;
                   biscoito_x = (short) ((Math.random() * width) + 1);
                   biscoito_y = (short) ((Math.random() * height) + 1);
                   y = biscoito_y;
                   x = biscoito_x;
                   if (livre[y][x] != true)
                      invalido = true;
               } while (invalido == true);
               g.setColor(biscoito_cor);
               g.fillOval(quadro_x+((x-1)*11),quadro_y+((y-1)*11),10,10);
               comido = false;
            }
            
            if ((sco % 200 == 0)&&(sco != 0)) {
                
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
                    grupo[y][x] = (byte) (num_grupos);
                    livre[y][x] = false;
                    num_obstaculos++;
                    mesmo_grupo = true;
                    if (num_grupos >= 2) {
                        if (grupo[y][x] != grupo[y][x-1])
                            grupo[y][x] = grupo[y][x-1];
                         else
                           if (grupo[y][x] != grupo[y][x+1])
                               grupo[y][x] = grupo[y][x+1];
                            else
                              if (grupo[y][x] != grupo[y-1][x])
                                  grupo[y][x] = grupo[y-1][x];
                               else
                                 if (grupo[y][x] != grupo[y+1][x])
                                     grupo[y][x] = grupo[y+1][x];
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
                                     grupo[y2][x2] = (byte) (num_grupos);
                                     livre[y2][x2] = false;
                                     invalido = false;
                                     for (j = 1; j <= 20; j++) {
                                         if (/*(j != num_grupos)&&*/(grupo[y][x-1] == j || grupo[y][x+1] == j || grupo[y-1][x] == j || grupo[y+1][x] == j))
                                            toca[num_grupos][j] = true;
                                     }
                                     g.fillRect(quadro_x+((x2-1)*11),quadro_y+((y2-1)*11),11,11);
                                }
                           }
                       } while (invalido == true);
                       if (num_grupos >= 2) {
                           if (grupo[y2][x2] != grupo[y2][x2-1])
                               grupo[y2][x2] = grupo[y2][x2-1];
                            else
                              if (grupo[y2][x2] != grupo[y2][x2+1])
                                  grupo[y2][x2] = grupo[y2][x2+1];
                               else
                                 if (grupo[y2][x2] != grupo[y2-1][x2])
                                     grupo[y2][x2] = grupo[y2-1][x2];
                                  else
                                    if (grupo[y2][x2] != grupo[y2+1][x2])
                                        grupo[y2][x2] = grupo[y2+1][x2];
                                     else
                                       mesmo_grupo = false;
                       }       
                    }
                    if (mesmo_grupo == false)
                        num_grupos++;
                    gerado_obstaculos = true;
                }
                if (timer >= 2000) {
                    numero = 1;
                    Perguntas(numero);
                    g.setColor(fundo_cor);
                    g.fillRect(quadro_x+comp2+40,quadro_y+25,300,95);
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
                        temp2 = Integer.toString(i+1);
                        g.drawString(temp2,2+quadro_x+((x-1)*11),quadro_y+(y*11));
                        livre[y][x] = false;
                        escolha[y][x] = true;
                    }
                    gerado_perguntas = true;
                    timer = 0;
                    g.setColor(cobra_cor);
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
                    if (gerado_perguntas == false)
                        timer += atraso;
                };  
            };  
            t.scheduleAtFixedRate(enter,400,20);//original: 400, altered because sharp turns were not triggering
            temp2 = ler.nextLine() + "p";
            temp = temp2.charAt(0); 
            if (((direcao == 119 && temp != 115) || (direcao == 115 && temp != 119) || (direcao == 97 && temp != 100) || (direcao == 100 && temp != 97)) && (temp == 97 || temp == 100 || temp == 115 || temp == 119))
               direcao = temp;
            switch (direcao) {
                case 119 : {cobra_y[0] = (short) (cobra_y[1] - 1);
                            cobra_x[0] = cobra_x[1];
                            break;}//w
                case 97 : {cobra_x[0] = (short) (cobra_x[1] - 1);
                           cobra_y[0] = cobra_y[1];
                           break;}//a
                case 115 : {cobra_y[0] = (short) (cobra_y[1] + 1);
                            cobra_x[0] = cobra_x[1];
                            break;}//s
                case 100 : {cobra_x[0] = (short) (cobra_x[1] + 1);
                            cobra_y[0] = cobra_y[1];
                            break;}//
            }
            x = cobra_x[0]; y = cobra_y[0];
            if (efeito == 2) {
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
                x = cobra_x[0]; y = cobra_y[0];
            }
            if (cobra[y][x] == true || (y == 0 || y == height+1 || x == 0 || x == width+1) || (obstaculo[y][x] == true && efeito != 4 && efeito != 5)) {
                perdeu = true; //cancel schedule
                t.cancel(); }
             else {
               livre[y][x] = false;
               g.setColor(fundo_cor);
               if (efeito == 4 && obstaculo[y][x] == true) { //destroi os obstaculos que estao juntos
                   i = grupo[y][x];
                   for (y = 1; y <= height; y++) {
                      for (x = 1; x <= width; x++) {
                           if (grupo[y][x] == i) {
                               grupo[y][x] = 0;
                               livre[y][x] = true;
                               obstaculo[y][x] = false;
                               num_obstaculos--;
                               g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                           }
                           else
                             if (grupo[y][x] > i)
                                 grupo[y][x]--;
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
                     }
                     g.setColor(fundo_cor);
                     x = cobra_x[tamanho]; y = cobra_y[tamanho];    
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
                  tamanho++;
                  cobra_x[tamanho] = cobra_x[tamanho-1];
                  cobra_y[tamanho] = cobra_y[tamanho-1];
                  sco += 200;
                  score = Integer.toString(sco);
                  g.setColor(fundo_cor);
                  g.fillRect(quadro_x+comp2+85,quadro_y+5,55,11);
                  g.setColor(Color.white);
                  g.drawString(score,quadro_x+comp2+90,quadro_y+16);
                  g.setColor(cobra_cor);
                  gerado_obstaculos = false;   }
                else 
                  if (escolha[y][x] == true) {
                     time = 30;
                     g.setColor(fundo_cor);
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
                     efeito = 4; //(byte)(min + ((Math.random()*diferenca)));jump
                     Efeitos(efeito);
                     if (efeito == 4) {
                        cobra_cor = new Color(76,44,18);
                        g.setColor(cobra_cor);
                        for (i = 1; i <= tamanho; i++) {
                             x = cobra_x[i]; y = cobra_y[i];
                             g.fillRect(quadro_x+((x-1)*11),quadro_y+((y-1)*11),11,11);
                        }   
                     }
                     gerado_perguntas = false;
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
