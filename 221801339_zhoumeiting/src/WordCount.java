
import java.io.File;
import java.util.*;
public class WordCount {
	public static void main(String[] args) {
		String file="";
	    Scanner in=new Scanner(System.in); 
		file=in.nextLine();
		String [] total = file.split("[\\s+]");
		String inputFile=total[0].toString();
		String outputFile=total[1].toString();
		File input=new File(inputFile);
		if(input.exists()) //确保输入文件存在
		{
			//Test test=new Test();
		    //test.reduceText(inputFile); 测试用代码
		    //Set<String> file_paths = new HashSet<String>();
		    //file_paths.add(file);
	        //String file_path="E:\\output.txt";  //因为是多个文件输入，一个文件输出，暂时不知道怎么同时输入文件路径
		    //Set<String> file_paths = new HashSet<String>();
		    String characters=new CountUtil().ReturnCharactersNum(inputFile);
		    String words=new CountUtil().ReturnWordsNum(inputFile);
		    String lines=new CountUtil().ReturnLinesNum(inputFile);
		    String maxWords=new CountUtil().ReturnMaxWordsNum(inputFile);
            String context=characters+words+lines+maxWords;
		    if(new CountUtil().OutputFile(outputFile, context))
		    {
			    System.out.println("File output success !");
		    }
		    else
		    {
				System.out.println("error !");
		    }
		    }
	    in.close();
	}
}
