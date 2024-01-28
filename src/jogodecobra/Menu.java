package jogodecobra;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Menu implements ActionListener {
   
    JFrame frame;
    JPanel telaPrincipal, telaCadastro, telaModo;
    JButton newGame, loadGame, confirmarCadastro, easyButton, normalButton, hardButton, jogador1Button, jogador2Button, jogador3Button, niveisButton, continuoButton, multiplayerButton;
    JTextField usuarioField;
    JPasswordField senhaField;
    JLabel mainBorder, miniBorder, usuarioText, senhaText, niveisText;
    
    int quadro_x = 440, quadro_y = 120, pixel = 11, comp = 420, comp2 = 508, larg = 6, x = 20;
    String jogadorNome[] = new String[3];
    String jogadorID[] = new String[3];
    String jogadorDificuldade[] = new String[3];
    
    byte num_jogadores = 0;
    
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
        
        telaCadastro = new JPanel();
        telaCadastro.setBorder(BorderFactory.createMatteBorder(5,5,5,5,Color.black));
        telaCadastro.setBackground(new Color(168,138,110));
        telaCadastro.setBounds(100,100,400,300);
        telaCadastro.setLayout(null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object buttonPressed = e.getSource();        
        if (e.getSource() == newGame) {
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
            confirmarCadastro.setBounds(150,300,90,50);
            
            telaCadastro.add(usuarioField);
            telaCadastro.add(senhaField);
            telaCadastro.add(usuarioText);
            telaCadastro.add(senhaText);
            telaCadastro.add(confirmarCadastro);
            telaPrincipal.add(telaCadastro);
            
                jogadorNome[num_jogadores] = "BOB";
              jogadorID[num_jogadores] = "ID";
              for (int i = 1; i <= 4; i++) {
                  jogadorID[num_jogadores] += (Math.random()*10);
              } 
              num_jogadores++;
            
            if (e.getSource() == confirmarCadastro) {
                telaCadastro.removeAll();
                frame.repaint();
                easyButton = new JButton("EASY");
                normalButton = new JButton("NORMAL");
                hardButton = new JButton("HARD");
                easyButton.addActionListener(this);
                normalButton.addActionListener(this);
                hardButton.addActionListener(this);
                easyButton.setBackground(Color.green);
                normalButton.setBackground(Color.yellow);
                hardButton.setBackground(Color.red);
                easyButton.setBounds(150,150,250,60);
                normalButton.setBounds(150,220,250,60);
                hardButton.setBounds(150,290,250,60);
                
                telaCadastro.add(easyButton);
                telaCadastro.add(normalButton);
                telaCadastro.add(hardButton);
                //telaPrincipal.add(telaCadastro);
                if (buttonPressed == easyButton)
                    jogadorDificuldade[num_jogadores] = "easy";
                else
                  if (buttonPressed == normalButton)
                      jogadorDificuldade[num_jogadores] = "normal";
                  else
                    if (buttonPressed == hardButton)
                        jogadorDificuldade[num_jogadores] = "hard";
            }
        }
        else
          if (buttonPressed == loadGame) {
              frame.remove(newGame);
              frame.remove(loadGame);
              frame.add(miniBorder);
              jogador1Button = new JButton(jogadorNome[0]);
              jogador2Button = new JButton(jogadorNome[1]);
              jogador3Button = new JButton(jogadorNome[2]);
              jogador1Button.setBounds(quadro_x+130,quadro_y+150,250,60);
              jogador2Button.setBounds(quadro_x+130,quadro_y+200,250,60);
              jogador3Button.setBounds(quadro_x+130,quadro_y+250,250,60);
              jogador1Button.addActionListener(this);
              jogador2Button.addActionListener(this);
              jogador3Button.addActionListener(this);
          }
          else
           if ((buttonPressed == jogador1Button && !jogadorNome[0].equals("")) || (buttonPressed == jogador2Button && !jogadorNome[1].equals("")) || (buttonPressed == jogador3Button && !jogadorNome[2].equals(""))) {
               niveisButton = new JButton("Niveis");
               continuoButton = new JButton("Modo Continuo");
               multiplayerButton = new JButton("Multiplayer");
               niveisButton.addActionListener(this);
               continuoButton.addActionListener(this);
               multiplayerButton.addActionListener(this);
           }
           else
             if (buttonPressed == niveisButton) {
                 niveisText = new JLabel("Niveis");
                 frame.remove(miniBorder);
                 
             }
             else
               if (buttonPressed == continuoButton) {
                   frame.remove(miniBorder);
                   frame.remove(niveisButton);
                   frame.remove(continuoButton);
                   frame.remove(multiplayerButton);
                   frame.dispose();
               }
                   
          
    }
    public static void main(String[] args) {
        new Menu();
    }
}