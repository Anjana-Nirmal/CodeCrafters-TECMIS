import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class CAeligibilityClass {

    static String courseCode;
    static double q1;
    static double q2;
    static double q3;
    static double assessment;
    static double midTerm;
    static String undergraduateID;
    static String course;
    static double CAmarks;
    public CAeligibilityClass(String courseCode)
    {
            this.courseCode=courseCode;

            DatabaseConnection databaseConnection=new DatabaseConnection();
            Connection conn=DatabaseConnection.getConnection();
            String sql="SELECT undergraduate_id, course_code, " +
                    "MAX(CASE WHEN type = 'Quiz 1' THEN marks END) AS quiz1, " +
                    "MAX(CASE WHEN type = 'Quiz 2' THEN marks END) AS quiz2, " +
                    "MAX(CASE WHEN type = 'Quiz 3' THEN marks END) AS quiz3, " +
                    "MAX(CASE WHEN type = 'Assessment' THEN marks END) AS assessment, " +
                    "MAX(CASE WHEN type = 'Mid Term' THEN marks END) AS mid_term " +
                    "FROM marks " +
                    "WHERE course_code = ?  " +
                    "GROUP BY undergraduate_id, course_code";

        try{
            PreparedStatement pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,courseCode);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next())
            {    undergraduateID=rs.getString(1);
                 course=rs.getString(2);
                 q1=rs.getDouble(3);
                 q2=rs.getDouble(4);
                 q3=rs.getDouble(5);
                 assessment=rs.getDouble(6);
                 midTerm=rs.getDouble(7);

                 double[] quizes={q1,q2,q3};
                Arrays.sort(quizes);
                double SumOfQuizes=quizes[1]+quizes[2];
                double TotalFullMarks=0;
                if(assessment>0)
                {
                    SumOfQuizes=SumOfQuizes+assessment;
                    TotalFullMarks=TotalFullMarks+100;
                }
                if(midTerm>0)
                {
                    SumOfQuizes=SumOfQuizes+midTerm;
                    TotalFullMarks=TotalFullMarks+100;
                }
                TotalFullMarks=TotalFullMarks+200;
                double anwers=(SumOfQuizes/TotalFullMarks)*100;
                 CAmarks=(anwers*40)/100;
                 DatabaseConnection databaseConnection1=new DatabaseConnection();
                 Connection conn2=DatabaseConnection.getConnection();
                String sql2="INSERT INTO ca_eligibility_temp(undergraduate_id,course_code,ca_marks,eligibility) VALUES(?,?,?,?)";

                    PreparedStatement pstmt1=conn2.prepareStatement(sql2);
                    pstmt1.setString(1,undergraduateID);
                    pstmt1.setString(2,course);
                    pstmt1.setDouble(3,CAmarks);
                    pstmt1.setString(4,CAmarks>=20? "Eligible": "Not eligible");
                    pstmt1.executeUpdate();



            }


        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }


    }
}
