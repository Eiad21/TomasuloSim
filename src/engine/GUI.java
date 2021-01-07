package engine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GUI extends JFrame {

  private JButton btnTutup  = new JButton("Tutup");
  private JButton btnTambah = new JButton("Tambah");
  public Engine engine;
  private JTextField txtA = new JTextField();
  private JTextField txtB = new JTextField();
  private JTextField txtC = new JTextField();

  private JLabel lblA = new JLabel("A :");
  private JLabel lblB = new JLabel("B :");
  private JLabel lblC = new JLabel("C :");
  
  
  public static void main(String[] args){
	  GUI f = new GUI(new Engine());
	    f.setVisible(true);
	  }
  public GUI(Engine engine){
	  this.engine = engine;
    setTitle("Tomasulo");
    setSize(1400,800);
    setLocation(new Point(300,200));
    
    GridLayout gl = new GridLayout(0,2);
    setLayout(gl);
    // START LEFT SIDE
    add(new JButton("Button 2"));
    // END LEFT SIDE
    
    // START RIGHT SIDE
    
    JPanel rightSide = new JPanel();
    add(rightSide);
    rightSide.setLayout(new GridLayout(2,0));
    
    JButton topRight = new JButton();
    JPanel bottomRight = new JPanel(new GridLayout(2,0));
    rightSide.add(topRight);
    rightSide.add(bottomRight);
    
    setUpBottomRight(bottomRight);
    
    
    // END LEFT SIDE
    
    setResizable(false);

    initComponent();    
    initEvent();    
  }

  private void setUpBottomRight(JPanel bottomRight) {
	  setUpMuls(bottomRight);
	  setUpAdds(bottomRight);
  }
  private void setUpMuls(JPanel bottomRight) {
	  String[][] data = engine.getMuls();
	  for(int i = 0;i<data.length;i++) {
		  for(int j = 0;j<data[0].length;j++)
			  System.out.print(data[i][j]+" ");
		  System.out.println();
	  }
	  String columns[] = {"Busy", "OP", "Vj", "Vk", "Qj", "Qk", "Rem-Time"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  bottomRight.add(sp);
  }
  private void setUpAdds(JPanel bottomRight) {
	  String[][] data = engine.getAdds();
	  for(int i = 0;i<data.length;i++) {
		  for(int j = 0;j<data[0].length;j++)
			  System.out.print(data[i][j]+" ");
		  System.out.println();
	  }
	  String columns[] = {"Busy", "OP", "Vj", "Vk", "Qj", "Qk", "Rem-Time"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  bottomRight.add(sp);
  }
  
  private void initComponent(){
    btnTutup.setBounds(300,130, 80,25);
    btnTambah.setBounds(300,100, 80,25);

    txtA.setBounds(100,10,100,20);
    txtB.setBounds(100,35,100,20);
    txtC.setBounds(100,65,100,20);

    lblA.setBounds(20,10,100,20);
    lblB.setBounds(20,35,100,20);
    lblC.setBounds(20,65,100,20);
    String x= "abdo "  ;
    for(int i=0;i<4;i++) {
    	JLabel lbool = new JLabel(x);
    	lbool.setBounds(20+(i*30),10,100,20);
        

    }
    x="eydo";
//    add(btnTutup);
//    add(btnTambah);
//
//    add(lblA);
//    add(lblB);
//    add(lblC);
//
//    add(txtA);
//    add(txtB);
//    add(txtC);
  }

  private void initEvent(){

    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e){
       System.exit(1);
      }
    });

    btnTutup.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnTutupClick(e);
      }
    });

    btnTambah.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        btnTambahClick(e);
      }
    });
  }
  
  private void btnTutupClick(ActionEvent evt){// next cycle
    System.exit(0);
  }
  
  private void btnTambahClick(ActionEvent evt){
    Integer x,y,z;
    try{
      x = Integer.parseInt(txtA.getText());
      y = Integer.parseInt(txtB.getText());
      z = x + y;
      txtC.setText(z.toString());

    }catch(Exception e){
      System.out.println(e);
      JOptionPane.showMessageDialog(null, 
          e.toString(),
          "Error", 
          JOptionPane.ERROR_MESSAGE);
    }
  }
}