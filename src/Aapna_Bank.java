import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Map;
public class Aapna_Bank extends JFrame {
    // CONNECTION
    private Connection con;
    private PreparedStatement ps;
    private JFrame frame;
    private JLabel heading_label;
    private JLabel sub_heading;
    private JButton exit;

    // FOR NEW ACCOUNT LABEL AND TEXTAREA
    private JLabel new_user_heading;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;

    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;

    private JButton Submit;

    // FOR WITHDRAW AND DEPOSIT OPTION
    private JLabel acc_label;
    private JLabel money_label;
    private JLabel insufficent;
    private JTextArea acc_text;
    private JTextArea money_text;

    private JButton withdraw;
    private JButton deposit;

    // PANEl
    private JPanel heading_panel;
    private JPanel new_user_panel;
    private JPanel with_depos;
    private JTabbedPane tabbedPane;
    void init_component()
    {
        // FRAME SETITNG
        frame=new JFrame("AAPNA BANK");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setVisible(true);

        // SETTING PANEL
        heading_panel=new JPanel();
        heading_panel.setLayout(null);
        new_user_panel=new JPanel();
        new_user_panel.setLayout(null);
        with_depos=new JPanel();
        with_depos.setLayout(null);

        // SETTING HEADING PANEL

        heading_label=new JLabel("WELCOME TO AAPNA BANK ");
        heading_label.setBounds(50,50,300,100);
        heading_panel.add(heading_label);
        heading_label.setFont(new Font("Arial",Font.BOLD,20));
        heading_label.setForeground(Color.PINK);

        sub_heading=new JLabel("Aapna Saath Aapna Viswas");
        sub_heading.setBounds(70,100,300,100);
        heading_panel.add(sub_heading);

        exit=new JButton("EXIT");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selction=JOptionPane.showConfirmDialog(frame,"DO YOU WANT TO EXIT","CONFIRM MESSAGE",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(selction==JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
        exit.setBounds(100,200,100,20);
        heading_panel.add(exit);
        // FOR NEW ACCOUNT SETUP

        new_user_heading=new JLabel("NEW USER ACCOUNT");
        label1=new JLabel("NAME:");
        label2=new JLabel("ADDRESS:");
        label3=new JLabel("Contact:");
        label4=new JLabel("Occupation");
        new_user_heading.setBounds(50,20,300,20);
        new_user_heading.setFont(new Font("Arial",Font.BOLD,20));
        label1.setBounds(20,70,100,20);
        label2.setBounds(20,120,100,20);
        label3.setBounds(20,170,150,20);
        label4.setBounds(20,220,100,20);

        textArea1=new JTextArea();
        textArea2=new JTextArea();
        textArea3=new JTextArea();
        textArea4=new JTextArea();

        textArea1.setBounds(200,70,100,20);
        textArea2.setBounds(200,120,100,20);
        textArea3.setBounds(200,170,100,20);
        textArea4.setBounds(200,220,100,20);

        Submit=new JButton("SUBMIT");
        Submit.setBounds(100,300,100,20);

        //  BUTTON ACTION LISTNER
        Submit.addActionListener(new New_Account());
        new_user_panel.add(new_user_heading);
        new_user_panel.add(label1);
        new_user_panel.add(label2);
        new_user_panel.add(label3);
        new_user_panel.add(label4);
        new_user_panel.add(textArea1);
        new_user_panel.add(textArea2);
        new_user_panel.add(textArea3);
        new_user_panel.add(textArea4);
        new_user_panel.add(Submit);

        // WITHHDRAW AND DEPOST SETTING FRAME
        acc_label=new JLabel("ENTER ACCOUNT NUMBER");
        money_label=new JLabel("ENTER MONEY");
        insufficent=new JLabel("INSUFFICENT BALANACE");
        acc_text=new JTextArea();
        money_text=new JTextArea();
        withdraw=new JButton("WITHDRAW");
        deposit=new JButton("DEPOSIT");

        acc_label.setBounds(20,50,200,20);
        money_label.setBounds(20,100,200,20);
        insufficent.setBounds(50,150,200,20);
        acc_text.setBounds(200,50,100,20);
        money_text.setBounds(200,100,100,20);
        withdraw.setBounds(50,200,100,20);
        deposit.setBounds(200,200,100,20);
        with_depos.add(acc_label);
        with_depos.add(acc_text);
        with_depos.add(money_label);
        with_depos.add(money_text);
        with_depos.add(withdraw);
        with_depos.add(deposit);
        with_depos.add(insufficent);
        insufficent.setVisible(false);

        // BUTTON ACTION LISTNER
        withdraw.addActionListener(new Withdraw());
        deposit.addActionListener(new Deposit());
        // ADDING TABED ON
        tabbedPane=new JTabbedPane();
        tabbedPane.add(heading_panel);
        tabbedPane.add(new_user_panel);
        tabbedPane.add(with_depos);
        // FRAME SETTING
        frame.add(tabbedPane);
    }
    Aapna_Bank()
    {
        Connection_for obj=new Connection_for();
        init_component();
    }

    // Action listner clas
    class New_Account implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name=textArea1.getText();
            String address=textArea2.getText();
            String contact=textArea3.getText();
            String occupation=textArea4.getText();

            String q="insert into bank(name,address,contact,occupation)values(?,?,?,?);";
            try{
                ps=con.prepareStatement(q);
                ps.setString(1,name);
                ps.setString(2,address);
                ps.setString(3,contact);
                ps.setString(4,occupation);
                ps.executeUpdate();
                System.out.println("DONE");
                textArea1.setText("");
                textArea2.setText("");
                textArea3.setText("");
                textArea4.setText("");
            }
            catch (SQLException m){
                m.getMessage();
            }
            //ps=con.prepareStatement();
        }
    }
    class Deposit implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int cur_money=0;
            int acc=Integer.parseInt(acc_text.getText());
            int money=Integer.parseInt(money_text.getText());
            try{
                String up="select balanace from bank where acc=?;";
                String q="update bank SET balanace=? where acc=?;";
                ps=con.prepareStatement(up);
                ps.setInt(1,acc);
                ResultSet rs=ps.executeQuery();
                while (rs.next()){
                    cur_money=rs.getInt(1);
                }
                money=money+cur_money;
                ps=con.prepareStatement(q);
                ps.setInt(1,money);
                ps.setInt(2,acc);
                ps.executeUpdate();
                acc_text.setText("");
                money_text.setText("");
                System.out.println("SUCCESS");
            }
            catch (SQLException Q1){
                Q1.getMessage();
            }
        }
    }
    class Withdraw implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int acc=Integer.parseInt(acc_text.getText());
            int money=Integer.parseInt(money_text.getText());
            int curr_money=0;
            String q="select balanace from bank where acc=?";
            try {
                ps = con.prepareStatement(q);
                ps.setInt(1, acc);
                ResultSet rs=ps.executeQuery();
                while (rs.next()){
                   curr_money=rs.getInt(1);
                }
                if(curr_money<money){
                    insufficent.setVisible(true);
                    insufficent.setText("INSUFFICENT BALANACE");
                    System.out.println("INSUFFICENT BALANACE");
                }
                else
                {
                    insufficent.setVisible(true);
                    insufficent.setText("SUCCESS");
                    money=curr_money-money;
                    String q1="update bank SET balanace=? where acc=?;";
                    ps=con.prepareStatement(q1);
                    ps.setInt(1,money);
                    ps.setInt(2,acc);
                    ps.executeUpdate();
                    acc_text.setText("");
                    money_text.setText("");
                }
            }
            catch (SQLException qm){
                qm.getMessage();
            }
        }
    }
    // CONNECTON CLASSS
    class Connection_for{
        Connection_for()
        {
           // System.out.println("RUN1");
            String url="jdbc:mysql://localhost:3306/aapnabank";
            try {
             //   System.out.println("RUN2");
                con = DriverManager.getConnection(url, "root", "ayush2312");
               // System.out.println("RUN3");
            }
            catch (SQLException e){
                e.getMessage();
            }
            //System.out.println("RUN4");
            if(con!=null){
                System.out.println("COONECTION HO GAYA");
            }
        }
    }
}
