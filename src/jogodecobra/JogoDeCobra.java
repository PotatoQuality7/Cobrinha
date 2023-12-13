package jogodecobra;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
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
        setBackground(Color.black);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void paint(Graphics g) {
        Timer t = new Timer();
        Scanner ler = new Scanner(System.in);
        short[] cobra_x = new short[101];
        short[] cobra_y = new short[101];
        short[] obstaculo_x = new short[60];
        short[] obstaculo_y = new short[60];        
        int sco, high;
        short x, x2, y, y2, i, j, pos, atraso, tamanho, biscoito_x, biscoito_y, num_obstaculos;
        short quadro_x, quadro_y, comp, comp2, larg, width, height, resto;
        byte numero, nivel, pixel, campo;
        boolean lenda, perdeu, comido, invalido, gerado;
        char direcao, temp;
        String efeito, temp2, highscore, score;
        direcao = "d".charAt(0); temp = "d".charAt(0); highscore = "0"; score = "0";
        num_obstaculos = 1; sco = 0; high = 0; tamanho = 7; biscoito_x = 0; biscoito_y = 0;
        quadro_x = 440; quadro_y = 200; pixel = 11; comp = 420; comp2 = 508; larg = 6; x = 400; y = 200;
        perdeu = false; comido = true; gerado = false;
        
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
        g.setColor(new Color(0,255,160));
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
        boolean[][] livre = new boolean[height+2][width+2];
        boolean[][] obstaculo = new boolean[height+2][width+2];
        for (i = 0; i <= height+1; i++) {
          for (j = 0; j <= width+1; j++) {
             if (i == 0 || i == height+1 || j == 0||j == width+1) {
                livre[i][j] = false; }
              else 
                livre[i][j] = true;
           }
        }
        g.setColor(new Color(0,255,160));
        pos = 4;
        for (i = 7; i >= 1; i--) {
            cobra_x[i] = pos++;
            cobra_y[i] = 5;
            x = cobra_x[i];
            livre[x][5] = false;
            g.fillRect(quadro_x+(x*11), quadro_y+(5*11), 10, 10);
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
               g.setColor(Color.yellow);
               g.fillOval(quadro_x+(x*11),quadro_y+(y*11),10,10);
               comido = false;
            }
            if ((sco % 2000 == 0)&&(sco != 0)&&(gerado == false) /*&& (nivel == 3)*/) {
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
               num_obstaculos++;
               g.setColor(Color.red);
               g.fillRect(quadro_x+(x*11),quadro_y+(y*11),11,11);
               numero = (byte) ((Math.random() * 4) + 2);
               System.out.printf("y: %d, x: %d%n",y,x);
               System.out.println("num: "+numero);
               campo = 1;
               for (i = 1; i <= numero; i++) {
                  do {
                      invalido = true;
                      x2 = (short) ((x-campo)+(Math.random()*((campo*2)+1)));
                      y2 = (short) ((y-campo)+(Math.random()*((campo*2)+1)));
                      System.out.printf("y: [%d..%d], x: [%d..%d]%n",y-campo,y+campo,x-campo,x+campo);
                      System.out.printf("y2: %d, x2: %d%n%n",y2,x2);
                      System.out.printf("y2: %d, x2: %d%n",y2,x2-1);
                      System.out.printf("y2: %d, x2: %d%n",y2,x2+1);
                      System.out.printf("y2: %d, x2: %d%n",y2-1,x2);
                      System.out.printf("y2: %d, x2: %d%n%n%n%n",y2+1,x2);
                      if ((x2 > 0 && x2 <= width)&&(y2 > 0 && y2 <= height)) {
                         if ((livre[y2][x2] == true)&&(obstaculo[y2][x2-1] == true || obstaculo[y2][x2+1] == true || obstaculo[y2-1][x2] == true || obstaculo[y2+1][x2] == true)) {
                            System.out.println("valido");
                            num_obstaculos++;
                            campo++;
                            obstaculo_x[num_obstaculos] = x2;                         
                            obstaculo_y[num_obstaculos] = y2;
                            obstaculo[y2][x2] = true;
                            livre[y2][x2] = false;
                            invalido = false;
                            g.fillRect(quadro_x+(x2*11),quadro_y+(y2*11),11,11);
                         }
                      }
                  } while (invalido == true);
               }
               gerado = true;
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
                };  
            };  
            t.scheduleAtFixedRate(enter,3000,1000);
            temp2 = ler.nextLine() + "p";
            System.out.println("Aqui:"+temp2);
            temp = temp2.charAt(0); 
            System.out.println("letra: "+temp);
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
                            break;}//d
            }
            x = cobra_x[0]; y = cobra_y[0];
            if ((livre[y][x] == false)&&(biscoito_x != x)&&(biscoito_y != y)) {
                perdeu = true; //cancel schedule
                t.cancel(); }
             else {
                livre[y][x] = false;
                x = cobra_x[tamanho];
                y = cobra_y[tamanho];
                
                g.setColor(Color.black);
                g.fillRect(quadro_x+(x*11),quadro_y+(y*11),10,10);
                livre[y][x] = true;
                for (i = (short) (tamanho-1); i >= 0; i--) {
                    cobra_x[i+1] = cobra_x[i];
                    cobra_y[i+1] = cobra_y[i];
                }  
                x = cobra_x[1]; y = cobra_y[1];
                g.setColor(new Color(0,255,160));
                g.fillRect(quadro_x+(x*11),quadro_y+(y*11),10,10);
                livre[y][x] = false;
                if ((cobra_x[1] == biscoito_x)&&(cobra_y[1] == biscoito_y)) {
                   comido = true;
                   tamanho++;
                   sco += 400;
                   score = Integer.toString(sco);
                   g.setColor(Color.black);
                   g.fillRect(quadro_x+comp2+85,quadro_y+5,55,11);
                   g.setColor(Color.white);
                   g.drawString(score,quadro_x+comp2+90,quadro_y+16);
                   g.setColor(new Color(0,255,160));
                   gerado = false;
                }
             }
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(JogoDeCobra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } 
        g.setColor(Color.red);
        g.drawString("Game Over",quadro_x+comp2+larg+44,quadro_y+66);
        if (sco > high) //store in file
           highscore = score;
               
    }
    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        JogoDeCobra jdc = new JogoDeCobra();
    }
    
}
