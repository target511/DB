// KwangWoon University
// Computer Software
// 2014726003
// 오철수
// DB HW2

import java.net.*;
import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
 
public class Client {
       public static void main(String[] args){
             try{
                   
                    // 1. 서버의 IP와 서버의 동작 포트 값(10001)을 인자로 넣어 socket 생성
                    Socket sock = new Socket("127.0.0.1", 10001);
                    BufferedReader keyboard =
                           new BufferedReader(new InputStreamReader(System.in));
                   
                    // 2. 생성된 Socket으로부터 InputStream과 OutputStream을 구함
                    OutputStream out = sock.getOutputStream();
                    InputStream in = sock.getInputStream();
                   
                    // 3. InputStream은 BufferedReader 형식으로 변환
                    //    OutputStream은 PrintWriter 형식으로 변환
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
                   
                    // 4. 키보드로부터 한 줄씩 입력받는 BufferedReader 객체 생성
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                   
                    String line = null;
                   
                    // 5. 키보드로부터 한 줄을 입력받음
                    System.out.println("Input Query : ");
                    while((line = keyboard.readLine()) != null){
                           if(line.equals("quit")) break;
                          
                           // 6. PrintWriter에 있는 println() 메소드를 이용해 서버에게 전송
                           pw.println(line);
                           pw.flush();
                          
                           // 7. 서버가 다시 반환하는 문자열을 BufferedReader에 있는
                           //    readLine()을 이용해서 읽어들임
                           String echo;
                           while (!(echo = br.readLine()).equals("EOF")) {
                        	   System.out.println(echo);
                           }
                           
                           System.out.println("---------------------------------------------------------------");
                           System.out.println("Input Query : ");
                    }
                   
                    pw.close();
                    br.close();
                    sock.close();
             }catch(Exception e){
                    System.out.println(e);
             }
       }
}

// select E.Fname from employee E, project P, works_on W where (W.Hours>=10.0) and P.Pname='ProductX' and P.Dnum='5'and Pnumber=Pno and W.Essn=E.Ssn;
// select E.Fname from employee E, dependent D where E.Fname=D.Dependent_name;
// select DISTINCT E.Fname from employee E, employee S, department D where S.Fname='Franklin' and S.Ssn= E.Super_ssn;
