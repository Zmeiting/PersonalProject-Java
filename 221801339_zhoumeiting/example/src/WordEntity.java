package WordCount;

public class WordEntity implements Comparable<WordEntity>{
	private String key;
    private Integer count;
    public WordEntity (String key, Integer count) {
        this.key = key;
        this.count = count;
    }
    public int compareTo(WordEntity o) {
        int cmp = count.intValue() - o.count.intValue();
        return (cmp == 0 ? key.compareTo(o.key) : -cmp);
        //ֻ���������һ�����žͿ��Ծ����������ǽ�������  -cmp�������У�cmp��������
        //��ΪTreeSet�����WorkForMap��compareTo�����������Լ�������
    }
    public void setKey(String s) { //����ת����Сд
    	key=s;
    }
    public String getKey() {
        return key;
    }
 
   public Integer getCount() {
        return count;
    }
}