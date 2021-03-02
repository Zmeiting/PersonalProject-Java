import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class CountUtil 
{
	/**
	 * 返回字符数
	 * @param file_paths
	 * @return
	 */
	public String ReturnCharactersNum(String file_path) {
        int count = 0,bytes = 0;
        String result = "";//用于存储返回值
        byte [] item = new byte[20*1024];//用存储读取数据的定常字节数组
        int len = item.length;//得到item的长度以避免循环时反复调用.length
        FileInputStream in = null;//声明一个文件输入流
        try {
            //for (String file_path : file_paths) {
                 in = new FileInputStream(file_path);//得到字符输入流，string为文件绝对路径         
                while ((bytes = in.read(item,0,len))!=-1) {
                		count+=bytes;//统计累计读取的字符数,一个英文字符占一个字节
                }
                result += "characters: "+count+"\n";//结果字符串拼接
                count = 0;  
            //}    
        } catch (FileNotFoundException e) {
            System.out.println("有文件输入错误，请核对！（如果不会使用相对路径，请使用绝对路径）"); //检查到文件不存在，提示错误
            System.exit(0); //结束程序
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();//关闭输入流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
	/**
	 * 返回单词数
	 * @param file_paths
	 * @return
	 */
	public String ReturnWordsNum(String file_path){
		int count=0;
		String result="";
		StringBuffer saveString=new StringBuffer();
		String tmp="";
		FileInputStream in=null;//文件字符输入流
		InputStreamReader isr=null;//字节输入流
		BufferedReader bis=null;//缓存输入流
		try {
			//for(String file_path:file_paths)
			{
				in=new FileInputStream(file_path);
				isr=new InputStreamReader(in);
				bis=new BufferedReader(isr);
				while((tmp=bis.readLine())!=null)//readLine返回读取字节，没有了就返回null
				{
					saveString.append(tmp);//将新读出来的数据保存到已有的后面
					saveString.append(" ");//以便分割再加一个空格，读一行加一个
				}
				//saveString.toString().replaceAll("[^A-Za-z0-9]"," ");
				tmp = saveString.toString(); //用字符串存储，以用split方法区分单词
				//tmp.replaceAll("[\\W]|_", " ");//所有非字母数字符号替换成空格
				tmp=tmp.replaceAll("[^A-Za-z0-9]", " ");//替换所有非字母数字的符号为空格
				tmp=tmp.toLowerCase();//全部变为小写方便计算前四个是否为字母
				String [] total = tmp.split("[\\s+]");//单词以空格分割
				count=total.length;
			    for(int i=0;i<total.length;i++)
			    {
			    	String s=total[i].toString();
			    	if(s.length()<4)
			    	{
			    		count--;
			    	}
			    	else
			    	{
			    		for(int j=0;j<4;j++)
			    		{
			    			char c=s.charAt(j);
			    			if(!(c>='a'&&c<='z'))//只要是字母都已经换成小写了
			    			{
			    				count--;
			    				break;
			    			}
			    		}
			    	}
			    }
				result += "words: "+count+"\n"; //结果字符串拼接
				count=0;
			}
		}catch (FileNotFoundException e) {
				System.out.println("有文件输入错误，请核对！（如果不会使用相对路径，请使用绝对路径）");
				System.exit(0); //结束程序
	    }catch (IOException e) {
	    	e.printStackTrace();
		}finally {
			try {
				in.close();//关闭文件字符输入流
				isr.close();//关闭字节输入流
				bis.close();//关闭缓存输入流
				} catch (IOException e) {
					 e.printStackTrace();
				}
		}
		return result;
    }
	/**
	 * 返回行数
	 * @param file_paths
	 * @return
	 */
	public String ReturnLinesNum(String file_path){
		int count=0;
		String result="";
		FileInputStream in=null;//文件字符输入流
		InputStreamReader isr=null;//字节输入流
		BufferedReader bis=null;//缓存输入流
		try {
			//for(String file_path:file_paths)
			{
				in=new FileInputStream(file_path);
				isr=new InputStreamReader(in);
				bis=new BufferedReader(isr);
				while(bis.readLine()!=null)
				{
					count++;
				}
				result += "lines: "+count+"\n"; //结果字符串拼接
				count=0;
			}
		}catch (FileNotFoundException e) {
				System.out.println("有文件输入错误，请核对！（如果不会使用相对路径，请使用绝对路径）");
				System.exit(0); //结束程序
	    }catch (IOException e) {
	    	e.printStackTrace();
		}finally {
			try {
				in.close();//关闭文件字符输入流
				isr.close();//关闭字节输入流
				bis.close();//关闭缓存输入流
				} catch (IOException e) {
					 e.printStackTrace();
				}
		}
		return result;
	}
	public String ReturnMaxWordsNum(String file_path){
		String result="";
		StringBuffer saveString=new StringBuffer();
		String tmp="";
		FileInputStream in=null;//文件字符输入流
		InputStreamReader isr=null;//字节输入流
		BufferedReader bis=null;//缓存输入流
		try {
			//for(String file_path:file_paths)
			{
				in=new FileInputStream(file_path);
				isr=new InputStreamReader(in);
				bis=new BufferedReader(isr);
				while((tmp=bis.readLine())!=null)//readLine返回读取字节，没有了就返回null
				{
					saveString.append(tmp);//将新读出来的数据保存到已有的后面
					saveString.append(" ");//方便区分单词，换一行读取加一个空格
				}
				Map<String,Integer>map = new HashMap<String, Integer>();//运用哈希排序的方法进行排序
				tmp = saveString.toString(); //用字符串存储，以用split方法区分单词
				/*for(int i=0;i<tmp.length();i++)//替换中文
				{
					char c = tmp.charAt(i);
					int v = (int)c;
					if(v>=19968 && v <= 171941){
						tmp.replace(new String(), " ");
					}
				}*/
				tmp=tmp.replaceAll("[^A-Za-z0-9]", " ");//所有非字母数字符号替换成空格
				tmp=tmp.toLowerCase();//全部换成小写
	            StringTokenizer st = new StringTokenizer(tmp," ");//分割字符串
	            //把分割好的单词保存在letter字符串中
	            while (st.hasMoreTokens()) 
	            {
	                 String letter = st.nextToken();
	                 int count;
	                 if (map.get(letter) == null) {
	                     count = 1;//表明了没有进行分割。
	                 } else {
	                     count = map.get(letter).intValue() + 1;
	                 }
	                 map.put(letter,count);
	            }
	            Set<WordEntity> set = new TreeSet<WordEntity>();
	            for (String key : map.keySet()) {
	                 set.add(new WordEntity(key,map.get(key)));
	             }
	            int count=1;
	            for (Iterator<WordEntity> it = set.iterator(); it.hasNext();) {
	                 WordEntity w = it.next();
	                 boolean isWords=true;
	                 if(w.getKey().length()>4)
	                 {
	                	 String s=w.getKey();
	                	 for(int i=0;i<4;i++)
	                	 {
	                		 char c=s.charAt(i);
  			    			 if(!(c>='a'&&c<='z'))//只要是字母都已经换成小写了
  			    			 {
  			    				isWords=false;
  			    				break;
  			    			 }
	                	 }
	                	 if(isWords==true)
	                	 {
	                	     result+=w.getKey()+": "+w.getCount()+"\n";
	                	     if(count==10)
	                	     {
	                		    break;
	                	     }   
	                         count++;
	                	 }
	                 }
	            }
			}
		}catch (FileNotFoundException e) {
				System.out.println("有文件输入错误，请核对！（如果不会使用相对路径，请使用绝对路径）");
				System.exit(0); //结束程序
	    }catch (IOException e) {
	    	e.printStackTrace();
		}finally {
			try {
				in.close();//关闭文件字符输入流
				isr.close();//关闭字节输入流
				bis.close();//关闭缓存输入流
				} catch (IOException e) {
					 e.printStackTrace();
				}
		}
		return result;
	}
	public boolean OutputFile(String File_path,String Context){
		File OutputFile = new File(File_path); //创建File对象
		FileOutputStream os = null; //声明 文件输出流
		byte [] a = null; //用于存储Context转化的byte字节数组
		try {
			if(!OutputFile.exists()) {        //判断文件是否存在
				OutputFile.createNewFile(); //不存在，创建一个文件
			}
			FileWriter fileWriter =new FileWriter(File_path);//设置清空文件内容功能
			fileWriter.write("");
		    fileWriter.flush();
		    fileWriter.close();
			os = new FileOutputStream(OutputFile); //获得输出流对象
			a = Context.getBytes(); //将Context转化为Byte数组，以便写入文件
			os.write(a); //将byte数组写入文件
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close(); //关闭输出流
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}