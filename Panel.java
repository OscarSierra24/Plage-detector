package Ruleta3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Panel extends JPanel implements ActionListener{
	private BufferedReader br,
						   br2;
	private JButton btEnviar,
					btEnviar2;
	private JLabel jlInfo,
				   jlResultado,
				   jlArchivo1,
				   jlArchivo2;
	private JPanel panelUp,
		   	panelDown,
		   	panelUpLeft,
		   	panelUpRight;
	private int filesSelected,
				repeatedWords;
	private long totalWords;
	private JFileChooser fc;
	private Calculos calculos,
					 calculos2;
	private String result;
	public Panel(){
		super();
		this.filesSelected=0;
		setLayout(new BorderLayout());
		this.calculos=new Calculos();
		this.calculos2=new Calculos();
		this.fc=new JFileChooser();
		
		btEnviar=new JButton("Enviar");
		btEnviar2=new JButton("Enviar");
		btEnviar.addActionListener(this);
		btEnviar2.addActionListener(this);
		
		panelUp=new JPanel();
		panelDown=new JPanel();
		panelUpLeft=new JPanel();
		panelUpRight=new JPanel();
		panelUpRight.setBackground(Color.darkGray);
		panelUp.setLayout(new BorderLayout());
		panelUpLeft.setLayout(new BorderLayout());
//		panelUpRight.setLayout(new BorderLayout());
		
		jlInfo=new JLabel("<html>Ingrese los archivo <br> que desea investigar</html>",SwingConstants.CENTER);
		jlArchivo1=new JLabel("          Archivo 1:          ");
		jlArchivo2=new JLabel("          Archivo 2:          ");
		jlResultado=new JLabel("");
		jlArchivo1.setForeground(Color.LIGHT_GRAY);
		jlArchivo2.setForeground(Color.LIGHT_GRAY);
		jlResultado.setForeground(Color.white);
		jlArchivo1.setFont(new Font("Harlow Solid Italic", Font.ITALIC,40));
		jlArchivo2.setFont(new Font("Harlow Solid Italic", Font.ITALIC,40));
		jlInfo.setFont(new Font("Harlow Solid Italic", Font.ITALIC,40));
		jlResultado.setFont(new Font("Harlow Solid Italic", Font.ITALIC,40));
	
		panelUp.setPreferredSize(new Dimension(700, 350));
		panelDown.setPreferredSize(new Dimension(700,350));
		panelUpLeft.setPreferredSize(new Dimension(350, 350));
		panelUpRight.setPreferredSize(new Dimension(350, 350));
		
		panelUp.add(panelUpLeft, BorderLayout.WEST);
		panelUp.add(panelUpRight);
	
		add(panelUp,BorderLayout.NORTH);
		add(panelDown);
		
		panelUpLeft.add(jlInfo,BorderLayout.CENTER);

		panelUpRight.add(jlArchivo1);
		panelUpRight.add(btEnviar);
		panelUpRight.add(jlArchivo2);
		panelUpRight.add(btEnviar2);
		panelDown.add(jlResultado);
		
		panelUp.setBackground(Color.LIGHT_GRAY);
		panelDown.setBackground(Color.black);
		setPreferredSize(new Dimension(700, 700));
		setVisible(true);
	}
	private void showResult(){
		jlResultado.setText(this.result);
		jlResultado.repaint();
		jlResultado.revalidate();
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btEnviar){
			this.fc.showOpenDialog(this);
			if(this.fc.getSelectedFile()!=null){
				String rutaArchivo=this.fc.getSelectedFile().toString();				
				try{
					br=new BufferedReader(new FileReader(this.fc.getSelectedFile()));
					calculos.setBr(br);
					calculos.readAll();
					calculos.countWords();
					filesSelected++;
					if(filesSelected==2){
						calculos.obtainDictArray();
						calculos2.obtainDictArray();
						calculos.compare(calculos2);
						
						this.repeatedWords=calculos.getX();
						this.totalWords=calculos.getN();
						System.out.println("repeated words: "+this.repeatedWords);
						System.out.println("total words:"+ this.totalWords);
						float porcentaje =((float) this.repeatedWords/this.totalWords);
						porcentaje=porcentaje*100;
						result=("<html>el archivo uno tiene<br>el " + (porcentaje) + "%<br> de sus"
								+ " palabras<br> repetidas </html>");
						showResult();
						calculos.getUniversalHash().deleteAll();
						calculos2.getUniversalHash().deleteAll();
						calculos.setX(0);
						calculos2.setX(0);
						filesSelected=0;
					}
				}catch(IOException ioe){
					System.out.println("ocurrió un error");
				}
			}
		}
		if(e.getSource()==this.btEnviar2){
			this.fc.showOpenDialog(this);
			if(this.fc.getSelectedFile()!=null){				
				try{
					br2=new BufferedReader(new FileReader(this.fc.getSelectedFile()));
					calculos2.setBr(br2);
					calculos2.readAll();
					calculos2.countWords();
					filesSelected++;
					if(filesSelected==2){
						calculos.obtainDictArray();
						calculos2.obtainDictArray();
						calculos.compare(calculos2);
						
						this.repeatedWords=calculos.getX();
						this.totalWords=calculos.getN();
						System.out.println("repeated words: "+this.repeatedWords);
						System.out.println("total words:"+ this.totalWords);
						float porcentaje =((float) this.repeatedWords/this.totalWords);
						porcentaje=porcentaje*100;
						result=("<html>el archivo uno tiene<br>el " + (porcentaje) + "%<br> de sus"
								+ " palabras<br> repetidas </html>");
						showResult();
						calculos.getUniversalHash().deleteAll();
						calculos2.getUniversalHash().deleteAll();
						calculos.setX(0);
						calculos2.setX(0);
						filesSelected=0;
					}
				}catch(IOException ioe){
					System.out.println("ocurrió un error");
				}
			}
		}
	}
}
