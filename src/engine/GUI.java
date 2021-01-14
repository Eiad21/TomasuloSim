package engine;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GUI extends JFrame {

  public Engine engine;
  JButton nextCycleBtn;
  
  JPanel topRight;
  JPanel bottomRight;
  
  public static void main(String[] args){
	  GUI f = new GUI(new Engine());
	    f.setVisible(true);
	  }
  public GUI(Engine engine){
	  this.engine = engine;
    setTitle("Tomasulo");
    setSize(1400,800);
    setLocation(new Point(300,200));
    
    GridLayout glMain = new GridLayout(0,2);
    JPanel mainContent = new JPanel(glMain);
    
    BorderLayout bl = new BorderLayout();
    setLayout(bl);
    
    nextCycleBtn = new JButton("Next Cycle");
    nextCycleBtn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			engine.runCycle();
			bottomRight.removeAll();
			topRight.removeAll();
			setUpBottomRight();
			setUpTopRight();
			refresh();
		}
	});
    // START LEFT SIDE
<<<<<<< HEAD
   // add(new JButton("Button 2"));
    
    JPanel leftSide = new JPanel();
    add(leftSide);
    leftSide.setLayout(new GridLayout(2,0));
    
    JButton topLeft = new JButton();
    JPanel bottomLeft = new JPanel(new GridLayout(2,0));
    leftSide.add(topLeft);
    leftSide.add(bottomLeft);
    
    setUpBottomRight(bottomLeft);
    
=======
    mainContent.add(new JButton("Button 2"));
>>>>>>> fb8a260ed30bfb8ae4606e3279d17e7f637a1a1c
    // END LEFT SIDE
    
    // START RIGHT SIDE
    
    JPanel rightSide = new JPanel();
    mainContent.add(rightSide);
    rightSide.setLayout(new GridLayout(2,0));
    
    topRight = new JPanel(new GridLayout(0,2));
    bottomRight = new JPanel(new GridLayout(2,0));
    rightSide.add(topRight);
    rightSide.add(bottomRight);
    
<<<<<<< HEAD
    setUpBottomRight(bottomRight);
    setUpBottomLeft(bottomLeft);

=======
    setUpBottomRight();
    setUpTopRight();
>>>>>>> fb8a260ed30bfb8ae4606e3279d17e7f637a1a1c
    
    // END LEFT SIDE
    
    add(mainContent, BorderLayout.CENTER);
    add(nextCycleBtn, BorderLayout.SOUTH);
    
    setResizable(false);

    initEvent();    
  }

  public void refresh() {
	  this.invalidate();
	  this.validate();
	  this.repaint();
	  // TODO: get updated values to be displayed
  }
<<<<<<< HEAD
  
  private void setUpBottomLeft(JPanel bottomLeft) {
	  setUpStores(bottomLeft);
	  setUpLoads(bottomLeft);
  }
  private void setUpMuls(JPanel bottomRight) {
	  String[][] data = engine.getMuls();
	  for(int i = 0;i<data.length;i++) {
		  for(int j = 0;j<data[0].length;j++)
			  System.out.print(data[i][j]+" ");
		  System.out.println();
	  }
	  
=======
  private void setUpBottomRight() {
	  setUpMuls();
	  setUpAdds();
  }
  private void setUpTopRight() {
	  setUpRegfile();
	  setUpMem();
  }
  private void setUpMuls() {
	  String[][] data = engine.getMuls();
>>>>>>> fb8a260ed30bfb8ae4606e3279d17e7f637a1a1c
	  String columns[] = {"Busy", "OP", "Vj", "Vk", "Qj", "Qk", "Rem-Time"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  bottomRight.add(sp);
  }

  private void setUpAdds() {
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
  private void setUpStores(JPanel bottomRight) {
	  String[][] data = engine.getStores();
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
  
<<<<<<< HEAD
  private void setUpLoads(JPanel bottomRight) {
	  String[][] data = engine.getLoads();
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
=======
  private void setUpRegfile() {
	  String[][] data = engine.getRegFile();
	  String columns[] = {"Value", "QI"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  topRight.add(sp);
>>>>>>> fb8a260ed30bfb8ae4606e3279d17e7f637a1a1c
  }
  
  private void setUpMem() {
	  String[][] data = engine.getMem();
	  String columns[] = {"Value"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  topRight.add(sp);
  }
  
//732 782 11
  private void initEvent(){
    this.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e){
       System.exit(1);
      }
    });
  }
}