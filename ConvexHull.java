import java.util.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class ConvexHull //extends Application
{
    public static int sizer = 0;
    public static double[] xEntries;
    public static double[] yEntries;
    public static double[] hullPoints;

    public static void main(String[]args)throws Exception
    {
        int maxPoints = 70;
        double xVal [] = new double [maxPoints];
        double yVal [] = new double [maxPoints];


        int pointCount = loadPoints(maxPoints, xVal, yVal);
        for(int i = 0; i < xVal.length; i++)
        {
            System.out.println("(" + xVal[i] + ", " + yVal[i] + ")");
            if(xVal[i] <= 0)
            {
                break;
            }
        }
        System.out.println(pointCount);
        if ( checkDuplicates(pointCount, xVal, yVal) ) return;
        System.out.println("Convex Hull Points: \n");
        computeConvexHull(pointCount, xVal, yVal);
        //launch(args);
    }

    static int loadPoints(int maxPoints, double xVal [], double yVal[])
    {
        double valueX = 0, valueY = 0;
        Scanner input = new Scanner(System.in);
        int counter = 0, yValCounter = 0, numPoints;
        xEntries = new double[maxPoints];
        yEntries = new double[maxPoints];

        while((valueX >= 0)&&(valueY >= 0))
        {
            for(int i = 0; i < maxPoints; i++)
            {
                System.out.println("X value: ");
                valueX = input.nextDouble();
                if(valueX < 0)
                {
                    break;
                }
                else
                {
                    xVal[i] = valueX;
                    xEntries[i] = valueX;
                    counter++;
                }


                System.out.println("Y value: ");
                valueY = input.nextDouble();
                if(valueY < 0)
                {
                    break;
                }
                else
                {
                    yVal[i] = valueY;
                    yEntries[i] = valueY;
                    counter++;
                    yValCounter++;
                }

                if (counter == 2*maxPoints)
                {
                    System.out.println("The array has reached it's maximum capacity! ");
                    break;
                }
            }
            break;
        }
        return yValCounter;
    }

    static boolean checkDuplicates(int pointCount, double xVal [], double yVal [])
    {
        for (int i = pointCount - 1; i >= 1; i --)
        {
            for (int j = 0; j < i; j ++)
            {
                if ((xVal[i] == xVal[j])||(yVal[i] == yVal[j]))
                {
                    System.out.println("Duplicate has been detected!");
                    return true;
                }
            }
        }
        return false;
    }

    static void computeConvexHull(int pointCount, double xVal [], double yVal [])
    {
        double m, c;

        for(int i = pointCount - 1 ; i >= 0; i--)
        {
            for(int j = 0; j <= i; j++)
            {
                int above = 0, below = 0;
                double[] pi = {xVal[i], yVal[i]};
                double[] pj = {xVal[j], yVal[j]};

                m = (pi[1] - pj[1]) / (pi[0] - pj[0]);
                c = (pi[1] - m*pi[0]);

                if((m != Double.NEGATIVE_INFINITY)&&(m != Double.POSITIVE_INFINITY))
                {

                    for(int k = 0; k < pointCount; k++)
                    {
                        double[] pk = {xVal[k], yVal[k]};

                        if( pk[1] > (m*pk[0] + c))
                        {
                            above++;
                        }
                        if (pk[1] < (m*pk[0] + c))
                        {
                            below++;
                        }
                    }

                    if(((above > 0) && !(below > 0)) || (!(above > 0)&& (below > 0)))
                    {
                        System.out.println(Arrays.toString(pi) + ","+ Arrays.toString(pj));
                    }
                }
            }
        }
    }
    //Graphics

    @Override
    public void start(Stage primaryStage)throws Exception
    {
      for(int i = 0; i < xEntries.length; i++)
      {
        if((int)xEntries[i] > sizer)
        {
          sizer = (int)xEntries[i];
          if((int)yEntries[i] > sizer)
          {
            sizer = (int)yEntries[i];
          }
        }
      }

      primaryStage.setTitle("Convex Hull");

      NumberAxis x = new NumberAxis(0,1.2*sizer,5);
      x.setLabel("X value");

      NumberAxis y = new NumberAxis(0,1.2*sizer,5);
      y.setLabel("Y value");

      ScatterChart s = new ScatterChart(x,y);

      //Series configuration
      XYChart.Series data = new XYChart.Series();
      data.setName("Points");

      for(int l = 0; l < xEntries.length;l++)
      {
          data.getData().add(new XYChart.Data(xEntries[l],yEntries[l]));
      }


      s.getData().add(data);

      Group root = new Group();
      root.getChildren().add(s);
      Scene scene = new Scene(root,600,400);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Convex Hull");
      primaryStage.show();

    }
}
