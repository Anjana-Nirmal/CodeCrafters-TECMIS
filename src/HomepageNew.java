import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomepageNew  extends  JFrame{
    private JPanel main;
    private JPanel main2;
    private JPanel main3;
    private JButton profileButton;
    private JButton manageCourseButton;
    private JButton uploadMarksButton;
    private JButton checkEligibilityButton;
    private JButton studentDetailsButton;
    private JButton viewAttandaceButton;
    private JButton viewNoticeButton;
    private JButton grade_GPAButton;
    private JLabel profile;
    private JLabel course;
    private JLabel uploadMarks;
    private JLabel Eligibility;
    private JLabel studentsDetails;
    private JLabel attendance;
    private JLabel notice;
    private JLabel grade;
    public static String Username;
     public HomepageNew(String Username)
     {
         this.Username=Username;

         setTitle("Lecturer Home");
         setSize(1000,500);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
         profile.setBorder(new LineBorder(Color.BLACK, 3));
         course.setBorder(new LineBorder(Color.BLACK, 3));
         uploadMarks.setBorder(new LineBorder(Color.BLACK, 3));
         Eligibility.setBorder(new LineBorder(Color.BLACK, 3));
         studentsDetails.setBorder(new LineBorder(Color.BLACK, 3));
         attendance.setBorder(new LineBorder(Color.BLACK, 3));
         notice.setBorder(new LineBorder(Color.BLACK, 3));
         grade.setBorder(new LineBorder(Color.BLACK, 3));
         setContentPane(main);

         setVisible(true);
         profileButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 UpdateProfile updateProfile=new UpdateProfile(Username);
                 updateProfile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 updateProfile.setVisible(true);
             }
         });
         manageCourseButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Coursemanage coursemanage=new Coursemanage();
                 coursemanage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 coursemanage.setVisible(true);
             }
         });
         uploadMarksButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 UploadMarks uploadMarks=new UploadMarks();
                 uploadMarks.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 uploadMarks.setVisible(true);
             }
         });
         checkEligibilityButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 CAEligibilty caEligibilty=new CAEligibilty();
                 caEligibilty.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 caEligibilty.setVisible(true);
             }
         });

         viewAttandaceButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Attendance attendance=new Attendance();
                 attendance.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 attendance.setVisible(true);
             }
         });
       viewNoticeButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 Notice v=new Notice();
                 v.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 v.setVisible(true);
             }
         });
         grade_GPAButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 FinalGrade finalMarksGrade=new FinalGrade();
                 finalMarksGrade.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                 finalMarksGrade.setVisible(true);
             }
         });
         studentDetailsButton.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 StudentsDetailsNew studentsDetails1=new StudentsDetailsNew();
                 studentsDetails1.setVisible(true);
             }
         });
     }

    public static void main(String[] args) {
        new HomepageNew(Username);
    }
}
