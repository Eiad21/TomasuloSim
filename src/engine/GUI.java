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
    mainContent.add(new JButton("Button 2"));
    // END LEFT SIDE
    
    // START RIGHT SIDE
    
    JPanel rightSide = new JPanel();
    mainContent.add(rightSide);
    rightSide.setLayout(new GridLayout(2,0));
    
    topRight = new JPanel(new GridLayout(0,2));
    bottomRight = new JPanel(new GridLayout(2,0));
    rightSide.add(topRight);
    rightSide.add(bottomRight);
    
    setUpBottomRight();
    setUpTopRight();
    
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
  
  private void setUpRegfile() {
	  String[][] data = engine.getRegFile();
	  String columns[] = {"Value", "QI"};
	  
	  JTable j = new JTable(data, columns);
	  JScrollPane sp = new JScrollPane(j);
	  topRight.add(sp);
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